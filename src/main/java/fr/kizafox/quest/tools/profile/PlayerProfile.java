package fr.kizafox.quest.tools.profile;

import java.util.UUID;

/**
 * Change this line to a short description of the class
 *
 * @author : KIZAFOX
 * @date : 16/09/2023
 * @project : AndoraQuest
 */
public class PlayerProfile {

    private UUID uuid;
    private String name;

    public PlayerProfile(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
