package se.poc.sikuli.webdriver.support;

public enum Score {
    FULL(0.995),
    GOOD(0.7),
    MEDIOCRE(0.5),
    LOW(0.3);

    private final double score;

    Score(double score) {
        this.score = score;
    }

    public double value() { return score; }
}
