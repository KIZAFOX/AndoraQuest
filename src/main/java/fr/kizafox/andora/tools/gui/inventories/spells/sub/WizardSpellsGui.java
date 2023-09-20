package fr.kizafox.andora.tools.gui.inventories.spells.sub;

import fr.kizafox.andora.Andora;
import fr.kizafox.andora.tools.ItemBuilder;
import fr.kizafox.andora.tools.base64.CustomSkull;
import fr.kizafox.andora.tools.database.requests.classes.ClassUnit;
import fr.kizafox.andora.tools.game.spells.wizard.WizardSpellUnit;
import fr.kizafox.andora.tools.gui.Gui;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

/**
 * Change this line to a short description of the class
 *
 * @author : KIZAFOX
 * @date : 20/09/2023
 * @project : Andora
 */
public class WizardSpellsGui extends Gui implements Listener {
    /**
     * Create a new Gui easily.
     *
     * @param instance This is the instance of the main class
     * @param size     This is the size of the Gui
     * @param title    This is the title of the Gui
     */
    public WizardSpellsGui(Andora instance, int size, String title) {
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

        Arrays.stream(WizardSpellUnit.values()).forEach(spells -> inventory.addItem(spells.getItemBuilder().toItemStack()));

        this.instance.getServer().getScheduler().runTask(this.instance, () -> player.openInventory(inventory));
    }

    /**
     * Interaction with the Gui
     *
     * @param event The InventoryClickEvent event from listener
     */
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals(this.getTitle())) {
            if (event.getCurrentItem() == null) return;

            final Player player = (Player) event.getWhoClicked();
            final ItemStack itemStack = event.getCurrentItem();

            Arrays.stream(WizardSpellUnit.values()).forEach(spells -> {
                if(itemStack.getItemMeta().getDisplayName().equals(spells.getItemBuilder().toItemStack().getItemMeta().getDisplayName())){
                    player.getInventory().addItem(spells.getItemBuilder().toItemStack());
                }
            });

            event.setCancelled(true);
        }
    }
}
