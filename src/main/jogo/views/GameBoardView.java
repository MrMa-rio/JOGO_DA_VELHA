package src.main.jogo.views;

public class GameBoardView {
    public void showGameBoard(String[][] board) {
        for (String[] strings : board) {
            System.out.printf(String.format("""
                    %s  |  %s  |  %s

                    """, strings[0], strings[1], strings[2]));
        }
    }

    public void showEmptyBoard(String[][] gameBoard){
        for (int i = 0; i < gameBoard.length; i++){
            if(i == 0){
                System.out.printf(String.format("""
                  %s  |  %s  |  %s

                  """, i,i+1, i+2 ));
            }
            else{
                System.out.printf(String.format("""
                  %s  |  %s  |  %s

                  """, i+2,i+3, i+4 ));
            }
        }
    }
    public void mountGameBoard(){
        String[][] gameBoard = new String[3][3];
        //Scanner scanner = new Scanner(System.in);
        int counter = 0;
        //GameVerifyBoardService verificaTabuleiro = new GameVerifyBoardService();
        for(int i = 0; i < gameBoard.length; i++){
            for (int j = 0; j < gameBoard.length; j++){
                gameBoard[i][j] = String.valueOf (counter++);
            }
        }
        showGameBoard(gameBoard);
    }
}
