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
import java.net.http.HttpResponse.PushPromiseHandler;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

public class Http2ClientPushExample {

    // Manager, który "ufam wszystkim". TYLKO DO TESTÓW LOKALNYCH!
    private static final TrustManager[] trustAllCerts = new TrustManager[]{
            new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() { return null; }
                public void checkClientTrusted(X509Certificate[] certs, String authType) { }
                public void checkServerTrusted(X509Certificate[] certs, String authType) { }
            }
    };

    public static void main(String[] args) throws Exception {
        // Konfiguracja SSLContext, aby ufał naszemu certyfikatowi
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, trustAllCerts, new SecureRandom());

        // Mapa do przechowywania "wepchniętych" odpowiedzi
        var pushedResponses = new ConcurrentHashMap<HttpRequest, CompletableFuture<HttpResponse<String>>>();

        // Nasz handler, który będzie reagował na obietnice push
        PushPromiseHandler<String> pushPromiseHandler = (initiatingRequest, pushPromiseRequest, acceptor) -> {
            System.out.println("!!! Otrzymano Push Promise! URI: " + pushPromiseRequest.uri());
            System.out.println("--> Inicjujące żądanie: " + initiatingRequest.uri());

            // Akceptujemy obietnicę i przechowujemy ją w mapie
            pushedResponses.put(pushPromiseRequest, acceptor.apply(BodyHandlers.ofString()));
        };

        HttpClient httpClient = HttpClient.newBuilder()
                .version(Version.HTTP_2)
                .sslContext(sslContext) // Używamy naszego niestandardowego kontekstu SSL
                .build();

        HttpRequest pageRequest = HttpRequest.newBuilder()
                .uri(URI.create("https://localhost:8443/")) // Celujemy w nasz lokalny serwer
                .build();

        System.out.println("Wysyłam główne żądanie do: " + pageRequest.uri());

        // Wysyłamy asynchronicznie, aby obsłużyć push promises
        httpClient.sendAsync(pageRequest, BodyHandlers.ofString(), pushPromiseHandler)
                .thenAccept(pageResponse -> {
                    System.out.println("\n--- Odpowiedź na GŁÓWNE żądanie ---");
                    System.out.println("Kod statusu: " + pageResponse.statusCode());
                    System.out.println("Treść: \n" + pageResponse.body().substring(0, 50) + "...");
                }).join();

        // Czekamy na zakończenie i sprawdzamy, co zostało "wepchnięte"
        System.out.println("\n--- Analiza WEPCHNIĘTYCH odpowiedzi ---");
        pushedResponses.forEach((req, future) -> {
            try {
                HttpResponse<String> pushedResponse = future.join();
                System.out.println("Wepchnięty zasób: " + req.uri());
                System.out.println("  -> Kod statusu: " + pushedResponse.statusCode());
                System.out.println("  -> Treść: " + pushedResponse.body());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}