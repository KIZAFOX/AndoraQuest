package fr.kizafox.andora.managers;

import fr.kizafox.andora.Andora;
import fr.kizafox.andora.managers.commands.CommandManager;
import fr.kizafox.andora.managers.commands.command.AndoraCommand;
import fr.kizafox.andora.managers.listeners.PlayerListeners;
import fr.kizafox.andora.tools.database.DBHandler;
import fr.kizafox.andora.tools.database.requests.DBQuery;
import fr.kizafox.andora.tools.database.requests.user.UserAccount;
import fr.kizafox.andora.tools.gui.inventories.InventoryManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Change this line to a short description of the class
 *
 * @author : KIZAFOX
 * @date : 15/09/2023
 * @project : AndoraQuest
 */
public class Managers {

    protected final Andora instance;

    protected final InventoryManager inventoryManager;

    public Managers(final Andora instance){
        this.instance = instance;

        this.inventoryManager = new InventoryManager(this.instance);

        this.registerListeners();
        this.registerCommands();
    }

    private void registerListeners() {
        this.instance.getServer().getPluginManager().registerEvents(new PlayerListeners(this.instance), this.instance);
    }

    private void registerCommands() {
        try {
            CommandManager.createCoreCommand(this.instance, "andora", "Description de la commande Andora", "/andora", (sender, list) -> {
                sender.sendMessage(ChatColor.DARK_GRAY + "" + ChatColor.STRIKETHROUGH + "-------------------------------");
                list.forEach(subCommand -> sender.sendMessage(ChatColor.YELLOW + subCommand.getSyntax() + " - " + ChatColor.WHITE + subCommand.getDescription()));
                sender.sendMessage(ChatColor.DARK_GRAY + "" + ChatColor.STRIKETHROUGH + "-------------------------------");
            }, Collections.singletonList("andora"), AndoraCommand.class);
        }catch (NoSuchFieldException | IllegalAccessException e){
            throw new RuntimeException(e);
        }
    }

    public InventoryManager getInventoryManager() {
        return inventoryManager;
    }
}
