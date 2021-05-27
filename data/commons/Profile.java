package fr.realcraft.plugin.data.commons;

import java.util.UUID;

public class Profile implements Cloneable {

    private int id;
    private UUID uuid;
    private String playerName;
    private int lapis_eco;

    public Profile() {

    }

    public Profile(int id, UUID uuid, String playerName, int lapis_eco) {
        this.id = id;
        this.uuid = uuid;
        this.playerName = playerName;
        this.lapis_eco = lapis_eco;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getLapis_eco() {
        return lapis_eco;
    }

    public void setLapis_eco(int lapis_eco) {
        this.lapis_eco = lapis_eco;
    }

    public static Profile createDefaultProfile() {
        return new Profile(1,UUID.randomUUID(), "Default", 0);
    }

    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (!(o instanceof Profile)) {
            return false;
        } else {
            return ((Profile) o).getId() == this.id;
        }
    }

    public Profile clone() {
        try {
            return (Profile) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return null;
    }
}
