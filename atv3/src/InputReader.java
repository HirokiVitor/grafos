import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class InputReader {
    public static BipartiteGraph read(String inputFile) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String firstLine = nextContentLine(reader);
            if (firstLine == null) {
                throw new IllegalArgumentException("Arquivo de entrada vazio.");
            }

            String[] header = firstLine.trim().split("\\s+");
            if (header.length != 2) {
                throw new IllegalArgumentException("Primeira linha deve conter C e V.");
            }

            int candidateCount = Integer.parseInt(header[0]);
            int jobCount = Integer.parseInt(header[1]);
            BipartiteGraph graph = new BipartiteGraph(candidateCount, jobCount);

            String line;
            int lineNumber = 1;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                line = line.trim();

                if (line.isEmpty()) {
                    continue;
                }

                String[] parts = line.split("\\s+");
                if (parts.length != 2) {
                    throw new IllegalArgumentException("Linha " + lineNumber + " deve conter candidato e vaga.");
                }

                int candidate = Integer.parseInt(parts[0]);
                int job = Integer.parseInt(parts[1]);
                graph.addEligibility(candidate, job);
            }

            return graph;
        }
    }

    private static String nextContentLine(BufferedReader reader) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            if (!line.trim().isEmpty()) {
                return line;
            }
        }

        return null;
    }
}
