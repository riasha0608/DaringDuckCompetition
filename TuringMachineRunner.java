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

        LinkedList<Transition> unusedTrans = new LinkedList<>();

        for (int i = 0; i < transitions.length - 1; i++) {
            // check if currentTransitionInput = currentTapeInput, if yes then move
            // to move, we change currentstate to what the transition says is hte nextstate
            wasLoop = false;
            System.out.println(tran1.toString());
            if (inputTape.currentState.equals(tran1.currentState)) {
                System.out.println("states are equal");
                //is the head of the tape = current input
                if (inputTape.head == tran1.currentInput) {
                    System.out.println("HEAD equals");
                    if (tran1.currentState.equals(tran1.nextState)) {
                        // LOOPS
                        processLoop(tran1, inputTape, tran1.currentInput);
                        System.out.println(inputTape.head + " LOOP IS DONE");
                        tran1 = new Transition(inputTape, transitions[i+1]);
                        wasLoop = true;
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
                } // END OF IF
                //so since it could be a valid transition but like it doesn't know to go back and check for another transition
                else {
                    unusedTrans.add(tran1);
                    for (int j = 0; j < unusedTrans.size(); j++) {
                        // check if inputTape.head == tran1.currentInput but like in this list so like
                        if (unusedTrans.get(j).currentInput == inputTape.head) {
                            System.out.println("INPUT EQUAL BUT ITS OUR SECOND TIME" + unusedTrans.get(j).toString());
                            if (unusedTrans.get(j).currentState.equals(unusedTrans.get(j).nextState)) {
                                // LOOPS
                                processLoop(unusedTrans.get(j), inputTape, unusedTrans.get(j).currentInput);
                                System.out.println(inputTape.head + " OLDDDDDD LOOP");
                                tran1 = new Transition(inputTape, transitions[i+1]);
                                wasLoop = true;
                            }
                            //not loop so we move!
                            else if (unusedTrans.get(j).direction.equals("L")) {
                                System.out.println("GOING LEFT BUT IN SECOND");
                                inputTape.goLeft();
                                //write
                                inputTape.write(tran1);
                            }
                            else { //needs to handle stay still "-"
                            System.out.println("GOING RIGHT BUT IN SECOND");
                                inputTape.goRight();
                                //write
                                inputTape.write(tran1);
                            }
                        }
                    } 
                }
                
            }
    
            if (wasLoop != true) {
                tran1 = new Transition(inputTape, transitions[i+1]);
            }
            
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
            System.out.println("REJECTED - tape still has more left: Current State is " + inputTape.currentState + "\n" +
            lastIndexOf(inputTape.list, inputTape.head) + "\n" + inputTape.list.size() + " head: " + inputTape.head
            );
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
