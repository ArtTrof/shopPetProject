package com.shop.project.service;

import com.shop.project.dto.product.ProductFullDTO;
import com.shop.project.dto.product.ProductToUpdateDTO;
import com.shop.project.models.Category;
import com.shop.project.models.Producer;
import com.shop.project.models.Product;
import com.shop.project.repository.CategoryRepo;
import com.shop.project.repository.ProducerRepo;
import com.shop.project.repository.ProductRepo;
import com.shop.project.util.ThrownException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

    //    @Transactional
//    public void saveProduct(Product product, MultipartFile image, String categoryId, String producerId) {
//        try {
//            product.setImage(image.getBytes());
//            product.setCategory(categoryRepo.findCategoryByName(categoryId).get());
//            product.setProducer(producerRepo.findProducerByName(producerId).get());
//            product.setContentType(image.getContentType());
//            productRepo.save(product);
//        } catch (IOException e) {
//            throw new ThrownException(e.getMessage());
//        }
//    }
    @Transactional
    public ResponseEntity<String> saveProduct(Product product, String category, String producer) {
        Optional<Category> categoryByName = categoryRepo.findCategoryByName(category);
        Optional<Producer> producerByName = producerRepo.findProducerByName(producer);
        if (categoryByName.isPresent()) {
            product.setCategory(categoryByName.get());
        } else {
            return ResponseEntity.badRequest().body(String.format("No category with name: %s", category));
        }
        if (producerByName.isPresent()) {
            product.setProducer(producerByName.get());
        } else {
            return ResponseEntity.badRequest().body(String.format("No producer with name: %s", producer));
        }
        productRepo.save(product);
        return ResponseEntity.ok().body("Product created successfully!");
    }

//    public Product getProductImage(Long id) {
//        var product = productRepo.findById(id);
//        if (product.isPresent()) {
//            return Product.builder().image(product.get().getImage()).contentType(product.get().getContentType()).build();
//        } else {
//            throw new ThrownException("No product with such id");
//        }
//    }

    public ResponseEntity<List<ProductFullDTO>> getAll(String productName, String categoryName, String producerName) {
        Optional<Producer> producer = Optional.empty();
        Optional<Category> category = Optional.empty();
        Optional<List<Product>> productList = Optional.empty();
        if (categoryName != null) {
            category = categoryRepo.findCategoryByName(categoryName);
            if (category.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
        }
        if (producerName != null) {
            producer = producerRepo.findProducerByName(producerName);
            if (producer.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
        }
        if (productName != null && categoryName != null && producerName != null) {
            productList = productRepo.findProductsByNameIgnoreCaseStartingWithAndCategoryAndProducer(productName, category.get(), producer.get());
        } else if (productName != null && categoryName != null) {
            productList = productRepo.findProductsByNameIgnoreCaseStartingWithAndCategory(productName, category.get());
        } else if (productName != null && producerName != null) {
            productList = productRepo.findProductsByNameIgnoreCaseStartingWithAndProducer(productName, producer.get());
        } else if (categoryName != null && producerName != null) {
            productList = productRepo.findProductByCategoryAndProducer(category.get(), producer.get());
        } else if (producerName != null) {
            productList = productRepo.findProductsByProducer(producer.get());
        } else if (categoryName != null) {
            productList = productRepo.findProductsByCategory(category.get());
        } else if (productName != null) {
            productList = productRepo.findProductsByNameIgnoreCaseStartingWith(productName);
        } else {
            productList = Optional.of(productRepo.findAll());
        }
        if (productList.isPresent() && !productList.get().isEmpty()) {
            return ResponseEntity.ok().body(parseListOfProductToFullDTO(productList.get()));
        } else {
            return ResponseEntity.notFound().build();
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
                    return ResponseEntity.badRequest().body(String.format("No producer found with name %s", dto.getProducer().get()));
                }
            }
            productRepo.save(product);
            return ResponseEntity.ok("Product saved successfully");
        } else {
            return ResponseEntity.badRequest().body(String.format("Product with id: %s doesn't exist", id));
        }
    }
}
