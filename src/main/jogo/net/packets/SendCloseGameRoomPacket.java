package src.main.jogo.net.packets;

import src.main.jogo.models.GameRoom;

public class SendCloseGameRoomPacket extends ClientPacket {
    String codeRoom;
    public SendCloseGameRoomPacket(String codeRoom){
        this.codeRoom = codeRoom;
    }

    public String getCodeRoom() {
        return codeRoom;
    }
}
