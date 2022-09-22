package com.clone.liner.service;

import com.clone.liner.model.product.Product;
import com.clone.liner.model.product.TypeOfProduct;
import com.clone.liner.repository.ProductRepository;
import com.clone.liner.util.UserUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ImageService imageService;

    public ProductService(ProductRepository productRepository, ImageService imageService) {
        this.productRepository = productRepository;
        this.imageService = imageService;
    }

    @Transactional
    public void productSave(Product product) {
        productRepository.save(product);
    }


    public void selectProductRole(Product product, String role) {
        switch (role) {
            case "PC" -> product.setTypeOfProducts(TypeOfProduct.PC);
            case "PHONE" -> product.setTypeOfProducts(TypeOfProduct.PHONE);
            case "TABLET" -> product.setTypeOfProducts(TypeOfProduct.TABLET);
        }
    }

    public void createProduct(Product product, String role, MultipartFile file) {
        selectProductRole(product, role);
        imageService.addImage(file, product);
        product.setCreatedTime(UserUtil.formatDateTimeNow());
    }
}
