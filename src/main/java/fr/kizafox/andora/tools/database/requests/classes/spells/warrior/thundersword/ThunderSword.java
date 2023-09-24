package fr.kizafox.andora.tools.database.requests.classes.spells.warrior.thundersword;

import fr.kizafox.andora.Andora;
import fr.kizafox.andora.tools.ItemBuilder;
import fr.kizafox.andora.tools.database.requests.classes.spells.handler.Spell;
import fr.kizafox.andora.tools.utils.RomanConverter;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;

/**
 * Change this line to a short description of the class
 *
 * @author : KIZAFOX
 * @date : 22/09/2023
 * @project : Andora
 */
public class ThunderSword extends Spell implements Listener {
    public ThunderSword(final Andora instance) {
        super(instance);

        this.instance.getServer().getPluginManager().registerEvents(this, this.instance);
    }

    @Override
    public String getName() {
        return "ThunderSword ";
    }

    @Override
    public int getLevel() {
        return 1;
    }

    @Override
    public int getManaCost() {
        return 20;
    }

    @Override
    public float getDamage() {
        return 20;
    }

    @Override
    public int getAOE() {
        return 0;
    }

    @Override
    public int getCooldown() {
        return 5;
    }

    @Override
    public ItemStack getItem() {
        return new ItemBuilder(Material.IRON_SWORD).addEnchant(Enchantment.DURABILITY, this.getLevel())
                .setName(ChatColor.RED + this.getName() + ChatColor.BOLD + RomanConverter.toRoman(this.getLevel()))
                .setLore(Arrays.asList(
                        ChatColor.DARK_GRAY + "Genre: " + ChatColor.AQUA + "Sort, Guerrier, Dégats",
                        " ",
                        ChatColor.WHITE + "" + ChatColor.BOLD + "       » Description «",
                        " ",
                        ChatColor.GRAY + "  Permet de faire apparaître,",
                        ChatColor.GRAY + "  des éclairs autour de soit.",
                        " ",
                        ChatColor.WHITE + "" + ChatColor.BOLD + "       » Statistiques «",
                        " ",
                        ChatColor.GRAY + "  Niveau du sort: " + ChatColor.YELLOW + this.getLevel(),
                        ChatColor.GRAY + "  Coût en mana: " + ChatColor.AQUA + this.getManaCost(),
                        ChatColor.GRAY + "  Dégats: " + ChatColor.RED + this.getDamage(),
                        ChatColor.GRAY + "  Zone d'effect: " + ChatColor.RED + this.getAOE(),
                        ChatColor.GRAY + "  Temps de recharge: " + ChatColor.WHITE + this.getCooldown(),
                        "",
                        ChatColor.GREEN + "" + ChatColor.ITALIC + "Lancer le sort: R-R-R"
                )).toItemStack();
    }

    @EventHandler (priority = EventPriority.MONITOR)
    public void onSpellEvent(PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        final ItemStack itemStack = player.getInventory().getItemInMainHand();

        if(itemStack.getItemMeta() == null) return;

        if (itemStack.getItemMeta().getDisplayName().equals(this.getItem().getItemMeta().getDisplayName())) {
            event.setCancelled(true);

            this.setupCombo(player, event.getAction(), (combo) -> {
                if (combo.length() == 3) {
                    Spell.WAND_COMBO.remove(player.getUniqueId());
                    int manaLeft = Spell.MANA.get(player.getUniqueId());

                    if (combo.equals("RRR")) {
                        if (manaLeft < this.getManaCost()) {
                            player.sendMessage(ChatColor.DARK_RED + "Vous n'avez pas la mana requise pour utiliser ce sort.");
                            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1f, 1f);
                            return;
                        }

                        Spell.MANA.put(player.getUniqueId(), manaLeft - this.getManaCost());

                        final Location location = player.getLocation();

                        new BukkitRunnable(){
                            int strikes = 3;

                            /**
                             * Runs this operation.
                             */
                            @Override
                            public void run() {
                                final Location[] locations = new Location[3];

                                for(int i = 0; i < 3; i++){
                                    double x = Math.random() * 6, z = Math.random() * 6;

                                    if(Math.random() > 0.5){
                                        x *= -1;
                                    }

                                    if(Math.random() > 0.5){
                                        z *= -1;
                                    }
                                    locations[i] = location.clone().add(x, 0, z);
                                }

                                Arrays.stream(locations).forEach(location -> {
                                    location.getWorld().strikeLightning(location);
                                    location.getWorld().spawnParticle(Particle.FLASH, location, 1);

                                    player.playSound(player.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 1.0F, 1.0F);
                                });

                                strikes -= 1;
                                if(strikes == 0){
                                    cancel();
                                }
                            }
                        }.runTaskTimer(this.instance, 0L, 6L);
                    }
                }
            });
        }
    }
}