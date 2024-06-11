package src.main.jogo.services;

import src.main.jogo.models.GameMatch;
import src.main.jogo.models.GameRoom;

public class GameMatchService {
    private GameMatch gameMatch;

    public GameMatchService(GameRoom gameRoom){
        gameMatch.setGameRoom(gameRoom);
        //2gameMatch.setPlayerInListPlayers();
    }
}
