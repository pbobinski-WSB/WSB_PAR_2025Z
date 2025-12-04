package wsb.par.graphclient;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class GraphQLClient {

    private static final String GRAPHQL_ENDPOINT = "http://localhost:8080/graphql";

    public static void main(String[] args) throws Exception {
        HttpClient client = HttpClient.newHttpClient();

        // --- Wywołanie Mutacji createUser ---
        System.out.println("--- Wysyłanie mutacji createUser ---");
        String mutation = """
            mutation {
              createUser(name: "Alicja z Javy", email: "alicja@java.pl") {
                name
                email
              }
            }
        """;

        String mutationJsonPayload = buildJsonPayload(mutation);

        HttpRequest mutationRequest = HttpRequest.newBuilder()
                .uri(URI.create(GRAPHQL_ENDPOINT))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(mutationJsonPayload))
                .build();

        HttpResponse<String> mutationResponse = client.send(mutationRequest, HttpResponse.BodyHandlers.ofString());
        System.out.println("Odpowiedź serwera (mutacja):\n" + mutationResponse.body());


        // --- Wywołanie Zapytania users ---
        System.out.println("\n--- Wysyłanie zapytania users ---");
        String query = """
            query {
              users {
                name
                email
              }
            }
        """;

        String queryJsonPayload = buildJsonPayload(query);

        HttpRequest queryRequest = HttpRequest.newBuilder()
                .uri(URI.create(GRAPHQL_ENDPOINT))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(queryJsonPayload))
                .build();

        HttpResponse<String> queryResponse = client.send(queryRequest, HttpResponse.BodyHandlers.ofString());
        System.out.println("Odpowiedź serwera (zapytanie):\n" + queryResponse.body());
    }

    // Prosta metoda pomocnicza do opakowania zapytania w obiekt JSON
    private static String buildJsonPayload(String graphqlQuery) {
        // Musimy "uciec" od znaków specjalnych w zapytaniu, zwłaszcza cudzysłowów i nowych linii
        String escapedQuery = graphqlQuery.replace("\"", "\\\"").replace("\n", "\\n");
        return String.format("{\"query\": \"%s\"}", escapedQuery);
    }
}
