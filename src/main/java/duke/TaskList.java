package duke;

import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.Todo;

import static duke.Duke.storage;
import static duke.Duke.ui;

import java.io.IOException;
import java.util.ArrayList;

/**
 * This class holds the ArrayList of task and the actions that can be done on it.
 * It will also call methods that edit data in the text file when actions are called.
 */
public class TaskList {
    protected ArrayList<Task> tasks;
    protected int taskCounter;

    public TaskList(ArrayList<Task> tasks, int taskCounter) {
        this.tasks = tasks;
        this.taskCounter = taskCounter;
    }

    /**
     * This method adds a task to the task list.
     * It also calls the methods to edit the duke text file.
     *
     * @param task task to be added
     */
    protected void addTask(Task task) {
        String fullDescription = task.toString();
        String type = fullDescription.substring(1,2);

        switch (type) {
        case "T":
            tasks.add(new Todo(task.getDescription()));
            break;
        case "D":
            tasks.add(new Deadline(task.getDescription(), String.valueOf(task.getDate())));
            break;
        case "E":
            tasks.add(new Event(task.getDescription(), String.valueOf(task.getDate())));
            break;
        }

        System.out.println(ui.indentation() + " Got it. I've added this task:");
        storage.updateSavedData(tasks.get(taskCounter));
        System.out.println(ui.indentation() + "  " + tasks.get(taskCounter));
        taskCounter++;
    }

    /**
     * This method deletes the task at the given index, and calls the method to edit the duke text file.
     *
     * @param index index of task to be deleted
     */
    protected void deleteTask(int index) {
        Task deleted = tasks.remove(index);
        taskCounter--;
        System.out.println(ui.indentation() + " Noted. I've removed this task:");
        System.out.println(ui.indentation() + "  " + deleted);
        printNumOfTasks();

        // remove from file
        String lineToDelete = deleted.getSavedLine();
        try {
            storage.editFile(lineToDelete, "delete" ,deleted);
        } catch (IOException e) {
            System.out.println("Unable to edit file: " +e.getMessage());
        }
    }

    /**
     * This method marks the task as done and prints out the relevant messages to show that it is marked as done.
     * It also calls methods to edit the duke text file accordingly.
     *
     * @param index index of task to be marked as done
     */
    protected void markTaskAsDone(int index) {
        Task taskDone = tasks.get(index);
        String originalLine = taskDone.getSavedLine();
        taskDone.markAsDone();
        System.out.println("     Nice! I've marked this task as done:");
        System.out.println("      " + taskDone.toString());
        try {
            storage.editFile(originalLine, "done", taskDone);
        } catch (IOException e) {
            System.out.println("Unable to edit file: " +e.getMessage());
        }
    }

    protected void find(String keywords) {
        ArrayList<Integer> matchingTasks = new ArrayList<Integer>();
        for (Task task : tasks) {
            String description = task.getDescription();
            if (description.contains(keywords)) {
                matchingTasks.add(tasks.indexOf(task));
            }
        }

        if (matchingTasks.size()==0) {
            System.out.println(ui.indentation() + " Sorry, there are no matching tasks.");
            return;
        }

        System.out.println(ui.indentation() + " Here are the matching tasks in your list:");
        for (int i : matchingTasks) {
            System.out.println(ui.indentation() + " " + (i+1) + "." + tasks.get(i));
        }
    }


    /**
     * This method prints the number of tasks in the task list as a statement for reference.
     */
    public void printNumOfTasks() {
        System.out.println(ui.indentation() + " Now you have " + taskCounter + (taskCounter <= 1 ? " task" : " tasks") + " in the list.");
    }

    /**
     * This method prints the tasks in a list form when "list" is called by the user.
     */
    public void printTasks() {
        if (taskCounter == 0) {
            System.out.println(ui.indentation() + " There is no task in the list yet.");
        }
        for (int i = 0; i < taskCounter; i++) {
            System.out.println(ui.indentation() + " " + (i+1) + ". " + tasks.get(i));
        }
    }
}
