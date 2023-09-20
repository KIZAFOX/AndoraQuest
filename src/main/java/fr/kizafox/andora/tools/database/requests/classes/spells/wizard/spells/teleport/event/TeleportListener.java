package fr.kizafox.andora.tools.database.requests.classes.spells.wizard.spells.teleport.event;

import fr.kizafox.andora.Andora;
import fr.kizafox.andora.tools.database.requests.classes.spells.Spell;
import fr.kizafox.andora.tools.database.requests.classes.spells.wizard.spells.fireball.FireballLevelOne;
import fr.kizafox.andora.tools.database.requests.classes.spells.wizard.spells.teleport.Teleport;
import fr.kizafox.andora.tools.utils.StringFormat;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

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
public class TeleportListener implements Listener {

    protected final Andora instance;

    private final Map<UUID, Long> cooldowns = new HashMap<>();

    public TeleportListener(final Andora instance) {
        this.instance = instance;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onClick(final PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        final ItemStack itemStack = player.getInventory().getItemInMainHand();

        if(itemStack.getItemMeta() == null) return;

        final Action action = event.getAction();

        final Teleport teleport = this.instance.getManagers().getSpellsManager().getTeleport();

        if (itemStack.getItemMeta().getDisplayName().equals(teleport.getItem().getItemMeta().getDisplayName())) {
            event.setCancelled(true);

            int cooldownTime = teleport.getCooldown();

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

                if (combo.equals("RRRL")) {
                    if (manaLeft < teleport.getManaCost()) {
                        player.sendMessage(ChatColor.DARK_RED + "Vous n'avez pas la mana requise pour utiliser ce sort.");
                        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1f, 1f);
                        return;
                    }

                    Spell.mana.put(player.getUniqueId(), manaLeft - teleport.getManaCost());

                    final Location location = player.getLocation();
                    final Vector direction = location.getDirection();
                    direction.multiply(5);
                    location.add(direction);
                    player.teleport(location);

                    cooldowns.put(player.getUniqueId(), System.currentTimeMillis());
                }
            }
        }
    }
}