package duke;

import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.Todo;

import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;

public class Duke {
    private static ArrayList<Task> tasks = new ArrayList<>();
    private static int taskCounter = 0;
    private static final String filePath = "../ip/data/duke.txt";

    public static void main(String[] args) {
        printStartMessage();
        try {
            readFileContent();
        } catch (FileNotFoundException e) {
            createFile(filePath);
        }
        Scanner in = new Scanner(System.in);

        // command loop
        String command = in.nextLine();
        while (!command.equals("bye")) {
            printHorizontalLine();

            if (command.equals("list")) {
                printTasks();
            } else if (command.contains("done")) {
                // assume that task index is keyed in last
                int taskIndex = Integer.parseInt(command.substring(command.length()-1)) - 1;
                done(taskIndex);
            } else if (command.contains("delete")) {
                int taskIndex = Integer.parseInt(command.substring(command.length()-1)) - 1;
                delete(taskIndex);
            } else {
                addTask(command);
            }

            printHorizontalLine();
            command = in.nextLine();
        }

        printExitMessage();
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

        switch (type) {
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
        updateSavedData(tasks.get(taskCounter));
        taskCounter++;
        printNumOfTasks();
    }

    public static void delete(int index) {
        Task deleted = tasks.remove(index);
        taskCounter--;
        System.out.println("     Noted. I've removed this task:");
        System.out.println("      " + deleted);
        printNumOfTasks();

        // remove from file
        String lineToDelete = deleted.getSavedLine();
        try {
            editFile(lineToDelete, "delete" ,deleted);
        } catch (IOException e) {
            System.out.println("Unable to edit file: " +e.getMessage());
        }
    }

    public static void done(int index) {
        Task taskDone = tasks.get(index);
        String originalLine = taskDone.getSavedLine();
        taskDone.markAsDone();
        try {
            editFile(originalLine, "done", taskDone);
        } catch (IOException e) {
            System.out.println("Unable to edit file: " +e.getMessage());
        }
    }

    public static void editFile(String line, String action, Task task) throws IOException{
        File originalFile = new File(filePath);
        Scanner s = new Scanner(originalFile);

        createFile("../ip/data/temp.txt");
        File tempFile = new File("../ip/data/temp.txt");
        FileWriter writer = new FileWriter(tempFile, true);

        while (s.hasNext()) {
            String input = s.nextLine();
            // scan the file for the task line and edit accordingly
            if (input.equals(line)) {
                if (action.equals("delete")) {
                    // skip this line
                } else if (action.equals("done")) {
                    writer.write(task.getSavedLine() + "\n");
                }
            } else {
                // else just copy line into temp file
                writer.write(input + "\n");
            }
        }
        writer.close();

        // delete original file and rename temp file to be original file
        if (!originalFile.delete()) {
            System.out.println("Cannot delete original");
        }
        if (!tempFile.renameTo(originalFile)) {
            System.out.println("Cannot rename temp file");
        }
    }

    public static void readFileContent() throws FileNotFoundException{
        File f = new File(filePath);
        Scanner s = new Scanner(f);
        ArrayList<String> taskList = new ArrayList<>();

        while (s.hasNext()) {
            taskList.add(s.nextLine());
        }

        for (String task : taskList) {
            addTaskFromFile(task);
            taskCounter++;
        }
    }

    public static void addTaskFromFile(String saved) {
        // Array Index -> 0: type, 1: description, 2: date
        String[] task = saved.split("/", 4);
        switch (task[0]) {
            case "T":
                tasks.add(new Todo(task[2]));
                break;
            case "D":
                tasks.add(new Deadline(task[2], task[3]));
                break;
            case "E":
                tasks.add(new Event(task[2], task[3]));
        }

        int done = Integer.parseInt(task[1]);
        if (done==1) {
            tasks.get(taskCounter).markAsDone();
        }

        System.out.println("added: " + task[2]);
    }

    public static void createFile(String fileName) {
        File f = new File(fileName);
        try {
            if (f.createNewFile()) {
                // System.out.println("File created: " + f.getName());
            }
        } catch (IOException e) {
            System.out.println("cannot create file: " + e.getMessage());
        }
    }

    public static void updateSavedData(Task task) {
        String savedData = task.getSavedLine();
        // System.out.println("saved line: " + savedData);
        try {
            FileWriter fw = new FileWriter(filePath, true);
            fw.write(savedData + "\n");
            fw.close();
        } catch (IOException e) {
            System.out.println("Error updating saved data: " + e.getMessage());
        }
    }

    private static void printStartMessage() {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("Hello from\n" + logo);
        System.out.println("     Hello! I'm duke.Duke");
        System.out.println("     What can I do for you?");
        printHorizontalLine();
    }

    public static void printHorizontalLine() {
        System.out.println("    ——————————————————————————————————————————————————");
    }

    private static void printNumOfTasks() {
        System.out.println("     Now you have " + taskCounter + (taskCounter <= 1 ? " task" : " tasks") + " in the list.");
    }

    public static void printTasks() {
        for (int i = 0; i < taskCounter; i++) {
            System.out.println("     " + (i+1) + ". " + tasks.get(i));
        }
    }

    private static void printExitMessage() {
        printHorizontalLine();
        System.out.println("     Bye. Hope to see you again soon!");
        printHorizontalLine();
    }
}
