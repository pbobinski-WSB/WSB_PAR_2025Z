import pika
import time
import json
import random

# Połączenie z RabbitMQ na porcie wystawionym na localhost
connection = pika.BlockingConnection(pika.ConnectionParameters('localhost'))
channel = connection.channel()

# Deklarujemy kolejkę. `durable=True` oznacza, że kolejka przetrwa restart brokera.
# Jeśli kolejka już istnieje, ta komenda nic nie zrobi.
channel.queue_declare(queue='temperatury', durable=True)

if __name__ == "__main__":
    print("Producent RabbitMQ uruchomiony... Naciśnij Ctrl+C aby zakończyć.")
    try:
        while True:
            # Generujemy losowy odczyt (logika taka sama jak w demo Kafki)
            sensor_id = f'sensor-0{random.randint(1, 3)}'
            temperatura = round(random.uniform(20, 32) + random.randint(0,2), 2)
            timestamp = int(time.time() * 1000)

            message_body = {
                'sensorId': sensor_id,
                'temperatura': temperatura,
                'timestamp': timestamp
            }
            
            # Publikujemy wiadomość do kolejki 'temperatury'
            channel.basic_publish(
                exchange='',
                routing_key='temperatury',
                body=json.dumps(message_body),
                # Zapewnia, że wiadomość przetrwa restart brokera
                properties=pika.BasicProperties(delivery_mode=pika.spec.PERSISTENT_DELIVERY_MODE)
            )
            
            print(f"Wysłano: {message_body}")
            time.sleep(1)

    except KeyboardInterrupt:
        print("\nZatrzymywanie producenta...")
    finally:
        connection.close()
        print("Producent zatrzymany.")