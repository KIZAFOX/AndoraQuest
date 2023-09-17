package fr.kizafox.andora.tools.gui.inventories.skills;

import fr.kizafox.andora.Andora;
import fr.kizafox.andora.tools.gui.Gui;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

/**
 * Change this line to a short description of the class
 *
 * @author : KIZAFOX
 * @date : 17/09/2023
 * @project : Andora
 */
public class SkillsGui extends Gui implements Listener {
    /**
     * Create a new Gui easily.
     *
     * @param instance This is the instance of the main class
     * @param size     This is the size of the Gui
     * @param title    This is the title of the Gui
     */
    public SkillsGui(Andora instance, int size, String title) {
        super(instance, size, title);

        this.instance.getServer().getPluginManager().registerEvents(this, this.instance);
    }

    /**
     * Show the Gui
     *
     * @param player The player
     */
    @Override
    public void display(Player player) {
        final Inventory inventory = Bukkit.createInventory(null, this.getSize(), this.getTitle());

        this.instance.getServer().getScheduler().runTask(this.instance, () -> player.openInventory(inventory));
    }

    /**
     * Interaction with the Gui
     *
     * @param event The InventoryClickEvent event from listener
     */
    @Override
    public void onInventoryClick(InventoryClickEvent event) {

    }
}
