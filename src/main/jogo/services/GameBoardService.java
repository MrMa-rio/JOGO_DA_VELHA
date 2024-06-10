package src.main.jogo.services;

import src.main.jogo.models.GameBoard;
import src.main.jogo.views.GameBoardView;

public class GameBoardService {
    private GameBoard ticTacToeBoard;
    private GameBoardView gameBoardView;

    public GameBoardService(){
        this.gameBoardView = new GameBoardView();
    }

    public GameBoard getTicTacToeBoard() {
        return ticTacToeBoard;
    }

    public void setTicTacToeBoard(GameBoard ticTacToeBoard) {
        this.ticTacToeBoard = ticTacToeBoard;
    }

    public GameBoard create(){
        int counter = 0;
        for(int i = 0; i < ticTacToeBoard.getGameBoard().length; i++){
            for (int j = 0; j < ticTacToeBoard.getGameBoard().length; j++){
                ticTacToeBoard.getGameBoard()[i][j] = String.valueOf (counter++);
            }
        }
        return getTicTacToeBoard();
    }
    void showEmptyBoard(String[][] gameBoard) {
        gameBoardView.showEmptyBoard(gameBoard);
    }
}
