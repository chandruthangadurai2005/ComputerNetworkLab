import java.util.*;

class Graph {
    private final Map<String, Map<String, Integer>> adj = new HashMap<>();

    public void addEdge(String src, String dest, int cost) {
        adj.computeIfAbsent(src, k -> new HashMap<>()).put(dest, cost);
        adj.computeIfAbsent(dest, k -> new HashMap<>()).put(src, cost); // Assuming undirected graph
    }

    public Map<String, Integer> dijkstra(String start) {
        Map<String, Integer> distances = new HashMap<>();
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(n -> n.cost));
        Set<String> visited = new HashSet<>();

        adj.keySet().forEach(node -> distances.put(node, Integer.MAX_VALUE));
        distances.put(start, 0);
        pq.add(new Node(start, 0));

        while (!pq.isEmpty()) {
            Node current = pq.poll();
            if (!visited.add(current.name)) continue;

            Map<String, Integer> neighbors = adj.getOrDefault(current.name, Collections.emptyMap());
            for (var entry : neighbors.entrySet()) {
                if (!visited.contains(entry.getKey())) {
                    int newDist = distances.get(current.name) + entry.getValue();
                    if (newDist < distances.get(entry.getKey())) {
                        distances.put(entry.getKey(), newDist);
                        pq.add(new Node(entry.getKey(), newDist));
                    }
                }
            }
        }
        return distances;
    }

    static class Node {
        String name;
        int cost;
        Node(String n, int c) { name = n; cost = c; }
    }
}

public class Ospf {
    public static void main(String[] args) {
        Graph network = new Graph();

        // Adding edges (router links) with costs
        network.addEdge("R1", "R2", 10);
        network.addEdge("R1", "R3", 15);
        network.addEdge("R2", "R4", 12);
        network.addEdge("R3", "R4", 10);
        network.addEdge("R4", "R5", 5);

        Map<String, Integer> shortestPaths = network.dijkstra("R1");

        System.out.println("Shortest paths from R1:");
        shortestPaths.forEach((node, cost) -> {
            System.out.println("To " + node + " = " + cost);
        });
    }
}
