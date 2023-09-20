package fr.kizafox.andora.tools.game;

import fr.kizafox.andora.Andora;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Change this line to a short description of the class
 *
 * @author : KIZAFOX
 * @date : 19/09/2023
 * @project : Andora
 */
public class GameManager {

    protected final Andora instance;

    private final Player player;

    public GameManager(Andora instance, Player player) {
        this.instance = instance;
        this.player = player;
    }

    public void hide(){
        Bukkit.getOnlinePlayers().forEach(players -> {
            players.hidePlayer(this.instance, this.player);
            this.player.hidePlayer(this.instance, players);
        });
    }

    public void show(){
        Bukkit.getOnlinePlayers().forEach(players -> {
            players.showPlayer(this.instance, this.player);
            player.showPlayer(this.instance, players);
        });
    }
}
