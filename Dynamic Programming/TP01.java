import java.util.*;
import java.io.*;

public class TP01 {
    private static InputReader in = new InputReader(System.in);
    private static PrintWriter out = new PrintWriter(System.out);
    public static ArrayList<Bucket> listBucket;
    public static Queue<Person> listPersonName;
    public static Queue<Person> Izuri;

    public static void main(String[] args) {

        // Total Complexity = N + MN^2 + H + Y + (O(KN^2) + M)H
        // MN^2 + Y + OHKN^2 + MH

        // Input Scheme
        // Asking boba field
        int fieldNumber = in.nextInt(); // Variable : N

        // Collecting field data
        // !!!Complexity = N!!!
        // int fieldList[] = new int[fieldNumber + 1];
        ArrayList<Integer> fieldList = new ArrayList<Integer>();
        for (int i = 1; i < fieldNumber + 1; i++) {
            fieldList.add(in.nextInt());
        }

        // Asking bucket amount in market
        int bucketNumber = in.nextInt(); // Variable : M
        listBucket = new ArrayList<Bucket>();

        // Collecting bucket data
        // Complexity = MN^2
        for (int j = 0; j < bucketNumber; j++) {
            String name = in.next(); // Variable : S
            int size = in.nextInt(); // Variable : C
            int expand = in.nextInt(); // Variable : F

            Bucket newBucket = new Bucket(name, size, expand);
            listBucket.add(newBucket);
            checkBoba(newBucket, fieldNumber, fieldList); // Complexity : N^2
            newBucket.getHighestBoba();
        }

        // sorting bucket
        bucketSorting(listBucket);

        int daysInMarket = in.nextInt(); // Variable : H
        listPersonName = new LinkedList<Person>(); // List of People Queue to print name
        Izuri = new LinkedList<Person>(); // List of Izuri's Requests
        int queueList[] = new int[daysInMarket]; // numbers of served people in market
        // Complexity = H.Y
        // Checking and asking query
        for (int k = 0; k < daysInMarket - 1; k++) { // Complexity : H
            String izuriCommand = in.next();
            String izuriTarget = in.next();
            Person newIzuri = new Person("Izuri", izuriCommand, izuriTarget);
            // Adding Izuri's query request
            Izuri.add(newIzuri);
            if (izuriCommand.equals("ADD") || izuriCommand.equals("UPDATE")) {
                int targetCap = in.nextInt();
                int targetExp = in.nextInt();
                newIzuri.changeTargetCapacity(targetCap);
                newIzuri.changeTargetExpansion(targetExp);
            } else if (izuriCommand.equals("RENAME")) {
                String newName = in.next();
                newIzuri.changeTargetName(newName);
            }

            // Adding market's request
            int peopleQueue = in.nextInt(); // Variable : Y
            for (int kk = 0; kk < peopleQueue; kk++) { // Complexity : Y
                String newPersonName = in.next();
                String newPersonCommand = in.next();
                String newPersonTarget = in.next();
                Person newPersonQueue = new Person(newPersonName, newPersonCommand, newPersonTarget);
                listPersonName.add(newPersonQueue);
                if (newPersonCommand.equals("ADD") || newPersonCommand.equals("UPDATE")) {
                    int newTargetCap = in.nextInt();
                    int newTargetExp = in.nextInt();
                    newPersonQueue.changeTargetCapacity(newTargetCap);
                    newPersonQueue.changeTargetExpansion(newTargetExp);
                } else if (newPersonCommand.equals("RENAME")) {
                    String newTargetName = in.next();
                    newPersonQueue.changeTargetName(newTargetName);
                }
            }

            queueList[k] = in.nextInt(); // Variable : O
        }

        // Running Queue's Query
        // Complexity = (O(KN^2) + M)H
        for (int transactionDay = 0; transactionDay < daysInMarket; transactionDay++) { // Complexity = H
            out.println("Hari ke-" + String.valueOf(transactionDay + 1) + ":");
            // For day one
            if (transactionDay == 0) {
                out.println("Hasil Panen");
                for (Bucket toPrint : listBucket) { // Complexity = M
                    out.println(toPrint.toString());
                }
            } else {

                // For not day one
                out.println("Permintaan yang dilayani");
                // Complexity = O.2(K + KN^2 + K + KN^2) + K^2 = O(KN^2) + K^2
                for (int aa = 0; aa < queueList[transactionDay - 1]; aa++) { // Complexity = O
                    Person temporary = listPersonName.poll();
                    out.print(temporary.getName() + " ");
                    if (temporary.getAction().equals("ADD")) { // Complexity = K.N^2
                        int counter = 0;
                        for (Bucket toCheck : listBucket) { // Complexity = K
                            if (toCheck.getName().equals(temporary.getTarget())) {
                                counter++;
                                break;
                            }
                        }
                        if (counter == 0) {
                            Bucket newBucket = new Bucket(temporary.getTarget(), temporary.getTargetCapacity(),
                                    temporary.getTargetExpansion());
                            listBucket.add(newBucket);
                            checkBoba(newBucket, fieldNumber, fieldList); // Complexity = N^2
                            newBucket.getHighestBoba();
                        }
                    } else if (temporary.getAction().equals("SELL")) { // Complexity = K
                        for (Bucket toCheck : listBucket) { // Complexity = K
                            if (toCheck.getName().equals(temporary.getTarget())) {
                                listBucket.remove(toCheck);
                                break;
                            }
                        }
                    } else if (temporary.getAction().equals("UPDATE")) { // Complexity K.N^2
                        for (Bucket toCheck : listBucket) { // Complexity = K
                            if (toCheck.getName().equals(temporary.getTarget())) {
                                toCheck.updateBucket(temporary.getTargetCapacity(), temporary.getTargetExpansion());
                                toCheck.clearBucketList();
                                checkBoba(toCheck, fieldNumber, fieldList); // Complexity = N^2
                                toCheck.getHighestBoba();
                                break;
                            }
                        }
                    } else if (temporary.getAction().equals("RENAME")) { // Complexity = K
                        int counter = 0;
                        int index = -1;
                        for (Bucket toCheck : listBucket) { // Complexity = K
                            if (toCheck.getName().equals(temporary.getTarget())) {
                                index = listBucket.indexOf(toCheck);
                                continue;
                            } else if (toCheck.getName().equals(temporary.toReturnNew())) {
                                counter++;
                                break;
                            }
                        }
                        if (counter == 0 && index != -1) {
                            listBucket.get(index).renameBucket(temporary.toReturnNew());
                        }
                    }
                }
                Person tempIzuri = Izuri.poll();
                if (tempIzuri.getAction().equals("ADD")) {
                    int counter = 0;
                    for (Bucket toCheck : listBucket) {
                        if (toCheck.getName().equals(tempIzuri.getTarget())) {
                            counter++;
                            break;
                        }
                    }
                    if (counter == 0) {
                        Bucket newBucket = new Bucket(tempIzuri.getTarget(), tempIzuri.getTargetCapacity(),
                                tempIzuri.getTargetExpansion());
                        listBucket.add(newBucket);
                        checkBoba(newBucket, fieldNumber, fieldList);
                        newBucket.getHighestBoba();
                    }
                } else if (tempIzuri.getAction().equals("SELL")) {
                    for (Bucket toCheck : listBucket) {
                        if (toCheck.getName().equals(tempIzuri.getTarget())) {
                            listBucket.remove(toCheck);
                            break;
                        }
                    }
                } else if (tempIzuri.getAction().equals("UPDATE")) {
                    for (Bucket toCheck : listBucket) {
                        if (toCheck.getName().equals(tempIzuri.getTarget())) {
                            toCheck.updateBucket(tempIzuri.getTargetCapacity(), tempIzuri.getTargetExpansion());
                            toCheck.clearBucketList();
                            checkBoba(toCheck, fieldNumber, fieldList);
                            toCheck.getHighestBoba();
                            break;
                        }
                    }
                } else if (tempIzuri.getAction().equals("RENAME")) {
                    int counter = 0;
                    int index = -1;
                    for (Bucket toCheck : listBucket) {
                        if (toCheck.getName().equals(tempIzuri.getTarget())) {
                            index = listBucket.indexOf(toCheck);
                            continue;
                        } else if (toCheck.getName().equals(tempIzuri.toReturnNew())) {
                            counter++;
                            break;
                        }
                    }
                    if (counter == 0 && index != -1) {
                        listBucket.get(index).renameBucket(tempIzuri.toReturnNew());
                    }
                }
                out.println("IZURI");
                out.println("Hasil Panen");
                bucketSorting(listBucket); // K : K^2
                for (Bucket toPrint : listBucket) {
                    out.println(toPrint.toString());
                }
            }
            out.println();
        }

        out.close();

    }

    public static void harvestResult(ArrayList<Bucket> toPrint) {
        out.println("Hasil Panen");
        for (Bucket harvestBoba : toPrint) {
            out.println(harvestBoba.getName() + " " + String.valueOf(harvestBoba.getMaxBoba()));
        }
    }

    public static void queryExecution(Person toExecute, ArrayList<Bucket> listBucket, int fieldNumber,
            int fieldList[]) {

    }

    // Sorting Bucket
    public static void bucketSorting(ArrayList<Bucket> toSort) {
        for (int aa = 1; aa < toSort.size(); aa++) {
            int tempBoba = toSort.get(aa).getMaxBoba();
            String tempName = toSort.get(aa).getName();
            int bb = aa;
            while (bb > 0) {
                if (tempBoba > toSort.get(bb - 1).getMaxBoba()) {
                    swapElement(bb, toSort, tempBoba, tempName);
                } else if (tempBoba == toSort.get(bb - 1).getMaxBoba()) {
                    if (tempName.compareTo(toSort.get(bb - 1).getName()) < 0) {
                        swapElement(bb, toSort, tempBoba, tempName);
                    }
                }
                bb--;
            }
        }
    }

    // Swap bucket's name for sorting
    private static void swapElement(int aa, ArrayList<Bucket> listOfBoba, int tempBoba, String tempName) {
        listOfBoba.get(aa).changeBoba(listOfBoba.get(aa - 1).getMaxBoba());
        listOfBoba.get(aa).renameBucket(listOfBoba.get(aa - 1).getName());
        listOfBoba.get(aa - 1).changeBoba(tempBoba);
        listOfBoba.get(aa - 1).renameBucket(tempName);
    }

    // Checking for boba in each field (knapsack modified algorithm)
    // Complexity : N^2
    public static void checkBoba(Bucket toCheck, int fieldNumber, ArrayList<Integer> fieldList) {
        // initiating 2D array for keeping the boba
        int answer[][] = new int[fieldNumber + 5][fieldNumber + 5];
        int capacity = toCheck.getCapacity();
        int expCapacity = toCheck.getExpansion();
        answer[0][0] = 0; // Basecase
        for (int ii = 0; ii < fieldNumber; ii++) { // Complexity : N
            for (int jj = ii; jj < fieldNumber + 1; jj++) { // Complexity : N
                if (ii == jj) {
                    answer[ii][jj] = 0;
                    toCheck.getBobaInBucket().add(answer[ii][jj]);
                } else if (ii == 0) {
                    answer[ii][jj] = min(capacity, fieldList.get(jj - 1) + answer[ii][jj - 1]);
                    toCheck.getBobaInBucket().add(answer[ii][jj]);
                } else if (ii != 0) {
                    int Try1 = answer[ii - 1][jj - 1]; // case 1: expansion, no taking boba
                    int Try2 = min(answer[ii][jj - 1] + fieldList.get(jj - 1), capacity + ii * expCapacity);
                    answer[ii][jj] = max(Try1, Try2);
                    toCheck.getBobaInBucket().add(answer[ii][jj]);
                }
            }
        }
    }

    private static int min(int a, int b) {
        return (a > b) ? b : a;
    }

    private static int max(int a, int b) {
        return (a > b) ? a : b;
    }

    static class Person {
        private String personName;
        private String targetBucket;
        private String personAction;
        private String targetNew;
        private int targetCapacity;
        private int targetExpansion;

        public Person(String name, String action, String target) {
            this.personName = name;
            this.personAction = action;
            this.targetBucket = target;
            this.targetNew = "";
            this.targetCapacity = 0;
            this.targetExpansion = 0;
        }

        public String getName() {
            return this.personName;
        }

        public String getAction() {
            return this.personAction;
        }

        public String getTarget() {
            return this.targetBucket;
        }

        public int getTargetCapacity() {
            return this.targetCapacity;
        }

        public int getTargetExpansion() {
            return this.targetExpansion;
        }

        public int changeTargetCapacity(int inputChange) {
            this.targetCapacity = inputChange;
            return this.targetCapacity;
        }

        public int changeTargetExpansion(int inputChange) {
            this.targetExpansion = inputChange;
            return this.targetExpansion;
        }

        public String changeTargetName(String inputChange) {
            this.targetNew = inputChange;
            return this.targetNew;
        }

        public String toReturnNew() {
            return this.targetNew;
        }

    }

    static class Bucket {
        private String bucketName;
        private int bucketCapacity;
        private int bucketExpansion;
        private ArrayList<Integer> bobaInBucket;
        private int maxBobaInBucket;

        public Bucket(String name, int capacity, int expansion) {
            this.bucketName = name;
            this.bucketCapacity = capacity;
            this.bucketExpansion = expansion;
            this.bobaInBucket = new ArrayList<Integer>();
            this.maxBobaInBucket = 0;
        }

        public String getName() {
            return this.bucketName;
        }

        public int getCapacity() {
            return this.bucketCapacity;
        }

        public int getExpansion() {
            return this.bucketExpansion;
        }

        public int expandBucket() {
            return this.bucketCapacity = getCapacity() + getExpansion();
        }

        public void renameBucket(String newName) {
            this.bucketName = newName;
        }

        public void changeBoba(int newBoba) {
            this.maxBobaInBucket = newBoba;
        }

        public void updateBucket(int newCapacity, int newExpansion) {
            this.bucketCapacity = newCapacity;
            this.bucketExpansion = newExpansion;
        }

        public ArrayList<Integer> getBobaInBucket() {
            return this.bobaInBucket;
        }

        public int getHighestBoba() {
            int result = this.bobaInBucket.get(0);
            for (int ii = 1; ii < this.bobaInBucket.size(); ii++) {
                result = (result >= this.bobaInBucket.get(ii)) ? result : this.bobaInBucket.get(ii);
            }
            this.maxBobaInBucket = result;
            return result;
        }

        public int getMaxBoba() {
            return this.maxBobaInBucket;
        }

        public String toString() {
            return getName() + " " + String.valueOf(this.maxBobaInBucket);
        }

        public void clearBucketList() {
            bobaInBucket.clear();
        }

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