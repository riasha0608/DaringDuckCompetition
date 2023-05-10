import java.util.LinkedList;

public class Transition {
    // A Transition class that keeps track of a tape symbol as input and the results:
    // what state to move to next, what to write, and whether to move the tape left
    // or right.

    int currentInput;
    String nextState;
    int output; //what is being written
    String direction; // "L" or "R" OR STAY STILL "-"
    Tape tape; // linked list of input --> NEEDS TO CHECK if our currentinput is equal to our currentinp in tape
    String currentState;

    public Transition(Tape tape, String tran) {
        currentState = tran.substring(1, 3);
        currentInput = Integer.valueOf(tran.substring(4, 5));
        nextState = tran.substring(10,12);
        output = Integer.valueOf(tran.substring(13, 14));
        direction = tran.substring(15, 16);
    }

    public String toString() {
        return "(" + currentState + "," + currentInput + ") = (" + nextState + "," + output + "," + direction + ")";
        //return currentState;
    }
}
