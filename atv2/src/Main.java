import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Locale;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Uso correto:");
            System.out.println("javac -d bin src/*.java");
            System.out.println("java -cp bin Main data/LesMiserables.gexf output");
            return;
        }

        String inputFile = args[0];
        String outputDirectory = args[1];
        String outputFile = outputDirectory + File.separator + "saida.txt";

        try {
            File directory = new File(outputDirectory);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            GexfReader reader = new GexfReader();
            Graph graph = reader.read(inputFile);

            BetweennessCentralityCalculator calculator = new BetweennessCentralityCalculator();
            Map<String, Double> centralities = calculator.calculate(graph);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
                writer.write("id;label;betweenness_centrality");
                writer.newLine();

                for (Vertex vertex : graph.getVertices()) {
                    double value = centralities.get(vertex.getId());
                    String line = String.format(Locale.US, "%s;%s;%.6f", vertex.getId(), vertex.getLabel(), value);
                    System.out.println(line);
                    writer.write(line);
                    writer.newLine();
                }
            }

            System.out.println("\nArquivo gerado com sucesso em: " + outputFile);
        } catch (Exception exception) {
            System.err.println("Erro ao executar o programa: " + exception.getMessage());
            exception.printStackTrace();
        }
    }
}
