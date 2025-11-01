class Task:
    def __init__(self, id, description, is_done=False):
        self.id = id
        self.description = description
        self.is_done = is_done

    def __str__(self):
        status = "x" if self.is_done else " "
        return f"{self.id}. [{status}] {self.description}"

class TaskRepository:
    def __init__(self):
        self._tasks = []
        self._next_id = 1

    def get_all_tasks(self):
        return self._tasks[:]

    def add_task(self, description):
        task = Task(id=self._next_id, description=description)
        self._tasks.append(task)
        self._next_id += 1

    def find_task_by_id(self, task_id):
        for task in self._tasks:
            if task.id == task_id:
                return task
        return None