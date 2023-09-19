package fr.kizafox.andora;

import fr.kizafox.andora.managers.Managers;
import fr.kizafox.andora.tools.database.requests.skills.PlayerSkills;
import fr.kizafox.andora.tools.database.requests.user.UserAccount;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class Andora extends JavaPlugin {

    private static Andora instance;

    private List<UserAccount> userAccounts;
    private List<PlayerSkills> playerSkills;

    private Managers managers;

    @Override
    public void onEnable() {
        instance = this;

        this.userAccounts = new ArrayList<>();
        this.playerSkills = new ArrayList<>();

        this.managers = new Managers(this);
    }

    public static Andora get() {
        return instance;
    }

    public static void sendLog(final String message){
        Bukkit.getConsoleSender().sendMessage(message);
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
