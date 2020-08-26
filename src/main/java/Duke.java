import java.util.Scanner;

public class Duke {
    public static void printHorizontalLine() {
        System.out.println("    ——————————————————————————————————————————————————");
    }

    public static void printTasks(int taskCounter, Task[] tasks) {
        for (int i=0; i<taskCounter; i++) {
            System.out.println("     " + (i+1) + ". [" + tasks[i].getStatusIcon() + "] " + tasks[i].getDescription());
        }
    }

    public static void main(String[] args) {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        Scanner in = new Scanner(System.in);
        Task[] tasks = new Task[100];
        int taskCounter = 0;

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
                tasks[taskCounter] = new Task(command);
                System.out.println("     added: " + tasks[taskCounter].getDescription());
                taskCounter++;
            }

            printHorizontalLine();
            command = in.nextLine();
        }

        printHorizontalLine();
        System.out.println("     Bye. Hope to see you again soon!");
        printHorizontalLine();
    }
}
