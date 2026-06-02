import java.util.Objects;

public class Vertex {
    private final String id;
    private final String label;

    public Vertex(String id, String label) {
        this.id = id;
        this.label = label;
    }

    public String getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Vertex)) {
            return false;
        }
        Vertex vertex = (Vertex) object;
        return Objects.equals(id, vertex.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
