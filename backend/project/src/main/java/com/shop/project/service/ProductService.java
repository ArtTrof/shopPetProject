package com.shop.project.service;

import com.shop.project.dto.product.ProductFullDTO;
import com.shop.project.dto.product.ProductToUpdateDTO;
import com.shop.project.models.Category;
import com.shop.project.models.Product;
import com.shop.project.repository.CategoryRepo;
import com.shop.project.repository.ProducerRepo;
import com.shop.project.repository.ProductRepo;
import com.shop.project.util.ThrownException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public void saveProduct(Product product, MultipartFile image, String categoryId, String producerId) {
        try {
            product.setImage(image.getBytes());
            product.setCategory(categoryRepo.findCategoryByName(categoryId).get());
            product.setProducer(producerRepo.findProducerByName(producerId).get());
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

    public ResponseEntity<List<ProductFullDTO>> getAll(String categoryName, String productName) {
        if (categoryName != null && !categoryName.isEmpty()) {
            Optional<Category> category = categoryRepo.findCategoryByName(categoryName);
            if (category.isEmpty()) {
                return ResponseEntity.badRequest().build();
            } else {
                Optional<List<Product>> productsWithCategory = productRepo.findProductByCategory(category.get());
                return productsWithCategory.map(products -> ResponseEntity.ok().body(parseListOfProductToFullDTO(products))).orElseGet(() -> ResponseEntity.badRequest().build());
            }
        } else if (productName != null && !productName.isEmpty()) {
            Optional<List<Product>> products = productRepo.findProductByNameStartsWith(productName);
            if (products.get().isEmpty()) {
                return ResponseEntity.badRequest().build();
            } else {
                return ResponseEntity.ok().body(parseListOfProductToFullDTO(products.get()));
            }
        } else {
            List<Product> products = productRepo.findAll();
            return ResponseEntity.ok().body(parseListOfProductToFullDTO(products));
        }
    }

    private List<ProductFullDTO> parseListOfProductToFullDTO(List<Product> products) {
        List<ProductFullDTO> dtos = new ArrayList<>();
        for (Product product : products) {
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

    public Product findById(Long id) {
        var product = productRepo.findById(id);
        if (product.isPresent()) {
            return product.get();
        } else {
            throw new ThrownException("No product with such id found");
        }
    }

    @Transactional
    public ResponseEntity<String> deleteById(Long id) {
        if (productRepo.existsById(id)) {
            productRepo.deleteById(id);
            return ResponseEntity.ok().body(String.format("Product with id %s was deleted successfully", id));
        } else {
            return ResponseEntity.badRequest().body(String.format("Product with id: %s doesn't exist", id));
        }
    }

    @Transactional
    public ResponseEntity<String> updateProductById(Long id, ProductToUpdateDTO dto) {
        if (productRepo.existsById(id)) {
            var product = productRepo.findById(id).get();
            dto.getName().ifPresent(product::setName);
            dto.getLongDescription().ifPresent(product::setLongDescription);
            dto.getShortDescription().ifPresent(product::setShortDescription);
            dto.getPrice().ifPresent(product::setPrice);
            dto.getIsAvailable().ifPresent(product::setAvailable);
            dto.getQuantity().ifPresent(product::setQuantity);

            if (dto.getCategory().isPresent()) {
                if (categoryRepo.findCategoryByName(dto.getCategory().get()).isPresent()) {
                    product.setCategory(categoryRepo.findCategoryByName(dto.getCategory().get()).get());
                } else {
                    return ResponseEntity.badRequest().body(String.format("No category found with name %s", dto.getCategory().get()));
                }
            }
            if (dto.getProducer().isPresent()) {
                if (producerRepo.findProducerByName(dto.getProducer().get()).isPresent()) {
                    product.setProducer(producerRepo.findProducerByName(dto.getProducer().get()).get());
                } else {
                    return ResponseEntity.badRequest().body(String.format("No producer found with name %s", dto.getCategory().get()));
                }
            }
            productRepo.save(product);
            return ResponseEntity.ok("Product saved successfully");
        } else {
            return ResponseEntity.badRequest().body(String.format("Product with id: %s doesn't exist", id));
        }
    }
}
