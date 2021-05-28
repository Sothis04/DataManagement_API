package fr.realcraft.plugin.data.commons;

import java.util.UUID;

public class Profile implements Cloneable {

    private int id;
    private UUID uuid;
    private String playerName;
    private double coins;

    public Profile() {

    }

    public Profile(int id, UUID uuid, String playerName, double coins) {
        this.id = id;
        this.uuid = uuid;
        this.playerName = playerName;
        this.coins = coins;
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

    public double getCoins() {
        return coins;
    }

    public void setCoins(double coins) {
        this.coins = coins;
    }

    public static Profile createDefaultProfile() {
        return new Profile(1,UUID.randomUUID(), "Default", 0.0);
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
