package kkramarenko.ecommerceapp.service;

import kkramarenko.ecommerceapp.entity.Product;
import kkramarenko.ecommerceapp.repository.ProductRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {

    private final ProductRepository productRepository;

    public AdminServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void createNewProduct(Product product) {
        productRepository.save(product);
    }

    @Override
    public void disableProduct(long productId) {
        Product targetProduct = productRepository.getOne(productId);
        targetProduct.setActive(false);
        productRepository.save(targetProduct);
    }

    @Override
    public void enableProduct(long productId) {
        Product targetProduct = productRepository.getOne(productId);
        targetProduct.setActive(true);
        productRepository.save(targetProduct);
    }
}
