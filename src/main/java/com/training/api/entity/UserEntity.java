package com.training.api.entity;

import java.sql.Timestamp;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "ms_user")
public class UserEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "user_name")
    private String userName;
    
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

    public UserEntity(String user_name, Timestamp created_date, int created_by, Date updated_date,
            Integer updated_by, Date deleted_date, String rec_status) {
        this.userName = user_name;
        this.createdDate = created_date;
        this.createdBy = created_by;
        this.updatedDate = updated_date;
        this.updatedBy = updated_by;
        this.deletedDate = deleted_date;
        this.recStatus = rec_status;
    }

    public UserEntity() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    
    // @Override
	// public String toString() {
	// 	return "User [id=" + id + ", user_name=" + user_name + ", created_date=" + created_date + "created_by=" + created_by + ", published=" + published + "]";
	// }
}
