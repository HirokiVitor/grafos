import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Graph {
    private final Map<String, Vertex> vertices;
    private final Map<String, Set<String>> adjacencyList;

    public Graph() {
        this.vertices = new LinkedHashMap<>();
        this.adjacencyList = new LinkedHashMap<>();
    }

    public void addVertex(String id, String label) {
        if (!vertices.containsKey(id)) {
            vertices.put(id, new Vertex(id, label));
            adjacencyList.put(id, new LinkedHashSet<>());
        }
    }

    public void addEdge(String sourceId, String targetId) {
        if (!vertices.containsKey(sourceId) || !vertices.containsKey(targetId)) {
            throw new IllegalArgumentException("Aresta possui vértice inexistente: " + sourceId + " - " + targetId);
        }

        // Como o grafo da atividade é não dirigido, a aresta é inserida nos dois sentidos.
        adjacencyList.get(sourceId).add(targetId);
        adjacencyList.get(targetId).add(sourceId);
    }

    public Vertex getVertex(String id) {
        return vertices.get(id);
    }

    public List<Vertex> getVertices() {
        return new ArrayList<>(vertices.values());
    }

    public Set<String> getNeighbors(String id) {
        return Collections.unmodifiableSet(adjacencyList.get(id));
    }

    public int getNumberOfVertices() {
        return vertices.size();
    }
}
