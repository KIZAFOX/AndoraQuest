package fr.kizafox.andora.managers.commands;

import fr.kizafox.andora.Andora;
import fr.kizafox.andora.managers.commands.helper.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

/**
 * Change this line to a short description of the class
 *
 * @author : KIZAFOX
 * @date : 18/09/2023
 * @project : Andora
 */
public class ClassesCommand extends SubCommand {

    private final Andora instance = Andora.get();

    @Override
    public String getName() {
        return "classes";
    }

    @Override
    public List<String> getAliases() {
        return Collections.singletonList("classes");
    }

    @Override
    public String getDescription() {
        return "Ouvre le menu des classes";
    }

    @Override
    public String getSyntax() {
        return "/andora classes";
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        if(!(sender instanceof final Player player)) return;

        player.sendMessage(ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + "Ouverture du menu des classes...");
        this.instance.getManagers().getInventoryManager().getClassesGui().display(player);
    }

    @Override
    public List<String> getSubCommandArguments(Player player, String[] args) {
        return null;
    }
}
