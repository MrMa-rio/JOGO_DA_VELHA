package src.main.jogo.net.packets;

public class DisconnectPacket extends ClientPacket {
    private static final long serialVersionUID = -3675889282016261921L;
    public final String message;

    public DisconnectPacket(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
