package duke;

public class Ui {
    public Ui() {
    }

    protected String indentation() {
        return "    ";
    }

    protected void printDivider() {
        System.out.println(indentation() + "——————————————————————————————————————————————————");
    }

    public void printStartMessage() {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("Hello from\n" + logo);
        System.out.println(indentation() + " Hello! I'm duke.Duke");
        System.out.println(indentation() + " What can I do for you?");
        printDivider();
    }

    public void printExitMessage() {
        printDivider();
        System.out.println(indentation() + " Bye. Hope to see you again soon!");
        printDivider();
    }
}
