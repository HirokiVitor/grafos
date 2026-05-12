import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class GexfParser {
    public Graph parse(String filePath) throws Exception {
        Graph graph = new Graph();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);

        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File(filePath));
        document.getDocumentElement().normalize();

        // Lê todos os nós do arquivo GEXF.
        NodeList nodes = document.getElementsByTagNameNS("*", "node");
        for (int i = 0; i < nodes.getLength(); i++) {
            Element node = (Element) nodes.item(i);
            String id = node.getAttribute("id");
            String label = node.hasAttribute("label") ? node.getAttribute("label") : id;
            graph.addVertex(id, label);
        }

        // Lê todas as arestas do arquivo GEXF.
        NodeList edges = document.getElementsByTagNameNS("*", "edge");
        for (int i = 0; i < edges.getLength(); i++) {
            Element edge = (Element) edges.item(i);
            String source = edge.getAttribute("source");
            String target = edge.getAttribute("target");
            graph.addEdge(source, target);
        }

        return graph;
    }
}
