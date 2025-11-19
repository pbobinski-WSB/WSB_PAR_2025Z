package wsb.par.grpc.protobuf;

import java.io.FileInputStream;
import java.io.IOException;

public class JavaDeserializer {
    public static void main(String[] args) throws IOException {
        // Symulacja otrzymania zserializowanych danych z Pythona
        byte[] serializedDataFromPython = new byte[] {8, 123, 18, 11, 84, 101, 115, 116, 32, 79, 98, 106, 101, 99, 116, 26, 5, 105, 116, 101, 109, 65, 26, 5, 105, 116, 101, 109, 66, 26, 5, 105, 116, 101, 109, 67, 34, 8, 10, 4, 107, 101, 121, 49, 16, 10, 34, 8, 10, 4, 107, 101, 121, 50, 16, 20
};
        // Deserializacja z tablicy bajtów
        MyDataProto.MyData deserializedData = MyDataProto.MyData.parseFrom(serializedDataFromPython);
        String filename = "myDataP.proto.bin";
        try (FileInputStream inputStream = new FileInputStream(filename)) {
            deserializedData = MyDataProto.MyData.parseFrom(inputStream);

            System.out.println("Dane Protobuf zostały odczytane z pliku:");
            System.out.println("ID: " + deserializedData.getId());
            System.out.println("Name: " + deserializedData.getName());
            System.out.println("Items: " + deserializedData.getItemsList());
            System.out.println("Values: " + deserializedData.getValuesMap());

        } catch (IOException e) {
            System.out.println("Wystąpił błąd podczas odczytu z pliku: " + e.getMessage());
        }

        System.out.println("Zdeserializowane dane (Java):");
        System.out.println("ID: " + deserializedData.getId());
        System.out.println("Name: " + deserializedData.getName());
        System.out.println("Items: " + deserializedData.getItemsList());
        System.out.println("Values: " + deserializedData.getValuesMap());
    }
}