package src.main.jogo.net.packets;

import src.main.jogo.models.GameRoom;
import src.main.jogo.models.Player;

public class SendPlayerEnteredRoomPacket extends ClientPacket {
    private final GameRoom gameRoom;
    private final Player guestPlayer;

    public GameRoom getGameRoom() {
        return gameRoom;
    }

    public Player getGuestPlayer() {
        return guestPlayer;
    }
    public SendPlayerEnteredRoomPacket(GameRoom gameRoom, Player guestPlayer){
        this.gameRoom = gameRoom;
        this.guestPlayer = guestPlayer;
    }
}
