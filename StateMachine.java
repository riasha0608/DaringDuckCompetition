import java.util.LinkedList;

public class StateMachine {
    // A StateMachine class that holds all of the states, keeps track of what the
    // current state is, and given an input, moves to the next state and tells the
    // tape to move left and right.

    String[] states;
    String currentState;
    LinkedList<Integer> input;

    public StateMachine(String[] states) {
        this.states = states;
    }

    public StateMachine(LinkedList<Integer> input) {
        this.input = input;
    }

    public int nextState(int inp) {
        return 0;
    }

    public String whichDirection () {
        return "LR";
    }
}
