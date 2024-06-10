package src.main.jogo.views;

import java.util.Scanner;

public class GameModeOnlineView {
    Scanner scanner = new Scanner(System.in);

    public String setCodeRoom(){
        System.out.println("Digite um nome para a sala: ");
        return scanner.nextLine();
    }

}
