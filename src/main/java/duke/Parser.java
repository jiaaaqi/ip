package duke;

import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.Todo;

import java.time.format.DateTimeParseException;

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
     * This method checks if a task command has a task description.
     *
     * @param command command string to be checked
     * @return taskArray string array which splits the type of task and its description
     * @throws DukeException if the description is not found
     */
    private String[] checkTaskDescription(String command) throws DukeException {
        // taskArray: 0->type of task, 1->description and date if applicable
        String[] taskArray = command.split(" ", 2);
        if (taskArray.length<2 || taskArray[1]==null) {
            throw new DukeException();
        }
        taskArray[0] = taskArray[0].toLowerCase();
        return taskArray;
    }

    /**
     * This method checks if the date for the task is present for deadlines and events.
     *
     * @param taskArray string array that stores task's type and whatever is behind
     * @return taskArrayWithDate string array that stores the task's type, description and date
     * @throws DukeException if date is not found for deadlines and events
     */
    private String[] checkTaskDate(String[] taskArray) throws DukeException {
        // newTaskArray: 0 -> type of task, 1 -> description, 2 -> date if applicable
        String[] newTaskArray = new String[3];
        newTaskArray[0] = taskArray[0];
        if (newTaskArray[0].equals("todo")) {
            return taskArray;
        } else {
            if (taskArray[0].equals("deadline") && !taskArray[1].contains("/by")) {
                throw new DukeException();
            } else if (taskArray[0].equals("event") && !taskArray[1].contains("/on")) {
                throw new DukeException();
            }
            int index = taskArray[1].indexOf("/");
            newTaskArray[1] = taskArray[1].substring(0, index-1);
            newTaskArray[2] = taskArray[1].substring(index+4);
        }
        return newTaskArray;
    }

    /**
     * This method splits the given task command into type of task, description and date
     * It also checks if the command has all the requirements to create a task, and creates the task.
     * If it does not meet the requirements, a null task will be returned.
     *
     * @return task task that is created
     */
    public Task extractTaskFromCommand() {
        String[] taskArray = new String[2];
        try {
            taskArray = checkTaskDescription(command);
        } catch (DukeException e) {
            System.out.println("     Oops! The description of " + taskArray[0] + " cannot be empty. :(");
            return null;
        }
        String[] taskArrayWithDate;
        try {
            taskArrayWithDate = checkTaskDate(taskArray);
        } catch (DukeException e) {
            System.out.println("     Oops! The date of " + taskArray[0] + " cannot be empty!!!");
            return null;
        }

        Task task;
        switch (taskArrayWithDate[0]) {
        case "todo":
            task = new Todo(taskArrayWithDate[1]);
            break;
        case "deadline":
            try {
                task = new Deadline(taskArrayWithDate[1], taskArrayWithDate[2]);
            } catch (DateTimeParseException e) {
                System.out.println("     Please input the date in the format: YYYY-MM-DD.");
                return null;
            }
            break;
        case "event":
            try {
                task = new Event(taskArrayWithDate[1], taskArrayWithDate[2]);
            } catch (DateTimeParseException e) {
                System.out.println("     Please input the date in the format: YYYY-MM-DD.");
                return null;
            }
            break;
        default:
            task = null;
        }

        return task;
    }

    public String extractKeywordsFromCommand() {
        String[] keywords = command.split(" ", 2);
        return keywords[1];
    }


    /**
     * This method checks if the input command is valid and returns the type of command as a string.
     * If invalid, "invalid" will be returned instead.
     *
     * @return typeOfCommand type of command
     */
    public String verifyCommand() throws DukeException {
        if (command.equals("list")) {
            return command;
        } else if (command.contains("todo") || command.contains("event") || command.contains("deadline")) {
            return "task";
        } else if (command.contains("done")) {
            return "done";
        } else if (command.contains("delete")) {
            return "delete";
        } else if (command.contains("find")) {
            return "find";
        } else {
            throw new DukeException();
        }
    }
}
