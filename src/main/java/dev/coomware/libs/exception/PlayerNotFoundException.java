package dev.coomware.libs.exception;

import dev.coomware.libs.tweaks.Messages;

public class PlayerNotFoundException extends Exception {
    public PlayerNotFoundException() {
        super(Messages.PLAYER_NOT_FOUND);
    }

    public PlayerNotFoundException(String ex) {
        super(ex);
    }
}
