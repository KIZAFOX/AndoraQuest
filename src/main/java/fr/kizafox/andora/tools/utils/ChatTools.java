package fr.kizafox.andora.tools.utils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * Change this line to a short description of the class
 *
 * @author : KIZAFOX
 * @date : 17/09/2023
 * @project : Andora
 */
public class ChatTools {

    public static final String PREFIX = ChatColor.GRAY + "[" + ChatColor.GOLD + ChatColor.BOLD + "Andora" + ChatColor.RESET + ChatColor.GRAY + "] ";

    public static void sendMessage(final Player player, String message){
        player.sendMessage(PREFIX + message);
    }
}
