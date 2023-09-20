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
 * @date : 20/09/2023
 * @project : Andora
 */
public class SpellsCommand extends SubCommand {

    private final Andora instance = Andora.get();

    @Override
    public String getName() {
        return "spells";
    }

    @Override
    public List<String> getAliases() {
        return Collections.singletonList("spells");
    }

    @Override
    public String getDescription() {
        return "Ouvre le menu des sorts";
    }

    @Override
    public String getSyntax() {
        return "/andora spells";
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        if(!(sender instanceof final Player player)) return;

        this.instance.getManagers().getInventoryManager().getSpellsGui().display(player);
        player.sendMessage(ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + "Ouverture du menu des sorts...");
    }

    @Override
    public List<String> getSubCommandArguments(Player player, String[] args) {
        return null;
    }
}
