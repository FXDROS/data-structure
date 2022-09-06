//Contributor :
//>>base idea by : MUHAMMAD ALIF SADDID - 1906398250

import java.io.*;
import java.util.*;

public class Lab02Revise {
    private static InputReader in = new InputReader(System.in);
    private static PrintWriter out = new PrintWriter(System.out);
    private static Node prev, data, next, pointerOne, pointerTwo;

    public static void main(String[] args) {
        data = new Node('_', null, null);
        prev = data;
        next = data;
        pointerOne = data;
        pointerTwo = data;
        String usrInput = in.next();
        int queyLoop = in.nextInt();
        for (int i = 0; i < usrInput.length(); i++) { // complex = S
            Node newNode = new Node(usrInput.charAt(i), null, null);
            next.setNext(newNode);
            newNode.setPrev(next);
            next = newNode;
            newNode = new Node('_', null, null);
            next.setNext(newNode);
            newNode.setPrev(next);
            next = newNode;
        }

        for (int j = 0; j < queyLoop; j++) { // complexity = Q
            String userQuery = in.next();
            if (userQuery.equals("GESER_KANAN")) {
                int pointer = in.nextInt();
                if (pointer == 1) {
                    pointerOne = pointerOne.getNext().getNext();
                } else {
                    pointerTwo = pointerTwo.getNext().getNext();
                }
            } else if (userQuery.equals("GESER_KIRI")) {
                int pointer = in.nextInt();
                if (pointer == 1) {
                    pointerOne = pointerOne.getPrev().getPrev();
                } else {
                    pointerTwo = pointerTwo.getPrev().getPrev();
                }
            } else if (userQuery.equals("HAPUS")) {
                int pointer = in.nextInt();
                if (pointer == 1) {
                    if (pointerOne.getNext() != null) {
                        if (pointerOne != pointerTwo) {
                            pointerOne.getPrev().getPrev().setNext(pointerOne.getNext());
                            pointerOne.getNext().setPrev(pointerOne.getPrev().getPrev());
                            pointerOne = pointerOne.getPrev().getPrev();
                        } else {
                            pointerOne.getPrev().getPrev().setNext(pointerOne.getNext());
                            pointerTwo.getPrev().getPrev().setNext(pointerTwo.getNext());
                            pointerOne.getNext().setPrev(pointerOne.getPrev().getPrev());
                            pointerTwo.getNext().setPrev(pointerTwo.getPrev().getPrev());
                            pointerOne = pointerOne.getPrev().getPrev();
                            pointerTwo = pointerTwo.getPrev().getPrev();
                        }
                    } else if (pointerOne.getNext() == null) {
                        if (pointerOne != pointerTwo) {
                            pointerOne.getPrev().getPrev().setNext(null);
                            pointerOne = pointerOne.getPrev().getPrev();
                        } else {
                            pointerOne.getPrev().getPrev().setNext(null);
                            pointerOne = pointerOne.getPrev().getPrev();
                            pointerTwo = pointerOne;
                        }
                    }
                } else {
                    if (pointerTwo.getNext() != null) {
                        if (pointerTwo != pointerOne) {
                            pointerTwo.getPrev().getPrev().setNext(pointerTwo.getNext());
                            pointerTwo.getNext().setPrev(pointerTwo.getPrev().getPrev());
                            pointerTwo = pointerTwo.getPrev().getPrev();
                        } else {
                            pointerOne.getPrev().getPrev().setNext(pointerOne.getNext());
                            pointerTwo.getPrev().getPrev().setNext(pointerTwo.getNext());
                            pointerOne.getNext().setPrev(pointerOne.getPrev().getPrev());
                            pointerTwo.getNext().setPrev(pointerTwo.getPrev().getPrev());
                            pointerOne = pointerOne.getPrev().getPrev();
                            pointerTwo = pointerTwo.getPrev().getPrev();
                        }
                    } else if (pointerTwo.getNext() == null) {
                        if (pointerTwo != pointerOne) {
                            pointerTwo.getPrev().getPrev().setNext(null);
                            pointerTwo = pointerTwo.getPrev().getPrev();
                        } else {
                            pointerTwo.getPrev().getPrev().setNext(null);
                            pointerTwo = pointerTwo.getPrev().getPrev();
                            pointerOne = pointerTwo;
                        }
                    }
                }
            } else if (userQuery.equals("SWAP")) {
                char temporaryData1 = pointerOne.getPrev().getData();
                char temporaryData2 = pointerTwo.getPrev().getData();
                pointerOne.getPrev().setData(temporaryData2);
                pointerTwo.getPrev().setData(temporaryData1);
            } else if (userQuery.equals("TULIS")) {
                int pointer = in.nextInt();
                char toAdd = in.next().charAt(0);
                Node writeNode = new Node(toAdd, null, null);
                Node barNode = new Node('_', null, null);
                writeNode.setNext(barNode);
                barNode.setPrev(writeNode);

                if (pointer == 1) {

                    if (pointerOne.getNext() != null) {
                        if (pointerOne != pointerTwo) {
                            pointerOne.getNext().setPrev(barNode);
                            barNode.setNext(pointerOne.getNext());
                            writeNode.setPrev(pointerOne);
                            pointerOne.setNext(writeNode);
                            pointerOne = barNode;
                        } else {
                            pointerOne.getNext().setPrev(barNode);
                            pointerTwo.getNext().setPrev(barNode);
                            barNode.setNext(pointerOne.getNext());
                            barNode.setNext(pointerTwo.getNext());
                            writeNode.setPrev(pointerOne);
                            writeNode.setPrev(pointerTwo);
                            pointerOne.setNext(writeNode);
                            pointerTwo.setNext(writeNode);
                            pointerOne = barNode;
                            pointerTwo = barNode;
                        }

                    } else {
                        if (pointerOne != pointerTwo) {
                            writeNode.setPrev(pointerOne);
                            pointerOne.setNext(writeNode);
                            pointerOne = barNode;
                        } else {
                            writeNode.setPrev(pointerOne);
                            writeNode.setPrev(pointerTwo);
                            pointerOne.setNext(writeNode);
                            pointerTwo.setNext(writeNode);
                            pointerOne = barNode;
                            pointerTwo = barNode;
                        }

                    }

                } else {
                    if (pointerTwo.getNext() != null) {
                        if (pointerTwo != pointerOne) {
                            pointerTwo.getNext().setPrev(barNode);
                            barNode.setNext(pointerTwo.getNext());
                            writeNode.setPrev(pointerTwo);
                            pointerTwo.setNext(writeNode);
                            pointerTwo = barNode;
                        } else {
                            pointerOne.getNext().setPrev(barNode);
                            pointerTwo.getNext().setPrev(barNode);
                            barNode.setNext(pointerOne.getNext());
                            barNode.setNext(pointerTwo.getNext());
                            writeNode.setPrev(pointerOne);
                            writeNode.setPrev(pointerTwo);
                            pointerOne.setNext(writeNode);
                            pointerTwo.setNext(writeNode);
                            pointerOne = barNode;
                            pointerTwo = barNode;
                        }

                    } else {
                        if (pointerOne != pointerTwo) {
                            writeNode.setPrev(pointerTwo);
                            pointerTwo.setNext(writeNode);
                            pointerTwo = barNode;
                        } else {
                            writeNode.setPrev(pointerOne);
                            writeNode.setPrev(pointerTwo);
                            pointerOne.setNext(writeNode);
                            pointerTwo.setNext(writeNode);
                            pointerOne = barNode;
                            pointerTwo = barNode;
                        }

                    }

                }

            }
            // toPrint(data);
        }
        toPrint(data);

        out.close();
    }

    public static void toPrint(Node data) { // 2S
        Node current = data.getNext();
        while (current != null) {
            out.print(current.getData());
            current = current.getNext().getNext();
        }
        out.println();
    }

    static class Node {
        public char data;
        public Node next;
        public Node prev;

        public Node(char data, Node next, Node prev) {
            this.data = data;
            this.next = next;
            this.prev = prev;
        }

        public void setData(char newData) {
            this.data = newData;
        }

        public char getData() {
            return this.data;
        }

        public void setNext(Node newNext) {
            this.next = newNext;
        }

        public Node getNext() {
            return this.next;
        }

        public void setPrev(Node newPrev) {
            this.prev = newPrev;
        }

        public Node getPrev() {
            return this.prev;
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