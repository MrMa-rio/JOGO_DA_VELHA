package src.main.jogo.net.packets;

import src.main.jogo.models.GameRoom;

public class SendCreateRoomPacket extends ClientPacket {

    private final GameRoom gameRoom;
    public SendCreateRoomPacket(GameRoom gameRoom) {
        this.gameRoom = gameRoom;
    }
    public GameRoom getGameRoom() {
        return gameRoom;
    }
}
