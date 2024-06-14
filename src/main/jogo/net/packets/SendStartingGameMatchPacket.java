package src.main.jogo.net.packets;

import src.main.jogo.models.GameMatch;

public class SendStartingGameMatchPacket extends ClientPacket {
    GameMatch gameMatch;
    public SendStartingGameMatchPacket(GameMatch gameMatch){
        this.gameMatch = gameMatch;
    }
}
