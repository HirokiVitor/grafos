import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Queue;

public class GraphStatistics {
    private final Graph graph;

    public GraphStatistics(Graph graph) {
        this.graph = graph;
    }

    private Map<String, Integer> breadthFirstSearchDistances(String sourceId) {
        Map<String, Integer> distances = new HashMap<>();
        Queue<String> queue = new ArrayDeque<>();

        distances.put(sourceId, 0);
        queue.add(sourceId);

        while (!queue.isEmpty()) {
            String current = queue.poll();

            for (String neighbor : graph.getNeighbors(current)) {
                if (!distances.containsKey(neighbor)) {
                    distances.put(neighbor, distances.get(current) + 1);
                    queue.add(neighbor);
                }
            }
        }

        return distances;
    }

    public Map<String, Integer> calculateEccentricity() {
        /*
         * Excentricidade de um vértice:
         * representa a maior distância, em número de arestas, entre esse vértice
         * e qualquer outro vértice do grafo. Em outras palavras, indica o quão
         * distante está o vértice mais afastado a partir do vértice analisado.
         */
        Map<String, Integer> eccentricity = new LinkedHashMap<>();

        for (Vertex vertex : graph.getVertices()) {
            Map<String, Integer> distances = breadthFirstSearchDistances(vertex.getId());
            int maxDistance = 0;

            for (int distance : distances.values()) {
                if (distance > maxDistance) {
                    maxDistance = distance;
                }
            }

            eccentricity.put(vertex.getId(), maxDistance);
        }

        return eccentricity;
    }

    public Map<String, Double> calculateNormalizedClosenessCentrality() {
        /*
         * Closeness centrality normalizada:
         * mede o quão próximo um vértice está de todos os demais vértices do grafo.
         * Para grafo conexo, a versão normalizada é calculada por:
         * (n - 1) / soma das distâncias do vértice até todos os outros vértices.
         * Quanto maior o valor, mais central é o vértice na rede.
         */
        Map<String, Double> closenessCentrality = new LinkedHashMap<>();
        int n = graph.getNumberOfVertices();

        for (Vertex vertex : graph.getVertices()) {
            Map<String, Integer> distances = breadthFirstSearchDistances(vertex.getId());
            int sumDistances = 0;

            for (int distance : distances.values()) {
                sumDistances += distance;
            }

            double normalizedCloseness = (double) (n - 1) / sumDistances;
            closenessCentrality.put(vertex.getId(), normalizedCloseness);
        }

        return closenessCentrality;
    }
}
