package src.main.jogo.net.packets;

import src.main.jogo.models.Player;

public class SendPlayerPacket extends ClientPacket {
    Player player;
    public Player getPlayer() {
        return player;
    }
    public SendPlayerPacket(Player player){
        this.player = player;
    }
}
