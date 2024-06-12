package src.main.jogo.net.packets;

import src.main.jogo.models.GameRoom;

public class SendGameRoomPacket extends ClientPacket {
    private final GameRoom gameRoom;

    public SendGameRoomPacket(GameRoom gameRoom) {
        this.gameRoom = gameRoom;
    }
    public GameRoom getGameRoom() {
        return gameRoom;
    }
}
