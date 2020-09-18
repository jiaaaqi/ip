package duke;

import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.Todo;

import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

public class Duke {
    private static ArrayList<Task> tasks = new ArrayList<>();
    private static int taskCounter = 0;

    public static void printHorizontalLine() {
        System.out.println("    ——————————————————————————————————————————————————");
    }

    public static void printTasks(int taskCounter, ArrayList<Task> tasks) {
        for (int i=0; i<taskCounter; i++) {
            System.out.println("     " + (i+1) + ". " + tasks.get(i));
        }
    }

    public static boolean checkCommand(String[] task) {
        boolean checkTask = false;
        boolean checkDescription;
        if (task[0].equals("todo") || task[0].equals("deadline") || task[0].equals("event")) {
            checkTask = true;
        }

        if (!checkTask) {
            System.out.println("     Oops! I'm sorry, but I don't know what that means. :(");
            return false;
        }

        if (task.length==2) {
            checkDescription = true;
        } else {
            System.out.println("     Oops! The description of " + task[0] + " cannot be empty.");
            return false;
        }

        return checkDescription;
    }

    public static void addTask(String command) {
        String[] task = command.split(" ", 2);

        if (!checkCommand(task)) {
            return;
        }

        String type = task[0];
        int index = task[1].indexOf("/");
        String description = task[1];
        String date = null;

        if (index != -1) {
            description = task[1].substring(0, index-1);
            date = task[1].substring(index+4);
        }

        System.out.println("     Got it. I've added this task:");

        switch(type) {
            case "todo":
                tasks.add(new Todo(description));
                break;
            case "deadline":
                tasks.add(new Deadline(description, date));
                break;
            case "event":
                tasks.add(new Event(description, date));
                break;
        }

        System.out.println("      " + tasks.get(taskCounter));
        taskCounter++;
        System.out.println("     Now you have " + taskCounter + (taskCounter<=1 ? " task" : " tasks") + " in the list.");
    }

    public static void delete(int index) {
        Task deleted = tasks.remove(index);
        taskCounter--;
        System.out.println("     Noted. I've removed this task:");
        System.out.println("      " + deleted);
        System.out.println("     Now you have " + taskCounter + (taskCounter<=1 ? " task" : " tasks") + " in the list.");
    }

    public static void main(String[] args) throws IOException {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        Scanner in = new Scanner(System.in);

        System.out.println("Hello from\n" + logo);
        System.out.println("     Hello! I'm duke.Duke");
        System.out.println("     What can I do for you?");
        printHorizontalLine();

        // read data from file
        /*
        File f = new File("./data/duke.txt");
        try {
            Scanner data = new Scanner(f);
        } catch (FileNotFoundException e) {
            f.createNewFile();
        }*/

        // command loop
        String command = in.nextLine();
        while (!command.equals("bye")) {
            printHorizontalLine();

            if (command.equals("list")) {
                printTasks(taskCounter, tasks);
            } else if (command.contains("done")) {
                // assume that task index is keyed in last
                int taskIndex = Integer.parseInt(command.substring(command.length()-1)) - 1;
                tasks.get(taskIndex).markAsDone();
            } else if (command.contains("delete")) {
                int taskIndex = Integer.parseInt(command.substring(command.length()-1)) - 1;
                delete(taskIndex);
            } else {
                addTask(command);
            }

            printHorizontalLine();
            command = in.nextLine();
        }

        printHorizontalLine();
        System.out.println("     Bye. Hope to see you again soon!");
        printHorizontalLine();
    }
}
