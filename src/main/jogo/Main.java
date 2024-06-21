package src.main.jogo;

import src.main.jogo.services.GamePlayerService;
import src.main.jogo.views.GameManagerView;
import java.io.IOException;

public class Main {
    static GamePlayerService gamePlayerService = new GamePlayerService();
    public static void main(final String[] args) throws IOException {
        GameManagerView gameManagerView = new GameManagerView();
        gamePlayerService.handleStartingPlayer();
        gameManagerView.startGame();
    }
}