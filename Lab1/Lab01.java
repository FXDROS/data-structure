//CONTRIBUTOR :
//JEREMY VICTOR ANDRE NAPITUPULU : 1906293114
//NATHANIA CALLISTA :

import java.io.*;
import java.util.*;

public class Lab01 {
    private static InputReader in = new InputReader(System.in);
    private static PrintWriter out = new PrintWriter(System.out);

    public static void main(String[] args) {

        // to get the length, stacks, and water starting point amounts
        // O(N)
        int firstLine[] = new int[3];
        for (int i = 0; i < 3; i++) {
            int toInputOne = in.nextInt();
            firstLine[i] = toInputOne;
        }

        int L = firstLine[0];
        int N = firstLine[1];
        int Q = firstLine[2];

        int width[] = new int[L + 1];
        int tempWidth[] = new int[L + 1];

        // to get the start and end point of the stack
        // O(N)
        for (int j = 0; j < N; j++) {
            int startStack = in.nextInt();
            int endStack = in.nextInt();
            int stackHeight = in.nextInt();

            tempWidth[startStack] += stackHeight;
            if (endStack != L) {
                tempWidth[endStack + 1] -= stackHeight;
            }
        }

        // System.out.println(Arrays.toString(tempWidth));

        // counting stack
        for (int ii = 1; ii < width.length; ii++) {
            width[ii] = tempWidth[0];
            width[ii] = width[ii - 1] + tempWidth[ii];
        }

        // initiate right and left stop index
        int rightStop[] = new int[width.length];
        int leftStop[] = new int[width.length];
        for (int i = 0; i < rightStop.length; i++) {
            rightStop[i] = i;
            leftStop[i] = i;
        }

        // look for right stop by searching from right
        for (int i = width.length - 1; i > 0; i--) {
            if (width[i] <= width[i - 1]) {
                rightStop[i - 1] = rightStop[i];
            }
        }

        // look for left index stop by searching from left
        for (int j = 1; j < width.length - 1; j++) {
            if (width[j] <= width[j + 1]) {
                leftStop[j + 1] = leftStop[j];
            }
        }

        // System.out.println(Arrays.toString(rightStop));
        // System.out.println(Arrays.toString(leftStop));

        // to get the starting point of the water
        ArrayList<Integer> waterValue = new ArrayList<Integer>();
        waterValue.add(0);
        for (int a = 1; a < Q + 1; a++) {
            waterValue.add(in.nextInt());
        }

        int waterFall[][] = new int[waterValue.size()][2];
        // ArrayList<Integer> endPoint = new ArrayList<Integer>();

        for (int ii = 1; ii < waterValue.size(); ii++) {
            waterFall[ii][0] = leftStop[waterValue.get(ii)];
            waterFall[ii][1] = rightStop[waterValue.get(ii)];
        }

        for (int xx = 1; xx < waterFall.length; xx++) {
            out.print(waterFall[xx][0]);
            out.print(" ");
            out.print(waterFall[xx][1]);
            out.println();
        }

        out.close();
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