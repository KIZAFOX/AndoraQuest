package fr.kizafox.andora.tools.scoreboard;

import fr.kizafox.andora.Andora;
import fr.kizafox.andora.tools.database.requests.skills.PlayerSkills;
import fr.kizafox.andora.tools.database.requests.user.UserAccount;
import fr.kizafox.andora.tools.utils.ChatTools;
import fr.mrmicky.fastboard.FastBoard;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Change this line to a short description of the class
 *
 * @author : KIZAFOX
 * @date : 17/09/2023
 * @project : Andora
 */
public class Board {

    protected final Andora instance;

    private final Map<UUID, FastBoard> boards = new HashMap<>();

    public Board(final Andora instance) {
        this.instance = instance;
        this.instance.getServer().getScheduler().runTaskTimer(this.instance, () -> this.boards.values().forEach(this::updateBoard), 0, 20);
    }

    public void onLogin(final PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        final FastBoard board = new FastBoard(player);

        board.updateTitle(ChatTools.PREFIX.replace("[", "").replace("]", ""));
        this.boards.put(player.getUniqueId(), board);
    }

    public void onLogout(final PlayerQuitEvent event) {
        final Player player = event.getPlayer();
        final FastBoard board = this.boards.remove(player.getUniqueId());

        if (board != null) board.delete();
    }

    private void updateBoard(final FastBoard board) {
        final PlayerSkills playerSkills = UserAccount.getUserAccount(board.getPlayer()).getPlayerSkills();

        board.updateLines(
                "",
                ChatColor.GOLD + "► Informations",
                ChatColor.YELLOW + "• Compte: " + ChatColor.WHITE + board.getPlayer().getName(),
                ChatColor.YELLOW + "• Classes: " + ChatColor.GRAY + UserAccount.getUserAccount(board.getPlayer()).getClassUnit().getName(),
                "",
                ChatColor.YELLOW + "• Point(s): " + ChatColor.LIGHT_PURPLE + playerSkills.getSkillPoint(),
                ChatColor.YELLOW + "• Dégât(s) bonus: " + ChatColor.LIGHT_PURPLE + playerSkills.getDamageBonus(),
                ChatColor.YELLOW + "• Vie(s) bonus: " + ChatColor.LIGHT_PURPLE + playerSkills.getHealthBonus(),
                "",
                ChatColor.YELLOW + "play.andora.fr"
        );
    }

    public Map<UUID, FastBoard> getBoards() {
        return boards;
    }
}