import java.io.PrintWriter;
import java.util.Locale;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);

        String inputFile = args.length >= 1 ? args[0] : "data/LesMiserables.gexf";
        String outputDirectory = args.length >= 2 ? args[1] : "output";

        try {
            GexfParser parser = new GexfParser();
            Graph graph = parser.parse(inputFile);

            GraphStatistics statistics = new GraphStatistics(graph);
            Map<String, Integer> eccentricity = statistics.calculateEccentricity();
            Map<String, Double> closenessCentrality = statistics.calculateNormalizedClosenessCentrality();

            writeEccentricityFile(graph, eccentricity, outputDirectory + "/eccentricity.txt");
            writeClosenessCentralityFile(graph, closenessCentrality, outputDirectory + "/closeness_centrality.txt");

            printEccentricity(graph, eccentricity);
            System.out.println();
            printClosenessCentrality(graph, closenessCentrality);
        } catch (Exception exception) {
            System.err.println("Erro ao processar o grafo: " + exception.getMessage());
            exception.printStackTrace();
        }
    }

    private static void writeEccentricityFile(Graph graph, Map<String, Integer> eccentricity, String filePath) throws Exception {
        try (PrintWriter writer = new PrintWriter(filePath)) {
            writer.println("id;label;eccentricity");
            for (Vertex vertex : graph.getVertices()) {
                writer.printf("%s;%s;%d%n", vertex.getId(), vertex.getLabel(), eccentricity.get(vertex.getId()));
            }
        }
    }

    private static void writeClosenessCentralityFile(Graph graph, Map<String, Double> closenessCentrality, String filePath) throws Exception {
        try (PrintWriter writer = new PrintWriter(filePath)) {
            writer.println("id;label;closeness_centrality_normalized");
            for (Vertex vertex : graph.getVertices()) {
                writer.printf("%s;%s;%.6f%n", vertex.getId(), vertex.getLabel(), closenessCentrality.get(vertex.getId()));
            }
        }
    }

    private static void printEccentricity(Graph graph, Map<String, Integer> eccentricity) {
        System.out.println("Eccentricity");
        for (Vertex vertex : graph.getVertices()) {
            System.out.printf("%s (%s): %d%n", vertex.getLabel(), vertex.getId(), eccentricity.get(vertex.getId()));
        }
    }

    private static void printClosenessCentrality(Graph graph, Map<String, Double> closenessCentrality) {
        System.out.println("Normalized Closeness Centrality");
        for (Vertex vertex : graph.getVertices()) {
            System.out.printf("%s (%s): %.6f%n", vertex.getLabel(), vertex.getId(), closenessCentrality.get(vertex.getId()));
        }
    }
}
