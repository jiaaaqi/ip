package duke.task;
import java.time.LocalDate;

public class Event extends Task {
    public Event(String description, String on) {
        super(description);
        this.date = LocalDate.parse(on);
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (on: " + super.printDate() + ")";
    }

    @Override
    public String getSavedLine() {
        String done = isDone ? "1" : "0";
        return "E/" + done + "/" + description + "/" + getDate();
    }
}
