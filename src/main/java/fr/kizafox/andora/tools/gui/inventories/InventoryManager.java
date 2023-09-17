package fr.kizafox.andora.tools.gui.inventories;

import fr.kizafox.andora.Andora;
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

    public InventoryManager(final Andora instance) {
        this.instance = instance;

        this.skillsGui = new SkillsGui(this.instance, 9 * 6, ChatColor.YELLOW + "Compétences");
    }

    public SkillsGui getSkillsGui() {
        return skillsGui;
    }
}
