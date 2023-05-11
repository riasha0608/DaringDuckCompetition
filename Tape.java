import java.util.LinkedList;

public class Tape {
    
    // A Tape class, that receives a starting input in its constructor, and can go left,
    // right, read, and write. The Tape class should automatically resize when it
    // discovers that more tape is needed. It should also have some sort of
    // toString() or oneCount() method so that we can all see the output when the
    // machine is done running!

    LinkedList<Integer> list = new LinkedList<>(); //now idk if i even needed to convert tho it makes resizing easier
    String alphabet;
    String currentState;
    int currentInp;
    int head; //value of head of tape
    int headCounter = 0;
    int NullSymbol;
    //LinkedList<Integer> outputBig = new LinkedList<>(); //just keeps adding to it
        
    // Tape constructor
    public Tape (String input, int nullSymbol) {
        NullSymbol = nullSymbol;
        //CONVERT string to linkedlist
        if (input == null) {
            list.add(nullSymbol);
        }
        else {
            for (int i = 0; i <= input.length() - 1; i++) {
                list.add(Integer.valueOf(input.substring(i, i+1)));
            }
        }
        head = list.get(0);
        headCounter = 0;
    }

    // RESIZE
    public LinkedList<Integer> resize(String direction){
        if (direction.equals("L")) {
            list.add(0, NullSymbol);
            head = list.get(0); //first thing in tape
            headCounter = 0;
        }
        else {
            list.add(NullSymbol);
            head = list.get(list.size() - 1); //final thing in tape
            headCounter = list.get(list.size()-1);
        }
        return list; //not sure if we'll have this return smthing or just add but
    }

    public void goLeft() {
        //reminder: list is our full input
        //find index of where our head is currently + subtract that from one and return whats at that index
        //if its out of bounds then call resize() from this class (above u!)
        int newIndex = list.indexOf(head) - 1;
        if (newIndex == -1) { //index of head is 0 so - 1 would give -1 meaning we are in a null position so we gotta resize
            resize("L");
        }
        head = list.get(newIndex);
        headCounter = newIndex;
    }

    public void goRight() {
        int newIndex = list.indexOf(head) + 1;
        
        if (newIndex == list.size()) { //our index is one outside of our tape (to the right obv)
            resize("R");
        }
        head = list.get(newIndex);
        headCounter = newIndex;
    }

    public void write(Transition tran) {
    
        list.set(headCounter, tran.output);
    
        
    }

    public String toString() {
        return "";
    }
}
