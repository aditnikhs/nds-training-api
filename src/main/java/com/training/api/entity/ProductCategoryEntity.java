package com.training.api.entity;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "ms_product_category", schema = "public")
public class ProductCategoryEntity {
    
    @Id
    @Column(name = "product_category_id", length = 6)
    private String productCategoryId;

    @Column(name = "product_category_name")
    private String productCategoryName;

    @Column(name = "created_date")
    private Timestamp createdDate;
    
    @Column(name = "created_by")
    private Integer createdBy;
    
    @Column(name = "updated_date")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date updatedDate;
    
    @Column(name = "updated_by")
    private Integer updatedBy;
    
    @Column(name = "deleted_date")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date deletedDate;

    @Column(name = "rec_status", length = 1)
    private String recStatus;

    public ProductCategoryEntity() {
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Integer getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getDeletedDate() {
        return deletedDate;
    }

    public void setDeletedDate(Date deletedDate) {
        this.deletedDate = deletedDate;
    }

    public String getRecStatus() {
        return recStatus;
    }

    public void setRecStatus(String recStatus) {
        this.recStatus = recStatus;
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
