package src.main.jogo.views;

import src.main.jogo.models.GameBoard;

import java.util.Objects;

public class GameBoardView {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLUE = "\u001B[34m";
    String TESTE = ANSI_RED + "TESTE" + ANSI_RESET;
    public void showGameBoard(String[][] board) {
        System.out.println("==========================================================");
        for (String[] strings : board) {
            System.out.printf(String.format("""
                    %s  |  %s  |  %s

                    """, coloredWord(strings[0]), coloredWord(strings[1]), coloredWord(strings[2])));
        }
    }

    String coloredWord(String word){
        if(Objects.equals(word, "X")){
            return ANSI_RED + word + ANSI_RESET;
        }
        else if (Objects.equals(word, "O")){
            return ANSI_BLUE + word + ANSI_RESET;
        }
        return word;
    }
    public void showEmptyBoard(GameBoard gameBoard){
        for (int i = 1; i <= gameBoard.getGameBoard().length; i++){
            if(i == 1){
                System.out.printf(String.format("""
                  %s  |  %s  |  %s

                  """, i,i+1, i+2 ));
            } else if (i == 3) {
                System.out.printf(String.format("""
                  %s  |  %s  |  %s

                  """, i+4,i+5, i+6 ));
            } else{
                System.out.printf(String.format("""
                  %s  |  %s  |  %s

                  """, i+2,i+3, i+4 ));
            }
        }
    }
    public void mountGameBoard(){
        String[][] gameBoard = new String[3][3];
        int counter = 1;
        for(int i = 1; i <= gameBoard.length; i++){
            for (int j = 1; j <= gameBoard.length; j++){
                gameBoard[i][j] = String.valueOf (counter++);
            }
        }
        showGameBoard(gameBoard);
    }
}
