package fr.kizafox.andora.managers;

import fr.kizafox.andora.Andora;
import fr.kizafox.andora.managers.commands.*;
import fr.kizafox.andora.managers.commands.helper.CommandManager;
import fr.kizafox.andora.managers.listeners.CancelListeners;
import fr.kizafox.andora.managers.listeners.PlayerListeners;
import fr.kizafox.andora.tools.database.DBHandler;
import fr.kizafox.andora.tools.database.requests.DBQuery;
import fr.kizafox.andora.tools.database.requests.classes.spells.wizard.SpellsManager;
import fr.kizafox.andora.tools.database.requests.classes.spells.wizard.spells.fireball.event.FireballListener;
import fr.kizafox.andora.tools.database.requests.classes.spells.wizard.spells.teleport.event.TeleportListener;
import fr.kizafox.andora.tools.database.requests.user.UserAccount;
import fr.kizafox.andora.tools.gui.inventories.InventoryManager;
import fr.kizafox.andora.tools.scoreboard.Board;
import org.bukkit.*;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Change this line to a short description of the class
 *
 * @author : KIZAFOX
 * @date : 15/09/2023
 * @project : AndoraQuest
 */
public class Managers {

    protected final Andora instance;

    protected World world;

    protected DBHandler dbHandler;

    protected final InventoryManager inventoryManager;
    protected final Board board;
    protected final SpellsManager spellsManager;

    public Managers(final Andora instance){
        this.instance = instance;

        this.world = this.instance.getServer().getWorlds().get(0);
        this.world.setGameRule(GameRule.RANDOM_TICK_SPEED, 0);
        this.world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
        this.world.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);
        this.world.setDifficulty(Difficulty.PEACEFUL);
        this.world.setWeatherDuration(0);
        this.world.setTime(6000L);

        this.dbHandler = new DBHandler.Builder().addURL("andora").build();

        new DBQuery(this.dbHandler.pool().getDataSource()).initializeDB();

        if(!Bukkit.getOnlinePlayers().isEmpty()){
            Instant start = Instant.now();

            Bukkit.getOnlinePlayers().forEach(players -> {
                new UserAccount(this.instance, players).initialize();
                if(players.isOp()){
                    final PluginDescriptionFile description = this.instance.getDescription();

                    players.sendMessage(ChatColor.DARK_GRAY + "" + ChatColor.STRIKETHROUGH + "----------------------------------------");
                    players.sendMessage(" ");
                    players.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + description.getName() + ChatColor.YELLOW + " (" + ChatColor.GOLD + description.getVersion() + ChatColor.YELLOW + ") par " + ChatColor.GOLD + description.getAuthors().get(0));
                    players.sendMessage(ChatColor.YELLOW + "Lien vers le site: " + ChatColor.GOLD + description.getWebsite());
                    players.sendMessage(" ");
                    players.sendMessage(ChatColor.YELLOW + "Le plugin a été rechargé en " + ChatColor.GOLD + (Duration.between(start, Instant.now())).toMillis() + ChatColor.YELLOW + "ms.");
                    players.sendMessage(ChatColor.DARK_GRAY + "" + ChatColor.STRIKETHROUGH + "----------------------------------------");
                }
            });
        }

        this.inventoryManager = new InventoryManager(this.instance);
        this.board = new Board(this.instance);
        this.spellsManager = new SpellsManager(this.instance);

        this.registerListeners();
        this.registerCommands();
    }

    public void uninject(){
        this.instance.getServer().getScheduler().runTaskAsynchronously(this.instance, () -> {
            if(!Bukkit.getOnlinePlayers().isEmpty()){
                Bukkit.getOnlinePlayers().forEach(players -> UserAccount.getUserAccount(players).delete());
            }

            this.dbHandler.pool.closePool();
        });
    }

    private void registerListeners() {
        final List<Listener> listeners = Arrays.asList(
                new PlayerListeners(this.instance),
                new CancelListeners(this.instance),
                new FireballListener(this.instance),
                new TeleportListener(this.instance)
        );

        listeners.forEach(listener -> this.instance.getServer().getPluginManager().registerEvents(listener, this.instance));
    }

    private void registerCommands() {
        try {
            CommandManager.createCoreCommand(this.instance, "andora", "Description de la commande Andora", "/andora", (sender, list) -> {
                sender.sendMessage(ChatColor.DARK_GRAY + "" + ChatColor.STRIKETHROUGH + "-------------------------------");
                list.forEach(subCommand -> sender.sendMessage(ChatColor.YELLOW + subCommand.getSyntax() + " - " + ChatColor.WHITE + subCommand.getDescription()));
                sender.sendMessage(ChatColor.DARK_GRAY + "" + ChatColor.STRIKETHROUGH + "-------------------------------");
            }, Collections.singletonList("andora"), SkillsCommand.class, ClassesCommand.class, SpellsCommand.class, PackCommand.class, ReloadCommand.class, ResetCommand.class);
        }catch (NoSuchFieldException | IllegalAccessException e){
            throw new RuntimeException(e);
        }
    }

    public DBHandler getDbHandler() {
        return dbHandler;
    }

    public InventoryManager getInventoryManager() {
        return inventoryManager;
    }

    public Board getBoard() {
        return board;
    }

    public SpellsManager getSpellsManager() {
        return spellsManager;
    }
}
