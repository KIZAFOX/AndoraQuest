package fr.kizafox.andora.tools.database.requests.classes.spells.wizard.spells.fireball.event;

import fr.kizafox.andora.Andora;
import fr.kizafox.andora.tools.database.requests.classes.spells.Spell;
import fr.kizafox.andora.tools.database.requests.classes.spells.wizard.spells.fireball.FireballLevelOne;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Change this line to a short description of the class
 *
 * @author : KIZAFOX
 * @date : 20/09/2023
 * @project : Andora
 */
public class FireballListener implements Listener {

    protected final Andora instance;

    private final Map<UUID, Long> cooldowns = new HashMap<>();

    public FireballListener(final Andora instance) {
        this.instance = instance;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onClick(final PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        final ItemStack itemStack = player.getInventory().getItemInMainHand();

        if(itemStack.getItemMeta() == null) return;

        final Action action = event.getAction();

        final FireballLevelOne fireballLevelOne = this.instance.getManagers().getSpellsManager().getFireballLevelOne();

        if (itemStack.getItemMeta().getDisplayName().equals(fireballLevelOne.getItem().getItemMeta().getDisplayName())) {
            event.setCancelled(true);

            int cooldownTime = fireballLevelOne.getCooldown();

            if (cooldowns.containsKey(player.getUniqueId())) {
                long secondsLeft = ((cooldowns.get(player.getUniqueId()) / 1000) + cooldownTime) - (System.currentTimeMillis() / 1000);

                if (secondsLeft > 0) {
                    player.sendMessage(ChatColor.RED + "Vous devez attendre " + secondsLeft + ChatColor.RESET + ChatColor.RED + " secondes avant de pouvoir réutiliser ce sort.");
                    return;
                }
            }

            String combo = Spell.wandCombo.get(player.getUniqueId());

            if (combo == null || combo.length() < 4) {
                if (combo == null) {
                    combo = "";
                }
                if (action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK) {
                    combo += "L";
                } else if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
                    combo += "R";
                }
                Spell.wandCombo.put(player.getUniqueId(), combo);
            }

            if (combo.length() == 4) {
                Spell.wandCombo.remove(player.getUniqueId());
                int manaLeft = Spell.mana.get(player.getUniqueId());

                if (combo.equals("LLLL")) {
                    if (manaLeft < fireballLevelOne.getManaCost()) {
                        player.sendMessage(ChatColor.DARK_RED + "Vous n'avez pas la mana requise pour utiliser ce sort.");
                        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1f, 1f);
                        return;
                    }

                    Spell.mana.put(player.getUniqueId(), manaLeft - fireballLevelOne.getManaCost());

                    final Fireball fireball = player.launchProjectile(Fireball.class);
                    fireball.setIsIncendiary(false);
                    fireball.setYield(fireballLevelOne.getDamage());

                    fireball.setFallDistance(fireballLevelOne.getAOE());
                    fireball.setDirection(player.getLocation().getDirection());

                    cooldowns.put(player.getUniqueId(), System.currentTimeMillis());
                }
            }
        }
    }
}