class TaskView:
    def display_tasks(self, tasks):
        print("\n--- Twoje Zadania ---")
        if not tasks:
            print("Brak zadań na liście.")
        else:
            for task in tasks:
                print(task)
        print("--------------------")

    def get_menu_choice(self):
        print("\nMenu:")
        print("1. Wyświetl wszystkie zadania")
        print("2. Dodaj nowe zadanie")
        print("3. Oznacz zadanie jako wykonane")
        print("0. Wyjdź")
        return input("Wybierz opcję: ")

    def get_new_task_description(self):
        return input("Wpisz treść nowego zadania: ")

    def get_task_id_to_mark_as_done(self):
        return int(input("Podaj ID zadania do oznaczenia jako wykonane: "))

    def show_message(self, message):
        print(message)