package fr.kizafox.andora.managers.commands;

import org.bukkit.command.CommandSender;

import java.util.List;

/**
 * Change this line to a short description of the class
 *
 * @author : KIZAFOX
 * @date : 17/09/2023
 * @project : Andora
 */
@FunctionalInterface
public interface CommandList {

    void displayCommandList(final CommandSender sender, final List<SubCommand> subCommandList);
}
