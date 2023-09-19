package fr.kizafox.andora.tools.database.requests.skills;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;

/**
 * Change this line to a short description of the class
 *
 * @author : KIZAFOX
 * @date : 17/09/2023
 * @project : Andora
 */
public enum SkillUnit{

    DAMAGE("Force", 12.5D, 0.5D, 10, Material.IRON_SWORD, Attribute.GENERIC_ATTACK_DAMAGE),
    HEALTH("Vitalité", 10.0D, 2.0D, 10, Material.APPLE, Attribute.GENERIC_MAX_HEALTH);

    private final String name;
    private final double percentage, value;
    private final int maxLevel;
    private final Material material;
    private final Attribute attribute;

    SkillUnit(String name, double percentage, double value, int maxLevel, Material material, Attribute attribute) {
        this.name = name;
        this.percentage = percentage;
        this.value = value;
        this.maxLevel = maxLevel;
        this.material = material;
        this.attribute = attribute;
    }

    public String getName() {
        return name;
    }

    public double getPercentage() {
        return percentage;
    }

    public double getValue() {
        return value;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public Material getMaterial() {
        return material;
    }

    public Attribute getAttribute() {
        return attribute;
    }
}
