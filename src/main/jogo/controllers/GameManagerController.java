package src.main.jogo.controllers;

import src.main.jogo.services.GamePlayerService;

public class GameManagerController {

    GamePlayerService gamePlayerService = new GamePlayerService();
    public boolean handleGameModelSelected(int gameModeSelected) {

        switch (gameModeSelected){
            case 1:
                gamePlayerService.handleGetGameRooms();
                break;
            case 2:
                gamePlayerService.handleCreatingRoom();
                System.out.println("Entrando em novo jogo online...");
                break;
            case 3:

                System.out.println("Entrando em um jogo online...");
                gamePlayerService.handleEnteringRoom();
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
