package duke.task;
import java.time.LocalDate;

public class Deadline extends Task {
    public Deadline(String description, String by) {
        super(description);
        this.date = LocalDate.parse(by);
    }

    @Override
    public String toString() {
        return ("[D]" + super.toString() + " (by: " + super.printDate() + ")");
    }

    @Override
    public String getSavedLine() {
        String done = isDone ? "1" : "0";
        return "D/" + done + "/" + description + "/" + getDate();
    }
}
