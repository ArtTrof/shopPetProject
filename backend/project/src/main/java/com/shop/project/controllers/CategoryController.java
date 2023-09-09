package com.shop.project.controllers;

import com.shop.project.models.Category;
import com.shop.project.repository.CategoryRepo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    CategoryRepo categoryRepo;
    @Operation(summary = "Get names", description = "Get all category names")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get all category names")
    })
    @GetMapping("/getAllNames")
    public ResponseEntity<List<String>> getAllNames() {
        return ResponseEntity.ok().body(categoryRepo.findAllNames());
    }
    @Operation(summary = "Get id and names", description = "Get all category id and names")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get all categories")
    })
    @GetMapping("/getAll")
    public ResponseEntity<List<Category>> getAll() {
        return ResponseEntity.ok().body(categoryRepo.findAll());
    }
    @Operation(summary = "Create category", description = "Create new category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category created"),
            @ApiResponse(responseCode = "400", description = "Category already exists")
    })
    @PostMapping("/new")
    public ResponseEntity<String> newCategory(@RequestParam(value = "name", required = true) String name) {
        if (categoryRepo.findCategoryByName(name).isPresent()) {
            return ResponseEntity.badRequest().body(String.format("Category with name %s already exists", name));
        } else {
            categoryRepo.save(Category.builder().name(name).build());
            return ResponseEntity.ok().body("Category successfully created");
        }
    }
    @Operation(summary = "Delete category", description = "Delete category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category deleted"),
            @ApiResponse(responseCode = "400", description = "Category with such id doesnt exists")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable(required = true, value = "id") Long id) {
        try {
            categoryRepo.deleteById(id);
            return ResponseEntity.ok().body(String.format("Category with id %s was deleted", id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Not found category with id:" + id + ",CODE:400");
        }
    }
}
