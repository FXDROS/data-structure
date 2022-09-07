import java.io.*;
import java.util.*;

public class TP02 {
    private static final InputReader in = new InputReader(System.in);
    private static final PrintWriter out = new PrintWriter(System.out);

    // Coplexity = N + Q(M+E+X+Y)
    public static void main(String[] args) {

        // Input
        int N = in.nextInt();
        int M = in.nextInt();
        int E = in.nextInt();
        int Q = in.nextInt();
        Vertex[] arrayVertex = new Vertex[N];
        Edge[] arrayEdgeEx = new Edge[E];
        Edge[] arrayEdge = new Edge[M];

        // Store Vertex and Vertexes' Name
        HashMap<String, Vertex> map = new HashMap<String, Vertex>();
        int logBase = 1;

        // Complexity = N
        for (int i = 0; i < N; i++) {
            String temp = in.next();

            // initiate new Vertex
            Vertex newVertex = new Vertex(temp, i);
            arrayVertex[i] = newVertex;
            map.put(temp, newVertex);
        }

        // Complexity = M
        for (int j = 0; j < M; j++) {
            String vertexStart = in.next();
            String vertexEnd = in.next();
            int travelingTime = in.nextInt();
            int coupon = in.nextInt();
            int closingTime = in.nextInt();

            // initiate new edge
            Edge newEdge = new Edge(travelingTime, closingTime, coupon, map.get(vertexStart), map.get(vertexEnd),
                    false);
            map.get(vertexStart).addEdge(newEdge);
            map.get(vertexEnd).addEdge(newEdge);
            map.get(vertexStart).addVertex(map.get(vertexEnd));
            map.get(vertexEnd).addVertex(map.get(vertexStart));
            arrayEdge[j] = newEdge;

            // count logarithmic value for coupon
            logBase = changeBase(logBase, coupon);
        }

        // Complexity = E
        for (int k = 0; k < E; k++) {
            String vertexStart = in.next();
            String vertexEnd = in.next();
            int coupon = in.nextInt();
            int closingTime = in.nextInt();

            // initiate new edge
            Edge newEdgeEx = new Edge(1, closingTime, coupon, map.get(vertexStart), map.get(vertexEnd), true);
            map.get(vertexStart).addEdge(newEdgeEx);
            map.get(vertexEnd).addEdge(newEdgeEx);
            map.get(vertexStart).addVertex(map.get(vertexEnd));
            map.get(vertexEnd).addVertex(map.get(vertexStart));
            arrayEdgeEx[k] = newEdgeEx;

            // count logarithmic value for coupon
            logBase = changeBase(logBase, coupon);

        }

        // set logarithmic value for coupon edge
        // Complexity = M
        for (Edge edgeLog : arrayEdge) {
            double b = (double) edgeLog.getCoupon();
            double logResult = Math.log(b) / Math.log((double) logBase);
            logResult = Math.round(logResult);
            edgeLog.setCoupon((int) logResult);
        }

        // set logarithmic value for coupon edge
        // Complexity = E
        for (Edge edgeLog : arrayEdgeEx) {
            double b = (double) edgeLog.getCoupon();
            double logResult = Math.log(b) / Math.log((double) logBase);
            logResult = Math.round(logResult);
            edgeLog.setCoupon((int) logResult);
        }

        // Complexity = Q.(E + M + X + Y)
        for (int l = 0; l < Q; l++) {
            String query = in.next();
            // Complexity = E + M
            if (query.equals("TANYA_JALAN")) {
                int timeX = in.nextInt();
                out.println(tanyaJalan(timeX, arrayEdge, arrayEdgeEx));
            }
            // Complexity = X + Y
            else if (query.equals("TANYA_HUBUNG")) {
                String start = in.next();
                String end = in.next();
                if (tanyaHubung(map.get(start), map.get(end), N)) {
                    out.println("YA");
                } else {
                    out.println("TIDAK");
                }
            }
            // Complexity = X + Y
            else if (query.equals("TANYA_KUPON")) {
                String start = in.next();
                String end = in.next();
                int temp = tanyaKupon(map.get(start), map.get(end), arrayVertex, N);

                if (temp == -1) {
                    out.println(-1);
                } else {
                    out.println(mathpower(temp, logBase));

                }
            }
            // Complexity = X + Y
            else if (query.equals("TANYA_EX")) {
                String end = in.next();
                String start = in.next();
                out.println(tanyaEx(map.get(end), map.get(start), arrayVertex, N));
            }
            // Complexity = X + Y
            else if (query.equals("TANYA_BIASA")) {
                String start = in.next();
                String end = in.next();
                out.println(tanyaBiasa(map.get(start), map.get(end), arrayVertex, N));
            }
        }

        out.close();

    }

    // get final result for tanya_kupon
    public static long mathpower(int exponent, int base) {
        long toReturn = 1;
        int mod = (1000000007);
        for (int i = 0; i < exponent; i++) {
            toReturn = ((toReturn % mod) * (base % mod)) % mod;
        }
        return toReturn;
    }

    // TANYA_EX
    public static int tanyaEx(Vertex start, Vertex end, Vertex[] toIterate, int totalGraph) {
        // initiate value
        for (Vertex toSetValue : toIterate) {
            toSetValue.setValue(-1);
        }

        // initiate boolean array
        boolean[] visited = new boolean[totalGraph];

        // initial infinite
        start.setValue(2000000);

        // set priority queue
        PriorityQueue<Vertex> queueCheck = new PriorityQueue<Vertex>(new The_Comparator());

        queueCheck.add(start);
        Vertex current;

        while (queueCheck.size() != 0) {

            current = queueCheck.remove();
            if (!visited[current.getIndex()]) {

                visited[current.getIndex()] = true;

                // get edges for every vertex that is connected
                for (Edge checkEdge : current.getEdgeAdj()) {
                    if (checkEdge.getExclusive()) {

                        // get value to compare
                        int getValueOne = checkEdge.getClosingTime() - checkEdge.getTravelingTime();
                        int getValueTwo = current.getVertexValue() - checkEdge.getTravelingTime();
                        int tempValue = Math.min(getValueTwo, getValueOne);

                        // set destination vertex's value (distance time)
                        if (checkEdge.getVertexStart() != current) {
                            if (checkEdge.getVertexStart().getVertexValue() < tempValue) {
                                checkEdge.getVertexStart().setValue(tempValue);
                            }
                            queueCheck.add(checkEdge.getVertexStart());
                        } else {
                            if (checkEdge.getVertexEnd().getVertexValue() < tempValue) {
                                checkEdge.getVertexEnd().setValue(tempValue);
                            }
                            queueCheck.add(checkEdge.getVertexEnd());
                        }

                    }
                }
            }
        }
        return end.getVertexValue();
    }

    // TANYA_BIASA
    public static int tanyaBiasa(Vertex start, Vertex end, Vertex[] toIterate, int totalGraph) {
        // initiate value
        for (Vertex toSetValue : toIterate) {
            toSetValue.setValue(-1);
        }

        // initiate boolean array
        boolean[] visited = new boolean[totalGraph];

        // initial infinite
        start.setValue(2000000);

        // set priority queue
        PriorityQueue<Vertex> queueCheck = new PriorityQueue<Vertex>(new The_Comparator());

        queueCheck.add(start);
        Vertex current;

        while (queueCheck.size() != 0) {

            current = queueCheck.remove();
            if (!visited[current.getIndex()]) {

                visited[current.getIndex()] = true;

                // get edges for every vertex that is connected
                for (Edge checkEdge : current.getEdgeAdj()) {
                    if (!checkEdge.getExclusive()) {

                        // get value to compare
                        int getValueOne = checkEdge.getClosingTime() - checkEdge.getTravelingTime();
                        int getValueTwo = current.getVertexValue() - checkEdge.getTravelingTime();
                        int tempValue = Math.min(getValueTwo, getValueOne);

                        // set destination vertex's value (distance time)
                        if (checkEdge.getVertexStart() != current) {
                            if (checkEdge.getVertexStart().getVertexValue() < tempValue) {
                                checkEdge.getVertexStart().setValue(tempValue);
                            }
                            queueCheck.add(checkEdge.getVertexStart());
                        } else {
                            if (checkEdge.getVertexEnd().getVertexValue() < tempValue) {
                                checkEdge.getVertexEnd().setValue(tempValue);
                            }
                            queueCheck.add(checkEdge.getVertexEnd());
                        }
                    }
                }
            }
        }
        return end.getVertexValue();
    }

    // TANYA_JALAN
    public static int tanyaJalan(int time, Edge[] normalEdge, Edge[] ExEdge) {
        int counter = 0;
        for (Edge toCheck : normalEdge) {

            // add counter if Edge is available
            if (toCheck.getClosingTime() > time) {
                counter += 1;
            }
        }

        for (Edge toCheck : ExEdge) {

            // add counter if Edge is available
            if (toCheck.getClosingTime() > time) {
                counter += 1;
            }
        }
        return counter;
    }

    // TANYA_HUBUNG
    public static boolean tanyaHubung(Vertex startVertex, Vertex endVertex, int totalGraph) {

        // initiate vertex queue
        Set<Vertex> listVertex = new HashSet<Vertex>();
        Queue<Vertex> queueVertex = new LinkedList<Vertex>();

        // initiate boolean array
        boolean[] visited = new boolean[totalGraph];

        queueVertex.add(startVertex);
        Vertex current;

        while (queueVertex.size() != 0) {
            current = queueVertex.remove();

            // checking neighbors
            if (!visited[current.getIndex()]) {
                visited[current.getIndex()] = true;
                queueVertex.addAll(current.getVertexAdj());
                listVertex.add(current);
            }
        }

        // checking connected vertexes
        for (Vertex toReturn : listVertex) {
            if (toReturn == endVertex) {
                return true;
            }
        }
        return false;
    }

    // get base number for coupon
    public static int changeBase(int currentBase, int newNumber) {
        if (currentBase == 1 && newNumber != 1) {
            currentBase = newNumber;
        } else if (newNumber != 1) {
            while (currentBase != newNumber) {
                if (currentBase > newNumber) {
                    currentBase /= newNumber;
                } else {
                    newNumber /= currentBase;
                }
            }
        }
        return currentBase;
    }

    // TANYA_KUPON
    public static int tanyaKupon(Vertex startVertex, Vertex endVertex, Vertex[] toIterate, int totalGraph) {
        // initiate value
        for (Vertex toSetValue : toIterate) {
            toSetValue.setValue(999999999);
        }

        // initiate boolean array
        boolean[] visited = new boolean[totalGraph];

        // initial zero
        startVertex.setValue(0);

        // set priority queue
        PriorityQueue<Vertex> queue = new PriorityQueue<Vertex>();

        queue.add(startVertex);
        Vertex current;

        while (queue.size() != 0) {
            current = queue.remove();
            if (!visited[current.getIndex()]) {

                visited[current.getIndex()] = true;

                // get edges for every vertex that is connected
                for (Edge checkEdge : current.getEdgeAdj()) {
                    if (checkEdge.getVertexEnd() != current) {

                        // get value to compare
                        int trackValue = checkEdge.getVertexStart().getVertexValue() + checkEdge.getCoupon();
                        int destination = checkEdge.getVertexEnd().getVertexValue();

                        // set destination vertex's value (distance time)
                        if (trackValue < destination) {
                            checkEdge.getVertexEnd().setValue(trackValue);
                        }
                        queue.add(checkEdge.getVertexEnd());
                    } else {

                        int trackValue = checkEdge.getVertexEnd().getVertexValue() + checkEdge.getCoupon();
                        int destination = checkEdge.getVertexStart().getVertexValue();
                        if (trackValue < destination) {
                            checkEdge.getVertexStart().setValue(trackValue);
                        }
                        queue.add(checkEdge.getVertexStart());
                    }
                }
            }
        }
        if (endVertex.getVertexValue() == 999999999) {
            return (int) -1;
        }
        return endVertex.getVertexValue();
    }

    public static void compareCoupon(int destinationValue, int trackValue, Vertex toCheck) {
        if (trackValue <= destinationValue) {
            toCheck.setValue(trackValue);
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

    static class The_Comparator implements Comparator<Vertex> {
        public int compare(Vertex vertexOne, Vertex vertexTwo) {
            return vertexTwo.getVertexValue() - vertexOne.getVertexValue();
        }
    }

    static class Edge {
        int travelingTime;
        int coupon;
        int closingTime;
        Vertex startVertex;
        Vertex endVertex;
        boolean exclusive;

        public Edge(int travelingTime, int closingTime, int coupon, Vertex startVertex, Vertex endVertex,
                boolean exclusive) {
            this.travelingTime = travelingTime;
            this.closingTime = closingTime;
            this.coupon = coupon;
            this.startVertex = startVertex;
            this.endVertex = endVertex;
            this.exclusive = exclusive;
        }

        public int getClosingTime() {
            return this.closingTime;
        }

        public int getTravelingTime() {
            return this.travelingTime;
        }

        public int getCoupon() {
            return this.coupon;
        }

        public void setCoupon(int logCoupon) {
            this.coupon = logCoupon;
        }

        public Vertex getVertexStart() {
            return this.startVertex;
        }

        public Vertex getVertexEnd() {
            return this.endVertex;
        }

        public boolean getExclusive() {
            return this.exclusive;
        }
    }

    static class Vertex implements Comparable<Vertex> {
        String name;
        Set<Edge> adjacencyEdge;
        Set<Vertex> adjacencyVertex;
        int vertexIndex;
        int vertexValue;

        public Vertex(String name, int vertexIndex) {
            this.name = name;
            this.adjacencyEdge = new HashSet<Edge>();
            this.adjacencyVertex = new HashSet<Vertex>();
            this.vertexIndex = vertexIndex;
        }

        public void addEdge(Edge toAdd) {
            this.adjacencyEdge.add(toAdd);
        }

        public int getIndex() {
            return this.vertexIndex;
        }

        public void addVertex(Vertex toAdd) {
            this.adjacencyVertex.add(toAdd);
        }

        public Set<Edge> getEdgeAdj() {
            return this.adjacencyEdge;
        }

        public Set<Vertex> getVertexAdj() {
            return this.adjacencyVertex;
        }

        public void setValue(int value) {
            this.vertexValue = value;
        }

        public int getVertexValue() {
            return this.vertexValue;
        }

        public String getName() {
            return this.name;
        }

        public int compareTo(Vertex others) {
            return this.vertexValue - others.getVertexValue();
        }

    }

}
