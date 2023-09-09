package com.shop.project.controllers;

import com.shop.project.dto.product.ProductFullDTO;
import com.shop.project.dto.product.ProductToUpdateDTO;
import com.shop.project.models.Product;
import com.shop.project.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Save product", description = "Save new product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success save"),
            @ApiResponse(responseCode = "400", description = "Exception")
    })
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, path = "/new")
    public ResponseEntity<String> saveNewProduct(@RequestParam("image") MultipartFile image,
                                       @RequestParam("name") String name,
                                       @RequestParam("shortDescription") String shortDescription,
                                       @RequestParam("longDescription") String longDescription,
                                       @RequestParam("price") double price,
                                       @RequestParam("isAvailable") boolean isAvailable,
                                       @RequestParam("quantity") int quantity,
                                       @RequestParam("categoryName") String category,
                                       @RequestParam("producerName") String producer
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
    @Operation(summary = "Get list of all products", description = "Get all products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products found")
    })
    @GetMapping("/getAll")
    public ResponseEntity<List<ProductFullDTO>> getAll(
            @RequestParam(name = "categoryName", required = false) String categoryName,
            @RequestParam(name = "productName", required = false) String productName) {
        return productService.getAll(categoryName, productName);
    }
    @Operation(summary = "Get product", description = "Get product by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product found"),
            @ApiResponse(responseCode = "400", description = "No product found with such id")
    })
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
    @Operation(summary = "Get product image", description = "Get product image by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product image found")
    })
    @GetMapping("/getImage/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
        var dto = productService.getProductImage(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(dto.getContentType()));
        return ResponseEntity.ok().headers(headers).body(dto.getImage());
    }
    @Operation(summary = "Delete product", description = "Delete product with id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product deleted")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProductById(@PathVariable Long id) {
        return productService.deleteById(id);
    }

    @Operation(summary = "Update product", description = "Update product with id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product updated"),
            @ApiResponse(responseCode = "400", description = "Wrong data input")
    })
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
