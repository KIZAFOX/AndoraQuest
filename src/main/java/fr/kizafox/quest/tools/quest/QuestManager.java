package fr.kizafox.quest.tools.quest;

import fr.kizafox.quest.AndoraQuest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Change this line to a short description of the class
 *
 * @author : KIZAFOX
 * @date : 15/09/2023
 * @project : AndoraQuest
 */
public class QuestManager {

    protected final AndoraQuest instance;

    public static final List<Quest> quests = new ArrayList<>();

    public QuestManager(final AndoraQuest instance) {
        this.instance = instance;
    }

    public void create(final String name, final String description, float goldReward){
        quests.add(new Quest(UUID.randomUUID(), name, description, goldReward, Quest.QuestStatus.AVAILABLE));
    }

    public static List<Quest> getQuests() {
        return quests;
    }
}
