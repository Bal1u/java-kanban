package Tasks;

import java.util.ArrayList;
import java.util.Objects;

public class Epic extends Task {

    private final ArrayList<Integer> subTasks = new ArrayList<>();


    public Epic(String name, String description) {
        super(name, description);
    }

    public Epic(int id, String name, String description) {
        super(id, name, description);
    }


    public ArrayList<Integer> getSubTasks() {
        return subTasks;
    }

    public void addSubTask(Integer subTaskId) {
        subTasks.add(subTaskId);
    }

    public void deleteSubTask(Integer subTaskId) {
        subTasks.remove(subTaskId);
    }

    public void removeSubTasksList() {
        subTasks.clear();
    }

    public TaskType getType() {
        return TaskType.EPIC;
    }

    @Override
    public String toString() {
        return new String(getId() + ",EPIC," + getName() + "," + getStatus() + "," + getDescription());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Epic epic = (Epic) obj;
        return (getId() == epic.getId()) &&
                Objects.equals(getName(), epic.getName()) &&
                Objects.equals(getDescription(), epic.getDescription());
    }

    @Override
    public int hashCode() {
        int hash = 17;
        hash = hash + getId();
        if (getName() != null) {
            hash = hash + getName().hashCode();
        }
        hash = hash*31;
        if (getDescription() != null) {
            hash = hash + getDescription().hashCode();
        }
        return hash;
    }
}