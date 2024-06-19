package src.main.jogo.net.packets;

import src.main.jogo.models.GameMatch;

public class SendWinLoseOrTiePacket extends ClientPacket {
    private final String message;
    private final GameMatch gameMatch;
    public SendWinLoseOrTiePacket(String message, GameMatch gameMatch){
        this.message = message;
        this.gameMatch = gameMatch;
    }

    public String getMessage() {
        return message;
    }

    public GameMatch getGameMatch() {
        return gameMatch;
    }
}
