package src.main.jogo.views;

import src.main.jogo.models.GameRoom;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicReference;

public class GameMatchView {
    Scanner scanner;
    public GameMatchView(){
        this.scanner = new Scanner(System.in);
    }
    public void showListGameRooms(ArrayList<GameRoom> gameRooms){
        System.out.println("TOTAL DE SALAS:");
        System.out.println("--------------------------------------------");
        if(gameRooms.isEmpty()){
            System.out.println("NENHUMA SALA POR AQUI...!");
        }
        gameRooms.forEach((room) -> {
            System.out.println("SALA " + (gameRooms.indexOf(room) + 1 ) + ": " + room.getCodeRoom());
            System.out.println("HOST: " + room.getHostId());
            System.out.println("--------------------------------------------");
        });
        System.out.println("Dados das salas recebido com sucesso!!");
        System.out.println("=================================================================");
    }
    public String choiceGameMatch(ArrayList<GameRoom> gameRooms){
        AtomicReference<String> codeRoom = new AtomicReference<>("");

        System.out.println("Escolha uma sala");
        System.out.print("--> ");
        int choice = scanner.nextInt();
        gameRooms.forEach((room) -> {
            if((gameRooms.indexOf(room) + 1) == choice){
                codeRoom.set(room.getCodeRoom());
            }
        });
        return codeRoom.get();
    }
}
