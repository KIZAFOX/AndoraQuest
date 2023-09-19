package fr.kizafox.andora.managers.commands;

import fr.kizafox.andora.Andora;
import fr.kizafox.andora.managers.commands.helper.SubCommand;
import fr.kizafox.andora.tools.database.requests.classes.ClassUnit;
import fr.kizafox.andora.tools.database.requests.skills.PlayerSkills;
import fr.kizafox.andora.tools.database.requests.user.UserAccount;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
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
public class ResetCommand extends SubCommand {

    private final Andora instance = Andora.get();

    @Override
    public String getName() {
        return "reset";
    }

    @Override
    public List<String> getAliases() {
        return Collections.singletonList("reset");
    }

    @Override
    public String getDescription() {
        return "Se reset soit même";
    }

    @Override
    public String getSyntax() {
        return "/andora reset";
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        if(!(sender instanceof final Player player)) return;

        player.setFoodLevel(20);
        player.setWalkSpeed(0.20F);
        player.setFlySpeed(0.15F);
        player.setAllowFlight(false);
        player.setMaxHealth(20.D);
        player.setExp(0.0F);
        player.setLevel(0);
        player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(1);

        PlayerSkills playerSkills = UserAccount.getUserAccount(player).getPlayerSkills();
        playerSkills.setHealthBonus(0.0D);
        playerSkills.setSkillPoint(10);
        playerSkills.setDamageBonus(0.0D);

        UserAccount.getUserAccount(player).setClassUnit(ClassUnit.PEASANT);

        player.sendMessage(ChatColor.DARK_RED + "Vous avez été reset !");
    }

    @Override
    public List<String> getSubCommandArguments(Player player, String[] args) {
        return null;
    }
}
