//SOURCE :
//https://www.geeksforgeeks.org/insert-a-string-into-another-string-in-java/
//https://stackoverflow.com/questions/13386107/how-to-remove-single-character-from-a-string

import java.io.*;
import java.util.*;

public class Lab02 {
    private static InputReader in = new InputReader(System.in);
    private static PrintWriter out = new PrintWriter(System.out);

    public static void main(String[] args) {
        String toPrint = in.next();
        int forQuery = in.nextInt();
        int pointerOne = -1;
        int pointerTwo = -1;

        for (int i = 0; i < forQuery; i++) {
            String queryCommand = in.next();
            if (queryCommand.equals("GESER_KANAN")) {
                int pointer = in.nextInt();
                if (pointer == 1) {
                    pointerOne += 1;
                } else {
                    pointerTwo += 1;
                }
            } else if (queryCommand.equals("GESER_KIRI")) {
                int pointer = in.nextInt();
                if (pointer == 1) {
                    pointerOne -= 1;
                } else {
                    pointerTwo -= 1;
                }
            } else if (queryCommand.equals("TULIS")) {
                int pointer = in.nextInt();
                String toAdd = in.next();
                if (pointer == 1) {
                    pointerOne += 1;
                    if (pointerOne <= pointerTwo + 1) {
                        pointerTwo += 1;
                    }
                    toPrint = writeChar(toPrint, toAdd, pointerOne);
                } else {
                    pointerTwo += 1;
                    if (pointerTwo <= pointerOne + 1) {
                        pointerOne += 1;
                    }
                    toPrint = writeChar(toPrint, toAdd, pointerTwo);
                }
            } else if (queryCommand.equals("HAPUS")) {
                int pointer = in.nextInt();
                if (pointer == 1) {
                    toPrint = removeChar(toPrint, pointerOne);
                    pointerOne -= 1;
                    pointerTwo = (pointerOne <= pointerTwo) ? pointerTwo - 1 : pointerTwo;
                } else {
                    toPrint = removeChar(toPrint, pointerTwo);
                    pointerTwo -= 1;
                    pointerOne = (pointerTwo <= pointerOne) ? pointerOne - 1 : pointerOne;
                }
            } else if (queryCommand.equals("SWAP")) {
                toPrint = swapChar(toPrint, pointerOne, pointerTwo);
            }

            pointerTwo = (pointerTwo > toPrint.length() - 1) ? toPrint.length() - 1
                    : (pointerTwo < -1) ? -1 : pointerTwo;
            pointerOne = (pointerOne > toPrint.length() - 1) ? toPrint.length() - 1
                    : (pointerOne < -1) ? -1 : pointerOne;

            // System.out.println("\n" + toPrint);
            // System.out.println("pointer one : " + String.valueOf(pointerOne));
            // System.out.println("pointer two : " + String.valueOf(pointerTwo) + "\n");
        }

        out.println(toPrint);

        out.close();

    }

    public static int moveRight(int pointer) {
        pointer += 1;
        return pointer;
    }

    public static int moveLeft(int pointer) {
        pointer -= 1;
        return pointer;
    }

    public static String writeChar(String toEdit, String toAdd, int indexPointer) {
        StringBuilder toReturn = new StringBuilder(toEdit);
        toReturn.insert(indexPointer, toAdd);
        return toReturn.toString();
    }

    public static String removeChar(String toEdit, int indexPointer) {
        StringBuilder toReturn = new StringBuilder(toEdit);
        toReturn.deleteCharAt(indexPointer);
        return toReturn.toString();
    }

    public static String swapChar(String toEdit, int indexPointerOne, int indexPointerTwo) {
        char tempOne = toEdit.charAt(indexPointerOne);
        char tempTwo = toEdit.charAt(indexPointerTwo);
        StringBuilder toReturn = new StringBuilder(toEdit);
        toReturn.deleteCharAt(indexPointerOne);
        toReturn.insert(indexPointerOne, tempTwo);
        toReturn.deleteCharAt(indexPointerTwo);
        toReturn.insert(indexPointerTwo, tempOne);
        return toReturn.toString();
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
}