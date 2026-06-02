import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class BetweennessCentralityCalculator {
    /**
     * A betweenness centrality mede quantas vezes um vertice aparece nos menores
     * caminhos entre pares de outros vertices do grafo. Assim, vertices com valor
     * alto costumam funcionar como pontes ou pontos estrategicos de passagem entre
     * diferentes regioes da rede.
     */
    public Map<String, Double> calculate(Graph graph) {
        List<Vertex> vertices = graph.getVertices();
        Map<String, Double> centrality = new LinkedHashMap<>();

        for (Vertex vertex : vertices) {
            centrality.put(vertex.getId(), 0.0);
        }

        for (Vertex source : vertices) {
            Deque<String> stack = new ArrayDeque<>();
            Map<String, List<String>> predecessors = new HashMap<>();
            Map<String, Integer> distance = new HashMap<>();
            Map<String, Double> sigma = new HashMap<>();

            for (Vertex vertex : vertices) {
                predecessors.put(vertex.getId(), new ArrayList<>());
                distance.put(vertex.getId(), -1);
                sigma.put(vertex.getId(), 0.0);
            }

            sigma.put(source.getId(), 1.0);
            distance.put(source.getId(), 0);

            Queue<String> queue = new ArrayDeque<>();
            queue.add(source.getId());

            while (!queue.isEmpty()) {
                String current = queue.remove();
                stack.push(current);

                for (String neighbor : graph.getNeighbors(current)) {
                    if (distance.get(neighbor) < 0) {
                        queue.add(neighbor);
                        distance.put(neighbor, distance.get(current) + 1);
                    }

                    if (distance.get(neighbor) == distance.get(current) + 1) {
                        sigma.put(neighbor, sigma.get(neighbor) + sigma.get(current));
                        predecessors.get(neighbor).add(current);
                    }
                }
            }

            Map<String, Double> dependency = new HashMap<>();
            for (Vertex vertex : vertices) {
                dependency.put(vertex.getId(), 0.0);
            }

            while (!stack.isEmpty()) {
                String vertex = stack.pop();

                for (String predecessor : predecessors.get(vertex)) {
                    double contribution = (sigma.get(predecessor) / sigma.get(vertex)) * (1.0 + dependency.get(vertex));
                    dependency.put(predecessor, dependency.get(predecessor) + contribution);
                }

                if (!vertex.equals(source.getId())) {
                    centrality.put(vertex, centrality.get(vertex) + dependency.get(vertex));
                }
            }
        }

        // Como o grafo e nao dirigido, cada menor caminho foi contado duas vezes.
        for (String vertexId : centrality.keySet()) {
            centrality.put(vertexId, centrality.get(vertexId) / 2.0);
        }

        return centrality;
    }
}
