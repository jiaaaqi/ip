package duke;

import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.Todo;

import static duke.Duke.storage;
import static duke.Duke.ui;

import java.io.IOException;
import java.util.ArrayList;

public class TaskList {
    protected ArrayList<Task> tasks;
    protected int taskCounter;

    public TaskList(ArrayList<Task> tasks, int taskCounter) {
        this.tasks = tasks;
        this.taskCounter = taskCounter;
    }

    protected void addTask(Task task) {
        String fullDescription = task.toString();
        String type = fullDescription.substring(1,2);

        switch (type) {
        case "T":
            tasks.add(new Todo(task.getDescription()));
            break;
        case "D":
            tasks.add(new Deadline(task.getDescription(), task.getDate()));
            break;
        case "E":
            tasks.add(new Event(task.getDescription(), task.getDate()));
            break;
        }

        System.out.println(ui.indentation() + " Got it. I've added this task:");
        storage.updateSavedData(tasks.get(taskCounter));
        System.out.println(ui.indentation() + "  " + tasks.get(taskCounter));
        taskCounter++;
    }

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

    protected void markTaskAsDone(int index) {
        Task taskDone = tasks.get(index);
        String originalLine = taskDone.getSavedLine();
        taskDone.markAsDone();
        System.out.println("     Nice! I've marked this task as done:");
        System.out.println("      " + toString());
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

    public void printNumOfTasks() {
        System.out.println(ui.indentation() + " Now you have " + taskCounter + (taskCounter <= 1 ? " task" : " tasks") + " in the list.");
    }

    public void printTasks() {
        for (int i = 0; i < taskCounter; i++) {
            System.out.println("     " + (i+1) + ". " + tasks.get(i));
        }
    }
}
