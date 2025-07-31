package org.example.helidon;



import io.helidon.common.reactive.Single;
import io.helidon.webclient.WebClient;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//sss
//sss
public class HelidonAsyncDemo2 {
    public static void main(String[] args) {
        WebClient client = WebClient.builder()
                .baseUri("https://jsonplaceholder.typicode.com")
                .build();

        Single<String> responseSingle = client.get()
                .path("/users")
                .request(String.class);

        // Subscribe to the response (asynchronous)
        responseSingle.thenAccept(body -> {
            System.out.println("\n\n=== Async HTTP Response received! ===");
            System.out.println(body);
        }).exceptionally(ex -> {
            ex.printStackTrace();
            return null;
        });

        System.out.print("Main thread: doing other work");
        for (int i = 0; i < 10; i++) {
            System.out.print(".");
            try {
                Thread.sleep(300); // Simulate work
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("\nMain thread: done with its own work!");

        // Give async HTTP enough time to complete before main thread exits
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


    }
}
