package fr.kizafox.andora.tools.gui.inventories;

import fr.kizafox.andora.Andora;
import fr.kizafox.andora.tools.gui.inventories.classes.ClassesGui;
import fr.kizafox.andora.tools.gui.inventories.skills.SkillsGui;
import fr.kizafox.andora.tools.gui.inventories.spells.SpellsGui;
import fr.kizafox.andora.tools.gui.inventories.spells.sub.ArcherSpellsGui;
import fr.kizafox.andora.tools.gui.inventories.spells.sub.DwarfSpellsGui;
import fr.kizafox.andora.tools.gui.inventories.spells.sub.WarriorSpellsGui;
import fr.kizafox.andora.tools.gui.inventories.spells.sub.WizardSpellsGui;
import org.bukkit.ChatColor;

/**
 * Change this line to a short description of the class
 *
 * @author : KIZAFOX
 * @date : 17/09/2023
 * @project : Andora
 */
public class InventoryManager {

    protected final Andora instance;

    protected final SkillsGui skillsGui;
    protected final ClassesGui classesGui;
    protected final SpellsGui spellsGui;

    protected final WarriorSpellsGui warriorSpellsGui;
    protected final DwarfSpellsGui dwarfSpellsGui;
    protected final ArcherSpellsGui archerSpellsGui;
    protected final WizardSpellsGui wizardSpellsGui;

    public InventoryManager(final Andora instance) {
        this.instance = instance;

        this.skillsGui = new SkillsGui(this.instance, 9 * 6, ChatColor.YELLOW + "Augmente tes compétences");
        this.classesGui = new ClassesGui(this.instance, 9 * 4, ChatColor.YELLOW + "Choisi une classe");
        this.spellsGui = new SpellsGui(this.instance, 9 * 4, ChatColor.YELLOW + "Liste des sorts");

        this.warriorSpellsGui = new WarriorSpellsGui(this.instance, 9 * 6, ChatColor.YELLOW + "Liste des sorts: Guerrier");
        this.dwarfSpellsGui = new DwarfSpellsGui(this.instance, 9 * 6, ChatColor.YELLOW + "Liste des sorts: Nain");
        this.archerSpellsGui = new ArcherSpellsGui(this.instance, 9 * 6, ChatColor.YELLOW + "Liste des sorts: Archère");
        this.wizardSpellsGui = new WizardSpellsGui(this.instance, 9 * 6, ChatColor.YELLOW + "Liste des sorts: Mage");
    }

    public SkillsGui getSkillsGui() {
        return skillsGui;
    }

    public ClassesGui getClassesGui() {
        return classesGui;
    }

    public SpellsGui getSpellsGui() {
        return spellsGui;
    }

    public WarriorSpellsGui getWarriorSpellsGui() {
        return warriorSpellsGui;
    }

    public DwarfSpellsGui getDwarfSpellsGui() {
        return dwarfSpellsGui;
    }

    public ArcherSpellsGui getArcherSpellsGui() {
        return archerSpellsGui;
    }

    public WizardSpellsGui getWizardSpellsGui() {
        return wizardSpellsGui;
    }
}
