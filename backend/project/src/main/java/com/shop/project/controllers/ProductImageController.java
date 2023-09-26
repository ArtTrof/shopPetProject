package com.shop.project.controllers;

import com.shop.project.service.ProductImageService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/image")
@Api(tags = "! Images for products endpoints")
public class ProductImageController {
    @Autowired
    private ProductImageService productImageService;

    @Operation(summary = "Insert the image to product", description = "Insert the image to product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Image uploaded"),
            @ApiResponse(responseCode = "400", description = "Fail")
    })
    //consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
    @PostMapping(path = "/add/{productId}")
    public ResponseEntity<String> addImageToProduct(
            @PathVariable Long productId,
            @RequestBody List<MultipartFile> images) {
        try {
            return productImageService.addImageToProduct(productId, images);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to add the image: " + e.getMessage());
        }
    }

    @Operation(summary = "Get the images to product", description = "Get the images to product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List blob of images"),
            @ApiResponse(responseCode = "400", description = "Fail")
    })
    @GetMapping("/getImagesForProduct/{productId}")
    public ResponseEntity<List<byte[]>> addImageToProduct(
            @PathVariable Long productId) {
        List<byte[]> listOfImages = productImageService.getImagesForProduct(productId);
        return ResponseEntity.ok().body(listOfImages);
    }
}
