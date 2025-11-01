class TaskController:

    def __init__(self, model, view):
        self._model = model
        self._view = view

    def run(self):
        while True:
            choice = self._view.get_menu_choice()
            if choice == '1':
                self.show_all_tasks()
            elif choice == '2':
                self.add_new_task()
            elif choice == '3':
                self.mark_task_as_done()
            elif choice == '0':
                self._view.show_message("Do widzenia!")
                break
            else:
                self._view.show_message("Nieznana opcja.")

    def show_all_tasks(self):
        tasks = self._model.get_all_tasks()  # Pobierz dane z modelu
        self._view.display_tasks(tasks)     # Przekaż dane do widoku

    def add_new_task(self):
        description = self._view.get_new_task_description()
        self._model.add_task(description)
        self._view.show_message("Zadanie dodane pomyślnie.")

    def mark_task_as_done(self):
        try:
            task_id = self._view.get_task_id_to_mark_as_done()
            task = self._model.find_task_by_id(task_id)
            if task:
                task.is_done = True
                self._view.show_message(f"Zadanie {task_id} oznaczone jako wykonane.")
            else:
                self._view.show_message(f"Nie znaleziono zadania o ID: {task_id}")
        except ValueError:
            self._view.show_message("Proszę podać poprawny numer ID.")