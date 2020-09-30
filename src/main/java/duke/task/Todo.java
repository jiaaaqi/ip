package duke.task;

public class Todo extends Task {
    public Todo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    @Override
    public String getSavedLine() {
        String done = isDone ? "1" : "0";
        return "T/" + done + "/" + description;
    }
}
