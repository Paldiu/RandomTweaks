package ns.jovial.randomtweaks.exception;

import ns.jovial.randomtweaks.tweaks.Messages;

public class PlayerNotFoundException extends Exception {
    public PlayerNotFoundException() {
        super(Messages.PLAYER_NOT_FOUND);
    }

    public PlayerNotFoundException(String ex) {
        super(ex);
    }
}
