package src.main.jogo.net.packets;

public class SendWinLoseOrTiePacket extends ClientPacket {
    private final String message;
    public SendWinLoseOrTiePacket(String message){
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
}
