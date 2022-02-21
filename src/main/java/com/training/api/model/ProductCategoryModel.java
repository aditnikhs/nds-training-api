package com.training.api.model;

public class ProductCategoryModel extends InformationModel {
    String productCategoryId;
    String productCategoryName;
    
    public ProductCategoryModel() {
    }

    public String getProductCategoryId() {
        return productCategoryId;
    }
    public void setProductCategoryId(String productCategoryId) {
        this.productCategoryId = productCategoryId;
    }
    public String getProductCategoryName() {
        return productCategoryName;
    }
    public void setProductCategoryName(String productCategoryName) {
        this.productCategoryName = productCategoryName;
    }
}
