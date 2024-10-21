package executor;

import task.Task;

import java.util.concurrent.CompletableFuture;

public class AsyncTaskExecutor {

    public CompletableFuture<String> execute(Task task) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(task.getPriority() * 1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return "Tarefa concluída: " + task.getName();
        });
    }

}
