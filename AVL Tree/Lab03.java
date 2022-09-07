// SOURCE :
// https://www.geeksforgeeks.org/avl-tree-set-1-insertion/

import java.io.*;
import java.util.*;

public class Lab03 {

    private static InputReader in = new InputReader(System.in);
    private static PrintWriter out = new PrintWriter(System.out);

    public static void main(String[] args) {
        Tree stockTree = new Tree();
        Tree distanceTree = new Tree();

        // TODO: process inputs
        int Q = in.nextInt();
        String command = in.next();
        String rootTree = in.next();
        int rootStock = in.nextInt();
        int rootDistance = in.nextInt();

        stockTree.insert(rootTree, rootStock);
        distanceTree.insert(rootTree, rootDistance);

        // Complexity = Q . log N
        for (int i = 1; i < Q; i++) {
            String queryCommand = in.next();

            // log N
            if (queryCommand.equals("INSERT")) {
                String storeName = in.next();
                int stockValue = in.nextInt();
                int distanceValue = in.nextInt();
                stockTree.insert(storeName, stockValue);
                distanceTree.insert(storeName, distanceValue);
            }

            // log N
            else if (queryCommand.equals("STOK_MINIMAL")) {
                int stockMin = in.nextInt();
                out.println(stockTree.countMinimal(stockMin));
            }

            // log N
            else if (queryCommand.equals("TOKO_STOK")) {
                int stockSearch = in.nextInt();
                out.println(stockTree.exists(stockSearch));
            }

            // log N
            else if (queryCommand.equals("TOKO_JARAK")) {
                int distanceSearch = in.nextInt();
                out.println(distanceTree.exists(distanceSearch));
            }

            // log N
            else if (queryCommand.equals("JARAK_MAKSIMAL")) {
                int maxSearch = in.nextInt();
                out.println(distanceTree.countMaximal(maxSearch));
            }
        }

        out.flush();
    }

    // taken from https://codeforces.com/submissions/Petr
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

class Tree {
    TreeNode root;

    public void insert(String storeName, int value) {
        root = insertNode(root, storeName, value);
    }

    // Complexity = log N
    private TreeNode insertNode(TreeNode node, String storeName, int value) {
        // TODO: implement insert node
        /* 1. Perform the normal BST insertion */

        // Complexity = log N
        if (node == null)
            return (new TreeNode(storeName, value, null, null));

        if (value < node.value) {
            node.left = insertNode(node.left, storeName, value);
            node.leftCount++;
        } else if (value > node.value) {
            node.right = insertNode(node.right, storeName, value);
            node.rightCount++;
        }

        /* 2. Update height of this ancestor node */
        node.height = 1 + max(height(node.left), height(node.right));

        /*
         * 3. Get the balance factor of this ancestor node to check whether this node
         * became unbalanced
         */

        // Complexity = log N
        int balance = getBalance(node);

        // If this node becomes unbalanced, then there
        // are 4 cases Left Left Case
        if (balance > 1 && value < node.left.value)
            return rightRotate(node);

        // Right Right Case
        if (balance < -1 && value > node.right.value)
            return leftRotate(node);

        // Left Right Case
        if (balance > 1 && value > node.left.value) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // Right Left Case
        if (balance < -1 && value < node.right.value) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        /* return the (unchanged) node pointer */
        return node;
    }

    private TreeNode rightRotate(TreeNode y) {
        // TODO: implement right rotation
        TreeNode first = y.left;
        TreeNode seccond = first.right;

        // Perform rotation
        first.right = y;
        y.left = seccond;

        // Update heights
        y.height = max(height(y.left), height(y.right)) + 1;
        first.height = max(height(first.left), height(first.right)) + 1;

        y.setLeftCount(first.getRightCount());
        first.setRightCount(y.getTotalCount());

        // Return new root
        return first;
    }

    private TreeNode leftRotate(TreeNode x) {
        // TODO: implement left rotation
        TreeNode first = x.right;
        TreeNode seccond = first.left;

        // Perform rotation
        first.left = x;
        x.right = seccond;

        // Update heights
        x.height = max(height(x.left), height(x.right)) + 1;
        first.height = max(height(first.left), height(first.right)) + 1;

        x.setRightCount(first.getLeftCount());
        first.setLeftCount(x.getTotalCount());

        // Return new root
        return first;
    }

    public TreeNode search(int value) {
        // TODO: implement search node
        // Complexity = log N
        TreeNode t = root;

        while (true) {
            if (t.value == value) {
                break;
            } else if (t.value > value && t.left != null) {
                t = t.left;
            } else if (t.value < value && t.right != null) {
                t = t.right;
            } else {
                t = null;
                break;
            }
        }

        return t;
    }

    public boolean exists(int value) {
        return search(value) != null;
    }

    public int countMinimal(int min) {
        return this.root.countMinimal(min);
    }

    public int countMaximal(int max) {
        return this.root.countMaximal(max);
    }

    // Utility function to get height of node
    private int height(TreeNode n) {
        return n == null ? 0 : n.height;
    }

    // Utility function to get max between two values
    private int max(int a, int b) {
        return (a > b) ? a : b;
    }

    // Utility function to get balance factor of node
    private int getBalance(TreeNode N) {
        if (N == null)
            return 0;

        return height(N.left) - height(N.right);
    }
}

class TreeNode {
    String storeName;
    int value;
    TreeNode left;
    TreeNode right;
    int leftCount;
    int rightCount;
    int height;

    public TreeNode(String storeName, int value, TreeNode left, TreeNode right) {
        this.left = left;
        this.right = right;
        this.storeName = storeName;
        this.value = value;
        this.height = 1;
    }

    public int countMinimal(int min) {
        // TODO: get count of nodes with at least value min recursively
        // Complexity = log N
        int counter = 0;
        boolean check = false;
        if (this.value >= min) {
            if (this.left != null) {
                counter += this.left.countMinimal(min) + this.getRightCount();
            } else {
                counter += this.getRightCount();
            }
            check = true;
        } else {
            if (this.right != null) {
                counter = this.right.countMinimal(min);
            }
        }

        return (check == true) ? counter + 1 : counter;
    }

    public int countMaximal(int max) {
        // TODO: get count of nodes with at most value max recursively
        // Complexity = log N
        int counter = 0;
        boolean check = false;

        if (this.value <= max) {
            if (this.right != null) {
                counter += this.right.countMaximal(max) + this.getLeftCount();
            } else {
                counter += this.getLeftCount();
            }
            check = true;
        } else {
            if (this.left != null) {
                return this.left.countMaximal(max);
            }
        }
        return (check == true) ? counter + 1 : counter;
    }

    public int getTotalCount() {
        return this.leftCount + this.rightCount + 1;
    }

    public int getLeftCount() {
        return this.leftCount;
    }

    public int getRightCount() {
        return this.rightCount;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setLeftCount(int count) {
        this.leftCount = count;
    }

    public void setRightCount(int count) {
        this.rightCount = count;
    }
}
