package fr.kizafox.andora.managers.commands.helper;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Change this line to a short description of the class
 *
 * @author : KIZAFOX
 * @date : 17/09/2023
 * @project : Andora
 */
public abstract class SubCommand {

    public abstract String getName();

    public abstract List<String> getAliases();

    public abstract String getDescription();

    public abstract String getSyntax();

    public abstract void perform(final CommandSender sender, final String[] args);

    public abstract List<String> getSubCommandArguments(final Player player, final String[] args);
}
