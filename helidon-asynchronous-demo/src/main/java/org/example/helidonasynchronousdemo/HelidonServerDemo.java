package org.example.helidonasynchronousdemo;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.helidon.webserver.Routing;
import io.helidon.webserver.WebServer;
import org.example.helidonasynchronousdemo.api.ProductResource;
import org.example.helidonasynchronousdemo.repository.ProductRepository;

public class HelidonServerDemo {
    public static void main(String[] args) {

        ProductRepository productRepository = new ProductRepository();
        ProductResource productResource = new ProductResource(productRepository,new ObjectMapper());

      Routing routing =  Routing.builder().register(productResource::register).build();
      WebServer server = WebServer.builder(routing).port(9090).build();
      server.start().thenAccept(v -> System.out.println("Server started"));
    }
}
