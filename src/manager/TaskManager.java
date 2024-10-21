package manager;

import repository.TaskRepository;
import task.Task;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class TaskManager {
    private TaskRepository repository;

    public TaskManager(TaskRepository repository) {
        this.repository = repository;
        loadTasks();
    }

    public void addTask(Task task) {
        List<Task> existingTasks = repository.findAll();
        if (existingTasks.stream().noneMatch(t -> t.getName().equals(task.getName()))) {
            repository.save(task);
        } else {
            System.out.println("Tarefa já existe.");
        }
    }

    public void executeTask(String taskName) {
        List<Task> tasks = repository.findAll();
        Optional<Task> taskOptional = tasks.stream()
                .filter(task -> task.getName().equals(taskName))
                .findFirst();

        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();
            System.out.println("Executando tarefa: " + task.getName());
            try {
                Thread.sleep(task.getPriority() * 1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println("Tarefa concluída: " + task.getName());
        } else {
            System.out.println("Tarefa não encontrada.");
        }
    }

    public void listTasks() {
        List<Task> tasks = repository.findAll();
        if (tasks.isEmpty()) {
            System.out.println("Nenhuma tarefa disponível.");
        } else {
            System.out.println("\nTarefas disponíveis:");
            tasks.forEach(task -> System.out.println(task.getName()));
        }
    }

    private void loadTasks() {
        List<Task> tasks = repository.findAll();
        tasks.forEach(this::addTask);
    }

    public void startMenu() {
        Scanner scanner = new Scanner(System.in);
        int option;

        do {
            System.out.println("\n--- Menu de Tarefas ---");
            System.out.println("1. Listar Tarefas");
            System.out.println("2. Adicionar Tarefa");
            System.out.println("3. Executar Tarefa");
            System.out.println("4. Sair");
            System.out.print("Escolha uma opção: ");
            option = scanner.nextInt();

            scanner.nextLine();

            switch (option) {
                case 1:
                    listTasks();
                    break;
                case 2:
                    System.out.print("Digite o nome da nova tarefa: ");
                    String taskName = scanner.nextLine();

                    System.out.print("Digite a prioridade da tarefa (1-5): ");
                    int priority = scanner.nextInt();

                    if (priority < 1 || priority > 5) {
                        System.out.println("Erro: A prioridade de tarefas deve ser entre 1 e 5.");
                    } else {
                        addTask(new Task(taskName, priority));
                        System.out.println("Tarefa adicionada!");
                    }
                    break;
                case 3:
                    System.out.print("Digite o nome da tarefa a ser executada: ");
                    String taskToExecute = scanner.nextLine();
                    executeTask(taskToExecute);
                    break;
                case 4:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (option != 4);

        scanner.close();
    }

}

