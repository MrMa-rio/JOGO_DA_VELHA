package src.main.jogo.services;

import src.main.jogo.net.packets.SendWinLoseOrTiePacket;

import java.util.Objects;

public class GameVerifyBoardService {
    String naVertical(String XO, String[][] tabuleiro, String playerName){
        for(int i = 0; i <= 2; i++){
            if(Objects.equals(XO, tabuleiro[0][i])){
                if(Objects.equals(XO, tabuleiro[1][i])){
                    if(Objects.equals(XO, tabuleiro[2][i])){

                        return String.format("O player %s ganhou na vertical", playerName);
                    }
                }
            }
        }
        return "";
    }
    String naHorizontal(String XO, String[][] tabuleiro, String playerName){
        for(int i = 0; i <= 2; i++){
            if(Objects.equals(XO, tabuleiro[i][0])){
                if(Objects.equals(XO, tabuleiro[i][1])){
                    if(Objects.equals(XO, tabuleiro[i][2])){
                        return String.format("O player %s ganhou na horizontal", playerName);
                    }
                }
            }
        }
        return "";
    }
    String naSemiCruzEsqDir(String XO, String[][] tabuleiro, String playerName){
        if(Objects.equals(XO, tabuleiro[0][0])){
            if(Objects.equals(XO, tabuleiro[1][1])){
                if (Objects.equals(XO, tabuleiro[2][2])) {
                    return String.format("O player %s ganhou na na diagonal esquerda para direita", playerName);
                }
            }
        }
        return "";
    }
    String naSemiCruzDirEsq(String XO, String[][] tabuleiro, String playerName){
        if(Objects.equals(XO, tabuleiro[0][2])){
            if(Objects.equals(XO, tabuleiro[1][1])){
                if (Objects.equals(XO, tabuleiro[2][0])){
                    return String.format("O player %s ganhou na diagonal direita para esquerda", playerName);
                }
            }
        }
        return "";
    }
    String temVelha(String[][] tabuleiro){
        int counter = 1;
        for (String[] strings : tabuleiro) {
            for (int j = 0; j < tabuleiro.length; j++) {
                if (Objects.equals(String.valueOf(counter++), strings[j])) {
                    return "";
                }
            }
        }
        return "DEU EMPATE";
    }
}
