package fr.kizafox.andora.tools.gui.inventories;

import fr.kizafox.andora.Andora;
import fr.kizafox.andora.tools.gui.inventories.classes.ClassesGui;
import fr.kizafox.andora.tools.gui.inventories.skills.SkillsGui;
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

    public InventoryManager(final Andora instance) {
        this.instance = instance;

        this.skillsGui = new SkillsGui(this.instance, 9 * 6, ChatColor.YELLOW + "Augmente tes compétences");
        this.classesGui = new ClassesGui(this.instance, 9 * 4, ChatColor.YELLOW + "Choisi une classe");
    }

    public SkillsGui getSkillsGui() {
        return skillsGui;
    }

    public ClassesGui getClassesGui() {
        return classesGui;
    }
}
