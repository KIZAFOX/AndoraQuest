package fr.kizafox.andora.managers.commands;

import fr.kizafox.andora.Andora;
import fr.kizafox.andora.managers.commands.helper.SubCommand;
import fr.kizafox.andora.tools.TexturePack;
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
public class PackCommand extends SubCommand {

    private final Andora instance = Andora.get();

    @Override
    public String getName() {
        return "pack";
    }

    @Override
    public List<String> getAliases() {
        return Collections.singletonList("pack");
    }

    @Override
    public String getDescription() {
        return "Télécharge le pack de texture";
    }

    @Override
    public String getSyntax() {
        return "/andora pack";
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        if(!(sender instanceof final Player player)) return;

        TexturePack.load(player);
    }

    @Override
    public List<String> getSubCommandArguments(Player player, String[] args) {
        return null;
    }
}
