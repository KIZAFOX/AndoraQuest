package fr.kizafox.andora.tools.game.spells.wizard.event;

import fr.kizafox.andora.Andora;
import fr.kizafox.andora.tools.game.spells.wizard.WizardSpellUnit;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
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

import java.util.*;

/**
 * Change this line to a short description of the class
 *
 * @author : KIZAFOX
 * @date : 20/09/2023
 * @project : Andora
 */
public class SpellListeners implements Listener {

    protected final Andora instance;

    private final Map<UUID, Long> cooldowns = new HashMap<>();
    private final Map<UUID, Integer> mana = new HashMap<>();
    private final Map<UUID, String> wandCombo = new HashMap<>();

    public SpellListeners(final Andora instance) {
        this.instance = instance;

        new BukkitRunnable(){

            /**
             * Actionbar task for mana and combo
             */
            @Override
            public void run() {
                Bukkit.getOnlinePlayers().forEach(players -> {
                    if(players.getInventory().getItemInMainHand().getItemMeta() == null) return;

                    if (players.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(WizardSpellUnit.FIREBALL_LVL_1.getItemBuilder().toItemStack().getItemMeta().getDisplayName()) || players.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(WizardSpellUnit.FIREBALL_LVL_2.getItemBuilder().toItemStack().getItemMeta().getDisplayName())) {
                        if (!mana.containsKey(players.getUniqueId())) {
                            mana.put(players.getUniqueId(), 200);
                        }

                        String message = ChatColor.AQUA + "Mana: " + ChatColor.GREEN + mana.get(players.getUniqueId()) + ChatColor.AQUA + " / " + ChatColor.GREEN + "200";
                        final String combo = wandCombo.get(players.getUniqueId());

                        if(combo != null){
                            message = message + "   " + ChatColor.YELLOW + "Combo: " + ChatColor.GREEN + formatCombo(combo);
                            //actionbar
                        }
                        players.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(message));
                    }else{
                        wandCombo.remove(players.getUniqueId());
                    }
                });
            }
        }.runTaskTimer(this.instance, 0L, 1L);

        new BukkitRunnable(){

            /**
             * Mana regen task
             */
            @Override
            public void run() {
                mana.keySet().forEach(uuid -> {
                    int manaLeft = mana.get(uuid);

                    if(manaLeft < 200){
                        mana.put(uuid, manaLeft + 1);
                    }
                });
            }
        }.runTaskTimer(this.instance, 0L, 2L);
    }

    private String formatCombo(String combo) {
        final StringBuilder comboBuilder = new StringBuilder(combo + ChatColor.GRAY);
        comboBuilder.append("_".repeat(Math.max(0, 3 - comboBuilder.length())));
        combo = comboBuilder.toString();
        combo = combo.toUpperCase().replace("L", ChatColor.DARK_BLUE + "L").replace("R", ChatColor.DARK_RED + "R");
        return combo;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onClick(final PlayerInteractEvent event){
        final Player player = event.getPlayer();
        final ItemStack itemStack = player.getInventory().getItemInMainHand();

        final Action action = event.getAction();

        final WizardSpellUnit fireball1 = WizardSpellUnit.FIREBALL_LVL_1;
        final WizardSpellUnit fireball2 = WizardSpellUnit.FIREBALL_LVL_2;

        if(itemStack.getItemMeta().getDisplayName().equals(fireball1.getItemBuilder().toItemStack().getItemMeta().getDisplayName())){
            event.setCancelled(true);

            int cooldownTime = fireball1.getCooldown();

            if(cooldowns.containsKey(player.getUniqueId())){
                long secondsLeft = ((cooldowns.get(player.getUniqueId()) / 1000) + cooldownTime) - (System.currentTimeMillis()/1000);

                if(secondsLeft > 0){
                    player.sendMessage(ChatColor.RED + "Vous devez attendre " + secondsLeft + ChatColor.RESET + ChatColor.RED + " secondes avant de pouvoir réutiliser ce sort.");
                    return;
                }
            }

            String combo = this.wandCombo.get(player.getUniqueId());

            if(combo == null || combo.length() < 4){
                if(combo == null){
                    combo = "";
                }
                if(action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK) {
                    combo += "L";
                }else if(action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK){
                    combo += "R";
                }
                this.wandCombo.put(player.getUniqueId(), combo);
            }

            if(combo.length() == 4){
                this.wandCombo.remove(player.getUniqueId());
                int manaLeft = this.mana.get(player.getUniqueId());

                if (combo.equals("RLLL")) {
                    if (manaLeft < fireball1.getManaCost()) {
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("\n" + ChatColor.RED + "Vous avez besoins de " + ChatColor.BOLD + (50 - manaLeft) + ChatColor.RESET + ChatColor.RED + " mana pour utiliser ce sort."));
                        return;
                    }

                    this.mana.put(player.getUniqueId(), manaLeft - fireball1.getManaCost());

                    final Fireball fireball = player.launchProjectile(Fireball.class);
                    fireball.setIsIncendiary(false);
                    fireball.setYield(fireball1.getDamage());

                    fireball.setFallDistance(fireball1.getAoe());
                    fireball.setDirection(player.getLocation().getDirection());

                    cooldowns.put(player.getUniqueId(), System.currentTimeMillis());
                }
            }
        }else if(itemStack.getItemMeta().getDisplayName().equals(fireball2.getItemBuilder().toItemStack().getItemMeta().getDisplayName())){
            event.setCancelled(true);

            int cooldownTime = fireball2.getCooldown();

            if(cooldowns.containsKey(player.getUniqueId())){
                long secondsLeft = ((cooldowns.get(player.getUniqueId()) / 1000) + cooldownTime) - (System.currentTimeMillis()/1000);

                if(secondsLeft > 0){
                    player.sendMessage(ChatColor.RED + "Vous devez attendre " + secondsLeft + ChatColor.RESET + ChatColor.RED + " secondes avant de pouvoir réutiliser ce sort.");
                    return;
                }
            }

            String combo = this.wandCombo.get(player.getUniqueId());

            if(combo == null || combo.length() < 4){
                if(combo == null){
                    combo = "";
                }
                if(action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK) {
                    combo += "L";
                }else if(action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK){
                    combo += "R";
                }
                this.wandCombo.put(player.getUniqueId(), combo);
            }

            if(combo.length() == 4){
                this.wandCombo.remove(player.getUniqueId());
                int manaLeft = this.mana.get(player.getUniqueId());

                if (combo.equals("RRRL")) {
                    if (manaLeft < 1) {
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("\n" + ChatColor.RED + "Vous avez besoins de " + ChatColor.BOLD + (50 - manaLeft) + ChatColor.RESET + ChatColor.RED + " mana pour utiliser ce sort."));
                        return;
                    }

                    this.mana.put(player.getUniqueId(), manaLeft - 1);

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
