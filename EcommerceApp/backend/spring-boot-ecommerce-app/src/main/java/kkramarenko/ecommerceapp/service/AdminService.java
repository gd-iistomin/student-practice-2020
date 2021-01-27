package kkramarenko.ecommerceapp.service;

import kkramarenko.ecommerceapp.entity.Product;

public interface AdminService {

    void createNewProduct(Product product);

    void disableProduct(long productId);

    void enableProduct(long productId);
}
