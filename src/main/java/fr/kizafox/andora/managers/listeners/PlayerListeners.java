package fr.kizafox.andora.managers.listeners;

import fr.kizafox.andora.Andora;
import fr.kizafox.andora.tools.TexturePack;
import fr.kizafox.andora.tools.database.requests.user.UserAccount;
import fr.kizafox.andora.tools.game.tasks.NewPlayerTask;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Arrays;
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

    @EventHandler (priority = EventPriority.MONITOR)
    public void onLogin(final PlayerJoinEvent event){
        final Player player = event.getPlayer();
        final UserAccount account = new UserAccount(this.instance, player);

        if(!account.hasAccount()){
            event.setJoinMessage(ChatColor.LIGHT_PURPLE + "Bienvenue sur le serveur " + player.getName() + " !");
            new NewPlayerTask(this.instance, player).runTaskTimer(this.instance, 20L, 20L);
        }else{
            event.setJoinMessage(ChatColor.GRAY + "[" + ChatColor.GREEN + "+" + ChatColor.GRAY + "] " + player.getName());

            this.instance.getServer().getScheduler().runTaskAsynchronously(this.instance, account::initialize);

            this.instance.getManagers().getBoard().onLogin(event);

            TexturePack.load(player);
        }
    }

    @EventHandler (priority = EventPriority.MONITOR)
    public void onLogout(final PlayerQuitEvent event){
        final Player player = event.getPlayer();

        event.setQuitMessage(ChatColor.GRAY + "[" + ChatColor.RED + "-" + ChatColor.GRAY + "] " + player.getName());

        UserAccount.getUserAccount(player).delete();
        this.instance.getManagers().getBoard().onLogout(event);
    }

    @EventHandler (priority = EventPriority.MONITOR)
    public void onAsyncPlayerChat(final AsyncPlayerChatEvent event){
        final Player player = event.getPlayer();
        final UserAccount account = UserAccount.getUserAccount(player);

        event.setCancelled(true);

        Bukkit.getOnlinePlayers().forEach(players -> {
            if(players == null) return;
            if(UserAccount.getUserAccount(players).hasAccount()){
                players.sendMessage(account.getClassUnit().getName() + " " + ChatColor.GRAY + player.getName() + ChatColor.DARK_GRAY + " » " + ChatColor.WHITE + event.getMessage());
            }
        });
    }

    @EventHandler (priority = EventPriority.MONITOR)
    public void onBlockBreak(final BlockBreakEvent event){
        final Player player = event.getPlayer();
        final UserAccount userAccount = UserAccount.getUserAccount(player);

        userAccount.addMoney(new Random().nextDouble(10));
        player.sendMessage(ChatColor.AQUA + "You have now " + ChatColor.BLUE + userAccount.getMoney() + ChatColor.AQUA + "€ in your balance!");
    }
}
