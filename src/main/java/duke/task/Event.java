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

    @Override
    public String getSavedLine() {
        String done = isDone ? "1" : "0";
        return "E/" + done + "/" + description + "/" + date;
    }
}
