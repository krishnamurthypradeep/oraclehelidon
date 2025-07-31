package org.example.helidonasynchronousdemo;

import io.helidon.common.reactive.Single;
import io.helidon.webclient.WebClient;

public class HelidonSyncDemo {

    public static void main(String[] args) {

        WebClient client = WebClient.builder()
                .baseUri("https://jsonplaceholder.typicode.com")
                .build();
        System.out.println("Main Thread: " + Thread.currentThread().getName());
      Single<String> result =  client.get().path("/users").request(String.class);
        System.out.println("Http Response received: "  );
      System.out.println(result.await());
    }
}
