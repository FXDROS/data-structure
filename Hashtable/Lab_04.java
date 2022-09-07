import java.io.*;
import java.util.*;

public class Lab_04 {
    private static InputReader in = new InputReader(System.in);
    private static PrintWriter out = new PrintWriter(System.out);

    // Complexity = Y.A + Z^2
    public static void main(String[] args) {
        int X = in.nextInt();
        int Y = in.nextInt();
        String toCheck = in.next();

        // Initiate alphabet value
        // Complexity = constant
        ArrayList<String> alphabet = new ArrayList<>();
        for (int i = 0; i < 26; i++) {
            alphabet.add(Character.toString((char) (i + 97)));
        }

        // Initiate string set
        Set<String> stringCollection = new HashSet<String>();
        // Complexity = Z^2
        for (int j = 1; j <= toCheck.length(); j++) {
            for (int k = 0; k <= toCheck.length() - j; k++) {
                stringCollection.add(toCheck.substring(k, k + j));
            }
        }

        // Initiate first LinkedList
        Node[] hashTable = new Node[Y];
        // Complexity = Y
        for (int l = 0; l < hashTable.length; l++) {
            Node modNode = new Node(String.valueOf(l), null);
            hashTable[l] = modNode;
        }

        // Iterate string set and get value
        // ArrayList<Integer> modKeeper = new ArrayList<Integer>();

        // Complexity = Z
        for (String checkCollection : stringCollection) {
            int tempKeep = countFunction(X, Y, alphabet, checkCollection);
            // out.println(tempKeep);
            Node addNode = new Node(checkCollection, null);
            addNode.setNext(hashTable[tempKeep].getNext());
            hashTable[tempKeep].setNext(addNode);
        }

        int trueCounter = 0;

        // Iterate linked list
        // Complexity = Y.A
        for (Node toPrint : hashTable) {
            int currentCounter = 0;
            if (toPrint.getNext() != null) {
                Node current = toPrint.getNext();
                while (current != null) {
                    currentCounter++;
                    current = current.getNext();
                }
            }

            // Getting combination value
            int comb = 0;
            if (currentCounter > 1) {
                comb = (currentCounter * (currentCounter - 1)) / 2;
            }

            trueCounter += comb;
        }

        out.println(trueCounter);

        out.close();
    }

    // Check Node
    public static void toPrint(Node data) {
        Node current = data.getNext();
        while (current != null) {
            out.print(current.getData() + " ");
            current = current.getNext().getNext();
        }
        out.println();
    }

    // SOURCE :
    // https://www.geeksforgeeks.org/modular-exponentiation-power-in-modular-arithmetic/
    /*
     * Iterative Function to calculate (x^y)%p in O(log y)
     */
    static int power(long x, long y, long p) {
        // Initialize result
        long res = 1;

        // Update x if it is more
        // than or equal to p
        x = x % p;

        if (x == 0)
            return 0; // In case x is divisible by p;

        while (y > 0) {
            // If y is odd, multiply x
            // with result
            if ((y & 1) == 1)
                res = (res * x) % p;

            // y must be even now
            // y = y / 2
            y = y >> 1;
            x = (x * x) % p;
        }
        return (int) res;
    }

    public static int countFunction(int X, int Y, ArrayList<String> valueKey, String toCheck) {
        int counter = 0;
        for (int i = 0; i < toCheck.length(); i++) {
            int a = (valueKey.indexOf(Character.toString(toCheck.charAt(i))) % Y + 1 % Y) % Y;
            int b = power(Long.valueOf(X), Long.valueOf(i), Long.valueOf(Y));

            counter += (a * b) % Y;
            // out.println(counter % Y);
        }
        return counter % Y;
    }

    static class InputReader {
        public BufferedReader reader;
        public StringTokenizer tokenizer;

        public InputReader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream), 32768);
            tokenizer = null;
        }

        public String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }

    }

    static class Node {
        public String data;
        public Node next;

        public Node(String data, Node next) {
            this.data = data;
            this.next = next;
        }

        public String getData() {
            return this.data;
        }

        public Node getNext() {
            return this.next;
        }

        public void setNext(Node newNext) {
            this.next = newNext;
        }
    }

}
