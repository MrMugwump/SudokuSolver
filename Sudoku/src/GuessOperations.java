import java.util.ArrayList;
import java.util.List;

public final class GuessOperations {
    static boolean GuessAndCheck(BoardPiece[][] possibleNumberBoard, int[][] board, int recursions){

        BoardPiece[][] previouspossibleNumberBoard = ClonePossibleNumberBoard(possibleNumberBoard);

        List<BoardPiece[][]> boards = new ArrayList<>();
        List<int[]> coordinatesForGuessing = new ArrayList<>();
        List<int[]> numbersToGuess = new ArrayList<>();

        List<List<int[]>> returnedLists = GetValuesForGuessing(possibleNumberBoard);

        coordinatesForGuessing = returnedLists.get(0);
        numbersToGuess = returnedLists.get(1);

        if (numbersToGuess.size()==0){
            return false;
        }

        int indexOfArrayToCheck = findIndexOfLowestValue(numbersToGuess);

        for (int i = 0; i < numbersToGuess.get(indexOfArrayToCheck).length; i++) {

            Guess(possibleNumberBoard, board, coordinatesForGuessing.get(indexOfArrayToCheck), numbersToGuess.get(indexOfArrayToCheck)[i]);

            if (BoardOperations.CheckForWin(board)){
                System.out.println("yay");
                BoardOperations.PrintBoard(board);
                System.out.println(recursions);
                return true;
            }

            boards.add(possibleNumberBoard);

            possibleNumberBoard = ClonePossibleNumberBoard(previouspossibleNumberBoard);

        }
        recursions ++;

        for (int i = 0; i < boards.size(); i++) {
            if (BoardOperations.CheckForStall(boards.get(i))){
                boards.remove(i);
                continue;
            }
            if(GuessAndCheck(boards.get(i),  board,recursions)){

                return true;
            }
        }
        /*for (BoardPiece[][] possibleNumberBoard1:boards
        ) {

            possibleNumberBoard = ClonePossibleNumberBoard(possibleNumberBoard1);
            if (BoardOperations.CheckForStall(possibleNumberBoard1)) {

                //BoardOperations.UpdateIntegerBoard(board,possibleNumberBoard1);
                //BoardOperations.PrintBoard(board);
                //System.out.println(boards.size());
                //if (boards.size()>3)
                    //System.out.println(boards.size());
                continue;
            }
            //System.out.println("hello");
            //BoardOperations.PrintBoard(board);

            if(GuessAndCheck(possibleNumberBoard1,  board,recursions)){

                //if (BoardOperations.CheckForStall(possibleNumberBoard1))
                  //  System.out.println("ruh roh");
                return true;
            }
        }*/

        return false;

    }

    private static int findIndexOfLowestValue(List<int[]> list){
        int lowestArray = 9;
        int indexOfLowestArray = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).length< lowestArray){
                indexOfLowestArray = i;
            }
        }
        return indexOfLowestArray;
    }

    private static List<List<int[]>> GetValuesForGuessing(BoardPiece[][] possibleNumberBoard){


        List<int[]> coordinatesForGuessing = new ArrayList<>();
        List<int[]> numbersToGuess = new ArrayList<>();

        for (BoardPiece[] array: possibleNumberBoard) { //dumb code
            for (BoardPiece boardPiece: array) {
                if (boardPiece.getTotalPossibleNumbers()!=0){
                    int counter = 0;

                    coordinatesForGuessing.add(boardPiece.coordinate);
                    for (int i = 0; i < boardPiece.possibleNumbers.length; i++) {
                        if (boardPiece.possibleNumbers[i])
                            counter++;
                    }

                    int[] numbersToStore = new int[counter];
                    counter = 0;
                    for (int i = 0; i < boardPiece.possibleNumbers.length; i++) {
                        if (boardPiece.possibleNumbers[i]){
                            numbersToStore[counter] = (i+1);
                            counter++;
                        }
                    }
                    numbersToGuess.add(numbersToStore);
                }
            }
        }

        List<List<int[]>> listsToReturn = new ArrayList<>();
        listsToReturn.add(coordinatesForGuessing);
        listsToReturn.add(numbersToGuess);

        return listsToReturn;
    }

    private static BoardPiece[][] ClonePossibleNumberBoard(BoardPiece[][] possibleNumberBoard){
        BoardPiece[][] clone = new BoardPiece[possibleNumberBoard.length][possibleNumberBoard[0].length];
        for (int i = 0; i < possibleNumberBoard.length; i++) {
            for (int j = 0; j < possibleNumberBoard[i].length; j++) {

                clone[i][j] = new BoardPiece(new int[]{i,j});
                clone[i][j].setCurrentNumber(possibleNumberBoard[i][j].getCurrentNumber());
                clone[i][j].possibleNumbers = possibleNumberBoard[i][j].possibleNumbers.clone();
            }
        }
        return clone;
    }


    private static void Guess(BoardPiece[][] possibleNumberBoard, int[][] board, int[] coordinateToGuessOn, int numberToGuess){
        // BoardPiece pieceToGuess
        int[][] previousBoard = new int[9][9];
        possibleNumberBoard[coordinateToGuessOn[0]][coordinateToGuessOn[1]].setCurrentNumber(numberToGuess);
        Rules.EliminatePossibleNumbersFromGivenPiece(possibleNumberBoard, possibleNumberBoard[coordinateToGuessOn[0]][coordinateToGuessOn[1]]);
        while(!BoardOperations.CheckForStall(possibleNumberBoard) || !BoardOperations.CheckForWin(board)){
            previousBoard = Main.copyBoard(previousBoard,board);

            BoardOperations.LookForNewNumbersToSet(possibleNumberBoard);

            BoardOperations.UpdateIntegerBoard(board, possibleNumberBoard);

            if (!Main.checkPreviousBoard(previousBoard,board)) {

                break;
            }

        }
    }
}
