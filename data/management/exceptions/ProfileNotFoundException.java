package fr.realcraft.plugin.data.management.exceptions;

import java.util.UUID;

public class ProfileNotFoundException extends Exception {

    public ProfileNotFoundException(UUID uuid) {
        super("The account (" + uuid.toString() + ") is not found");
    }
}
