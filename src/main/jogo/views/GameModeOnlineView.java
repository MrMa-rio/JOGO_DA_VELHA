package src.main.jogo.views;

import java.util.Scanner;

public class GameModeOnlineView {
    Scanner scanner = new Scanner(System.in);

    public String setCodeRoom(){
        System.out.println("Digite o codigo da sala: ");
        return scanner.nextLine();
    }
    public String setPlayerName(){
        System.out.println("Digite seu nome de jogador: ");
        return scanner.nextLine();
    }
    public String setIpAdress(){
        System.out.println("Insira o endereco IP: ");
        return scanner.nextLine();
    }
    public int setPort(){
        System.out.println("Insira a PORTA: ");
        return scanner.nextInt();
    }
}
