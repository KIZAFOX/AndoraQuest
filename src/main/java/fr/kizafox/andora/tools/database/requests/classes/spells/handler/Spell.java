package fr.kizafox.andora.tools.database.requests.classes.spells.handler;

import fr.kizafox.andora.Andora;
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
public abstract class Spell implements SpellListener{

    protected final Andora instance;

    public static final Map<UUID, Integer> MANA = new HashMap<>();
    public static final Map<UUID, String> WAND_COMBO = new HashMap<>();


    public Spell(final Andora instance) {
        this.instance = instance;
    }

    public abstract String getName();

    public abstract int getLevel();

    public abstract int getManaCost();

    public abstract float getDamage();

    public abstract int getAOE();

    public abstract int getCooldown();

    public abstract ItemStack getItem();
}
