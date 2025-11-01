"""
Nowy, uproszczony Widok.
Jego jedynym zadaniem jest wyświetlanie wyników w konsoli.
Nie ma w nim żadnej logiki związanej z interaktywnym menu.
"""

class CliTaskView:
    def display_tasks(self, tasks):
        if not tasks:
            print("Brak zadań na liście.")
        else:
            for task in tasks:
                print(task)

    def show_message(self, message):
        print(message)

    def show_error(self, error_message):
        print(f"Błąd: {error_message}")