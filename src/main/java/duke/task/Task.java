package duke.task;

public class Task {
    protected String description;
    protected boolean isDone;
    protected String date;

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

    public String getDate() {
        return date;
    }

    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * This method returns the status icon based on boolean value of if a task is done
     *
     * @return icon status icon of the task
     */
    public String getStatusIcon() {
        // return tick or cross symbol
        return (isDone ? "\u2713" : "\u2718");
    }

    /**
     * This method returns the line of the task which is to be printed.
     * It includes the type of task in the subclasses, status icon, description and date if applicable.
     *
     * @return line string which describes the task
     */
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }

    /**
     * This method is used in the subclasses. It will return the line that is to be saved to the duke text file.
     * @return savedLine line to be saved into text file
     */
    public String getSavedLine() {
        return null;
    }
}
