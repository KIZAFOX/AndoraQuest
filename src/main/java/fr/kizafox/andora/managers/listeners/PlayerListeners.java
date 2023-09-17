package fr.kizafox.andora.managers.listeners;

import fr.kizafox.andora.Andora;
import fr.kizafox.andora.tools.database.requests.user.UserAccount;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Random;

/**
 * Change this line to a short description of the class
 *
 * @author : KIZAFOX
 * @date : 15/09/2023
 * @project : AndoraQuest
 */
public class PlayerListeners implements Listener {

    protected final Andora instance;

    public PlayerListeners(final Andora instance) {
        this.instance = instance;
    }

    @EventHandler
    public void onLogin(final PlayerJoinEvent event){
        final Player player = event.getPlayer();
        final UserAccount userAccount = new UserAccount(this.instance, player);

        userAccount.initialize();
        player.sendMessage(ChatColor.YELLOW + "Hello, " + ChatColor.AQUA + player.getName() + ChatColor.YELLOW + " you have " + ChatColor.AQUA + userAccount.getMoney() + ChatColor.YELLOW + "€ in your balance!");
    }
    @EventHandler
    public void onLogout(final PlayerQuitEvent event){
        final Player player = event.getPlayer();
        UserAccount.getUserAccount(player).delete();
    }

    @EventHandler
    public void onBlockBreak(final BlockBreakEvent event){
        final Player player = event.getPlayer();
        final UserAccount userAccount = UserAccount.getUserAccount(player);

        userAccount.addMoney(new Random().nextDouble(10));
        player.sendMessage(ChatColor.AQUA + "You have now " + ChatColor.BLUE + userAccount.getMoney() + ChatColor.AQUA + "€ in your balance!");
    }
}
