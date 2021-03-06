package duke;

import duke.task.Task;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Duke {
    private static final String filePath = "duke.txt";
    protected static Ui ui = new Ui();
    protected static TaskList taskList;
    protected static Storage storage = new Storage(filePath);

    public static void main(String[] args) {
        ui.printStartMessage();
        run();
        ui.printExitMessage();
    }

    /**
     * This method reads user inputs as commands and executes them accordingly.
     * It starts off by scanning for a duke text file to check for any saved data.
     * Then it will go into a command loop of waiting for the next command and executing it.
     * To exit the loop, the user would have to type "bye".
     */
    private static void run() {
        try {
            taskList = new TaskList(storage.load(), storage.getTaskCounter());
        } catch (FileNotFoundException e) {
            storage.createFile(filePath);
            taskList = new TaskList(new ArrayList<Task>(), storage.getTaskCounter());
        }
        Scanner in = new Scanner(System.in);

        // command loop
        String command = in.nextLine();
        Parser parser = new Parser(command);
        while (!command.equals("bye")) {
            ui.printDivider();
            String typeOfCommand;

            try {
                typeOfCommand = parser.verifyCommand();
            } catch (DukeException e) {
                System.out.println("     Oops! I'm sorry, but I don't know what that means. " +
                        "Please refer to the user guide (https://jiaaaqi.github.io/ip/) if you need any help.");
                ui.printDivider();
                command = in.nextLine();
                parser.setCommand(command);
                continue;
            }

            switch(typeOfCommand) {
            case "list":
                taskList.printTasks();
                break;
            case "help":
                parser.help();
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
                if (parser.extractTaskFromCommand() != null) {
                    taskList.addTask(parser.extractTaskFromCommand());
                }
                break;
            }

            ui.printDivider();
            command = in.nextLine();
            parser.setCommand(command);
        }
    }
}
