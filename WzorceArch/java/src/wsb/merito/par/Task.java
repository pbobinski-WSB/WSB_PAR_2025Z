package wsb.merito.par;

public class Task {
    private int id;
    private String description;
    private boolean isDone;

    public Task(int id, String description) {
        this.id = id;
        this.description = description;
        this.isDone = false;
    }

    // Gettery i Settery...
    public int getId() { return id; }
    public String getDescription() { return description; }
    public boolean isDone() { return isDone; }
    public void setDone(boolean done) { isDone = done; }

    @Override
    public String toString() {
        return id + ". [" + (isDone ? "x" : " ") + "] " + description;
    }
}
