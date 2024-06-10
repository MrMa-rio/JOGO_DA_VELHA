package src.main.jogo.net.packets;

public class SendPositionPacket extends ClientPacket {
    public static final long serialVersionUID = -710902470934092114L;
    private final String message;

    public SendPositionPacket(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
