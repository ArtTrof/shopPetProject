package com.shop.project.controllers;

import com.shop.project.models.Category;
import com.shop.project.models.Producer;
import com.shop.project.repository.CategoryRepo;
import com.shop.project.repository.ProducerRepo;
import io.swagger.annotations.Api;
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
@RequestMapping("/producer")
@Api(tags = "! Producers of product endpoints")
public class ProducerController {
    @Autowired
    private ProducerRepo producerRepo;

    @Operation(summary = "Get names", description = "Get all producer names")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get all producer names")
    })
    @GetMapping("/getAllNames")
    public ResponseEntity<List<String>> getAllNames() {
        return ResponseEntity.ok().body(producerRepo.findAllNames());
    }

    @Operation(summary = "Get id and names", description = "Get all producer id and names")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get all categories")
    })
    @GetMapping("/getAll")
    public ResponseEntity<List<Producer>> getAll() {
        return ResponseEntity.ok().body(producerRepo.findAll());
    }

    @Operation(summary = "Create producer", description = "Create new producer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producer created"),
            @ApiResponse(responseCode = "400", description = "Producer already exists")
    })
    @PostMapping("/new")
    public ResponseEntity<String> newCategory(@RequestParam(value = "name", required = true) String name) {
        if (producerRepo.findProducerByName(name).isPresent()) {
            return ResponseEntity.badRequest().body(String.format("Category with name %s already exists", name));
        } else {
            producerRepo.save(Producer.builder().name(name).build());
            return ResponseEntity.ok().body("Producer successfully created");
        }
    }

    @Operation(summary = "Delete producer", description = "Delete producer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producer deleted"),
            @ApiResponse(responseCode = "400", description = "Producer with such id doesnt exists")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable(required = true, value = "id") Long id) {
        try {
            producerRepo.deleteById(id);
            return ResponseEntity.ok().body(String.format("Category with id %s was deleted", id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Not found producer with id:" + id + ",CODE:400");
        }
    }
}
