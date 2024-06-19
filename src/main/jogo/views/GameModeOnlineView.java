package src.main.jogo.views;

import java.util.Objects;
import java.util.Scanner;

public class GameModeOnlineView {
    Scanner scanner = new Scanner(System.in);

    public String setCodeRoom(){
        System.out.println("Digite o codigo da sala: ");
        System.out.print("--> ");
        return scanner.nextLine();
    }
    public String setPlayerName(){
        System.out.println("Digite seu nome de jogador: ");
        System.out.print("--> ");
        return scanner.nextLine();
    }
    public String setIpAdress(){
        System.out.println("Escolha o endereco IP: ");
        System.out.println("[1] --> 192.168.3.18");
        System.out.println("[2] --> 172.16.232.203");
        System.out.print("--> ");
        String choice = scanner.nextLine();
        while(true){
            if(Objects.equals(choice, "1")) return "192.168.3.18";
            if (Objects.equals(choice, "2")) return "172.16.232.203";
            System.out.println("Escolha invalida!!");
            System.out.print("--> ");
            choice = scanner.nextLine();
        }
    }
    public int setPort(){
        //System.out.println("Insira a PORTA: ");
        return 1234;
    }
}
