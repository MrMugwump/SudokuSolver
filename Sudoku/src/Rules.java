import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class Rules {
    static List<Integer> GetCurrentNumbersInColumn(int[][] board, int columnNumber){
        List<Integer> currentNumbers = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            for (int[] array: board) {
                if (array[columnNumber]==i){
                    currentNumbers.add(i);
                }
            }
        }
        return currentNumbers;
    }

    static List<Integer> GetMissingNumbersInColumn(int[][] board, int columnNumber){
        List<Integer> missingNumbers = new ArrayList<>(List.of(1, 2, 3/*, 4, 5, 6, 7, 8, 9*/));

        List<Integer> currentNumbers = GetCurrentNumbersInColumn(board, columnNumber);

        for (Integer currentNumber : currentNumbers) {
            missingNumbers.remove(currentNumber);
        }
        return missingNumbers;
    }

    static int CanNumberBeAddedAndWhat(int[][] board, int column){
        List<Integer> missingNumbers = GetMissingNumbersInColumn(board, column);
        if (missingNumbers.size()==1)
            return missingNumbers.get(0);

        return 0;
    }

    static void EliminatePossibleNumbers(BoardPiece[][] board){
        for (BoardPiece[] array: board
        ) {
            for (BoardPiece boardPiece: array) {
                if (boardPiece.getTotalPossibleNumbers()==0){
                    EliminatePossibleNumbersFromGivenPiece(board, boardPiece);
                }
            }
        }
    }
    
    static void EliminatePossibleNumbersFromGivenPiece(BoardPiece[][] board, BoardPiece currentPiece){

        EliminatePossibleNumbersInColumn(board, currentPiece);
        EliminatePossibleNumbersInRow(board, currentPiece);
        EliminatePossibleNumbersInBox(board, currentPiece);
    }

    private static void EliminatePossibleNumbersInColumn(BoardPiece[][] board, BoardPiece currentPiece){
        int[] coordinate = currentPiece.coordinate;
        int number = currentPiece.getCurrentNumber();


        if (number == 0)
            return; //failsafe

        for (BoardPiece[] array: board) {

            array[coordinate[1]].changeCurrentPossibleNumbers(number-1); //sets the current number as not possible

        }
    }

    private static void EliminatePossibleNumbersInRow(BoardPiece[][] board, BoardPiece currentPiece){
        int[] coordinate = currentPiece.coordinate;
        //int[] testCoordinate = new int[]{8,1};
        int number = currentPiece.getCurrentNumber();

        if (number==0)
            return;

        for (BoardPiece boardPiece: board[coordinate[0]]){

            boardPiece.changeCurrentPossibleNumbers(number-1);
        }
    }

    private static void EliminatePossibleNumbersInBox(BoardPiece[][] board, BoardPiece currentPiece) {
        int[] coordinate = currentPiece.coordinate;

        int number = currentPiece.getCurrentNumber();

        if (number==0)
            return;

        //System.out.println("hello"+coordinate[1]);

        int[] coordinateOfBox = new int[]{
                findCoordinateOfBox(coordinate[0]),findCoordinateOfBox(coordinate[1])};

        //System.out.println(coordinateOfBox[0]+" "+coordinateOfBox[1]);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                
                board[i+coordinateOfBox[0]][j+coordinateOfBox[1]].changeCurrentPossibleNumbers(number-1);
                
            }
        }
        
        
    }



    public static int PossiblePlacesForNumberInBox(BoardPiece[][] board, int[] boxCoordinates, int number){
        int counter = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i+boxCoordinates[0]][j+boxCoordinates[1]].possibleNumbers[number-1]){
                    //System.out.println("Coordinates: "+(i+boxCoordinates[0])+","+(j+boxCoordinates[1]));
                    counter++;
                }
            }
        }
        return counter;
    }

    public static int PossiblePlacesForNumberInRow(BoardPiece[][] board,int rowNumber, int number){
        int counter = 0;
        for (int i = 0; i < 9; i++) {
            if(board[rowNumber][i].possibleNumbers[number-1])
                counter++;
        }
        return counter;
    }

    public static int PossiblePlacesForNumberInColumn(BoardPiece[][] board,int columnNumber, int number){
        int counter = 0;
        for (int i = 0; i < 9; i++) {
            if(board[i][columnNumber].possibleNumbers[number-1])
                counter++;
        }
        return counter;
    }



    private static int findCoordinateOfBox(int coordinate){
        if (coordinate<3){
            /*  x x x
                0 0 0
                0 0 0 */
            return 0;
        }
        else if (coordinate<6){
            /*  0 0 0
                x x x
                0 0 0 */
            return 3;
        }
        else if (coordinate<9){
            /*  0 0 0
                0 0 0
                x x x */
            return 6;
        }
        return 0;
    }
    
}
