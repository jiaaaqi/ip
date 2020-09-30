package duke.task;

public class Deadline extends Task {
    public Deadline(String description, String by) {
        super(description);
        this.date = by;
    }

    @Override
    public String toString() {
        return ("[D]" + super.toString() + " (by: " + date + ")");
    }

    @Override
    public String getSavedLine() {
        String done = isDone ? "1" : "0";
        return "D/" + done + "/" + description + "/" + date;
    }
}
