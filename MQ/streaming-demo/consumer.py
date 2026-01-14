import json
from kafka import KafkaConsumer

# Tworzymy konsumenta Kafki
# 'group_id' pozwala Kafce zarządzać, które wiadomości zostały już przetworzone
# 'auto_offset_reset' = 'earliest' -> zaczynamy czytać od najstarszej wiadomości
consumer = KafkaConsumer(
    'temperatury',
    bootstrap_servers=['localhost:9092'],
    auto_offset_reset='earliest',
    group_id='grupa-analityczna-1',
    value_deserializer=lambda x: json.loads(x.decode('utf-8'))
)

ALERT_THRESHOLD = 30.0

if __name__ == "__main__":
    print("Konsument uruchomiony... Oczekiwanie na dane.")
    try:
        for message in consumer:
            reading = message.value
            temperatura = reading.get('temperatura', 0)
            
            print(f"Odebrano: {reading}")
            
            # Prosta logika "biznesowa"
            if temperatura > ALERT_THRESHOLD:
                print(f"--- !!! ALERT: Wysoka temperatura [{temperatura}°C] na sensorze {reading.get('sensorId')} !!! ---")
    except KeyboardInterrupt:
        print("\nZatrzymywanie konsumenta...")
    except Exception as e:
        print(f"Wystąpił nieoczekiwany błąd: {e}")
    finally:
        # Ten blok wykona się zawsze przy zamykaniu
        print("Zamykanie połączenia z Kafką...")
        consumer.close()
        print("Konsument zatrzymany.")