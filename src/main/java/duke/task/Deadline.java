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
        return "D/" + description + "/" + date;
    }
}
