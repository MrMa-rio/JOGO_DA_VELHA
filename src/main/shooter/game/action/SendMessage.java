package src.main.shooter.game.action;

import java.io.Serializable;
import java.util.ArrayList;

import src.main.shooter.utils.ArraySet;

/**
 * More about actions are in the {@code server} class, but instant actions are
 * done instantly and long actions are done every tick until it is told not to
 * be done.
 * 
 * To clear actions or add or remove, do getInstantActions and directly
 * call functions on them such as add() or clear() or
 * 
 * @see src.main.shooter.net.Server
 * @see java.util.ArrayList
 */
public class SendMessage implements Serializable {
    private static final long serialVersionUID = -4852037557772448218L;

    private String message;

    public SendMessage(String message) {
        this.message = message;
    }


    public String getMessage() {
        return message;
    }
}
