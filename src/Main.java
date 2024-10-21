    import manager.TaskManager;
    import repository.FileTaskRepository;
    import repository.TaskRepository;


    public class Main {
        public static void main(String[] args) {

            TaskRepository taskRepository = new FileTaskRepository("tasks.txt");

            TaskManager taskManager = new TaskManager(taskRepository);

            taskManager.startMenu();

        }
    }
