package src.main.jogo.services;

import src.main.jogo.models.GameBoard;
import src.main.jogo.views.GameBoardView;

public class GameBoardService {
    private final GameBoardView gameBoardView;
    public GameBoardService(){
        this.gameBoardView = new GameBoardView();
    }
    public GameBoard create(GameBoard gameBoard){
        int counter = 0;
        for(int i = 0; i < gameBoard.getGameBoard().length; i++){
            for (int j = 0; j < gameBoard.getGameBoard().length; j++){
                gameBoard.getGameBoard()[i][j] = String.valueOf (counter++);
            }
        }
        return gameBoard;
    }
    public void showEmptyBoard(GameBoard gameBoard) {
        gameBoardView.showEmptyBoard(gameBoard);
    }


}
