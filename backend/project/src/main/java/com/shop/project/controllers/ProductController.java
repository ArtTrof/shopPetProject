package com.shop.project.controllers;

import com.shop.project.dto.product.ProductFullDTO;
import com.shop.project.dto.product.ProductToUpdateDTO;
import com.shop.project.models.Product;
import com.shop.project.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

import static com.shop.project.util.validators.ValidationErrorResponse.getValidationErrors;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ModelMapper mapper;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, path = "/new")
    public ResponseEntity<String> test(@RequestParam("image") MultipartFile image,
                                       @RequestParam("name") String name,
                                       @RequestParam("shortDescription") String shortDescription,
                                       @RequestParam("longDescription") String longDescription,
                                       @RequestParam("price") double price,
                                       @RequestParam("isAvailable") boolean isAvailable,
                                       @RequestParam("quantity") int quantity,
                                       @RequestParam("category_id") Long category,
                                       @RequestParam("producer_id") Long producer
    ) {
        try {
            Product product = Product.builder()
                    .name(name)
                    .price(price)
                    .isAvailable(isAvailable)
                    .shortDescription(shortDescription)
                    .longDescription(longDescription)
                    .quantity(quantity)
                    .build();
            productService.saveProduct(product, image, category, producer);
            return ResponseEntity.ok("Product registered successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(String.format(e.getMessage() + ",CODE: 400"));
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<ProductFullDTO>> getAll() {
        var products = productService.getAll();
        return ResponseEntity.ok().body(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductFullDTO> getOne(@PathVariable Long id) {
        try {
            var product = productService.findById(id);
            var dto = mapToProductFullDTO(product);
            dto.setCategory(product.getCategory().getName());
            dto.setProducer(product.getProducer().getName());
            return ResponseEntity.ok().body(dto);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/getImage/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
        var dto = productService.getProductImage(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(dto.getContentType()));
        return ResponseEntity.ok().headers(headers).body(dto.getImage());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProductById(@PathVariable Long id) {
        return productService.deleteById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateProductById(@PathVariable Long id,
                                                    @RequestBody @Valid ProductToUpdateDTO dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(getValidationErrors(bindingResult));
        }
        return productService.updateProductById(id, dto);
    }

    public ProductFullDTO mapToProductFullDTO(Product product) {
        return mapper.map(product, ProductFullDTO.class);
    }
}
