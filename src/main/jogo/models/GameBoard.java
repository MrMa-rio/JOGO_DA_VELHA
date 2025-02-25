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
    public void setPosition(String position, String XO){
        for (String[] a : gameBoard) {
            for (int j = 0; j < gameBoard.length; j++) {
                if (Objects.equals((position), a[j])) {
                    a[j] = XO;
                    break;
                }
            }
        }
    }
    public boolean isValidPosition(String position) {
        for (String[] a : gameBoard) {
            for (int j = 0; j < gameBoard.length; j++) {
                if (Objects.equals((position), a[j])) {
                    return true;
                }
            }
        }
        return false;
    }
}