package src.main.jogo.net.packets;

public class SendDisconnectPacket extends ClientPacket {
    private static final long serialVersionUID = -3675889282016261921L;
    public final String message;

    public SendDisconnectPacket(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
