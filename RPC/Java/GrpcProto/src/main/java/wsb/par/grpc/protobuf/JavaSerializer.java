package wsb.par.grpc.protobuf;


import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class JavaSerializer {
    public static void main(String[] args) throws IOException {
        Map<String, Integer> values = new HashMap<>();
        values.put("key1", 10);
        values.put("key2", 20);

        MyDataProto.MyData data = MyDataProto.MyData.newBuilder()
                .setId(123)
                .setName("Test Object")
                .addAllItems(Arrays.asList("itemA", "itemB", "itemC"))
                .putAllValues(values)
                .build();

        // Serializacja do tablicy bajtów
        byte[] serializedData = data.toByteArray();

        System.out.println("Zserializowane dane (Java): " + Arrays.toString(serializedData));

        String filename = "myDataJ.proto.bin";

        try (FileOutputStream outputStream = new FileOutputStream(filename)) {
            data.writeTo(outputStream);
            System.out.println("Dane Protobuf zostały zapisane do pliku: " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Możesz teraz przesłać te dane (np. przez sieć, zapis do pliku) do Pythona
    }
}
