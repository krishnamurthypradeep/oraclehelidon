package org.example.helidonasynchronousdemo.repository;

import org.example.helidonasynchronousdemo.domain.Product;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

public class ProductRepository {

    private final Map<Integer, Product> products = new ConcurrentHashMap<>();

    public Product save(Product product) {
        products.put(product.getId(), product);
        return product;
    }
    public Optional<Product> findById(int id) {
        return Optional.ofNullable(products.get(id));
    }
    public List<Product> findAll() {
        return new ArrayList<>(products.values());
    }
    public Optional<List<Product>> findByPriceGreaterThan(Double price) {
        return Optional.of(products.values().stream()
                .filter(product -> product.getPrice()>= price)
                .collect(Collectors.toList()));
    }
}
