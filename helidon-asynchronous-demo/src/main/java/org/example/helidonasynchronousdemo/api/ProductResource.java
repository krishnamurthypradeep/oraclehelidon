package org.example.helidonasynchronousdemo.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.helidon.webserver.Routing;
import io.helidon.webserver.ServerRequest;
import io.helidon.webserver.ServerResponse;
import org.example.helidonasynchronousdemo.domain.Product;
import org.example.helidonasynchronousdemo.repository.ProductRepository;

public class ProductResource {
    private final ProductRepository productRepository;
    private final ObjectMapper objectMapper;
    public ProductResource(ProductRepository productRepository,ObjectMapper objectMapper) {
        this.productRepository = productRepository;
        this.objectMapper = objectMapper;
    }
    public void register(Routing.Rules rules) {
        rules.post("/products",this::create )
                .get("/products",this::getAll)
                .get("/products/{id}",this::getById)
                .get("/products/find/{price}",this::getByPriceGreaterThan);
        ;

    }
    private void create(ServerRequest req, ServerResponse res) {
        req.content().as(String.class).thenAccept(json -> {
            try {
               Product product = objectMapper.readValue(json,Product.class);
               productRepository.save(product);
               res.status(201).send(objectMapper.writeValueAsString(product));
            } catch (JsonProcessingException e) {
                res.status(400).send("Bad Request");
            }
        });
    }
    private void getAll(ServerRequest req, ServerResponse res) {
        try {
            res.send(objectMapper.writeValueAsString(productRepository.findAll()));
        } catch (JsonProcessingException e) {
            res.status(500).send("JSON ERROR");
        }
    }
    // http://localhost:9090/products/1
    private void getById(ServerRequest req, ServerResponse res) {
        String id = req.path().param("id");
        productRepository.findById(Integer.parseInt(id))
                .ifPresentOrElse(product -> {
                            try {
                                res.send(objectMapper.writeValueAsString(product));

                            } catch (Exception e) {
                                res.status(500).send("Bad Request");
                            }
                        },
                        () -> res.status(404).send("Product ot Found"));

    }

    private void getByPriceGreaterThan(ServerRequest req, ServerResponse res) {
        String price = req.path().param("price");
        productRepository.findByPriceGreaterThan(Double.parseDouble(price))
                .ifPresentOrElse(products -> {
                            try {
                                res.send(objectMapper.writeValueAsString(products));

                            } catch (Exception e) {
                                res.status(500).send("Bad Request");
                            }
                        },
                        () -> res.status(404).send("Product with Price Not Found"));

    }
}
