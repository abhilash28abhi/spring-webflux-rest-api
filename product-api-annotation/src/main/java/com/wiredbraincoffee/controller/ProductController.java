package com.wiredbraincoffee.controller;

import com.wiredbraincoffee.model.Product;
import com.wiredbraincoffee.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public Flux<Product> getAllProducts () {
        return productRepository.findAll();
    }

    @GetMapping("{id}")
    public Mono<ResponseEntity<Product>> getProductById (@PathVariable String id) {
        return productRepository.findById(id)
                .map(p -> ResponseEntity.ok(p))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Product> saveProduct(@RequestBody Product product) {
        return productRepository.save(product);
    }

    @PutMapping("{id}")
    public Mono<ResponseEntity<Product>> updateProduct (@PathVariable String id,
                                                        @RequestBody Product product) {
        return productRepository.findById(id)
                .flatMap(exsistingProduct -> {
                    exsistingProduct.setName(product.getName());
                    exsistingProduct.setPrice(product.getPrice());
                    return productRepository.save(exsistingProduct);
                })
                .map(updateProduct -> ResponseEntity.ok(updateProduct))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public Mono<ResponseEntity<Void>> deleteProductById (@PathVariable String id) {
        return productRepository.findById(id)
                .flatMap(exsistingProduct -> {
                    return productRepository.delete(exsistingProduct);
                })
                .then(Mono.just(ResponseEntity.ok().<Void>build()))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping
    public Mono<Void> deleteAllProducts() {
        return productRepository.deleteAll();
    }
}
