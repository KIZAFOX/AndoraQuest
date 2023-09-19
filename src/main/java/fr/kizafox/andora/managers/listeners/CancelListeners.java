package fr.kizafox.andora.managers.listeners;

import fr.kizafox.andora.Andora;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.Arrays;
import java.util.List;

/**
 * Change this line to a short description of the class
 *
 * @author : KIZAFOX
 * @date : 17/09/2023
 * @project : Andora
 */
public class CancelListeners implements Listener {

    protected final Andora instance;

    public CancelListeners(final Andora instance) {
        this.instance = instance;
    }

    @EventHandler
    public void onCommandUse(final PlayerCommandPreprocessEvent event) {
        final List<String> commands = Arrays.asList("?", "pl", "about", "version", "ver", "plugins", "reload", "bukkit:?", "bukkit:pl", "bukkit:about", "bukkit:version", "bukkit:ver", "bukkit:plugins", "bukkit:reload", "minecraft:pl", "minecraft:plugins", "minecraft:about", "minecraft:version", "minecraft:ver");
        commands.forEach(all -> {
            String[] arrCommand = event.getMessage().toLowerCase().split(" ", 2);
            if (arrCommand[0].equalsIgnoreCase("/" + all.toLowerCase())) {
                event.setCancelled(true);
            }
        });
    }
}
