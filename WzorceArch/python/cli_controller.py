import sys

"""
Nowy Kontroler, który operuje na argumentach linii poleceń.
Nie ma pętli while, przetwarza polecenie i kończy działanie.
"""
class CliTaskController:
    def __init__(self, model, view):
        self._model = model
        self._view = view

    def execute(self, args):
        if len(args) < 2:
            self._view.show_error("Brak polecenia. Dostępne: list, add, done.")
            return

        command = args[1]

        if command == "list":
            self.show_all_tasks()
        elif command == "add":
            if len(args) < 3:
                self._view.show_error('Brak opisu zadania. Użycie: add "opis zadania"')
            else:
                self.add_new_task(args[2])
        elif command == "done":
            if len(args) < 3:
                self._view.show_error("Brak ID zadania. Użycie: done <id>")
            else:
                try:
                    task_id = int(args[2])
                    self.mark_task_as_done(task_id)
                except ValueError:
                    self._view.show_error("ID zadania musi być liczbą.")
        else:
            self._view.show_error(f"Nieznane polecenie: {command}")
    
    def show_all_tasks(self):
        tasks = self._model.get_all_tasks()
        self._view.display_tasks(tasks)

    def add_new_task(self, description):
        self._model.add_task(description)
        self._view.show_message(f'Zadanie "{description}" dodane pomyślnie.')

    def mark_task_as_done(self, task_id):
        task = self._model.find_task_by_id(task_id)
        if task:
            task.is_done = True
            self._view.show_message(f"Zadanie {task_id} oznaczone jako wykonane.")
        else:
            self._view.show_error(f"Nie znaleziono zadania o ID: {task_id}")