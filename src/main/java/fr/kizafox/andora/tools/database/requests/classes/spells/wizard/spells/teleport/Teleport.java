package fr.kizafox.andora.tools.database.requests.classes.spells.wizard.spells.teleport;

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
import org.bukkit.util.Vector;

import java.util.Arrays;

/**
 * Change this line to a short description of the class
 *
 * @author : KIZAFOX
 * @date : 20/09/2023
 * @project : Andora
 */
public class Teleport extends Spell implements Listener {

    public Teleport(final Andora instance) {
        super(instance);

        this.instance.getServer().getPluginManager().registerEvents(this, this.instance);
    }

    @Override
    public String getName() {
        return "Téléportation ";
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
        return 0;
    }

    @Override
    public int getAOE() {
        return 0;
    }

    @Override
    public int getCooldown() {
        return 0;
    }

    @Override
    public ItemStack getItem() {
        return new ItemBuilder(Material.ENCHANTED_BOOK).addBookEnchant(Enchantment.ARROW_DAMAGE, this.getLevel())
                .setName(ChatColor.RED + this.getName() + ChatColor.BOLD + RomanConverter.toRoman(this.getLevel()))
                .setLore(Arrays.asList(
                        ChatColor.DARK_GRAY + "Genre: " + ChatColor.AQUA + "Sort, Mage, Téléportation",
                        " ",
                        ChatColor.WHITE + "" + ChatColor.BOLD + "       » Description «",
                        " ",
                        ChatColor.GRAY + "  Permet de se téléport,",
                        ChatColor.GRAY + "  à 5 blocs devant soit.",
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
                        final Vector direction = location.getDirection();
                        direction.multiply(5);
                        location.add(direction);
                        player.teleport(location);

                        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);
                        player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, .5f, .5f);

                        player.spawnParticle(Particle.PORTAL, player.getLocation(), 100, 1, 1, 1);
                    }
                }
            });
        }
    }
}