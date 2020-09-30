package duke;

import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.Todo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
    private final String filePath;
    private int taskCounter = 0;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    public int getTaskCounter() {
        return taskCounter;
    }

    /**
     * This method creates a new file with the given file path.
     * An IO exception error is thrown in the method, in case the file cannot be created.
     *
     * @param filePath  file path of where the file is supposed to be created
     */
    public void createFile(String filePath) {
        File f = new File(filePath);
        try {
            if (f.createNewFile()) {
                // System.out.println("File created: " + f.getName());
            }
        } catch (IOException e) {
            System.out.println("cannot create file: " + e.getMessage());
        }
    }

    /**
     * This method reads the duke text file present in ip/data,
     * and it loads data from the file into a Task ArrayList.
     * Returns the collected data in the ArrayList.
     *
     * @return tasks an ArrayList of tasks recorded in the data file
     * @throws FileNotFoundException If the file to be read from cannot be found
     */
    public ArrayList<Task> load() throws FileNotFoundException {
        ArrayList<Task> tasks = new ArrayList<>();
        File f = new File(filePath);
        Scanner s = new Scanner(f);

        ArrayList<String> taskList = new ArrayList<>();
        while (s.hasNext()) {
            taskList.add(s.nextLine());
        }

        for (String saved : taskList) {
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

            // System.out.println("added: " + task[2]);
            taskCounter++;
        }

        return tasks;
    }

    /**
     * This method makes the necessary edits to the duke text file
     * whenever a task is marked as done or is deleted from the task list.
     * To do this, it creates a temporary file and loop through the
     * original file to find the line that needs to be edited.
     * Then it will replace the original file with the temporary file.
     *
     * @param line Previous version of task that is saved in the file
     * @param action Changes that is made to the task (deleted or done)
     * @param task Task that is changed
     * @throws IOException if the function is unable to scan the original file
     */
    public void editFile(String line, String action, Task task) throws IOException {
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
                // else copy line into temp file
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

    /**
     * This method adds a line to the duke text file whenever a new task is added
     *
     * @param task New task that is added
     */
    public void updateSavedData(Task task) {
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
}
