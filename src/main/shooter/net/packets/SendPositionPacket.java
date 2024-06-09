package src.main.shooter.net.packets;

import src.main.shooter.game.action.SendMessage;

public class SendPositionPacket extends ClientPacket {
    public static final long serialVersionUID = -710902470934092114L;
    public final SendMessage SendMessage;

    public SendPositionPacket(SendMessage message) {
        this.SendMessage = message;
    }
}
