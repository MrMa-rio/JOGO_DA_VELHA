package src.main.jogo.net.packets;

import src.main.jogo.models.PlayerInMatch;

public class SendStateGameBoardPacket extends ClientPacket {
    private static final long serialVersionUID = -710902470934092114L;
    private String position = "";
    private String codeRoom;
    private final PlayerInMatch playerInMatch;
    private String hostXO;
    private final boolean isFirstMove;

    public SendStateGameBoardPacket(PlayerInMatch playerInMatch){
        this.isFirstMove = true;
        this.playerInMatch = playerInMatch;
    }
    public SendStateGameBoardPacket(PlayerInMatch playerInMatch, String codeRoom, String position, String hostXO) {
        this.codeRoom = codeRoom;
        this.position = position;
        this.playerInMatch = playerInMatch;
        this.isFirstMove = false;
        this.hostXO = hostXO;
    }
    public String getPosition() {
        return position;
    }
    public boolean getIsFirstMove(){
        return isFirstMove;
    }
    public String getCodeRoom() {
        return codeRoom;
    }
    public String getHostXO() {
        return hostXO;
    }
    public PlayerInMatch getPlayerInMatch() {
        return playerInMatch;
    }
}
