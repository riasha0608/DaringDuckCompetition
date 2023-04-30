import java.util.Scanner;

public class TuringMachineRunner {
    // A TuringMachineRunner class that has main, manually assembles your chosen
    // machine, runs the Turing Machine, checks whether each new state is a final
    // state, and does something reasonable with the output when the final state is
    // reached.

    // As long as your Turing Machine will properly represent the final state 
    // (tape and FSM state) of any inputted machine, it is legitimate.
    
    public static void main(String[] args) {
        System.out.println("Please enter input");
        Scanner in = new Scanner(System.in);
        String inp = in.nextLine();

        Tape inputTape = new Tape(inp);
    }
}
