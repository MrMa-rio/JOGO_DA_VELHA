package src.main.jogo.net.packets;

import src.main.jogo.models.GameBoard;
import src.main.jogo.models.Player;
import src.main.jogo.models.PlayerInMatch;

public class SendStateGameBoardPacket extends ClientPacket {
    public static final long serialVersionUID = -710902470934092114L;
    private String position = "";
    private String codeRoom;
    private PlayerInMatch playerInMatch;
    private String hostXO;
    private GameBoard gameBoard;
    private boolean isFirstMove;

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
    public SendStateGameBoardPacket(GameBoard gameBoard){
        this.gameBoard = gameBoard;
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
    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public String getHostXO() {
        return hostXO;
    }

    public PlayerInMatch getPlayerInMatch() {
        return playerInMatch;
    }
}
