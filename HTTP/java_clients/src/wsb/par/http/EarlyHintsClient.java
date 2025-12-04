package wsb.par.http;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

public class EarlyHintsClient {

    // Manager "ufam wszystkim" (dla naszego samo-podpisanego certyfikatu)
    private static final TrustManager[] trustAllCerts = new TrustManager[]{
            new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() { return null; }
                public void checkClientTrusted(X509Certificate[] certs, String authType) { }
                public void checkServerTrusted(X509Certificate[] certs, String authType) { }
            }
    };

    public static void main(String[] args) throws Exception {
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, trustAllCerts, new SecureRandom());

        HttpClient httpClient = HttpClient.newBuilder()
                .version(Version.HTTP_2)
                .sslContext(sslContext)
                .build();

        HttpRequest pageRequest = HttpRequest.newBuilder()
                .uri(URI.create("https://localhost:8443/"))
                .build();

        System.out.println("Wysyłam główne żądanie do: " + pageRequest.uri());

        // Wysyłamy żądanie, używając specjalnego handlera, który potrafi obsłużyć
        // wiele odpowiedzi (w tym informacyjne, jak 103).
        HttpResponse<String> response = httpClient.send(pageRequest, responseInfo -> {

            System.out.println("\n!!! OTRZYMANO ODPOWIEDŹ OD SERWERA !!!");
            System.out.println("  -> Kod statusu: " + responseInfo.statusCode());

            // Sprawdzamy, czy to jest odpowiedź informacyjna 103 Early Hints
            if (responseInfo.statusCode() == 103) {
                System.out.println("  -> To jest Early Hint!");
                responseInfo.headers().firstValue("link").ifPresent(linkHeader -> {
                    System.out.println("  -> Serwer sugeruje pobranie: " + linkHeader);
                    // W prawdziwej przeglądarce, w tym momencie rozpocząłby się proces pobierania /style.css
                });

                // Zwracamy handler, który będzie czekał na ostateczną odpowiedź (200 OK)
                return BodyHandlers.ofString().apply(responseInfo);
            }

            // Dla finalnej odpowiedzi (200 OK), po prostu ją przetwarzamy
            return BodyHandlers.ofString().apply(responseInfo);
        });

        System.out.println("\n--- OSTATECZNA ODPOWIEDŹ (po opóźnieniu) ---");
        System.out.println("Finalny kod statusu: " + response.statusCode());
        System.out.println("Treść (fragment): \n" + response.body().substring(0, Math.min(80, response.body().length())) + "...");
    }
}
