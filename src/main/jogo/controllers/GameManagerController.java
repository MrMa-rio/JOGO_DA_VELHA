package src.main.jogo.controllers;


import src.main.jogo.services.GameModeOnlineService;
import src.main.jogo.views.GameBoardView;
import src.main.jogo.views.GameManagerView;
import src.main.jogo.views.GameModeOnlineView;

public class GameManagerController {


    GameModeOnlineService gameModeOnlineService = new GameModeOnlineService();
    GameModeOnlineView gameModeOnlineView = new GameModeOnlineView();
    public boolean handleGameModelSelected(int gameModeSelected) {
        String ipAdress;
        int port;
        switch (gameModeSelected){
            case 1:
                System.out.println("Entrando em novo jogo offline...");
                break;
            case 2:

                do{
                    ipAdress = gameModeOnlineView.setIpAdress();
                    port = gameModeOnlineView.setPort();
                } while(!gameModeOnlineService.initializeClient(ipAdress, port));
                System.out.println("Entrando em novo jogo online...");
                gameModeOnlineService.createPlayer();
                gameModeOnlineService.createRoom();
                break;
            case 3:
                ipAdress = gameModeOnlineView.setIpAdress();
                port = gameModeOnlineView.setPort();
                gameModeOnlineService.initializeClient(ipAdress, port);
                gameModeOnlineService.createPlayer();
                gameModeOnlineService.enterRoom();
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
