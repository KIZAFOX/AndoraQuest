package fr.kizafox.andora.tools.database.requests.classes.spells.archer;

import fr.kizafox.andora.tools.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

import java.util.Arrays;

/**
 * Change this line to a short description of the class
 *
 * @author : KIZAFOX
 * @date : 20/09/2023
 * @project : Andora
 */
public enum ArcherSpellUnit {

    FIREBALL_LVL_1("Fireball I", 1, 150, 1, 1, 0,
            new ItemBuilder(Material.ENCHANTED_BOOK).addBookEnchant(Enchantment.PROTECTION_FIRE, 1)
                    .setName(ChatColor.RED + "Fireball " + ChatColor.BOLD + "I")
                    .setLore(Arrays.asList(
                            ChatColor.DARK_GRAY + "Genre: " + ChatColor.AQUA + "Sort, Mage, Feu",
                            " ",
                            ChatColor.WHITE + "" + ChatColor.BOLD + "       » Description:",
                            ChatColor.GRAY + "  Lance une boule de feu,",
                            ChatColor.GRAY + "  qui fait des dégats de zone,",
                            ChatColor.GRAY + "  et brûle les ennemis touchés.",
                            " ",
                            ChatColor.WHITE + "" + ChatColor.BOLD + "       » Statistiques «",
                            " ",
                            ChatColor.GRAY + "  Niveau du sort: " + ChatColor.YELLOW + "1",
                            ChatColor.GRAY + "  Coût en mana: " + ChatColor.AQUA + "50",
                            ChatColor.GRAY + "  Dégats: " + ChatColor.RED + "1",
                            ChatColor.GRAY + "  Zone d'effect: " + ChatColor.RED + "4",
                            ChatColor.GRAY + "  Temps de recharge: " + ChatColor.WHITE + "5s",
                            " ",
                            ChatColor.GREEN + "» Cliquez choisir cette classe"
                    ))),
    FIREBALL_LVL_2("Fireball II", 2, 150, 1, 1, 0,
            new ItemBuilder(Material.ENCHANTED_BOOK).addBookEnchant(Enchantment.PROTECTION_FIRE, 2)
                    .setName(ChatColor.RED + "Fireball " + ChatColor.BOLD + "II")
                    .setLore(Arrays.asList(
                            ChatColor.DARK_GRAY + "Genre: " + ChatColor.AQUA + "Sort, Mage, Feu",
                            " ",
                            ChatColor.WHITE + "" + ChatColor.BOLD + "       » Description:",
                            ChatColor.GRAY + "  Lance une boule de feu,",
                            ChatColor.GRAY + "  qui fait des dégats de zone,",
                            ChatColor.GRAY + "  et brûle les ennemis touchés.",
                            " ",
                            ChatColor.WHITE + "" + ChatColor.BOLD + "       » Statistiques «",
                            " ",
                            ChatColor.GRAY + "  Niveau du sort: " + ChatColor.YELLOW + "2",
                            ChatColor.GRAY + "  Coût en mana: " + ChatColor.AQUA + "50",
                            ChatColor.GRAY + "  Dégats: " + ChatColor.RED + "1",
                            ChatColor.GRAY + "  Zone d'effect: " + ChatColor.RED + "4",
                            ChatColor.GRAY + "  Temps de recharge: " + ChatColor.WHITE + "5s",
                            " ",
                            ChatColor.GREEN + "» Cliquez choisir cette classe"
                    ))),
    FIREBALL_LVL_3("Fireball III", 3, 50, 1, 1, 5,
            new ItemBuilder(Material.ENCHANTED_BOOK).addBookEnchant(Enchantment.PROTECTION_FIRE, 3)
                    .setName(ChatColor.RED + "Fireball " + ChatColor.BOLD + "III")
                    .setLore(Arrays.asList(
                            ChatColor.DARK_GRAY + "Genre: " + ChatColor.AQUA + "Sort, Mage, Feu",
                            " ",
                            ChatColor.WHITE + "" + ChatColor.BOLD + "       » Description:",
                            ChatColor.GRAY + "  Lance une boule de feu,",
                            ChatColor.GRAY + "  qui fait des dégats de zone,",
                            ChatColor.GRAY + "  et brûle les ennemis touchés.",
                            " ",
                            ChatColor.WHITE + "" + ChatColor.BOLD + "       » Statistiques «",
                            " ",
                            ChatColor.GRAY + "  Niveau du sort: " + ChatColor.YELLOW + "3",
                            ChatColor.GRAY + "  Coût en mana: " + ChatColor.AQUA + "50",
                            ChatColor.GRAY + "  Dégats: " + ChatColor.RED + "1",
                            ChatColor.GRAY + "  Zone d'effect: " + ChatColor.RED + "4",
                            ChatColor.GRAY + "  Temps de recharge: " + ChatColor.WHITE + "5s",
                            " ",
                            ChatColor.GREEN + "» Cliquez choisir cette classe"
                    ))),
    FIREBALL_LVL_4("Fireball IV", 4, 50, 1, 1, 5,
            new ItemBuilder(Material.ENCHANTED_BOOK).addBookEnchant(Enchantment.PROTECTION_FIRE, 4)
                    .setName(ChatColor.RED + "Fireball " + ChatColor.BOLD + "IV")
                    .setLore(Arrays.asList(
                            ChatColor.DARK_GRAY + "Genre: " + ChatColor.AQUA + "Sort, Mage, Feu",
                            " ",
                            ChatColor.WHITE + "" + ChatColor.BOLD + "       » Description:",
                            ChatColor.GRAY + "  Lance une boule de feu,",
                            ChatColor.GRAY + "  qui fait des dégats de zone,",
                            ChatColor.GRAY + "  et brûle les ennemis touchés.",
                            " ",
                            ChatColor.WHITE + "" + ChatColor.BOLD + "       » Statistiques «",
                            " ",
                            ChatColor.GRAY + "  Niveau du sort: " + ChatColor.YELLOW + "4",
                            ChatColor.GRAY + "  Coût en mana: " + ChatColor.AQUA + "50",
                            ChatColor.GRAY + "  Dégats: " + ChatColor.RED + "1",
                            ChatColor.GRAY + "  Zone d'effect: " + ChatColor.RED + "4",
                            ChatColor.GRAY + "  Temps de recharge: " + ChatColor.WHITE + "5s",
                            " ",
                            ChatColor.GREEN + "» Cliquez choisir cette classe"
                    )));

    private final String name;
    private final int level, manaCost, damage, aoe, cooldown;
    private final ItemBuilder itemBuilder;

    ArcherSpellUnit(String name, int level, int manaCost, int damage, int aoe, int cooldown, ItemBuilder itemBuilder) {
        this.name = name;
        this.level = level;
        this.manaCost = manaCost;
        this.damage = damage;
        this.aoe = aoe;
        this.cooldown = cooldown;
        this.itemBuilder = itemBuilder;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public int getManaCost() {
        return manaCost;
    }

    public int getDamage() {
        return damage;
    }

    public int getAoe() {
        return aoe;
    }

    public int getCooldown() {
        return cooldown;
    }

    public ItemBuilder getItemBuilder() {
        return itemBuilder;
    }
}
