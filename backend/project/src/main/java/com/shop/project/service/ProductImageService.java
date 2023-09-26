package com.shop.project.service;

import com.shop.project.models.Product;
import com.shop.project.models.ProductImage;
import com.shop.project.repository.ProductImageRepo;
import com.shop.project.repository.ProductRepo;
import com.shop.project.util.ImagesCompressor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductImageService {
    @Autowired
    private ProductImageRepo imageRepo;
    @Autowired
    private ProductRepo productRepo;

    public ResponseEntity<String> addImageToProduct(Long productId, List<MultipartFile> images) throws IOException {
        Optional<Product> optionalProduct = productRepo.findById(productId);
        if (optionalProduct.isPresent()) {
            List<String> originalFileNames = new ArrayList<>();
            if (images.isEmpty()) {
                return ResponseEntity.badRequest().body("No images input");
            } else {
                for (MultipartFile image : images) {
                    ProductImage productImage = ProductImage.builder()
                            .fileName(image.getOriginalFilename())
                            .contentType(image.getContentType())
                            .image(ImagesCompressor.compressImage(image.getBytes()))
                            .product(optionalProduct.get())
                            .build();
                    imageRepo.save(productImage);
                    originalFileNames.add(productImage.getFileName());
                }
            }
            return ResponseEntity.ok("Image added to the product successfully.Filenames:"
                    + originalFileNames);
        } else {
            return ResponseEntity.badRequest().body(String.format("No product with id %s", productId));
        }
    }

    public List<byte[]> getImagesForProduct(Long productId) {
        Optional<List<ProductImage>> allByProductId = imageRepo.findAllByProduct_Id(productId);
        if (allByProductId.isPresent() && !allByProductId.get().isEmpty()) {
            List<byte[]> imageBytes = new ArrayList<>();
            var images = allByProductId.get();
            for (ProductImage image : images) {
                imageBytes.add(ImagesCompressor.decompressImage(image.getImage()));
            }
            return imageBytes;
        } else {
            return Collections.emptyList();
        }
    }
}
