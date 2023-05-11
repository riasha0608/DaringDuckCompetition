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

        boolean wasLoop = false;

        //loop through every transition PER input on tape.
        for (int i = 0; i < inputTape.list.size(); i++) {
            // check if currentTransitionInput = currentTapeInput, if yes then move
            // to move, we change currentstate to what the transition says is hte nextstate
            for (int k = 0; k < transitions.length - 1; k++) {
                // keep looping until we find a transitionstate that is equal to our currentstate and ALSO has equal transitionInput & tape head
                System.out.println(tran1.toString());
                wasLoop = false;
                System.out.println(inputTape.currentState);
                if (inputTape.currentState.equals(tran1.currentState) && tran1.currentInput == inputTape.head) {
                    System.out.println("SLAYSLAY FRFR");
                    //check if its a loop
                    if (tran1.currentState.equals(tran1.nextState)) {
                        // LOOPS
                        processLoop(tran1, inputTape, tran1.currentInput);
                        System.out.println(inputTape.head + " LOOP IS DONE");
                        System.out.println(inputTape.currentState + " YAWRR k = " + k);
                        tran1 = new Transition(inputTape, transitions[k+1]);
                        wasLoop = true;
                        k = transitions.length - 1; //THIS WORKS YAY
                    }
                    //not loop so we move!
                    else if (tran1.direction.equals("L")) {
                        inputTape.goLeft();
                        inputTape.currentState = tran1.nextState;
                        //write
                        inputTape.write(tran1);
                        k = transitions.length - 1;
                        System.out.println("current STATE: " + inputTape.currentState);
                        //i--;
                    }
                    else { //needs to handle stay still "-"
                        inputTape.goRight();
                        inputTape.currentState = tran1.nextState;
                        //write
                        inputTape.write(tran1);
                        k = transitions.length - 1;
                    }
                }
                else {
                    System.out.println(inputTape.currentState + " k = " + k);
                    tran1 = new Transition(inputTape, transitions[k+1]);
                    wasLoop = false;
                }
                
            }
            System.out.println("i = " + i);
            tran1 = new Transition(inputTape, transitions[0]);
            inputTape.currentState = startState;
            
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
                System.out.println("\n output: " + inputTape.outputBig);
            }
        }
        else {
            System.out.println("REJECTED - tape still has more left: Current State is " + inputTape.currentState + "\n" +
            lastIndexOf(inputTape.list, inputTape.head) + "\n" + inputTape.list.size() + " head: " + inputTape.head);
            System.out.println("\n output: " + inputTape.outputBig);

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
    public static void processLoop(Transition tran, Tape tape, int currentinp1) {
        // keeps going through the tape until it hits a value that is not equal to the transitions-current-input
        //will break when head != tran.currentInput
        System.out.println(tape.head + " ABOUT TO LOOP " + tran.currentInput);
        //theres 2 
        while (tape.head == currentinp1) {
            if (tran.direction.equals("L")) {
                tape.goLeft();
                //write
                tape.write(tran);
                System.out.println(tape.head + " WENT LEFT");
                System.out.println(tape.currentState + " aaaa");
            }
            else {
                tape.goRight();
                //write
                tape.write(tran);
                System.out.println(tape.head + " WENT RIGHT");
            }
        } //010000002
    }

    public static void printOutput (Tape tape) {
        for (int i = 0; i < tape.outputBig.size(); i++) {
            System.out.print(tape.outputBig.get(i));
        }
    }
}
