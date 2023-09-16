package fr.kizafox.quest.tools.quest;

import java.util.UUID;

/**
 * Change this line to a short description of the class
 *
 * @author : KIZAFOX
 * @date : 15/09/2023
 * @project : Quest
 */
public record Quest(UUID questUUID, String name, String description, float goldReward, QuestStatus questStatus) {

    public enum QuestStatus {
        AVAILABLE,
        IN_PROGRESS,
        DONE,
        UNAVAILABLE
    }
}
