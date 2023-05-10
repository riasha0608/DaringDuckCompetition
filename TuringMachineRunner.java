import java.util.LinkedList;
import java.util.Scanner;

public class TuringMachineRunner {
    // A TuringMachineRunner class that has main, manually assembles your chosen
    // machine, runs the Turing Machine, checks whether each new state is a final
    // state, and does something reasonable with the output when the final state is
    // reached.

    // As long as your Turing Machine will properly represent the final state 
    // (tape and FSM state) of any inputted machine, it is legitimate.
    
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        
        //ALL STATES
        // THE NAMES CANNOT BE LONGER THAN 2 LETTERS SORRY.
        System.out.println("Please list ALL states in your TM (seperate by '; '')");
        String states1 = in.nextLine();
        String states[] = states1.split("; ");

        //ALPHABET
        System.out.println("What is the alphabet? (include your null symbol) (seperate by '; ')");
        String alphabet = in.nextLine();

        //INPUT
        System.out.println("Please enter your input");
        String input = in.nextLine();

        //TRANSITIONS
        System.out.println("List your state transitions (seperate with ; )");
        String transitions1 = in.nextLine();
        String transitions[] = transitions1.split("; ");

        //TRANSITION FUNCTION TO TEST: (q0,0) = (q1,0,R); (q1,0) = (q0,0,R); (q0,1) = (q0,1,R); (q1,1) = (q1,1,R)
        
        
        //START STATE
        System.out.println("What is your start state?");
        String startState = in.nextLine();

        //NULL SYMBOL
        System.out.println("What would you like to use as your null symbol?"); //for now assumes itll always be a number
        int nullSymbol = in.nextInt();

        //ACCEPT STATES
        System.out.println("What are your accept states? (seperate with '; ')");
        String accep1 = in.nextLine();
        String accep = in.nextLine();
        String[] acceptStates = accep.split("; ");
        
        //CREATING OBJECTS
        //StateMachine stateMachine = new StateMachine(acceptStates);
        Tape inputTape = new Tape(input, nullSymbol);

        Transition tran1 = new Transition(inputTape, transitions[0]);
        inputTape.currentState = startState;

        for (int i = 0; i < transitions.length; i++) {
            // check if currentTransitionInput = currentTapeInput, if yes then move
            // to move, we change currentstate to what the transition says is hte nextstate
            if (inputTape.currentState.equals(tran1.currentState)) {
                //is the head of the tape = current input
                if (inputTape.head == tran1.currentInput) {
                    //
                    if (tran1.currentState.equals(tran1.nextState)) {
                        // LOOPS
                        processLoop(tran1, inputTape);
                    }
                    //not loop so we move!
                    else if (tran1.direction.equals("L")) {
                        inputTape.goLeft();
                        //write
                        inputTape.write(tran1);
                    }
                    else { //needs to handle stay still "-"
                        inputTape.goRight();
                        //write
                        inputTape.write(tran1);
                    }
                }
            }
            //if states r not equal, don't move states but move to next transition
            // ????

            //inputTape.head = Integer.valueOf(input.substring(i, input.length()-1));
            tran1 = new Transition(inputTape, transitions[i]);
            
        } //END OF HUGE FOR LOOP
        
        if (lastIndexOf(inputTape.list, inputTape.head) == inputTape.list.size() - 1) {
            //if currentstate == acceptstate (one of them)
            boolean accepts = false;
            for (int i = 0; i < acceptStates.length; i++) {
                if (inputTape.currentState.equals(acceptStates[i])) {
                    System.out.print("Output: ");
                    printOutput(inputTape);
                    accepts = true;
                    System.out.println("\nACCEPTED! Final State: " + inputTape.currentState);
                }
            }
            if (accepts == false) {
                System.out.println("REJECTED - did not end on an accept state; Current State is " + inputTape.currentState);
            }
        }
        else {
            System.out.println("REJECTED - tape still has more left: Current State is " + inputTape.head);
        }
        
    }
    public static int lastIndexOf(LinkedList<Integer> list, int element) {
        int index = -1; //not in list
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == element) {
                index = i;
            }
        }
        return index;
    }
    //takes ONE transition which is a loop and processes it [will be changing head of tape through ]
    public static void processLoop(Transition tran, Tape tape) {
        // keeps going through the tape until it hits a value that is not equal to the transitions-current-input
    
        //will break when head == tran.currentInput
        while (tape.head != tran.currentInput) {
            if (tran.direction.equals("L")) {
                tape.goLeft();
                //write
                tape.write(tran);
            }
            else {
                tape.goRight();
                //write
                tape.write(tran);
            }
        }
    }

    public static void printOutput (Tape tape) {
        for (int i = 0; i < tape.outputBig.size(); i++) {
            System.out.print(tape.outputBig.get(i));
        }
    }
}
