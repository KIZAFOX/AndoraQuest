package fr.kizafox.andora.tools.database.requests.classes.spells.archer;

import fr.kizafox.andora.Andora;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Change this line to a short description of the class
 *
 * @author : KIZAFOX
 * @date : 20/09/2023
 * @project : Andora
 */
public class ArcherSpellsManager {

    protected final Andora instance;

    private final List<ItemStack> itemForSpell;

    public ArcherSpellsManager(final Andora instance) {
        this.instance = instance;

        this.itemForSpell = new ArrayList<>();
    }

    public List<ItemStack> getItemForSpell() {
        return itemForSpell;
    }
}
