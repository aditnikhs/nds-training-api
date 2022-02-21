package com.training.api.controller;

import java.util.List;

import com.training.api.model.ProductModel;
import com.training.api.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {
    
    @Autowired
    ProductService service;
    
    @GetMapping("/all")
    public ResponseEntity<String> getAll(){
        return service.getAll();
    }

    @PostMapping("/add")
    public ResponseEntity<String> add(@RequestBody ProductModel model){
        return service.add(model);
    }

    @PostMapping("/update")
    public ResponseEntity<String> update(@RequestBody ProductModel model){
        return service.update(model);
    }

    @PostMapping("/delete")
    public ResponseEntity<String> delete(@RequestBody ProductModel model){
        return service.delete(model);
    }

    @PostMapping("/data")
    public ResponseEntity<String> getProductById(@RequestBody ProductModel model){
        return service.getById(model);
    }

    @PostMapping("/search-by-category")
    public ResponseEntity<String> searchByCategory(@RequestBody ProductModel model){
        return service.searchByCategory(model);
    }

    @PostMapping("/search-by-category-2")
    public ResponseEntity<String> searchByCategory2(@RequestBody ProductModel model){
        return service.searchByCategory2(model);
    }

    @PostMapping("/add-batch")
    public ResponseEntity<String> addBatch(@RequestBody List<ProductModel> list){
        return service.addBatch(list);
    }
}
