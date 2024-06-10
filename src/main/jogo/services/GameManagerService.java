package src.main.jogo.services;

import src.main.jogo.models.GameBoard;
import src.main.jogo.models.Player;
import src.main.jogo.models.GameRoom;

import java.util.ArrayList;

public class GameManagerService {
    private ArrayList<Player> guestPlayers;
    private ArrayList<GameRoom> listGameRooms;
    private final GameBoardService gameManagerService = new GameBoardService();
    private final GameBoard gameBoard = new GameBoard();

//    public void teste() {
//        gameManagerService.create();
//        gameManagerService.showEmptyBoard(gameBoard);
//    }

}
