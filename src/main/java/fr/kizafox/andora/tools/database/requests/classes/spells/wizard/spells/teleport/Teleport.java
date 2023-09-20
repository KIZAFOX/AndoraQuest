package fr.kizafox.andora.tools.database.requests.classes.spells.wizard.spells.teleport;

import fr.kizafox.andora.tools.ItemBuilder;
import fr.kizafox.andora.tools.database.requests.classes.spells.Spell;
import fr.kizafox.andora.tools.utils.RomanConverter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

/**
 * Change this line to a short description of the class
 *
 * @author : KIZAFOX
 * @date : 20/09/2023
 * @project : Andora
 */
public class Teleport extends Spell {
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
        return 75;
    }

    @Override
    public int getDamage() {
        return 0;
    }

    @Override
    public int getAOE() {
        return 0;
    }

    @Override
    public int getCooldown() {
        return 1;
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
                        ChatColor.GRAY + "  Temps de recharge: " + ChatColor.WHITE + this.getCooldown()
                )).toItemStack();
    }
}
