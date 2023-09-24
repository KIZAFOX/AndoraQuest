package fr.kizafox.andora.tools.database.requests.classes.spells.dwarf.turtleshell;

import fr.kizafox.andora.Andora;
import fr.kizafox.andora.tools.ItemBuilder;
import fr.kizafox.andora.tools.database.requests.classes.spells.handler.Spell;
import fr.kizafox.andora.tools.utils.RomanConverter;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Change this line to a short description of the class
 *
 * @author : KIZAFOX
 * @date : 24/09/2023
 * @project : Andora
 */
public class TurtleShell extends Spell implements Listener {

    private final List<ArmorStand> armorStands = new ArrayList<>();

    public TurtleShell(final Andora instance) {
        super(instance);

        this.instance.getServer().getPluginManager().registerEvents(this, this.instance);
    }

    @Override
    public String getName() {
        return "TurtleShell ";
    }

    @Override
    public int getLevel() {
        return 1;
    }

    @Override
    public int getManaCost() {
        return 50;
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
                        ChatColor.GRAY + "  Permet d'appliquer une résistance',",
                        ChatColor.GRAY + "  très puissante pendant 10 secondes.",
                        " ",
                        ChatColor.WHITE + "" + ChatColor.BOLD + "       » Statistiques «",
                        " ",
                        ChatColor.GRAY + "  Niveau du sort: " + ChatColor.YELLOW + this.getLevel(),
                        ChatColor.GRAY + "  Coût en mana: " + ChatColor.AQUA + this.getManaCost(),
                        ChatColor.GRAY + "  Résistance: " + ChatColor.RED + this.getDamage(),
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

                        player.getActivePotionEffects().add(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * 10, (int) this.getDamage()));

                        final int numArmorStands = 10;
                        final float radius = 2.0F;
                        final double angleIncrement = Math.toRadians(36);

                        //spawn armorstands
                        for (int i = 0; i < numArmorStands; i++) {
                            double angle = i * angleIncrement;
                            double x = player.getLocation().getX() + radius * Math.cos(angle);
                            double y = player.getLocation().getY() + 1.0;
                            double z = player.getLocation().getZ() + radius * Math.sin(angle);

                            Location standLocation = new Location(player.getWorld(), x, y, z);
                            ArmorStand armorStand = (ArmorStand) player.getWorld().spawnEntity(standLocation, EntityType.ARMOR_STAND);

                            armorStand.setSmall(true);
                            armorStand.setGravity(false);
                            armorStand.setVisible(true);
                            armorStand.setBasePlate(false);

                            armorStand.getEquipment().setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));

                            armorStands.add(armorStand);
                        }

                        //follow player
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                double radius = 2.0;
                                double angleIncrement = Math.toRadians(36);

                                for (int i = 0; i < armorStands.size(); i++) {
                                    double angle = i * angleIncrement;
                                    double x = player.getLocation().getX() + radius * Math.cos(angle);
                                    double y = player.getLocation().getY() + 1.0;
                                    double z = player.getLocation().getZ() + radius * Math.sin(angle);

                                    Location standLocation = new Location(player.getWorld(), x, y, z);
                                    armorStands.get(i).teleport(standLocation);
                                }
                            }
                        }.runTaskTimer(this.instance, 0L, 1L);

                        new BukkitRunnable() {
                            double angle = 0;

                            @Override
                            public void run() {
                                angle += Math.toRadians(1);
                                double centerX = player.getLocation().getX();
                                double centerZ = player.getLocation().getZ();

                                for (ArmorStand armorStand : armorStands) {
                                    double x = centerX + radius * Math.cos(angle);
                                    double z = centerZ + radius * Math.sin(angle);

                                    Location standLocation = armorStand.getLocation();
                                    standLocation.setX(x);
                                    standLocation.setY(player.getLocation().getY() + 2.5);
                                    standLocation.setZ(z);

                                    angle += angleIncrement;
                                }
                            }
                        }.runTaskTimer(this.instance, 0L, 1L);

                        // Supprime les ArmorStands après 10 secondes
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