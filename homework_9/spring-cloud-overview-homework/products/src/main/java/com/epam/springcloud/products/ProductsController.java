package com.epam.springcloud.products;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping
@RestController
public class ProductsController {
    @Autowired
    ProductsRepository repository;

    @PostMapping
    public Product createProduct() {
        var name = RandomStringUtils.randomAlphabetic(16);
        var quantity = RandomUtils.nextLong(1, 10);
        repository.add(name, quantity);

        return new Product(name, quantity);
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return repository.getAll();
    }

    @GetMapping("/{productName}")
    public Product getProduct(@PathVariable String productName) {
        return new Product(
                productName,
                repository.getQuantity(productName)
        );
    }

    @GetMapping("/remove/{productName}")
    public ResponseEntity<Product> removeOneProduct(@PathVariable String productName) throws JsonProcessingException {
        Long quantity = repository.getQuantity(productName);
        if (quantity <= 0) {
            throw new NotFoundProductException();
        }
        repository.removeOneByName(productName);
        return new ResponseEntity<>(new Product(productName, quantity), HttpStatus.OK);
    }
}
