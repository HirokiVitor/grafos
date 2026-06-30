import java.util.List;
import java.util.HashSet;
import java.util.Set;

public class MaxFlowMatcherTest {
    public static void main(String[] args) throws Exception {
        shouldFindMaximumMatchingForProvidedInput();
        shouldRejectCandidateOutsideRange();
        System.out.println("Todos os testes passaram.");
    }

    private static void shouldFindMaximumMatchingForProvidedInput() throws Exception {
        BipartiteGraph graph = InputReader.read("data/candidatos-vagas.txt");
        MaxFlowMatcher matcher = new MaxFlowMatcher(graph);

        List<Assignment> assignments = matcher.findMaximumMatching();

        assertEquals(30, assignments.size(), "Quantidade maxima de candidatos alocados");
        assertValidAssignments(graph, assignments);
    }

    private static void shouldRejectCandidateOutsideRange() {
        BipartiteGraph graph = new BipartiteGraph(2, 2);

        try {
            graph.addEligibility(2, 1);
            throw new AssertionError("Deveria rejeitar candidato fora do intervalo.");
        } catch (IllegalArgumentException expected) {
            assertEquals(
                    "Candidato fora do intervalo: 2",
                    expected.getMessage(),
                    "Mensagem de erro para candidato invalido");
        }
    }

    private static void assertValidAssignments(BipartiteGraph graph, List<Assignment> assignments) {
        Set<Integer> usedCandidates = new HashSet<>();
        Set<Integer> usedJobs = new HashSet<>();

        for (Assignment assignment : assignments) {
            int candidate = assignment.getCandidate();
            int job = assignment.getJob();

            if (!usedCandidates.add(candidate)) {
                throw new AssertionError("Candidato alocado mais de uma vez: " + candidate);
            }

            if (!usedJobs.add(job)) {
                throw new AssertionError("Vaga alocada mais de uma vez: " + job);
            }

            if (!graph.isEligible(candidate, job)) {
                throw new AssertionError("Alocacao nao permitida pela entrada: " + candidate + " " + job);
            }
        }
    }

    private static void assertEquals(Object expected, Object actual, String message) {
        if (!expected.equals(actual)) {
            throw new AssertionError(message + ". Esperado: " + expected + ", obtido: " + actual);
        }
    }
}
