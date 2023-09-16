package fr.kizafox.quest.managers;

import fr.kizafox.quest.AndoraQuest;
import fr.kizafox.quest.managers.commands.QuestCommand;
import fr.kizafox.quest.managers.listeners.PlayerListeners;
import fr.kizafox.quest.tools.quest.QuestManager;
import fr.kizafox.quest.tools.quest.json.QuestSerialize;

import java.util.Objects;

/**
 * Change this line to a short description of the class
 *
 * @author : KIZAFOX
 * @date : 15/09/2023
 * @project : AndoraQuest
 */
public class Managers {

    protected final AndoraQuest instance;

    private final QuestSerialize questSerialize;
    private final QuestManager questManager;

    public Managers(final AndoraQuest instance){
        this.instance = instance;

        this.questSerialize = new QuestSerialize(this.instance);
        this.questSerialize.load();
        this.questManager = new QuestManager(this.instance);

        this.instance.getServer().getPluginManager().registerEvents(new PlayerListeners(this.instance), this.instance);
        Objects.requireNonNull(this.instance.getCommand("quest")).setExecutor(new QuestCommand(this.instance));
    }

    public QuestSerialize getQuestSerialize() {
        return questSerialize;
    }

    public QuestManager getQuestManager() {
        return questManager;
    }
}
