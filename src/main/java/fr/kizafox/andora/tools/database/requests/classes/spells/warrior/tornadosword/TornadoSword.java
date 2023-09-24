package fr.kizafox.andora.tools.database.requests.classes.spells.warrior.tornadosword;

import fr.kizafox.andora.Andora;
import fr.kizafox.andora.tools.ItemBuilder;
import fr.kizafox.andora.tools.database.requests.classes.spells.handler.Spell;
import fr.kizafox.andora.tools.utils.RomanConverter;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Change this line to a short description of the class
 *
 * @author : KIZAFOX
 * @date : 22/09/2023
 * @project : Andora
 */
public class TornadoSword extends Spell implements Listener {

    private final List<ArmorStand> armorStands = new ArrayList<>();

    public TornadoSword(final Andora instance) {
        super(instance);

        this.instance.getServer().getPluginManager().registerEvents(this, this.instance);
    }

    @Override
    public String getName() {
        return "TornadoSword ";
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
                        ChatColor.GRAY + "  des tornades.",
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

    @EventHandler(priority = EventPriority.MONITOR)
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

                        new BukkitRunnable(){
                            int angle = 0;

                            final int lines = 4;
                            final double maxRadius = 10, maxHeight = 15, height = .5;
                            final double radiusCalcul = maxRadius / maxHeight;

                            /**
                             * Runs this operation.
                             */
                            @Override
                            public void run() {
                                for (int l = 0; l < lines; l++) {
                                    for (double y = 0; y < maxHeight; y += height) {
                                        final double radius = y * radiusCalcul;
                                        final double x = Math.cos(Math.toRadians((double) 360 / lines * l + y * 25 - angle)) * radius;
                                        final double z = Math.sin(Math.toRadians((double) 360 / lines * l + y * 25 - angle)) * radius;

                                        player.getLocation().getWorld().spawnParticle(Particle.CLOUD, player.getLocation().clone().add(x, y, z), 1, 0, 0,0, 0);
                                    }
                                }
                                angle++;
                            }
                        }.runTaskTimer(this.instance, 0L, 1L);

                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                armorStands.forEach(Entity::remove);
                                armorStands.clear();
                            }
                        }.runTaskLater(this.instance, 20L * 10);
                    }
                }
            });
        }
    }
}