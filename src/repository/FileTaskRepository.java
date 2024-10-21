package repository;

import task.Task;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileTaskRepository implements TaskRepository{

    private String filePath;

    public FileTaskRepository(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void save(Task task) {
        try {
            Files.writeString(Path.of(filePath), task.getName() + "," + task.getPriority() + "\n",
                    java.nio.file.StandardOpenOption.CREATE,
                    java.nio.file.StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Task> findAll() {
        List<Task> tasks = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(Path.of(filePath));
            for (String line : lines) {
                String[] parts = line.split(",");
                tasks.add(new Task(parts[0], Integer.parseInt(parts[1])));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tasks;
    }

}
