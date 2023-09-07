package com.shop.project.controllers;

import com.shop.project.dto.product.ProductFullDTO;
import com.shop.project.models.Product;
import com.shop.project.repository.ProductRepo;
import com.shop.project.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepo productRepo;
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

    @GetMapping("/getImage/{id}")
    public ResponseEntity<byte[]> getAllV2(@PathVariable Long id) {
        var dto = productService.getProductImage(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(dto.getContentType()));
        return ResponseEntity.ok().headers(headers).body(dto.getImage());
    }
}
