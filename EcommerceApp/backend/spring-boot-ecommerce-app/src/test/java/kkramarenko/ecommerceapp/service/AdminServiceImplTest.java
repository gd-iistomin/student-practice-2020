package kkramarenko.ecommerceapp.service;

import kkramarenko.ecommerceapp.entity.Product;
import kkramarenko.ecommerceapp.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("Admin service test")
@ExtendWith(MockitoExtension.class)
class AdminServiceImplTest {

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    AdminServiceImpl adminService;

    @Captor
    ArgumentCaptor<Product> productArgumentCaptor;

    @Test
    void disableProduct() {
        Product product = new Product();
        product.setId(1L);
        product.setName("test");
        product.setActive(true);

        when(productRepository.getOne(1L)).thenReturn(product);

        when(productRepository.save(productArgumentCaptor.capture())).thenReturn(null);

        adminService.disableProduct(1L);

        verify(productRepository).save(any());

        assertFalse(productArgumentCaptor.getValue().isActive());
    }

    @Test
    void enableProduct() {
        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("test2");
        product2.setActive(false);

        when(productRepository.getOne(2L)).thenReturn(product2);

        when(productRepository.save(productArgumentCaptor.capture())).thenReturn(null);

        adminService.enableProduct(2L);

        verify(productRepository).save(any());

        assertTrue(productArgumentCaptor.getValue().isActive());
    }
}