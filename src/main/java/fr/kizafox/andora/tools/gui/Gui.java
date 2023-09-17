package fr.kizafox.andora.tools.gui;

import fr.kizafox.andora.Andora;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * Change this line to a short description of the class
 *
 * @author : KIZAFOX
 * @date : 05/09/2023
 * @project : Cosmee-Lobby
 */
public abstract class Gui {

    protected final Andora instance;

    protected final int size;
    protected final String title;

    /**
     * Create a new Gui easily.
     *
     * @param instance This is the instance of the main class
     * @param size     This is the size of the Gui
     * @param title    This is the title of the Gui
     */
    public Gui(final Andora instance, final int size, final String title) {
        this.instance = instance;
        this.size = size;
        this.title = title;
    }

    /**
     * Create a new Gui easily.
     *
     * @param instance This is the instance of the main class
     * @param title    This is the title of the Gui
     */
    public Gui(final Andora instance, final String title) {
        this.instance = instance;
        this.size = 9;
        this.title = title;
    }

    /**
     * Show the Gui
     *
     * @param player The player
     */
    public abstract void display(final Player player);

    /**
     * Interaction with the Gui
     *
     * @param event The InventoryClickEvent event from listener
     */
    public abstract void onInventoryClick(final InventoryClickEvent event);

    public Andora get() {
        return instance;
    }

    public int getSize() {
        return size;
    }

    public String getTitle() {
        return title;
    }
}
