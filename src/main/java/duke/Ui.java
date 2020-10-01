package duke;

/**
 * This class represents the user interface.
 * It holds methods that prints messages and dividers to the user.
 */
public class Ui {
    public Ui() {
    }

    /**
     * This method returns the fixed indentation for the divider and to separate user inputs from system outputs.
     *
     * @return indentation spaces for indentation
     */
    protected String indentation() {
        return "    ";
    }

    /**
     * This method prints a divider to divide the user inputs from system outputs.
     */
    protected void printDivider() {
        System.out.println(indentation() + "——————————————————————————————————————————————————");
    }

    /**
     * This method prints the starting logo and message when the user first start the program.
     */
    public void printStartMessage() {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("Hello from\n" + logo);
        System.out.println(indentation() + " Hello! I'm Duke.");
        System.out.println(indentation() + " What can I do for you?");
        printDivider();
    }

    /**
     * This method prints the ending message after the user types "bye" which stops the program.
     */
    public void printExitMessage() {
        printDivider();
        System.out.println(indentation() + " Bye. Hope to see you again soon!");
        printDivider();
    }
}
