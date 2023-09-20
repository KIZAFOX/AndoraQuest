package fr.kizafox.andora.tools.game.tasks;

import fr.kizafox.andora.Andora;
import fr.kizafox.andora.tools.TexturePack;
import fr.kizafox.andora.tools.database.requests.classes.ClassUnit;
import fr.kizafox.andora.tools.database.requests.user.UserAccount;
import fr.kizafox.andora.tools.game.GameManager;
import fr.kizafox.andora.tools.game.LocationUnit;
import fr.kizafox.andora.tools.utils.ChatTools;
import fr.mrmicky.fastboard.FastBoard;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Change this line to a short description of the class
 *
 * @author : KIZAFOX
 * @date : 19/09/2023
 * @project : Andora
 */
public class NewPlayerTask extends BukkitRunnable {

    protected final Andora instance;

    private final Player player;
    private final GameManager gameManager;

    public static int timer = 32;

    public NewPlayerTask(Andora instance, Player player) {
        this.instance = instance;

        this.player = player;
        this.gameManager = new GameManager(this.instance, this.player);
        this.gameManager.hide();

        this.player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, (int) (20L * 35), 9999));
    }

    /**
     * Runs this operation.
     */
    @Override
    public void run() {
        this.player.teleport(LocationUnit.BOAT_SPAWN.getLocation());
        switch (timer) {
            case 33 ->
                    this.player.sendTitle(ChatTools.PREFIX.replace("[", "").replace("]", ""), ChatColor.GREEN + "Les origines d'Andora", 20, 20 * 3, 20);
            case 30 -> {
                this.player.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + "Une poignée d’esclaves échappa à de redoutables tyrans et s’enfuit en quête d’un pays libre.");
                this.player.sendMessage(" ");
            }
            case 25 -> {
                this.player.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + "Ces courageux fugitifs durent alors franchir les Montagnes Grises où un terrible Dragon manqua de les réduire en cendres.");
                this.player.sendMessage(" ");
            }
            case 20 -> {
                this.player.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + "Mais leur chef Brandur l’affronta avec une telle volonté, une telle détermination, que le Dragon, pourtant immortel, commença à craindre pour sa vie.");
                this.player.sendMessage(" ");
            }
            case 15 -> {
                this.player.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + "Il permit alors à Brandur et à ses amis de passer, mais depuis ce jour, il envoyait de terribles Créatures pour se venger et tuer Brandur.");
                this.player.sendMessage(" ");
            }
            case 10 -> {
                this.player.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + "Les Légendes d’Andor commencent environ quatre-vingts ans après ces événements. Entre temps, Brandur est devenu Roi de la contrée et le pays a été baptisé Andor…");
                this.player.sendMessage(" ");
            }
            case 0 -> {
                this.instance.getServer().getScheduler().runTaskAsynchronously(this.instance, () -> new UserAccount(this.instance, this.player).initialize());

                player.setGameMode(GameMode.ADVENTURE);
                player.setFoodLevel(20);
                player.setWalkSpeed(0.20F);
                player.setFlySpeed(0.15F);
                player.setAllowFlight(false);
                player.setExp(0);
                player.setLevel(0);
                player.setHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getDefaultValue());

                this.player.teleport(LocationUnit.MAIN_TOWN.getLocation());
                this.player.sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + "/angora pack - Pour être plus immergé dans le jeu !");
                this.player.sendMessage(ChatColor.GREEN + "" + ChatColor.ITALIC + "Le début de votre aventure commence ici...");

                this.gameManager.show();

                final FastBoard board = new FastBoard(player);
                board.updateTitle(ChatTools.PREFIX.replace("[", "").replace("]", ""));
                this.instance.getManagers().getBoard().getBoards().put(player.getUniqueId(), board);

                TexturePack.load(player);

                this.cancel();
            }
        }

        switch (timer) {
            case 5, 3, 2, 1, 4 -> this.player.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + "Début de votre aventure dans " + timer + "s !");
        }

        timer--;
    }
}
