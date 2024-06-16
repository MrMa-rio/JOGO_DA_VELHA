package src.main.jogo.services;

import java.util.Objects;

public class GameVerifyBoardService {
    boolean naVertical(String player, String[][] tabuleiro){
        for(int i = 0; i <= 2; i++){
            if(Objects.equals(player, tabuleiro[0][i])){
                if(Objects.equals(player, tabuleiro[1][i])){
                    if(Objects.equals(player, tabuleiro[2][i])){
                        System.out.println(String.format("O player %s ganhou na vertical", player));
                        return true;
                    }
                }
            }
        }
        return false;
    }
    boolean naHorizontal(String player, String[][] tabuleiro){
        for(int i = 0; i <= 2; i++){
            if(Objects.equals(player, tabuleiro[i][0])){
                if(Objects.equals(player, tabuleiro[i][1])){

                    if(Objects.equals(player, tabuleiro[i][2])){
                        System.out.println(String.format("O player %s ganhou na horizontal", player));
                        return true;
                    }
                }
            }
        }
        return false;
    }
    boolean naSemiCruzEsqDir(String player, String[][] tabuleiro){
        if(Objects.equals(player, tabuleiro[0][0])){
            if(Objects.equals(player, tabuleiro[1][1])){

                if (Objects.equals(player, tabuleiro[2][2])) {
                    System.out.println(String.format("O player %s ganhou na na diagonal esquerda para direita", player));
                    return true;
                }
            }
        }
        return false;
    }
    boolean naSemiCruzDirEsq(String player, String[][] tabuleiro){
        if(Objects.equals(player, tabuleiro[0][2])){
            if(Objects.equals(player, tabuleiro[1][1])){

                if (Objects.equals(player, tabuleiro[2][0])){
                    System.out.println(String.format("O player %s ganhou na diagonal direita para esquerda", player));
                    return true;
                }
            }
        }
        return false;
    }
    boolean temVelha(String[][] tabuleiro){
        int counter = 0;
        for (String[] strings : tabuleiro) {
            for (int j = 0; j < tabuleiro.length; j++) {
                if (Objects.equals(String.valueOf(counter++), strings[j])) {
                    return true;
                }
            }
        }
        return false;
    }
}
