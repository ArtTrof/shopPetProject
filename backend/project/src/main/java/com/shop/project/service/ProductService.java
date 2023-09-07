package com.shop.project.service;

import com.shop.project.dto.product.ProductFullDTO;
import com.shop.project.models.Product;
import com.shop.project.repository.CategoryRepo;
import com.shop.project.repository.ProducerRepo;
import com.shop.project.repository.ProductRepo;
import com.shop.project.util.ThrownException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private ProducerRepo producerRepo;

    @Transactional
    public void saveProduct(Product product, MultipartFile image, Long categoryId, Long producerId) {
        try {
            product.setImage(image.getBytes());
            product.setCategory(categoryRepo.findById(categoryId).get());
            product.setProducer(producerRepo.findById(producerId).get());
            product.setContentType(image.getContentType());
            productRepo.save(product);
        } catch (IOException e) {
            throw new ThrownException(e.getMessage());
        }
    }

    public Product getProductImage(Long id) {
        var product = productRepo.findById(id);
        if (product.isPresent()) {
            return Product.builder().image(product.get().getImage()).contentType(product.get().getContentType()).build();
        } else {
            throw new ThrownException("No product with such id");
        }
    }

    public List<ProductFullDTO> getAll() {
        List<Product> products = productRepo.findAll();
        List<ProductFullDTO> dtos = new ArrayList<>();
        for (Product product : products){
            dtos.add(new ProductFullDTO(product.getId(),
                    product.getName(),
                    product.getShortDescription(),
                    product.getLongDescription(),
                    product.getPrice(),
                    product.isAvailable(),
                    product.getQuantity(),
                    product.getCategory().getName(),
                    product.getProducer().getName()));
        }
        return dtos;
    }
}
