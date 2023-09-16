package fr.kizafox.quest.managers.commands;

import fr.kizafox.quest.AndoraQuest;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Change this line to a short description of the class
 *
 * @author : KIZAFOX
 * @date : 16/09/2023
 * @project : AndoraQuest
 */
public class QuestCommand implements CommandExecutor {

    protected final AndoraQuest instance;

    public QuestCommand(final AndoraQuest instance) {
        this.instance = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof final Player player)) return true;

        if(args.length < 3){
            player.sendMessage(ChatColor.RED + "Usage: /quest <name> <description> <goldReward>");
            return true;
        }

        this.instance.getManagers().getQuestManager().create(args[0], args[1], Float.parseFloat(args[2]));
        this.instance.getManagers().getQuestSerialize().save();
        player.sendMessage(ChatColor.GREEN + "Quest created !");

        return false;
    }
}
