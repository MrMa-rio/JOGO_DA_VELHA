package src.main.jogo.net.packets;

public class SendMessagePacket extends ClientPacket {
   private static final long serialVersionUID = -710902470934092114L;
    private final String message;
    public SendMessagePacket(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
}
