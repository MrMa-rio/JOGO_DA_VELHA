package src.main.jogo.net.packets;

import src.main.jogo.models.GameBoard;

public class SendGameBoardPacket extends ClientPacket {
    GameBoard gameBoard;
    public SendGameBoardPacket(GameBoard gameBoard){
        this.gameBoard = gameBoard;
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }
}
