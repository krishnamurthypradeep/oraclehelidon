package org.example.helidon;



import io.helidon.webclient.WebClient;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class HelidonAsyncDemo {
    public static void main(String[] args) {
        WebClient client = WebClient.builder()
                .baseUri("https://jsonplaceholder.typicode.com")
                .build();

        ExecutorService executor = Executors.newSingleThreadExecutor();

        CompletableFuture.supplyAsync(() ->
                                client.get()
                                        .path("/users")
                                        .request(String.class).
                                        await()
                                        // blocking, but runs in executor thread
                        , executor)
                .thenAccept(body -> {
                    System.out.println("Async response:\n" + body);
                })
                .exceptionally(ex -> {
                    ex.printStackTrace();
                    return null;
                });

        System.out.println("Main thread continues...");
        // For demo only: give async time to finish
        try { Thread.sleep(2000); } catch (Exception ignored) {}

        executor.shutdown();


    }
}
