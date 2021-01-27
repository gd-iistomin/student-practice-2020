package kkramarenko.ecommerceapp.controller;


import kkramarenko.ecommerceapp.entity.Product;
import kkramarenko.ecommerceapp.repository.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api")
public class AdminController {

    private final ProductRepository productRepository;

    public AdminController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @PostMapping("/create-new-item")
    public void saveNewItem(@RequestBody Product product){
        productRepository.save(product);
    }

    @PutMapping("/disable-product/{productId}")
    @Transactional
    public void disableProduct(@PathVariable long productId){
        Product targetProduct = productRepository.getOne(productId);
        targetProduct.setActive(false);
        productRepository.save(targetProduct);
    }

}
