import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Queue;

public class MaxFlowMatcher {
    private final BipartiteGraph graph;

    public MaxFlowMatcher(BipartiteGraph graph) {
        this.graph = graph;
    }

    public List<Assignment> findMaximumMatching() {
        int candidateCount = graph.getCandidateCount();
        int jobCount = graph.getJobCount();
        int source = candidateCount + jobCount;
        int sink = source + 1;
        int totalVertices = sink + 1;
        int[][] capacity = new int[totalVertices][totalVertices];

        for (int candidate = 0; candidate < candidateCount; candidate++) {
            capacity[source][candidate] = 1;

            for (int job : graph.getEligibleJobs(candidate)) {
                int jobNode = candidateCount + job;
                capacity[candidate][jobNode] = 1;
            }
        }

        for (int job = 0; job < jobCount; job++) {
            int jobNode = candidateCount + job;
            capacity[jobNode][sink] = 1;
        }

        int[][] flow = new int[totalVertices][totalVertices];
        int[] parent = new int[totalVertices];

        while (findAugmentingPath(source, sink, capacity, flow, parent)) {
            int pathFlow = 1;

            for (int current = sink; current != source; current = parent[current]) {
                int previous = parent[current];
                flow[previous][current] += pathFlow;
                flow[current][previous] -= pathFlow;
            }
        }

        List<Assignment> assignments = new ArrayList<>();
        for (int candidate = 0; candidate < candidateCount; candidate++) {
            for (int job : graph.getEligibleJobs(candidate)) {
                int jobNode = candidateCount + job;
                if (flow[candidate][jobNode] == 1) {
                    assignments.add(new Assignment(candidate, job));
                }
            }
        }

        assignments.sort(Comparator
                .comparingInt(Assignment::getCandidate)
                .thenComparingInt(Assignment::getJob));
        return assignments;
    }

    private boolean findAugmentingPath(
            int source,
            int sink,
            int[][] capacity,
            int[][] flow,
            int[] parent) {
        for (int vertex = 0; vertex < parent.length; vertex++) {
            parent[vertex] = -1;
        }

        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(source);
        parent[source] = source;

        while (!queue.isEmpty()) {
            int current = queue.remove();

            for (int next = 0; next < capacity.length; next++) {
                int residualCapacity = capacity[current][next] - flow[current][next];
                if (parent[next] == -1 && residualCapacity > 0) {
                    parent[next] = current;

                    if (next == sink) {
                        return true;
                    }

                    queue.add(next);
                }
            }
        }

        return false;
    }
}
