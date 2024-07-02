package src.main.jogo.services;

import src.main.jogo.models.GameBoard;
import src.main.jogo.models.GameMatch;
import src.main.jogo.views.GameBoardView;
import src.main.jogo.views.GameMovePlayerView;

public class GameMatchService {
    private final GameMatch gameMatch;
    private final GameBoardView gameBoardView;
    private final GameMovePlayerView gameMovePlayerView;

    public GameMatchService(GameMatch gameMatch) {
        this.gameMatch = gameMatch;
        this.gameBoardView = new GameBoardView();
        this.gameMovePlayerView = new GameMovePlayerView();
    }

    public GameMatch getGameMatch() {
        return gameMatch;

    }

    public void startingGameBoard() {
        System.out.println("COMECANDO PARTIDA...");
        gameBoardView.showEmptyBoard(gameMatch.getGameBoard());

    }

    public String handleChoicePosition() {
        String position = gameMovePlayerView.choicePositionPlayer();
        while (!gameMatch.getGameBoard().isValidPosition(position)) {
            System.out.println("posicao invalida!!");
            position = gameMovePlayerView.choicePositionPlayer();
        }
        return position;
    }

    public String handleChoiceXO(String hostXO) {
        String choiceXO = gameMovePlayerView.choiceXO(hostXO);
        return choiceXO;
    }

    public void handleShowBoardState(GameBoard gameBoard) {
        gameBoardView.showGameBoard(gameBoard.getGameBoard());
    }
}
