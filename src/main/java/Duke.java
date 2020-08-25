import java.util.Scanner;

public class Duke {
    public static void printHorizontalLine() {
        System.out.println("    ——————————————————————————————————————————————————");
    }

    public static void main(String[] args) {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        Scanner in = new Scanner(System.in);

        System.out.println("Hello from\n" + logo);
        System.out.println("    Hello! I'm Duke");
        System.out.println("    What can I do for you?");
        printHorizontalLine();

        // echo loop
        String command = in.nextLine();
        while (!command.equals("bye")) {
            printHorizontalLine();
            System.out.println("    " + command);
            printHorizontalLine();
            command = in.nextLine();
        }

        printHorizontalLine();
        System.out.println("    Bye. Hope to see you again soon!");
        printHorizontalLine();
    }
}
