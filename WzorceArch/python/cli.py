import sys
# 1. Importujemy TEN SAM model co w poprzedniej aplikacji!
from model import TaskRepository
# 2. Importujemy NOWE komponenty Widoku i Kontrolera
from cli_view import CliTaskView
from cli_controller import CliTaskController

if __name__ == "__main__":
    repository = TaskRepository()
    
    # Wypełniamy model przykładowymi danymi
    repository.add_task("Przygotować materiały na ćwiczenia")
    repository.add_task("Sprawdzić projekty studentów")

    view = CliTaskView()
    controller = CliTaskController(repository, view)

    # 3. Uruchamiamy logikę, przekazując argumenty z linii poleceń (sys.argv)
    controller.execute(sys.argv)
    