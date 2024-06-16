package src.main.jogo.net.packets;

import src.main.jogo.models.GameBoard;

public class SendStateGameBoardPacket extends ClientPacket {
    public static final long serialVersionUID = -710902470934092114L;
    private String position = "";
    private String codeRoom;
    private String playerId;
    private String XO;
    private GameBoard gameBoard;
    private boolean isFirstMove;

    public SendStateGameBoardPacket(String playerId){
        this.isFirstMove = true;
        this.playerId = playerId;
    }
    public SendStateGameBoardPacket(String playerId, String codeRoom, String position, String XO) {
        this.codeRoom = codeRoom;
        this.position = position;
        this.XO = XO;
        this.isFirstMove = false;
        this.playerId = playerId;
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

    public String getXO() {
        return XO;
    }

    public String getCodeRoom() {
        return codeRoom;
    }

    public void setXO(String XO) {
        this.XO = XO;
    }

    public String getPlayerId() {
        return playerId;
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }
}
