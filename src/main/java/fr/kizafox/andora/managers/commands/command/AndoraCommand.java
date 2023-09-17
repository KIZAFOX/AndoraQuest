package fr.kizafox.andora.managers.commands.command;

import fr.kizafox.andora.Andora;
import fr.kizafox.andora.managers.commands.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.List;

/**
 * Change this line to a short description of the class
 *
 * @author : KIZAFOX
 * @date : 17/09/2023
 * @project : Andora
 */
public class AndoraCommand extends SubCommand {

    private final Andora instance = Andora.get();

    @Override
    public String getName() {
        return "skills";
    }

    @Override
    public List<String> getAliases() {
        return Collections.singletonList("skill");
    }

    @Override
    public String getDescription() {
        return "Description de la commande des compétences";
    }

    @Override
    public String getSyntax() {
        return "/andora skills";
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        if(!(sender instanceof final Player player)) return;

        this.instance.getManagers().getInventoryManager().getSkillsGui().display(player);
        player.sendMessage(ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + "Ouverture du menu des compétences...");
    }

    @Override
    public List<String> getSubCommandArguments(Player player, String[] args) {
        return null;
    }
}
