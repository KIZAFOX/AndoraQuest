package fr.kizafox.andora.managers.commands;

import fr.kizafox.andora.Andora;
import fr.kizafox.andora.managers.commands.helper.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

/**
 * Change this line to a short description of the class
 *
 * @author : KIZAFOX
 * @date : 17/09/2023
 * @project : Andora
 */
public class ReloadCommand extends SubCommand {

    private final Andora instance = Andora.get();

    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public List<String> getAliases() {
        return Collections.singletonList("reload");
    }

    @Override
    public String getDescription() {
        return "Recharge le plugin";
    }

    @Override
    public String getSyntax() {
        return "/andora reload";
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        if(!(sender instanceof Player)) return;

        this.instance.getManagers().uninject();
        this.instance.getServer().getScheduler().runTaskLater(this.instance, () -> this.instance.getServer().reload(), 20L * 2);
    }

    @Override
    public List<String> getSubCommandArguments(Player player, String[] args) {
        return null;
    }
}
