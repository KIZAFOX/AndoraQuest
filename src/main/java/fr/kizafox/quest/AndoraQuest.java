package fr.kizafox.quest;

import fr.kizafox.quest.managers.Managers;
import org.bukkit.plugin.java.JavaPlugin;

public final class AndoraQuest extends JavaPlugin {

    private static AndoraQuest instance;

    private Managers managers;

    @Override
    public void onEnable() {
        instance = this;

        this.managers = new Managers(this);
    }

    @Override
    public void onDisable() {

    }

    public static AndoraQuest get() {
        return instance;
    }

    public Managers getManagers() {
        return managers;
    }
}
