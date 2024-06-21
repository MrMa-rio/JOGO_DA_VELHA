package src.main.jogo.net.packets;


public class SendClientPacket extends ClientPacket {
    private final String clientId;
    public String getClientId() {
        return clientId;
    }
    public SendClientPacket(String clientId){
        this.clientId = clientId;
    }
}
