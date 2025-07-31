package org.example.helidonasynchronousdemo;


import io.helidon.common.reactive.Single;
import io.helidon.webclient.WebClient;

public class HelidonAsyncDemo {
    public static void main(String[] args) {
        WebClient client = WebClient.builder()
                .baseUri("https://jsonplaceholder.typicode.com")
                .build();
        Single<String> result =
       client.get().path("/users").request(String.class);
        result.thenAccept(body -> {
                    System.out.println("Thread "+Thread.currentThread().getName());
            System.out.println("Http Response received: " + body);

                })
                .exceptionally(ex -> {
                    ex.printStackTrace();
                    return null;
                });
        System.out.println("Main Thread doing other things");
        for(int i=0;i<10;i++){
            System.out.println(".");
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Main Thread done");

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
