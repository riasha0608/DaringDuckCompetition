// so we can calculate our score
public class InOutCounter {
    public static void main(String[] args) {
        String str = "21111111111111111111111111111111111111111111111111111111111111111";
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.substring(i, i+1).equals("1")) {
                count++;
            }
        }
        System.out.println(count);

        System.out.println(countInput("10000002"));

        System.out.println(countInput("010000002"));
        System.out.println(countOutput("21111111111111111111111111111111111111111111111111111111111111111"));
    }

    public static int countInput(String input) {
        int count1 = 0;
        for (int i = 0; i < input.length(); i++) {
            count1++;
        }
        return count1;
    }

    public static int countOutput(String input) {
        int count = 0;
        for (int i = 0; i < input.length(); i++) {
            if (input.substring(i, i+1).equals("1")) {
                count++;
            }
        }
       return count;
    }
}


// 21111111111111111111111111111111111111111111111111111111111111111
// input: 010000002