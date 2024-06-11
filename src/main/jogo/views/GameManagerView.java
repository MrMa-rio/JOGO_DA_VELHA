package src.main.jogo.views;

import src.main.jogo.controllers.GameManagerController;
import src.main.jogo.models.GameRoom;
import src.main.jogo.models.Player;

import java.util.ArrayList;
import java.util.Scanner;

public class GameManagerView {
    GameManagerController gameManagerController = new GameManagerController(this);
    Scanner scanner = new Scanner(System.in);
    public String sendPlayerName(){
        System.out.print("Digite seu nome de jogador: ");
        return scanner.next();
    }
    public void receiveGuestPlayerConnected(Player guestPlayer){
        System.out.println("------------------------------------------");
        System.out.println("Recebendo dados de um Player...");
        System.out.println("Player ID: " + guestPlayer.getPlayerId());
        System.out.println("PlayerTag: " + guestPlayer.getPlayerName());
        System.out.println("--------------------------------------------");
    }
    public void showListGuestPlayersConnected(ArrayList<Player> guestPlayers){
        System.out.println("TOTAL DE PLAYERS CONECTADOS:");
        System.out.println("--------------------------------------------");
        if(guestPlayers.isEmpty()){
            System.out.println("Sem Player conectados!");
        }
        guestPlayers.forEach((guest) -> {
            System.out.println("PlayerTag: " + guest.getPlayerName());
            System.out.println("--------------------------------------------");
        });
        System.out.println("Dados dos Jogadores recebido com sucesso!!");
    }
    public void showListGameRooms(ArrayList<GameRoom> gameRooms){
        System.out.println("TOTAL DE SALAS:");
        System.out.println("--------------------------------------------");
        if(gameRooms.isEmpty()){
            System.out.println("Sem Salas Disponiveis!");
        }
        gameRooms.forEach((room) -> {
            System.out.println("SALA " + (gameRooms.indexOf(room) + 1 ) + ": " + room.getCodeRoom());
            System.out.println("HOST: " + room.getHostId());
            System.out.println("--------------------------------------------");
        });
        System.out.println("Dados das salas recebido com sucesso!!");
    }
    public void startGame(){
        do{
            System.out.println("--------------------------------------------");
            System.out.println("[1] NOVO JOGO OFFLINE <-");
            System.out.println("[2] NOVO JOGO ONLINE <-");
            System.out.println("[3] ENTRAR EM UMA SALA <-");
            System.out.println("[0] SAIR DO JOGO <-");
            System.out.print("--> ");
        } while(!gameManagerController.handleGameModelSelected(scanner.nextInt()));
        System.out.println("--------------------------------------------");
    }
}
