package duke.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Task {
    protected String description;
    protected boolean isDone;
    protected LocalDate date;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
        this.date = null;
    }

    public String getDescription() {
        return description;
    }

    public boolean isDone() {
        return isDone;
    }

    public LocalDate getDate() {
        return date;
    }

    public String printDate() {
        return date.format(DateTimeFormatter.ofPattern("MMM dd yyyy"));
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public String getStatusIcon() {
        // return tick or cross symbol
        return (isDone ? "\u2713" : "\u2718");
    }

    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }

    public String getSavedLine() {
        return null;
    }
}
