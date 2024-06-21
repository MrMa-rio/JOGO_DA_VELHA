package src.main.jogo.net.packets;

public class SendEnterRoomPacket extends ClientPacket {

    private final String codeRoom;
    public SendEnterRoomPacket(String codeRoom) {
        this.codeRoom = codeRoom;
    }
    public String getCodeRoom() {
        return codeRoom;
    }
}
