package fr.kizafox.andora.tools.database.requests.classes.spells.wizard;

import fr.kizafox.andora.Andora;
import fr.kizafox.andora.tools.database.requests.classes.spells.wizard.spells.fireball.FireballLevelOne;
import fr.kizafox.andora.tools.database.requests.classes.spells.wizard.spells.teleport.Teleport;
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
public class WizardSpellsManager {

    protected final Andora instance;

    private final List<ItemStack> itemForSpell;

    public WizardSpellsManager(final Andora instance) {
        this.instance = instance;

        this.itemForSpell = new ArrayList<>();
        this.itemForSpell.add(new FireballLevelOne(this.instance).getItem());
        this.itemForSpell.add(new Teleport(this.instance).getItem());
    }

    public List<ItemStack> getItemForSpell() {
        return itemForSpell;
    }
}
