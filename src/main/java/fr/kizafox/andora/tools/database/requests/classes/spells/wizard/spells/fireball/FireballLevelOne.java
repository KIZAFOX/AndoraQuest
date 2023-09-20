package fr.kizafox.andora.tools.database.requests.classes.spells.wizard.spells.fireball;

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
public class FireballLevelOne extends Spell {
    @Override
    public String getName() {
        return "Fireball ";
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
    public int getDamage() {
        return 10;
    }

    @Override
    public int getAOE() {
        return 2;
    }

    @Override
    public int getCooldown() {
        return 5;
    }

    @Override
    public ItemStack getItem() {
        return new ItemBuilder(Material.ENCHANTED_BOOK).addBookEnchant(Enchantment.PROTECTION_FIRE, 1)
                .setName(ChatColor.RED + this.getName() + ChatColor.BOLD + RomanConverter.toRoman(this.getLevel()))
                .setLore(Arrays.asList(
                        ChatColor.DARK_GRAY + "Genre: " + ChatColor.AQUA + "Sort, Mage, Feu",
                        " ",
                        ChatColor.WHITE + "" + ChatColor.BOLD + "       » Description «",
                        " ",
                        ChatColor.GRAY + "  Lance une boule de feu,",
                        ChatColor.GRAY + "  qui fait des dégats de zone,",
                        ChatColor.GRAY + "  et brûle les ennemis touchés.",
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
