package src.main.jogo.models;

import java.io.Serializable;
import java.util.Objects;

public class GameBoard implements Serializable {
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
//    public boolean setPosition(int x, int y, String value){
//        if(this.gameBoard[x][y].equals("X") || this.gameBoard[x][y].equals("Y")) return false;
//        this.gameBoard[x][y] = value;
//        return true;
//    }
    public void setPosition(String position, String XO){
        String player = position;
        for (String[] a : gameBoard) {
            for (int j = 0; j < gameBoard.length; j++) {
                if (Objects.equals((player), a[j])) {
                    a[j] = XO;
                    break;
                }
            }
        }
    }
    public String getPosition(int x, int y){
        return gameBoard[x][y];
    }
}
