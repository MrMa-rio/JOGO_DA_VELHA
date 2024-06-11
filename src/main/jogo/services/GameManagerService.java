package src.main.jogo.services;

import src.main.jogo.models.GameBoard;
import src.main.jogo.models.Player;
import src.main.jogo.models.GameRoom;
import src.main.jogo.views.GameManagerView;

import java.util.ArrayList;

public class GameManagerService {
    private final ArrayList<Player> guestPlayers = new ArrayList<>();
    private final ArrayList<GameRoom> listGameRooms = new ArrayList<>();
    private final GameBoardService gameBoardService = new GameBoardService();
    private final GameManagerView gameManagerView = new GameManagerView();
    private final GameBoard gameBoard = new GameBoard();


    public void setGameRoomsOnList(GameRoom gameRoom) {
        this.listGameRooms.add(gameRoom);
    }

    public ArrayList<GameRoom> getListGameRooms() {
        return listGameRooms;
    }
    public void showListGameRooms(){
        gameManagerView.showListGameRooms(listGameRooms);
    }

    public void handleCreateRoom(GameRoom gameRoom) {

        setGameRoomsOnList(gameRoom);
        showListGameRooms();
    }
}
