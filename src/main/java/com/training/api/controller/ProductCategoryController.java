package com.training.api.controller;

import com.training.api.model.ProductCategoryModel;
import com.training.api.service.ProductCategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product-category")
public class ProductCategoryController {

    @Autowired
    ProductCategoryService service;
    
    @GetMapping("/all")
    public ResponseEntity<String> getAll(){
        return service.getAll();
    }

    @PostMapping("/add")
    public ResponseEntity<String> add(@RequestBody ProductCategoryModel model){
        return service.add(model);
    }

    @PostMapping("/update")
    public ResponseEntity<String> update(@RequestBody ProductCategoryModel model){
        return service.update(model);
    }

    @PostMapping("/delete")
    public ResponseEntity<String> delete(@RequestBody ProductCategoryModel model){
        return service.delete(model);
    }

    @PostMapping("/search")
    public ResponseEntity<String> search(@RequestBody ProductCategoryModel model){
        return service.search(model);
    }

    // @PostMapping("/search-product")
    // public String searchProduct(@RequestBody ProductCategoryModel model){
    //     return service.searchProduct(model);
    // }
}
