package fr.kizafox.andora.tools.database.requests.classes;

import fr.kizafox.andora.tools.base64.heads.HeadUnit;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Change this line to a short description of the class
 *
 * @author : KIZAFOX
 * @date : 16/09/2023
 * @project : Andora
 */
public enum ClassUnit {

    PEASANT("Paysant", 0, 0, 0, 0, 0, 0, 0, null, null),
    WARRIOR("Guerrier", 26.0D, 5.0D, 5.0D, 10.0D, 3.0D, 0.09D, 10, HeadUnit.WARRIOR, Arrays.asList(
            ChatColor.DARK_GRAY + "Genre: " + ChatColor.AQUA + "Combattant, Juggernaut",
            " ",
            ChatColor.WHITE + "" + ChatColor.BOLD + "       » Description:",
            "",
            ChatColor.GRAY + "  Le guerrier est un combattant,",
            ChatColor.GRAY + "  qui se bat au corps à corps,",
            ChatColor.GRAY + "  il dispose d'une grande force",
            ChatColor.GRAY + "  et d'une endurance moyenne. ",
            " ",
            ChatColor.WHITE + "" + ChatColor.BOLD + "       » Statistiques «",
            " ",
            ChatColor.GRAY + "  Vie: " + ChatColor.RED + "25" + ChatColor.BOLD + " ♥",
            ChatColor.GRAY + "  Résistances physiques: " + ChatColor.LIGHT_PURPLE + "5" + ChatColor.BOLD +  " \uD83D\uDEE1",
            ChatColor.GRAY + "  Résistances au burst: " + ChatColor.LIGHT_PURPLE + "5" + ChatColor.BOLD +  " \uD83D\uDEE1",
            ChatColor.GRAY + "  Dégâts: " + ChatColor.GREEN + "10 ⚔",
            ChatColor.GRAY + "  Vitesse d'attaque: " + ChatColor.BLUE + "3" + ChatColor.BOLD + " \uD83C\uDFF9",
            ChatColor.GRAY + "  Vitesse de déplacement: " + ChatColor.BLUE + "0.09" + ChatColor.BOLD + " \uD83C\uDFF9",
            " ",
            ChatColor.GREEN + "» Cliquez choisir cette classe"
    )),
    DWARF("Nain", 30.0D, 10.0D, 10.0D, 5.0D, 1.5D, 0.09D, 21, HeadUnit.DWARF, Arrays.asList(
            ChatColor.DARK_GRAY + "Genre: " + ChatColor.AQUA + "Combattant, Tank",
            " ",
            ChatColor.WHITE + "" + ChatColor.BOLD + "       » Description:",
            ChatColor.GRAY + "  Le nain est un combattant,",
            ChatColor.GRAY + "  qui se bat au corps à corps,",
            ChatColor.GRAY + "  il a beaucoup de points de vie",
            ChatColor.GRAY + "  mais une faible endurance. ",
            " ",
            ChatColor.WHITE + "" + ChatColor.BOLD + "       » Statistiques «",
            " ",
            ChatColor.GRAY + "  Vie: " + ChatColor.RED + "30 ♥",
            ChatColor.GRAY + "  Résistances physiques: " + ChatColor.LIGHT_PURPLE + "10" + ChatColor.BOLD +  " \uD83D\uDEE1",
            ChatColor.GRAY + "  Résistances au burst: " + ChatColor.LIGHT_PURPLE + "10" + ChatColor.BOLD +  " \uD83D\uDEE1",
            ChatColor.GRAY + "  Dégâts: " + ChatColor.GREEN + "5 ⚔",
            ChatColor.GRAY + "  Vitesse d'attaque: " + ChatColor.BLUE + "1.5" + ChatColor.BOLD + " \uD83C\uDFF9",
            ChatColor.GRAY + "  Vitesse de déplacement: " + ChatColor.BLUE + "0.09" + ChatColor.BOLD + " \uD83C\uDFF9",
            " ",
            ChatColor.GREEN + "» Cliquez choisir cette classe"
    )),
    ARCHER("Archère", 10.0D, 1.0D, 1.0D, 15.0D, 4.0D, 0.15D, 23, HeadUnit.ARCHER, Arrays.asList(
            ChatColor.DARK_GRAY + "Genre: " + ChatColor.AQUA + "Distance, Tireur d'élite",
            " ",
            ChatColor.WHITE + "" + ChatColor.BOLD + "       » Description:",
            ChatColor.GRAY + "  L'achère est une tireuse d'élite,",
            ChatColor.GRAY + "  qui se bat à distance,",
            ChatColor.GRAY + "  elle dispose de peut de force",
            ChatColor.GRAY + "  mais est très agile.",
            " ",
            ChatColor.WHITE + "" + ChatColor.BOLD + "       » Statistiques «",
            " ",
            ChatColor.GRAY + "  Vie: " + ChatColor.RED + "10 ♥",
            ChatColor.GRAY + "  Résistances physiques: " + ChatColor.LIGHT_PURPLE + "1" + ChatColor.BOLD +  " \uD83D\uDEE1",
            ChatColor.GRAY + "  Résistances au burst: " + ChatColor.LIGHT_PURPLE + "1" + ChatColor.BOLD +  " \uD83D\uDEE1",
            ChatColor.GRAY + "  Dégâts: " + ChatColor.GREEN + "15 ⚔",
            ChatColor.GRAY + "  Vitesse d'attaque: " + ChatColor.BLUE + "4" + ChatColor.BOLD + " \uD83C\uDFF9",
            ChatColor.GRAY + "  Vitesse de déplacement: " + ChatColor.BLUE + "0.15" + ChatColor.BOLD + " \uD83C\uDFF9",
            " ",
            ChatColor.GREEN + "» Cliquez choisir cette classe"
    )),
    WIZARD("Magicien", 15.0D, 1.0D, 1.0D, 10.0D, 2.0D, 0.1D, 16, HeadUnit.WIZARD, Arrays.asList(
            ChatColor.DARK_GRAY + "Genre: " + ChatColor.AQUA + "Distance, Magie",
            " ",
            ChatColor.WHITE + "" + ChatColor.BOLD + "       » Description:",
            ChatColor.GRAY + "  Le magicien est un duelist,",
            ChatColor.GRAY + "  il dispose d'une grande quantitée de mana",
            ChatColor.GRAY + "  mais ne resiste pas au corps à corps.",
            " ",
            ChatColor.WHITE + "" + ChatColor.BOLD + "       » Statistiques «",
            " ",
            ChatColor.GRAY + "  Vie: " + ChatColor.RED + "15 ♥",
            ChatColor.GRAY + "  Résistances physiques: " + ChatColor.LIGHT_PURPLE + "1" + ChatColor.BOLD +  " \uD83D\uDEE1",
            ChatColor.GRAY + "  Résistances au burst: " + ChatColor.LIGHT_PURPLE + "1" + ChatColor.BOLD +  " \uD83D\uDEE1",
            ChatColor.GRAY + "  Dégâts: " + ChatColor.GREEN + "10 ⚔",
            ChatColor.GRAY + "  Vitesse d'attaque: " + ChatColor.BLUE + "2" + ChatColor.BOLD + " \uD83C\uDFF9",
            ChatColor.GRAY + "  Vitesse de déplacement: " + ChatColor.BLUE + "0.1" + ChatColor.BOLD + " \uD83C\uDFF9",
            " ",
            ChatColor.GREEN + "» Cliquez choisir cette classe"
    ));

    private final String name;
    private final double health, armor, armorToughness, attackDamage, attackSpeed, movementSpeed;
    private final int slot;
    private final HeadUnit head;
    private final List<String> description;

    ClassUnit(String name, double health, double armor, double armorToughness, double attackDamage, double attackSpeed, double movementSpeed, int slot, HeadUnit head, List<String> description) {
        this.name = name;
        this.health = health;
        this.armor = armor;
        this.armorToughness = armorToughness;
        this.attackDamage = attackDamage;
        this.attackSpeed = attackSpeed;
        this.movementSpeed = movementSpeed;
        this.slot = slot;
        this.head = head;
        this.description = description;
    }

    public static final List<ClassUnit> rank = new ArrayList<>();

    static{
        rank.addAll(Arrays.asList(ClassUnit.values()));
    }

    public static ClassUnit getByName(String name){
        return Arrays.stream(values()).filter(c -> c.getName().equalsIgnoreCase(name)).findAny().orElse(ClassUnit.PEASANT);
    }

    public String getName() {
        return name;
    }

    public double getHealth() {
        return health;
    }

    public double getArmor() {
        return armor;
    }

    public double getArmorToughness() {
        return armorToughness;
    }

    public double getAttackDamage() {
        return attackDamage;
    }

    public double getAttackSpeed() {
        return attackSpeed;
    }

    public double getMovementSpeed() {
        return movementSpeed;
    }

    public int getSlot() {
        return slot;
    }

    public HeadUnit getHead() {
        return head;
    }

    public List<String> getDescription() {
        return description;
    }
}
