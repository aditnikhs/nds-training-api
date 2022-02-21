package com.training.api.service;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.training.api.entity.ProductCategoryEntity;
import com.training.api.global.GlobalConstant;
import com.training.api.global.GlobalFunction;
import com.training.api.model.ResponseModel;
import com.training.api.model.ProductCategoryModel;
import com.training.api.repository.ProductCategoryRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ProductCategoryService implements Serializable {
    
    @Autowired
    public ProductCategoryRepo repo;

    public ResponseEntity<String> getAll(){
        ResponseModel responseModel = new ResponseModel();

        try {
            List<ProductCategoryEntity> listPC = new ArrayList<ProductCategoryEntity>();
            repo.findAll().forEach(listPC::add);;

            responseModel.setResponseCode(GlobalConstant.SUCCESS_CODE);
            responseModel.setResponseMessage("Success");
            responseModel.setResponseData(listPC);

            return new ResponseEntity<String>(
                GlobalFunction.returnResponse(responseModel),
                HttpStatus.OK);

        } catch (Exception e) {
            responseModel.setResponseCode(GlobalConstant.INTERNAL_ERROR_CODE);
            responseModel.setResponseMessage(e.getMessage());
            responseModel.setResponseDescription(e.getMessage());
            e.printStackTrace();
            
            return new ResponseEntity<String>(
                GlobalFunction.returnResponse(responseModel),
                HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<String> add(ProductCategoryModel model){
        ResponseModel responseModel = new ResponseModel();

        try {
            ArrayList<String> error = new ArrayList<String>();
            if (model.getProductCategoryName()==null){
                error.add("Product Category Name is Required");
            } 
            else if (repo.countNameUsed(model.getProductCategoryName())!=0){
                error.add("Product Category Name is Used");
            }
            if (model.getCreatedBy()==null||model.getCreatedBy()<=0){
                error.add("Created By is Required and greater than 0");
            }

            if (error.size()>0){
                responseModel.setResponseCode(GlobalConstant.EXTERNAL_ERROR_CODE);
				responseModel.setResponseMessage("User input wrong value...");
				responseModel.setResponseDescription(error.toString());
                
                return new ResponseEntity<String>(
                    GlobalFunction.returnResponse(responseModel),
                    HttpStatus.BAD_REQUEST);
            }

            ProductCategoryEntity entity = new ProductCategoryEntity();

            Long count = repo.count();
            int currentId = Long.valueOf(count).intValue() + 1;
            String productId = "PC" + String.format("%04d", currentId);

            entity.setProductCategoryId(productId);
            entity.setProductCategoryName(model.getProductCategoryName());
            entity.setCreatedBy(model.getCreatedBy());
            entity.setCreatedDate(new Timestamp(new Date().getTime()));
            entity.setRecStatus(GlobalConstant.REC_STATUS_ACTIVE);

            entity = repo.save(entity);

			responseModel.setResponseCode("00");
			responseModel.setResponseMessage("Successfully Create New Product Category");
			responseModel.setResponseData(entity);

            return new ResponseEntity<String>(
                GlobalFunction.returnResponse(responseModel),
                HttpStatus.OK);

        } catch (Exception e) {
            responseModel.setResponseCode(GlobalConstant.INTERNAL_ERROR_CODE);
            responseModel.setResponseMessage(e.getMessage());
            responseModel.setResponseDescription(e.getMessage());
            e.printStackTrace();

            return new ResponseEntity<String>(
                GlobalFunction.returnResponse(responseModel),
                HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> update(ProductCategoryModel model){
        ResponseModel responseModel = new ResponseModel();

        try {
            ArrayList<String> error = new ArrayList<String>();
            if (model.getProductCategoryId()==null){
                error.add("Product Category Id is Required");
            } else if (repo.countIdUsed(model.getProductCategoryId())<=0){
                error.add("Product Category Id not Found");
            }
            if (model.getProductCategoryName()==null){
                error.add("Product Category Name is Required");
            } else if (repo.countNameUsedExclude(model.getProductCategoryName(),model.getProductCategoryId())!=0){
                error.add("Product Category Name is Used");
            }
            if (model.getUpdatedBy()==null||model.getUpdatedBy()<=0){
                error.add("Updated By is Required and Greater than 0");
            } 

            if (error.size()>0){
                responseModel.setResponseCode(GlobalConstant.EXTERNAL_ERROR_CODE);
				responseModel.setResponseMessage("User input wrong value...");
				responseModel.setResponseDescription(error.toString());
                
                return new ResponseEntity<String>(
                    GlobalFunction.returnResponse(responseModel),
                    HttpStatus.BAD_REQUEST);
            }

            ProductCategoryEntity entity = new ProductCategoryEntity();
            entity.setProductCategoryId(model.getProductCategoryId());
            entity.setProductCategoryName(model.getProductCategoryName());
            entity.setUpdatedBy(model.getUpdatedBy());
            entity.setUpdatedDate(new java.sql.Date(new Date().getTime()));

            entity = repo.save(entity);

			responseModel.setResponseCode("00");
			responseModel.setResponseMessage("Successfully Update Product Category " + entity.getProductCategoryId());
			responseModel.setResponseData(entity);

            return new ResponseEntity<String>(
                GlobalFunction.returnResponse(responseModel),
                HttpStatus.OK);

        } catch (Exception e) {
            responseModel.setResponseCode(GlobalConstant.INTERNAL_ERROR_CODE);
            responseModel.setResponseMessage(e.getMessage());
            responseModel.setResponseDescription(e.getMessage());
            
            return new ResponseEntity<String>(
                GlobalFunction.returnResponse(responseModel),
                HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> delete(ProductCategoryModel model){
        ResponseModel responseModel = new ResponseModel();

        try {
            ArrayList<String> error = new ArrayList<String>();
            if (model.getProductCategoryId()==null){
                error.add("Product Category Id is Required");
            }else if (repo.countIdUsed(model.getProductCategoryId())<=0){
                error.add("Product Category Id not Found");
            }

            if (error.size()>0){
                responseModel.setResponseCode(GlobalConstant.EXTERNAL_ERROR_CODE);
				responseModel.setResponseMessage("User input wrong value...");
				responseModel.setResponseDescription(error.toString());
                
                return new ResponseEntity<String>(
                    GlobalFunction.returnResponse(responseModel),
                    HttpStatus.BAD_REQUEST);
            }

            ProductCategoryEntity entity = new ProductCategoryEntity();
            entity.setProductCategoryId(model.getProductCategoryId());
            entity.setDeletedDate(new java.sql.Date(new Date().getTime()));
            entity.setRecStatus(GlobalConstant.REC_STATUS_NON_ACTIVE);

            entity = repo.save(entity);

			responseModel.setResponseCode("00");
			responseModel.setResponseMessage("Successfully Delete Product Category " + model.getProductCategoryId());
            
            return new ResponseEntity<String>(
                GlobalFunction.returnResponse(responseModel),
                HttpStatus.OK);


        } catch (Exception e) {
            responseModel.setResponseCode(GlobalConstant.INTERNAL_ERROR_CODE);
            responseModel.setResponseMessage(e.getMessage());
            responseModel.setResponseDescription(e.getMessage());
            e.printStackTrace();
            
            return new ResponseEntity<String>(
                GlobalFunction.returnResponse(responseModel),
                HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> search(ProductCategoryModel model){
        ResponseModel responseModel = new ResponseModel();

        try {
           List<ProductCategoryEntity> listEntity = new ArrayList<ProductCategoryEntity>();
           listEntity = repo.findAll(ProductCategoryRepo.specification(model));

           if (listEntity == null) throw new Exception("Product Category not found") ;
			
           responseModel.setResponseCode("00");
           responseModel.setResponseMessage("Successfully Search Product Category ");
           responseModel.setResponseData(listEntity);
           
           return new ResponseEntity<String>(
                GlobalFunction.returnResponse(responseModel),
                HttpStatus.OK);

        } catch (Exception e) {
            responseModel.setResponseCode(GlobalConstant.INTERNAL_ERROR_CODE);
            responseModel.setResponseMessage(e.getMessage());
            responseModel.setResponseDescription(e.getMessage());
            e.printStackTrace();
            
            return new ResponseEntity<String>(
                GlobalFunction.returnResponse(responseModel),
                HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // public String searchProduct(ProductCategoryModel model) {
    //     ResponseModel responseModel = new ResponseModel();

    //     try {
    //        List<ProductEntity> listEntity = new ArrayList<ProductEntity>();
    //        listEntity = repo.searchProduct(model.getProductCategoryId());

    //        if (listEntity == null) throw new Exception("Product Category not found") ;
			
    //        responseModel.setResponseCode("00");
    //        responseModel.setResponseMessage("Successfully Search Product Category ");
    //        responseModel.setResponseData(listEntity);

    //     } catch (Exception e) {
    //         responseModel.setResponseCode(GlobalConstant.INTERNAL_ERROR_CODE);
    //         responseModel.setResponseMessage(e.getMessage());
    //         responseModel.setResponseDescription(e.getMessage());
    //         e.printStackTrace();
    //     }

    //     return GlobalFunction.returnResponse(responseModel);
    // }
}
