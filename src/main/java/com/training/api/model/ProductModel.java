package com.training.api.model;

public class ProductModel extends InformationModel{
    Integer productId;
    String productName;
    String productCategoryId;


    public ProductModel() {
    }
    public Integer getProductId() {
        return productId;
    }
    public void setProductId(Integer productId) {
        this.productId = productId;
    }
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public String getProductCategoryId() {
        return productCategoryId;
    }
    public void setProductCategoryId(String productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    
}
