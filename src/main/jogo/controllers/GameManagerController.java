package src.main.jogo.controllers;

import src.main.jogo.net.Client;
import src.main.jogo.views.GameBoardView;
import src.main.jogo.views.GameManagerView;

public class GameManagerController {
    private GameManagerView gameManagerView;
    GameBoardView gameBoardView = new GameBoardView();

    public GameManagerController(GameManagerView gameManagerView) {
        this.gameManagerView = gameManagerView;
    }

    public boolean handleGameModelSelected(int gameModeSelected) {
        switch (gameModeSelected){
            case 1:
                System.out.println("Entrando em novo jogo offline...");
                gameBoardView.mountGameBoard();
                break;
            case 2:
                System.out.println("Entrando em novo jogo online...");
                new Client("172.16.232.203", 1234).run();
                break;
            case 3:
                System.out.println("Entrando em uma sala...");
                break;
            case 0:
                System.out.println("Saindo do jogo...");

                break;
            default:
                System.out.println("Opçao Invalida!");

                return false;
        }
        return true;
    }
}
