package com.training.api.service;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.training.api.entity.ProductEntity;
import com.training.api.entity.ProductEntityInfo;
import com.training.api.global.GlobalConstant;
import com.training.api.global.GlobalFunction;
import com.training.api.model.ProductModel;
import com.training.api.model.ResponseModel;
import com.training.api.repository.ProductCategoryRepo;
import com.training.api.repository.ProductInfoRepo;
import com.training.api.repository.ProductRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ProductService implements Serializable {
    
    @Autowired
    ProductRepo repo;

    @Autowired
    ProductCategoryRepo catRepo;

    @Autowired
    ProductInfoRepo infoRepo;

    @Autowired
    ProductTransactionService tranService;

    public ResponseEntity<String> getAll(){
        ResponseModel responseModel = new ResponseModel();

        try {
            List<ProductEntity> listP = new ArrayList<ProductEntity>();
            repo.findAll().forEach(listP::add);;

            responseModel.setResponseCode(GlobalConstant.SUCCESS_CODE);
            responseModel.setResponseMessage("Success");
            responseModel.setResponseData(listP);

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

    public ResponseEntity<String> add(ProductModel model){
        ResponseModel responseModel = new ResponseModel();

        try {
            ArrayList<String> error = new ArrayList<String>();
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

            if (error.size()>0){
                responseModel.setResponseCode(GlobalConstant.EXTERNAL_ERROR_CODE);
				responseModel.setResponseMessage("User input wrong value...");
				responseModel.setResponseDescription(error.toString());
                
                return new ResponseEntity<String>(
                GlobalFunction.returnResponse(responseModel),
                HttpStatus.BAD_REQUEST);
            }

            ProductEntity entity = new ProductEntity();
            entity.setProductName(model.getProductName());
            entity.setProductCategoryId(model.getProductCategoryId());
            entity.setCreatedBy(model.getCreatedBy());
            entity.setCreatedDate(new Timestamp(new Date().getTime()));
            entity.setRecStatus(GlobalConstant.REC_STATUS_ACTIVE);

            entity = repo.save(entity);

			responseModel.setResponseCode("00");
			responseModel.setResponseMessage("Successfully Create New Product");
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

    public ResponseEntity<String> update(ProductModel model){
        ResponseModel responseModel = new ResponseModel();

        try {
            ArrayList<String> error = new ArrayList<String>();
            if (model.getProductId()==null){
                error.add("Product Id is Required");
            } else if (repo.countIdUsed(model.getProductId())<=0){
                error.add("Product Id not Found");
            }
            if (model.getProductName().isEmpty()||model.getProductName()==null){
                error.add("Product Name is Required");
            } else if (repo.countNameUsed(model.getProductName())!=0){
                error.add("Product Name is Used");
            }
            if (model.getProductCategoryId().isEmpty()||model.getProductCategoryId()==null){
                error.add("Product Category Id is Required");
            } else if (catRepo.countIdUsed(model.getProductCategoryId())<=0){
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

            ProductEntity entity = new ProductEntity();
            entity.setProductId(model.getProductId());
            entity.setProductName(model.getProductName());
            entity.setProductCategoryId(model.getProductCategoryId());
            entity.setUpdatedBy(model.getUpdatedBy());
            entity.setUpdatedDate(new java.sql.Date(new Date().getTime()));

            entity = repo.save(entity);

			responseModel.setResponseCode("00");
			responseModel.setResponseMessage("Successfully Update Product");
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

    public ResponseEntity<String> delete(ProductModel model){
        ResponseModel responseModel = new ResponseModel();

        try {
            ArrayList<String> error = new ArrayList<String>();
            if (model.getProductId()==null){
                error.add("Product Id is Required");
            } else if (repo.countIdUsed(model.getProductId())<=0){
                error.add("Product Id not Found");
            }

            if (error.size()>0){
                responseModel.setResponseCode(GlobalConstant.EXTERNAL_ERROR_CODE);
				responseModel.setResponseMessage("User input wrong value...");
				responseModel.setResponseDescription(error.toString());
                
                return new ResponseEntity<String>(
                GlobalFunction.returnResponse(responseModel),
                HttpStatus.BAD_REQUEST);
            }
            
            ProductEntity entity = new ProductEntity();
            entity.setProductCategoryId(model.getProductCategoryId());
            entity.setDeletedDate(new java.sql.Date(new Date().getTime()));
            entity.setRecStatus(GlobalConstant.REC_STATUS_NON_ACTIVE);

            entity = repo.save(entity);

			responseModel.setResponseCode("00");
			responseModel.setResponseMessage("Successfully Delete Product");

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

    public ResponseEntity<String> searchByCategory(ProductModel model) {
        ResponseModel responseModel = new ResponseModel();

        try {
           List<ProductEntityInfo> listEntity = new ArrayList<ProductEntityInfo>();
           listEntity = infoRepo.findByCategory(model.getProductCategoryId());

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

    public ResponseEntity<String> searchByCategory2(ProductModel model) {
        ResponseModel responseModel = new ResponseModel();

        try {
           List<ProductEntityInfo> listEntity = new ArrayList<ProductEntityInfo>();
           listEntity = infoRepo.findByCategory2(model.getProductCategoryId());

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
    
    public ResponseEntity<String> getById(ProductModel model) {
        ResponseModel responseModel = new ResponseModel();

        try {
            ProductEntityInfo entity = new ProductEntityInfo();
            ArrayList<String> error = new ArrayList<String>();

            if (model.getProductId()==null){
                error.add("Product Id is Required");
            } 
            if (error.size()>0){
                responseModel.setResponseCode(GlobalConstant.EXTERNAL_ERROR_CODE);
				responseModel.setResponseMessage("User input wrong value...");
				responseModel.setResponseDescription(error.toString());
                
                return new ResponseEntity<String>(
                GlobalFunction.returnResponse(responseModel),
                HttpStatus.BAD_REQUEST);
            }
            
            entity = infoRepo.findProductById(model.getProductId());
            if (entity==null) throw new Exception("Product Not Found");

            responseModel.setResponseCode(GlobalConstant.SUCCESS_CODE);
            responseModel.setResponseMessage("Successfully Pick Product");
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

    public ResponseEntity<String> addBatch(List<ProductModel> list) {
        ResponseModel responseModel = new ResponseModel();

        try {
            List<ProductEntity> entities = new ArrayList<ProductEntity>();

            entities = tranService.save(list);

			responseModel.setResponseCode("00");
			responseModel.setResponseMessage("Successfully Create New Product");
			responseModel.setResponseData(entities);

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
}
