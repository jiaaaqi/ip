package duke;

import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.Todo;

public class Parser {
    private String command;

    public Parser(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

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
