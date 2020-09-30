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
