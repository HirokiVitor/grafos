import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class GexfReader {
    public Graph read(String filePath) throws Exception {
        Graph graph = new Graph();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(false);
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File(filePath));
        document.getDocumentElement().normalize();

        NodeList nodes = document.getElementsByTagName("node");
        for (int i = 0; i < nodes.getLength(); i++) {
            Element node = (Element) nodes.item(i);
            String id = node.getAttribute("id");
            String label = node.getAttribute("label");
            graph.addVertex(id, label);
        }

        NodeList edges = document.getElementsByTagName("edge");
        for (int i = 0; i < edges.getLength(); i++) {
            Element edge = (Element) edges.item(i);
            String source = edge.getAttribute("source");
            String target = edge.getAttribute("target");
            graph.addUndirectedEdge(source, target);
        }

        return graph;
    }
}
