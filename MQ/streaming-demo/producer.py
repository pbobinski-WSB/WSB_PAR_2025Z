import time
import json
import random
from kafka import KafkaProducer

# Funkcja do serializacji JSON
def json_serializer(data):
    return json.dumps(data).encode('utf-8')

# Tworzymy producenta Kafki
# 'kafka:29092' to adres brokera wewnątrz sieci Dockera
producer = KafkaProducer(
    bootstrap_servers=['localhost:9092'],
    value_serializer=json_serializer
)

if __name__ == "__main__":
    print("Producent uruchomiony... Wysyłanie symulowanych odczytów temperatury.")
    try:
        while True:
            # Generujemy losowy odczyt
            sensor_id = f'sensor-0{random.randint(1, 3)}'
            temperatura = round(random.uniform(20, 32) + random.randint(0,2), 2) # Czasem generuje wyższą temp.
            timestamp = int(time.time() * 1000)

            reading = {
                'sensorId': sensor_id,
                'temperatura': temperatura,
                'timestamp': timestamp
            }
            
            # Wysyłamy wiadomość do tematu 'temperatury'
            producer.send('temperatury', reading)
            
            print(f"Wysłano: {reading}")
            
            # Czekamy sekundę
            time.sleep(1)

    except KeyboardInterrupt:
        print("\nZatrzymywanie producenta...")
    except Exception as e:
        print(f"Wystąpił nieoczekiwany błąd: {e}")
    finally:
        # Ten blok wykona się zawsze przy zamykaniu
        print("Wysyłanie ostatnich wiadomości (flush) i zamykanie połączenia...")
        producer.flush()  # Upewnij się, że wszystkie wiadomości w buforze zostały wysłane
        producer.close()
        print("Producent zatrzymany.")