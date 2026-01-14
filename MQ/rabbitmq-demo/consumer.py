import pika
import json
import time

# Połączenie z RabbitMQ
connection = pika.BlockingConnection(pika.ConnectionParameters('localhost'))
channel = connection.channel()

# Upewniamy się, że kolejka istnieje
channel.queue_declare(queue='temperatury', durable=True)

ALERT_THRESHOLD = 30.0

# Definiujemy funkcję callback, która będzie wywoływana dla każdej wiadomości
def callback(ch, method, properties, body):
    reading = json.loads(body)
    temperatura = reading.get('temperatura', 0)
    
    print(f"Odebrano: {reading}")
    
    if temperatura > ALERT_THRESHOLD:
        print(f"--- !!! ALERT: Wysoka temperatura [{temperatura}°C] na sensorze {reading.get('sensorId')} !!! ---")
    
    # Symulujemy trochę pracy
    time.sleep(0.5)
    
    # KLUCZOWY ELEMENT: Potwierdzamy przetworzenie wiadomości.
    # RabbitMQ teraz usunie ją z kolejki.
    ch.basic_ack(delivery_tag=method.delivery_tag)

# Mówimy RabbitMQ, żeby nie wysyłał nowej wiadomości do konsumenta, dopóki nie potwierdzi poprzedniej.
channel.basic_qos(prefetch_count=1)

# Rozpoczynamy konsumpcję
channel.basic_consume(queue='temperatury', on_message_callback=callback)

if __name__ == "__main__":
    print("Konsument RabbitMQ uruchomiony... Oczekiwanie na dane. Naciśnij Ctrl+C aby zakończyć.")
    try:
        channel.start_consuming()
    except KeyboardInterrupt:
        print("\nZatrzymywanie konsumenta...")
    finally:
        connection.close()
        print("Konsument zatrzymany.")