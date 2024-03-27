import Tasks.Epic;
import Tasks.Status;
import Tasks.SubTask;
import Tasks.Task;
import Manager.TaskManager;

public class Main {

    public static void main(String[] args) {


        TaskManager taskManager = new TaskManager();

        Task task1 = taskManager.createTask(new Task("Задача 1", "Описание задачи 1", Status.NEW));
        Task task2 = taskManager.createTask(new Task("Задача 2", "Описание задачи 2", Status.NEW));
        Epic epic3 = taskManager.createEpic(new Epic("Эпик 1", "Описание эпика 1"));
        Epic epic4 = taskManager.createEpic(new Epic("Эпик 2", "Описание эпика 2"));
        SubTask subTask5 = taskManager.createSubTask(new SubTask("Подзадача 1", "Описание подзадачи 1", Status.NEW, 3));
        SubTask subTask6 = taskManager.createSubTask(new SubTask("Подзадача 2", "Описание подзадачи 2", Status.NEW, 3));
        SubTask subTask7 = taskManager.createSubTask(new SubTask("Подзадача 3", "Описание подзадачи 3", Status.NEW, 4));

        System.out.println(taskManager.getAllTasks());
        System.out.println(taskManager.getAllEpic());
        System.out.println(taskManager.getAllSubTasks());

        System.out.println("Обновление задачи");
        taskManager.updateTask(new Task(1, "Задача 1", "Описание задачи 1", Status.DONE));
        System.out.println(taskManager.getTaskById(1));

        System.out.println("Обновление подзадачи");
        taskManager.updateSubTask(new SubTask(5, "Подзадача 1", "Описание подзадачи 1", Status.IN_PROGRESS,3));
        System.out.println(taskManager.getSubTaskById(5));


        System.out.println("Обновление эпика");
        Epic correctedEpic = new Epic(3, "Новый эпик", "Новое описание");
        taskManager.updateEpic(correctedEpic);
        System.out.println(taskManager.getEpicById(3));

        System.out.println(taskManager.getAllTasks());
        System.out.println(taskManager.getAllEpic());
        System.out.println(taskManager.getAllSubTasks());

        System.out.println("Удаление задачи");
        taskManager.deleteTaskById(1);

        System.out.println("Удаление эпика");
        taskManager.deleteEpicById(3);

        System.out.println(taskManager.getAllTasks());
        System.out.println(taskManager.getAllEpic());
        System.out.println(taskManager.getAllSubTasks());

    }
}
