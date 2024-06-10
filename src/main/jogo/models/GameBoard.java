package src.main.jogo.models;

public class GameBoard {
    String[][] gameBoard;
    public GameBoard(){
         this.gameBoard = new String[3][3];
    }

    public String[][] getGameBoard() {
        return gameBoard;
    }

    public void setGameBoard(String[][] gameBoard) {
        this.gameBoard = gameBoard;
    }
}
