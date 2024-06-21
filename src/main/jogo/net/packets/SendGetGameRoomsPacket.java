package src.main.jogo.net.packets;

import src.main.jogo.models.GameRoom;

import java.util.ArrayList;

public class SendGetGameRoomsPacket extends ClientPacket{
    private ArrayList<GameRoom> gameRooms;

    public SendGetGameRoomsPacket(){}
    public SendGetGameRoomsPacket(ArrayList<GameRoom> gameRooms){
        this.gameRooms = gameRooms;
    }
    public ArrayList<GameRoom> getGameRooms() {
        return gameRooms;
    }
}
