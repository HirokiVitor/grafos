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

    public void addUndirectedEdge(String sourceId, String targetId) {
        if (!vertices.containsKey(sourceId) || !vertices.containsKey(targetId)) {
            throw new IllegalArgumentException("A aresta possui vertice inexistente.");
        }

        adjacencyList.get(sourceId).add(targetId);
        adjacencyList.get(targetId).add(sourceId);
    }

    public List<Vertex> getVertices() {
        return new ArrayList<>(vertices.values());
    }

    public Vertex getVertex(String id) {
        return vertices.get(id);
    }

    public Set<String> getNeighbors(String id) {
        return Collections.unmodifiableSet(adjacencyList.get(id));
    }
}
