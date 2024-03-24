import java.util.HashMap;
import java.util.ArrayList;

public class TaskManager {
    private static int nextId = 0;
    protected HashMap<Integer, Task> tasks;
    protected HashMap<Integer, SubTask> subtasks;
    protected HashMap<Integer, Epic> epics;

    public TaskManager() {
        this.tasks = new HashMap<>();
        this.subtasks = new HashMap<>();
        this.epics = new HashMap<>();
    }
    private int generateId() {
        return ++nextId;
    }

    public Task createTask(Task task) {
        System.out.println("Добавление задачи: " + task);
        int taskId = generateId();
        task.setId(taskId);
        tasks.put(taskId, task);
        return tasks.get(taskId);
    }

    public Epic createEpic(Epic epic) {
        System.out.println("Добавление эпика : " + epic);
        int taskId = generateId();
        epic.setId(taskId);
        epics.put(taskId, epic);
        return epics.get(taskId);
    }

    public SubTask createSubTask(SubTask subTask) {
        System.out.println("Добавление подзадачи: " + subTask);
        int saved = subTask.getEpic();
        if (!epics.containsKey(saved)) {
            System.out.println("Эпик не найден. Задача не добавлена.");
            return null;
        }
        int taskId = generateId();
        subTask.setId(taskId);
        subtasks.put(taskId, subTask);
        Epic epic = epics.get(saved);
        epic.addSubTask(subTask.getId());
        epic.setStatus(calculateStatus(epic));
        return subtasks.get(taskId);
    }

    public ArrayList<Task> getAllTasks() {
        ArrayList<Task> taskList = new ArrayList<>();
        for (Task task : tasks.values()) {
            taskList.add(task);
        }
        return taskList;
    }

    public ArrayList<SubTask> getAllSubTasks() {
        ArrayList<SubTask> subTaskList = new ArrayList<>();
        for (SubTask subtask : subtasks.values()) {
            subTaskList.add(subtask);
        }
        return subTaskList;
    }

    public ArrayList<Epic> getAllEpic() {
        ArrayList<Epic> epicList = new ArrayList<>();
        for (Epic epic : epics.values()) {
            epicList.add(epic);
        }
        return epicList;
    }

    public void deleteAllTasks() {
        tasks.clear();
    }


    public void deleteAllEpics() {
        epics.clear();
        subtasks.clear();
    }


    public void deleteAllSubTasks() {
        subtasks.clear();
        for (Epic epic : epics.values()) {
            epic.removeSubTasksList();
            epic.setStatus(calculateStatus(epic));
        }
    }

    public Task getTaskById(int id) {
        Task task = tasks.get(id);
        if (task == null) {
            return null;
        }
        return task;
    }


    public Epic getEpicById(int id) {
        Epic epic = epics.get(id);
        if (epic == null) {
            return null;
        }
        return epic;
    }


    public SubTask getSubTaskById(int id) {
        SubTask subTask = subtasks.get(id);
        if (subTask == null) {
            return null;
        }
        return subTask;
    }


    public void updateTask(Task task) {
        if (!tasks.containsKey(task.getId())) {
            System.out.println("Задача не найдена.");
            return;
        }
        tasks.put(task.getId(), task);
    }


    public void updateEpic(Epic epic) {
        if (!epics.containsKey(epic.getId())) {
            System.out.println("Эпик не найден.");
            return;
        }
        Epic saved = epics.get(epic.getId());
        saved.setName(epic.getName());
        saved.setDescription(epic.getDescription());
    }


    public void updateSubTask(SubTask subTask) {

        if (!subtasks.containsKey(subTask.getId())) {
            System.out.println("Подзадача не найдена.");
            return;
        }
        int epicId = subTask.getEpic();
        if (!epics.containsKey(epicId)) {
            System.out.println("Эпик не найден.");
            return;
        }
        ArrayList<Integer> epicSubTaskList = epics.get(epicId).getSubTasks();
        if (!epicSubTaskList.contains(subTask.getId())) {
            System.out.println("Неправильно указан эпик в подзадаче");
            return;
        }
        Epic epic = epics.get(epicId);
        epic.setStatus(calculateStatus(epic));
        subtasks.put(subTask.getId(), subTask);
    }


    public void deleteTaskById(int id) {
        if (!tasks.containsKey(id)) {
            System.out.println("Задача не найдена");
            return;
        }
        tasks.remove(id);

    }


    public void deleteEpicById(int id) {
        if (!epics.containsKey(id)) {
            System.out.println("Эпик не найден");
            return;
        }
        Epic saved = epics.get(id);
        for (Integer subTaskIdForDelete : saved.getSubTasks()) {
            subtasks.remove(subTaskIdForDelete);
        }
        epics.remove(id);
    }


    public void deleteSubTaskById(int id) {
        if (!subtasks.containsKey(id)) {
            System.out.println("Подзадача не найдена");
            return;
        }
        SubTask subTask = subtasks.get(id);
        int savedEpicId = subTask.getEpic();
        Epic savedEpic = epics.get(savedEpicId);
        subtasks.remove(id);
        savedEpic.deleteSubTask(id);
        savedEpic.setStatus(calculateStatus(savedEpic));
    }

    public ArrayList<SubTask> getSubTaskList(Epic epic) {
        if (!epics.containsValue(epic)) {
            System.out.println("Эпик не найден");
            return null;
        }
        ArrayList<SubTask> list = new ArrayList<>();
        for (Integer subTaskId : epic.getSubTasks()) {
            list.add(subtasks.get(subTaskId));
        }
        return list;
    }

    public Status calculateStatus(Epic epic) {
        ArrayList<Integer> subTaskList = epic.getSubTasks();
        if (subTaskList.isEmpty()) {
            return Status.NEW;
        }
        int newStatus = 0;
        int doneStatus = 0;
        for (Integer subTaskId : subTaskList) {
            if (subtasks.get(subTaskId).getStatus().equals(Status.NEW)) {
                newStatus++;
            }
            if (subtasks.get(subTaskId).getStatus().equals(Status.DONE)) {
                doneStatus++;
            }
        }
        if (newStatus == subTaskList.size()) {
            return Status.NEW;
        }
        if (doneStatus == subTaskList.size()) {
            return Status.DONE;
        }
        return Status.IN_PROGRESS;
    }

}
