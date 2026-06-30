import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Uso correto:");
            System.out.println("javac -d bin src/*.java");
            System.out.println("java -cp bin Main data/candidatos-vagas.txt output");
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

            BipartiteGraph graph = InputReader.read(inputFile);
            MaxFlowMatcher matcher = new MaxFlowMatcher(graph);
            List<Assignment> assignments = matcher.findMaximumMatching();

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
                writer.write(String.valueOf(assignments.size()));
                writer.newLine();

                for (Assignment assignment : assignments) {
                    String line = assignment.getCandidate() + " " + assignment.getJob();
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
