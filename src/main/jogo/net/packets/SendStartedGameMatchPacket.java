package src.main.jogo.net.packets;

import src.main.jogo.models.GameMatch;

public class SendStartedGameMatchPacket extends ClientPacket {
    private final GameMatch gameMatch;
    public SendStartedGameMatchPacket(GameMatch gameMatch){
        this.gameMatch = gameMatch;
    }
    public GameMatch getGameMatch() {
        return gameMatch;
    }
}
