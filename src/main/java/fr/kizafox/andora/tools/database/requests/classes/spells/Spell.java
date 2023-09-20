package fr.kizafox.andora.tools.database.requests.classes.spells;

import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Change this line to a short description of the class
 *
 * @author : KIZAFOX
 * @date : 20/09/2023
 * @project : Andora
 */
public abstract class Spell {

    public static final Map<UUID, Integer> mana = new HashMap<>();
    public static final Map<UUID, String> wandCombo = new HashMap<>();

    public abstract String getName();

    public abstract int getLevel();

    public abstract int getManaCost();

    public abstract int getDamage();

    public abstract int getAOE();

    public abstract int getCooldown();

    public abstract ItemStack getItem();
}
