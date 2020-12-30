import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args){
        new Main();
    }


    //private boolean win = false;

    private BoardPiece[][] possibleNumbersBoard;

    public Main(){
        int[][] board = createSparseBoard();

        possibleNumbersBoard = BoardOperations.CreatePossibleNumbersBoard();

        BoardOperations.SetNumbers(board,possibleNumbersBoard);

        int counter = 0;
        int[][] previousBoard = new int[9][9];
        while(!BoardOperations.CheckForStall(possibleNumbersBoard) || !BoardOperations.CheckForWin(board)) {
            counter++;
            previousBoard = copyBoard(previousBoard, board);

            BoardOperations.LookForNewNumbersToSet(possibleNumbersBoard);

            BoardOperations.UpdateIntegerBoard(board, possibleNumbersBoard);

            if (!checkPreviousBoard(previousBoard, board)) {
                break;
            }
        }
        BoardOperations.PrintBoard(board);

        int recursions = 0;

        GuessOperations.GuessAndCheck(possibleNumbersBoard, board, recursions);

        //GuessAndCheck(possibleNumbersBoard,board);
    }


    static boolean checkPreviousBoard(int[][] previousBoard, int[][] board){
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j]!=previousBoard[i][j])
                    return true;
            }
        }
        return false;
    }

    private void PrintPossibleNumbers(){

        System.out.println("   0  1  2  3  4  5  6  7  8 ");
        for (int i = 0; i < possibleNumbersBoard.length; i++) {
            System.out.print(i+" ");
            for (int j = 0; j < possibleNumbersBoard[i].length; j++) {
                System.out.print("|" + possibleNumbersBoard[i][j].getTotalPossibleNumbers() + "|");
            }
            System.out.println();
        }

    }
    private void PrintPossibleNumbers(BoardPiece[][] possibleNumbersBoard){

        System.out.println("   0  1  2  3  4  5  6  7  8 ");
        for (int i = 0; i < possibleNumbersBoard.length; i++) {
            System.out.print(i+" ");
            for (int j = 0; j < possibleNumbersBoard[i].length; j++) {
                System.out.print("|" + possibleNumbersBoard[i][j].getTotalPossibleNumbers() + "|");
            }
            System.out.println();
        }

    }

    static int[][] copyBoard(int[][] board, int[][] boardToCopy){
        board = new int[boardToCopy.length][boardToCopy[0].length];

        for (int i = 0; i < boardToCopy.length; i++) {
            for (int j = 0; j < boardToCopy[i].length; j++) {
                board[i][j] = boardToCopy[i][j];
            }
        }
        return board;
    }

    private int[][] createTestBoard(){
        int[][] testBoard = new int[][]
                {
                        {0,0,4,0,1,0,7,0,0},
                        {1,9,0,7,0,6,0,0,0},
                        {0,0,0,0,0,0,0,6,0},
                        {0,7,0,4,0,0,9,0,5},
                        {0,0,0,0,6,0,0,0,0},
                        {3,0,5,0,0,8,0,1,0},
                        {0,8,0,0,0,0,0,0,0},
                        {0,0,0,5,0,7,0,9,1},
                        {0,0,3,0,2,0,5,0,0},
                };
        return testBoard;
    }

    private int[][] createAlternateBoard(){
        int[][] board = new int[][]{
                        {0,0,1,0,0,8,0,3,0},
                        {0,6,0,0,0,0,0,2,0},
                        {8,0,0,6,0,0,0,7,4},
                        {3,0,4,2,5,0,0,0,0},
                        {0,0,0,0,6,0,0,0,0},
                        {0,0,0,0,4,1,9,0,3},
                        {9,3,0,0,0,5,0,0,7},
                        {0,4,0,0,0,0,0,6,0},
                        {0,8,0,4,0,0,1,0,0},
                };
        return board;
    }

    private int[][] createSparseBoard(){
        int[][] board = new int[][]{
                {0,0,0,7,0,9,0,3,0},
                {0,0,0,0,8,0,0,0,0},
                {0,0,0,0,0,0,0,1,0},
                {1,0,0,0,0,0,0,8,0},
                {0,0,0,8,0,1,0,9,0},
                {0,5,0,0,0,0,0,0,6},
                {0,7,0,1,0,0,0,0,0},
                {0,0,1,0,5,0,8,0,0},
                {0,2,0,9,0,4,0,0,0}
        };
        return board;
    }
    
}
