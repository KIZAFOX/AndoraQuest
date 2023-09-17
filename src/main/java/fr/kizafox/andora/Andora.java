package fr.kizafox.andora;

import fr.kizafox.andora.managers.Managers;
import fr.kizafox.andora.tools.database.DBHandler;
import fr.kizafox.andora.tools.database.requests.DBQuery;
import fr.kizafox.andora.tools.database.requests.skills.PlayerSkills;
import fr.kizafox.andora.tools.database.requests.user.UserAccount;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;

public final class Andora extends JavaPlugin {

    private static Andora instance;

    private DBHandler dbHandler;
    private List<UserAccount> userAccounts;
    private List<PlayerSkills> playerSkills;

    private Managers managers;

    @Override
    public void onEnable() {
        instance = this;

        this.dbHandler = new DBHandler.Builder().addURL("andora").build();

        this.userAccounts = new ArrayList<>();
        this.playerSkills = new ArrayList<>();

        new DBQuery(this.getDbHandler().pool().getDataSource()).initializeDB();

        this.managers = new Managers(this);
    }

    @Override
    public void onDisable() {
        this.dbHandler.pool.closePool();
    }

    public static Andora get() {
        return instance;
    }

    public static void sendLog(final String message){
        Bukkit.getConsoleSender().sendMessage(message);
    }

    public DBHandler getDbHandler() {
        return dbHandler;
    }

    public List<UserAccount> getUserAccounts() {
        return userAccounts;
    }

    public List<PlayerSkills> getPlayerSkills() {
        return playerSkills;
    }

    public Managers getManagers() {
        return managers;
    }

}
