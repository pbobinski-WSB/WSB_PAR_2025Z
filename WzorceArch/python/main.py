from model import TaskRepository
from view import TaskView
from controller import TaskController

if __name__ == "__main__":
    # Stworzenie instancji komponentów MVC
    repository = TaskRepository()
    view = TaskView()
    controller = TaskController(repository, view)

    # Uruchomienie głównej logiki aplikacji
    controller.run()