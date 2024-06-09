package src.main.shooter.net.packets;

import src.main.shooter.game.action.SendMessage;

public class DisconnectPacket extends ClientPacket {
    private static final long serialVersionUID = -3675889282016261921L;
    public final SendMessage SendMessage;



    public DisconnectPacket(SendMessage message) {
        this.SendMessage = message;
    }
}
