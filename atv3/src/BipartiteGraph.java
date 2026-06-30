import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BipartiteGraph {
    private final int candidateCount;
    private final int jobCount;
    private final List<List<Integer>> eligibleJobsByCandidate;

    public BipartiteGraph(int candidateCount, int jobCount) {
        if (candidateCount < 0 || candidateCount > 50) {
            throw new IllegalArgumentException("Numero de candidatos invalido: " + candidateCount);
        }

        if (jobCount < 0 || jobCount > 50) {
            throw new IllegalArgumentException("Numero de vagas invalido: " + jobCount);
        }

        this.candidateCount = candidateCount;
        this.jobCount = jobCount;
        this.eligibleJobsByCandidate = new ArrayList<>();

        for (int candidate = 0; candidate < candidateCount; candidate++) {
            eligibleJobsByCandidate.add(new ArrayList<>());
        }
    }

    public void addEligibility(int candidate, int job) {
        validateCandidate(candidate);
        validateJob(job);

        List<Integer> eligibleJobs = eligibleJobsByCandidate.get(candidate);
        if (!eligibleJobs.contains(job)) {
            eligibleJobs.add(job);
        }
    }

    public boolean isEligible(int candidate, int job) {
        validateCandidate(candidate);
        validateJob(job);
        return eligibleJobsByCandidate.get(candidate).contains(job);
    }

    public int getCandidateCount() {
        return candidateCount;
    }

    public int getJobCount() {
        return jobCount;
    }

    public List<Integer> getEligibleJobs(int candidate) {
        validateCandidate(candidate);
        return Collections.unmodifiableList(eligibleJobsByCandidate.get(candidate));
    }

    private void validateCandidate(int candidate) {
        if (candidate < 0 || candidate >= candidateCount) {
            throw new IllegalArgumentException("Candidato fora do intervalo: " + candidate);
        }
    }

    private void validateJob(int job) {
        if (job < 0 || job >= jobCount) {
            throw new IllegalArgumentException("Vaga fora do intervalo: " + job);
        }
    }
}
