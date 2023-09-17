package fr.kizafox.andora.managers.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Change this line to a short description of the class
 *
 * @author : KIZAFOX
 * @date : 17/09/2023
 * @project : Andora
 */
public class CoreCommand extends Command {

    private final List<SubCommand> subcommands;
    private final CommandList commandList;

    public CoreCommand(String name, String description, String usageMessage, CommandList commandList, List<String> aliases, List<SubCommand> subCommands) {
        super(name, description, usageMessage, aliases);

        this.subcommands = subCommands;
        this.commandList = commandList;
    }

    public List<SubCommand> getSubCommands() {
        return subcommands;
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (args.length > 0) {
            for (int i = 0; i < getSubCommands().size(); i++) {
                if (args[0].equalsIgnoreCase(getSubCommands().get(i).getName()) || (getSubCommands().get(i).getAliases() != null && getSubCommands().get(i).getAliases().contains(args[0]))) {
                    getSubCommands().get(i).perform(sender, args);
                }
            }
        } else {
            if (commandList == null) {
                sender.sendMessage("--------------------------------");
                subcommands.forEach(subCommand -> sender.sendMessage(subCommand.getSyntax() + " - " + subCommand.getDescription()));
                sender.sendMessage("--------------------------------");
            } else {
                commandList.displayCommandList(sender, subcommands);
            }
        }
        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        if (args.length == 1) {
            final List<String> subcommandsArguments = new ArrayList<>();

            for (int i = 0; i < getSubCommands().size(); i++) {
                subcommandsArguments.add(getSubCommands().get(i).getName());
            }
            return subcommandsArguments;
        } else if (args.length >= 2) {
            for (int i = 0; i < getSubCommands().size(); i++) {
                if (args[0].equalsIgnoreCase(getSubCommands().get(i).getName())) {
                    final List<String> subCommandArgs = getSubCommands().get(i).getSubCommandArguments((Player) sender, args);

                    if (subCommandArgs != null){
                        return subCommandArgs;
                    }
                    return Collections.emptyList();
                }
            }
        }
        return Collections.emptyList();
    }
}