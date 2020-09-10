import java.util.Scanner;

public class Duke {
    public static Task[] tasks = new Task[100];
    public static int taskCounter = 0;

    public static void printHorizontalLine() {
        System.out.println("    ——————————————————————————————————————————————————");
    }

    public static void printTasks(int taskCounter, Task[] tasks) {
        for (int i=0; i<taskCounter; i++) {
            System.out.println("     " + (i+1) + ". " + tasks[i]);
        }
    }

    public static void addTask(String command) {
        String[] task = command.split(" ", 2);
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
                tasks[taskCounter] = new Todo(description);
                break;
            case "deadline":
                tasks[taskCounter] = new Deadline(description, date);
                break;
            case "event":
                tasks[taskCounter] = new Event(description, date);
                break;
        }

        System.out.println("      " + tasks[taskCounter]);
        taskCounter++;
        System.out.println("     Now you have " + taskCounter + (taskCounter==1 ? " task" : " tasks") + " in the list.");
    }

    public static void main(String[] args) {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        Scanner in = new Scanner(System.in);

        System.out.println("Hello from\n" + logo);
        System.out.println("     Hello! I'm Duke");
        System.out.println("     What can I do for you?");
        printHorizontalLine();

        // command loop
        String command = in.nextLine();
        while (!command.equals("bye")) {
            printHorizontalLine();

            if (command.equals("list")) {
                printTasks(taskCounter, tasks);
            } else if (command.contains("done")) {
                // assume that task index is keyed in last
                int taskIndex = Integer.parseInt(command.substring(command.length()-1)) - 1;
                tasks[taskIndex].markAsDone();
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
