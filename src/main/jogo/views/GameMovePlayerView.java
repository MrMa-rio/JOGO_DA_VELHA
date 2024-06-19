package src.main.jogo.views;

import java.util.Objects;
import java.util.Scanner;

public class GameMovePlayerView {
    Scanner scanner = new Scanner(System.in);
    public String choicePositionPlayer(){
        scanner.reset();
        System.out.println("Escolha uma posicao: ");
        System.out.print("--> ");
        return scanner.nextLine();
    }
    public String choiceXO(String previousXO){
        String choice = "0";
        String XO = "";
        if(Objects.equals(previousXO, "")){
            do {
                System.out.println("Escolha entre X e O:");
                System.out.println("[1] X");
                System.out.println("[2] O");
                System.out.print("--> ");
                scanner.reset();
                choice = scanner.nextLine();
                switch (choice){
                    case "1":
                        XO = "X";
                        break;
                    case "2":
                        XO = "O";
                        break;
                    default:
                        System.out.println("Escolha invalida!");
                        choice = "0";
                        break;
                }
            }while (choice.equals("0"));
        }
        else {
            if (previousXO.equals("X")){
                System.out.println("Voce e o: O");
                return "O";
            }
            System.out.println("Voce e o: X");
            return "X";
        }
        return XO;
    }
}
