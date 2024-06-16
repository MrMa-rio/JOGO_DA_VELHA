package src.main.jogo.views;

import src.main.jogo.models.GameBoard;

import java.util.Scanner;

public class GameMatchView {
    private final GameBoardView gameBoardView;
    Scanner scanner;
    public GameMatchView(){
        this.gameBoardView = new GameBoardView();
        this.scanner = new Scanner(System.in);
    }
    public void startingGame(GameBoard gameBoard){
        gameBoardView.showEmptyBoard(gameBoard);
    }
}
