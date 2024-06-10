package src.main.jogo;


import src.main.jogo.services.GameBoardService;
import src.main.jogo.services.GameManagerService;
import src.main.jogo.views.GameManagerView;

import java.io.IOException;

public class Main {
    public static void main(final String[] args) throws IOException {
        GameManagerView gameManagerView = new GameManagerView();
        gameManagerView.startGame();
    }
}