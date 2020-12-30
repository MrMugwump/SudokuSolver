import java.util.Arrays;

public class BoardPiece {
    public boolean[] possibleNumbers = new boolean[9];

    public int[] coordinate;

    private int currentNumber;

    public BoardPiece(int[] coordinate){
        Arrays.fill(possibleNumbers, true);

        getTotalPossibleNumbers();

        this.coordinate = coordinate;
    }

    public int getTotalPossibleNumbers(){
        int counter = 0;
        for (boolean bool: possibleNumbers
        ) {
            //System.out.println(bool);
            if (bool) {
                counter++;

            }
        }
        //System.out.println(counter);
        return counter;
    }

    public int getCurrentPossibleNumber(){
        if (getTotalPossibleNumbers()==1){
            for (int i = 0; i < possibleNumbers.length; i++) {
                if (possibleNumbers[i]){
                    return i;
                }
            }
        }
        return 0; //failsafe
    }

    public void changeCurrentPossibleNumbers(int currentNumber){
        possibleNumbers[currentNumber] = false; }

    public int getCurrentNumber() { return currentNumber; }

    public void setCurrentNumber(int currentNumber) {
        this.currentNumber = currentNumber;

        if (currentNumber!=0)
            Arrays.fill(possibleNumbers,false);

    }

    public void changeCurrentNumberToOnlyAvailableOption(){
        setCurrentNumber(getCurrentPossibleNumber()+1);
    }
}
