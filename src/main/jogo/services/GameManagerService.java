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

    public void setGameRoomsInList(GameRoom gameRoom) {
        this.listGameRooms.add(gameRoom);
    }
    public void setGuestPlayersInList(Player player) {
        this.guestPlayers.add(player);
    }
    public ArrayList<GameRoom> getListGameRooms() {
        return listGameRooms;
    }
    public void showListGameRooms(){
        gameManagerView.showListGameRooms(listGameRooms);
    }
    public void showListGuestPlayers(){
        gameManagerView.showListGuestPlayersConnected(guestPlayers);
    }
    public void handleCreateRoom(GameRoom gameRoom) {
        setGameRoomsInList(gameRoom);
        showListGameRooms();
    }
    public void existRoom(String codeRoom){
        //gitif(listGameRooms.isEmpty()) return false;
       // else if (listGameRooms.stream().map(() -> )) {
            
        //}
    }
}