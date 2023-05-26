package SE02.Capstone.dynamodb.models;

public class ScoredStory implements Comparable<ScoredStory> {
    private Story story;
    private double score;

    public ScoredStory(Story story, double score) {
        this.story = story;
        this.score = score;
    }

    public Story getStory() {
        return story;
    }

    public double getScore() {
        return score;
    }

    @Override
    public int compareTo(ScoredStory other) {
        // Implement comparison logic based on score for sorting purposes
        return Double.compare(this.score, other.score);
    }
}
