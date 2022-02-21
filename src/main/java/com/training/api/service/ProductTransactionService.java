package com.training.api.service;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.training.api.entity.ProductEntity;
import com.training.api.global.GlobalConstant;
import com.training.api.model.ProductModel;
import com.training.api.repository.ProductCategoryRepo;
import com.training.api.repository.ProductRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductTransactionService implements Serializable {

    @Autowired
    ProductRepo repo;

    @Autowired
    ProductCategoryRepo catRepo;

    @Transactional(
            propagation = Propagation.REQUIRES_NEW,
            rollbackFor = {Exception.class})
    public List<ProductEntity> save(List<ProductModel> list) throws Exception{

        ArrayList<String> error = new ArrayList<String>();
        for (ProductModel model : list){
            if (model.getProductName().isEmpty()||model.getProductName()==null){
                error.add("Product Name is Required");
            } 
            else if (repo.countNameUsed(model.getProductName())!=0){
                error.add("Product Name is Used");
            }
            if (model.getProductCategoryId().isEmpty()||model.getProductCategoryId()==null){
                error.add("Product Category Id is Required");
            } else if (catRepo.countIdUsed(model.getProductCategoryId())<=0){
                error.add("Product Category Id not Found");
            }
        }
        
        if (error.size()>0) throw new Exception(error.toString());

        List<ProductEntity> entities = new ArrayList<ProductEntity>();

        for (ProductModel model : list){

            if (repo.countNameUsed(model.getProductName())!=0){
                throw new Exception(model.getProductName() + " is Already Used");
            }

            ProductEntity entity = new ProductEntity();
            entity.setProductName(model.getProductName());
            entity.setProductCategoryId(model.getProductCategoryId());
            entity.setCreatedBy(model.getCreatedBy());
            entity.setCreatedDate(new Timestamp(new Date().getTime()));
            entity.setRecStatus(GlobalConstant.REC_STATUS_ACTIVE);

            entity = repo.save(entity);
            entities.add(entity);
        }
        
        return entities;
    }
    
}
