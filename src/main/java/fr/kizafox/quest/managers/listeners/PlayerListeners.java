package fr.kizafox.quest.managers.listeners;

import fr.kizafox.quest.AndoraQuest;
import fr.kizafox.quest.tools.quest.QuestManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Change this line to a short description of the class
 *
 * @author : KIZAFOX
 * @date : 15/09/2023
 * @project : AndoraQuest
 */
public class PlayerListeners implements Listener {

    protected final AndoraQuest instance;

    public PlayerListeners(final AndoraQuest instance) {
        this.instance = instance;
    }

    @EventHandler
    public void onLogin(final PlayerJoinEvent event){
        final Player player = event.getPlayer();

        if(!QuestManager.getQuests().isEmpty()){
            QuestManager.getQuests().forEach(quest -> {
                player.sendMessage(ChatColor.DARK_GRAY + "" +  ChatColor.STRIKETHROUGH + "--------------------------------------------------");
                player.sendMessage(ChatColor.YELLOW + "UUID : " + ChatColor.GREEN + quest.questUUID());
                player.sendMessage(ChatColor.YELLOW + "Name : " + ChatColor.GREEN + quest.name());
                player.sendMessage(ChatColor.YELLOW + "Description : " + ChatColor.GREEN + quest.description());
                player.sendMessage(ChatColor.YELLOW + "Gold Reward : " + ChatColor.GREEN + quest.goldReward());
                player.sendMessage(ChatColor.YELLOW + "Status : " + ChatColor.GREEN + quest.questStatus());
                player.sendMessage(ChatColor.DARK_GRAY + "" +  ChatColor.STRIKETHROUGH + "--------------------------------------------------");
            });
        }else{
            player.sendMessage(ChatColor.RED + "No quests found !");
        }
    }
}
