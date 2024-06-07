package src.main.shooter.net.packets;

import src.main.shooter.game.ClientGame;
import src.main.shooter.game.action.SendMessage;

public class SendMessagePacket extends ClientPacket {
    private static final long serialVersionUID = -710902470934092114L;
    public final SendMessage SendMessage;



    public SendMessagePacket(SendMessage message) {
        this.SendMessage = message;
    }
}
