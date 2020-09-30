package duke;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class Duke {
    private static final String filePath = "data/duke.txt";
    protected static Ui ui = new Ui();
    protected static TaskList taskList;
    protected static Storage storage = new Storage(filePath);

    public static void main(String[] args) {
        ui.printStartMessage();
        run();
        ui.printExitMessage();
    }

    private static void run() {
        try {
            taskList = new TaskList(storage.load(), storage.getTaskCounter());
        } catch (FileNotFoundException e) {
            storage.createFile(filePath);
        }
        Scanner in = new Scanner(System.in);

        // command loop
        String command = in.nextLine();
        Parser parser = new Parser(command);
        while (!command.equals("bye")) {
            ui.printDivider();
            String typeOfCommand = parser.verifyCommand();

            switch(typeOfCommand) {
            case "list":
                taskList.printTasks();
                break;
            case "find":
                taskList.find(parser.extractKeywordsFromCommand());
                break;
            case "done":
                int taskIndex = Integer.parseInt(command.substring(command.length()-1)) - 1;
                taskList.markTaskAsDone(taskIndex);
                break;
            case "delete":
                taskIndex = Integer.parseInt(command.substring(command.length()-1)) - 1;
                taskList.deleteTask(taskIndex);
                break;
            case "task":
                taskList.addTask(parser.extractTaskFromCommand());
                break;
            default:
                System.out.println("     Oops! I'm sorry, but I don't know what that means. " +
                        "Please refer to user guide for help.");
            }

            ui.printDivider();
            command = in.nextLine();
            parser.setCommand(command);
        }
    }
}
