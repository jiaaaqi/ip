package duke;

import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.Todo;

/**
 * This class will handle user commands.
 * Handling includes check if the command is valid, returning the type of command,
 * and also extracting the data from commands to return to the run method in Duke.java.
 */
public class Parser {
    private String command;

    public Parser(String command) {
        this.command = command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    /**
     * This method checks if the task command has all the required parameters.
     * If the task is a todo task, only description is required.
     * The method will return a checkDescription flag.
     * If the task is a deadline or event, both description and date is required.
     * If the deadline or event does not have description, the method will return false immediately,
     * and would not go on to check the presence of a date.
     *
     * @param task Array of strings that has been split into type, description and date
     * @return checkDate flag to check if it has a date
     */
    private boolean checkTaskInputCommand(String[] task) {
        boolean checkDescription;
        boolean checkDate;

        if (task.length>=2) {
            checkDescription = true;
        } else {
            System.out.println("     Oops! The description of " + task[0] + " cannot be empty. :(");
            return false;
        }

        if (task[0].equals("event") || task[0].contains("deadline")) {
            if (task[2] != null) {
                checkDate = true;
            } else {
                System.out.println("     Oops! The date of " + task[0] + " cannot be empty!!!");
                checkDate = false;
            }
        } else {
            return checkDescription;
        }

        return checkDate;
    }

    /**
     * This method splits the given task command into type of task, description and date
     * It also checks if the command has all the requirements to create a task, and creates the task.
     * If it does not meet the requirements, a null task will be returned.
     *
     * @return task task that is created
     */
    public Task extractTaskFromCommand() {
        String[] taskArray = command.split(" ", 2);
        String type = taskArray[0];
        int index = taskArray[1].indexOf("/");
        String description = taskArray[1];
        String date = null;

        if (index != -1) {
            description = taskArray[1].substring(0, index-1);
            date = taskArray[1].substring(index+4);
        }
        String[] newTaskArray = new String[3];
        newTaskArray[0] = type;
        newTaskArray[1] = description;
        newTaskArray[2] = date;

        if (!checkTaskInputCommand(newTaskArray)) {
            return null;
        }

        Task task;
        switch (type) {
        case "todo":
            task = new Todo(description);
            break;
        case "deadline":
            task = new Deadline(description, date);
            break;
        case "event":
            task = new Event(description, date);
            break;
        default:
            task = null;
        }

        return task;
    }

    /**
     * This method checks if the input command is valid and returns the type of command as a string.
     * If invalid, "invalid" will be returned instead.
     *
     * @return typeOfCommand type of command
     */
    public String verifyCommand() {
        if (command.equals("list")) {
            return command;
        } else if (command.contains("todo") || command.contains("event") || command.contains("deadline")) {
            return "task";
        } else if (command.contains("done")) {
            return "done";
        } else if (command.contains("delete")) {
            return "delete";
        } else {
            return "invalid";
        }
    }
}
