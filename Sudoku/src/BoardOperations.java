import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class BoardOperations {

    static int[][] CreateBoard(){
        int[][] board = new int[9][9];
        for (int[] ints : board) {
            Arrays.fill(ints, 0);
        }
        return board;
    }

    static BoardPiece[][] CreatePossibleNumbersBoard(){
        BoardPiece[][] possibleNumberBoard= new BoardPiece[9][9];
        for (int i = 0; i < possibleNumberBoard.length; i++) {
            for (int j = 0; j < possibleNumberBoard[i].length; j++) {
                possibleNumberBoard[i][j] = new BoardPiece(new int[]{i,j});
            }
        }

        return possibleNumberBoard;
    }

    static void SetNumbers(int[][] board, BoardPiece[][] possibleNumbersBoard){
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j]!=0){
                    //System.out.println(i + j);
                    possibleNumbersBoard[i][j].setCurrentNumber(board[i][j]);
                    //System.out.print(possibleNumbersBoard[i][j].getTotalPossibleNumbers());
                    //System.out.println(" "+possibleNumbersBoard[i][j].getCurrentNumber());
                    Rules.EliminatePossibleNumbers(possibleNumbersBoard);
                }
            }
        }
    }

    static void LookForNewNumbersToSet(BoardPiece[][] possibleNumberBoard){
        for (BoardPiece[] array: possibleNumberBoard
        ) {
            for (BoardPiece boardPiece: array
            ) {
                if (boardPiece.getTotalPossibleNumbers()==1){
                    //System.out.println("hello");
                    boardPiece.changeCurrentNumberToOnlyAvailableOption();
                    Rules.EliminatePossibleNumbersFromGivenPiece(possibleNumberBoard, boardPiece);
                }
            }
        }
        for (int k = 1; k <= 9; k++) {
            CheckBoxForSettablePieces(possibleNumberBoard, k);
            CheckColumnForSettablePieces(possibleNumberBoard,k);
            CheckRowForSettablePieces(possibleNumberBoard,k);
        }
    }

    private static void CheckBoxForSettablePieces(BoardPiece[][] possibleNumberBoard, int number){ //probably the area with the most room for optimization
        int[] boxCoordinates;
        for (int i = 0; i < 9; i+=3) {
            for (int j = 0; j < 9; j+=3) {
                boxCoordinates= new int[]{i,j};

                    if (Rules.PossiblePlacesForNumberInBox(possibleNumberBoard,boxCoordinates,number)==1){
                        //.out.println("Box Coordinates:"+boxCoordinates[0]+","+boxCoordinates[1]);
                        SetSettablePieceInBox(possibleNumberBoard,boxCoordinates,number);    //only activates if there is only one place for a number in a box.
                    }


            }
        }
    }

    private static void SetSettablePieceInBox(BoardPiece[][] possibleNumberBoard, int[] boxCoordinates, int number){
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (possibleNumberBoard[i+boxCoordinates[0]][j+boxCoordinates[1]].possibleNumbers[number-1]){
                    possibleNumberBoard[i+boxCoordinates[0]][j+boxCoordinates[1]].setCurrentNumber(number);
                    Rules.EliminatePossibleNumbersFromGivenPiece(possibleNumberBoard, possibleNumberBoard[i+boxCoordinates[0]][j+boxCoordinates[1]]);
                }
            }
        }
    }

    private static void CheckColumnForSettablePieces(BoardPiece[][] possibleNumberBoard, int number){
        for (int i = 0; i < 9; i++) {
            if(Rules.PossiblePlacesForNumberInColumn(possibleNumberBoard,i,number)==1){
                //System.out.println("ayo");
                SetSettablePieceInColumn(possibleNumberBoard,i,number);
            }
        }
    }

    private static void SetSettablePieceInColumn(BoardPiece[][] possibleNumberBoard, int columnNumber, int number){
        for (BoardPiece[] array:possibleNumberBoard) {
            if (array[columnNumber].possibleNumbers[number-1]) {
                array[columnNumber].setCurrentNumber(number);
                Rules.EliminatePossibleNumbersFromGivenPiece(possibleNumberBoard, array[columnNumber]);
            }
        }
    }

    private static void CheckRowForSettablePieces(BoardPiece[][] possibleNumberBoard, int number){
        for (int i = 0; i < 9; i++) {
            if(Rules.PossiblePlacesForNumberInRow(possibleNumberBoard,i,number)==1) {
                //System.out.println("Row: "+i);
                SetSettablePieceInRow(possibleNumberBoard, i, number);
            }
        }
    }

    private static void SetSettablePieceInRow(BoardPiece[][] possibleNumberBoard, int rowNumber, int number){
        for (BoardPiece boardPiece: possibleNumberBoard[rowNumber]) {
            if (boardPiece.possibleNumbers[number-1]){
                boardPiece.setCurrentNumber(number);
                Rules.EliminatePossibleNumbersFromGivenPiece(possibleNumberBoard, boardPiece);
            }
        }
    }
    static void UpdateIntegerBoard(int[][] board, BoardPiece[][] possibleNumbersBoard){
        for (int i = 0; i < possibleNumbersBoard.length; i++) {
            for (int j = 0; j < possibleNumbersBoard[i].length; j++) {
                board[i][j] = possibleNumbersBoard[i][j].getCurrentNumber();
            }
        }
    }

    static void PrintBoard(int[][] board){
        System.out.println("   0  1  2  3  4  5  6  7  8 ");
        for (int i = 0; i < board.length; i++) {
            System.out.print(i+" ");
            for (int j = 0; j < board[i].length; j++) {
                System.out.print("|" + board[i][j] + "|");
            }
            System.out.println();
        }

    }



    static boolean CheckForStall(BoardPiece[][] possibleNumbersBoard){
        for (BoardPiece[] array:possibleNumbersBoard
        ) {
            for (BoardPiece boardPiece: array
            ) {
                if (boardPiece.getTotalPossibleNumbers()!=0)
                    return false;
            }
        }
        return true;
    }

    static boolean CheckForWin(int[][] board){
        //if (board[0][0]=6)
        for (int[] array: board
        ) {
            for (int integer: array
            ) {
                if (integer==0)
                    return false;
            }
        }
        return true;
    }

}
