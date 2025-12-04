import asyncio
from typing import List, AsyncGenerator

import strawberry
from fastapi import FastAPI
from strawberry.fastapi import GraphQLRouter

# --- Prosta baza danych w pamięci i dane ---
users_db = []

@strawberry.type
class User:
    """Definicja typu User, oparta na adnotacjach typów."""
    name: str
    email: str

# --- Mechanizm Publikacji/Subskrypcji (Pub/Sub) w pamięci ---
# Użyjemy wbudowanej w asyncio kolejki do rozgłaszania zdarzeń.
# To jest znacznie prostsze i bardziej eleganckie niż ręczne zarządzanie listą.
user_created_queue = asyncio.Queue()

# --- Definicja zapytań (Queries) ---
@strawberry.type
class Query:
    @strawberry.field
    async def users(self) -> List[User]:
        """Zwraca listę wszystkich użytkowników."""
        print(">>> Wywołano query 'users'")
        return users_db

# --- Definicja Mutacji ---
@strawberry.type
class Mutation:
    @strawberry.mutation
    async def create_user(self, name: str, email: str) -> User:
        """Tworzy nowego użytkownika, zapisuje go i publikuje zdarzenie."""
        print(f">>> Wywołano mutację 'createUser' z danymi: {name}, {email}")
        new_user = User(name=name, email=email)
        users_db.append(new_user)
        
        # Publikujemy zdarzenie, umieszczając nowego użytkownika w kolejce.
        await user_created_queue.put(new_user)
        
        return new_user

# --- Definicja Subskrypcji ---
@strawberry.type
class Subscription:
    @strawberry.subscription
    async def user_created(self) -> AsyncGenerator[User, None]:
        """Nasłuchuje na zdarzenia tworzenia nowych użytkowników."""
        print(">>> Nowa subskrypcja 'userCreated' aktywowana!")
        local_queue = asyncio.Queue()
        # TODO: This is a simplistic implementation. In a real app, you'd want
        # a more robust pub/sub system that doesn't duplicate messages.
        # For this example, we'll just read from the global queue.
        # A better approach would be a fan-out pattern.
        
        # A simplified listener:
        while True:
            # Czekamy asynchronicznie, aż w głównej kolejce pojawi się nowy użytkownik.
            user = await user_created_queue.get()
            print(f">>> Wysyłam zdarzenie do subskrybenta: {user.name}")
            # 'yield' wysyła dane do klienta przez otwarte połączenie WebSocket.
            yield user

# --- Tworzenie schematu i aplikacji ---
schema = strawberry.Schema(query=Query, mutation=Mutation, subscription=Subscription)

# Strawberry dostarcza gotowy router, który obsługuje HTTP i WebSockets
graphql_app = GraphQLRouter(schema)

app = FastAPI()
# Montujemy router pod adresem /graphql.
# Ten jeden endpoint obsłuży wszystko: Query, Mutation i Subscription.
app.include_router(graphql_app, prefix="/graphql")