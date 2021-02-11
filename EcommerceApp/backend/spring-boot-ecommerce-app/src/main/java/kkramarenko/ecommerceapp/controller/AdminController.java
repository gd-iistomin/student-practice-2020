package kkramarenko.ecommerceapp.controller;


import kkramarenko.ecommerceapp.entity.Product;
import kkramarenko.ecommerceapp.service.AdminService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/create-new-item")
    public void saveNewItem(@RequestBody Product product) {
        adminService.createNewProduct(product);
    }

    @PutMapping("/disable-product/{productId}")
    public void disableProduct(@PathVariable long productId) {
        adminService.disableProduct(productId);
    }

    @PutMapping("/enable-product/{productId}")
    public void enableProduct(@PathVariable long productId) {
        adminService.enableProduct(productId);
    }

}
