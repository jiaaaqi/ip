public class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getDescription() {
        return description;
    }

    public void markAsDone() {
        this.isDone = true;
        System.out.println("     Nice! I've marked this task as done: ");
        System.out.println("        [" + getStatusIcon() + "] " + getDescription());
    }

    public String getStatusIcon() {
        // return tick or cross symbol
        return (isDone ? "\u2713" : "\u2718");
    }
}
