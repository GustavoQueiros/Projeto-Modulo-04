package repository;

import task.Task;

import java.util.List;

public interface TaskRepository {

    void save(Task task);
    List<Task> findAll();

}
