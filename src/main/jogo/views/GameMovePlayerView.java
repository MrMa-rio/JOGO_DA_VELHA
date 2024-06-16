package src.main.jogo.views;

import java.util.Scanner;

public class GameMovePlayerView {
    Scanner scanner = new Scanner(System.in);
    public String choicePositionPlayer(){
        System.out.println("Escolha uma posicao: ");
        return scanner.nextLine();
    }
    public String choiceXO(){
        System.out.println("Escolha um marcador (ex:  X ou Y): ");
        return scanner.nextLine();
    }
}
