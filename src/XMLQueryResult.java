public class XMLQueryResult {
    private String path;
    private int hits;
    private int percentage;

    public XMLQueryResult() {

    }

    public XMLQueryResult(String path, int hits, int percentage) {
        this.path = path;
        this.hits = hits;
        this.percentage = percentage;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getHits() {
        return hits;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public String toString() {
        return String.format("%s: %d hit(s) %d%%", path, hits, percentage);
    }
}
