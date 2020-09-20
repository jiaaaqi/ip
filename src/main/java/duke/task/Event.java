package duke.task;

public class Event extends Task {
    public Event(String description, String on) {
        super(description);
        this.date = on;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (on: " + date + ")";
    }
}
