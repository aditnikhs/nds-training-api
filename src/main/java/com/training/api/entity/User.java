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
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long user_id;

    @Column(name = "user_name")
    private String user_name;
    
    @Column(name = "created_date")
    private Timestamp created_date;
    
    @Column(name = "created_by")
    private int created_by;
    
    @Column(name = "updated_date")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date updated_date;
    
    @Column(name = "updated_by")
    private String updated_by;
    
    @Column(name = "deleted_date")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date deleted_date;

    @Column(name = "rec_status", length = 1)
    private String rec_status;

    public User(String user_name, Timestamp created_date, int created_by, Date updated_date,
            String updated_by, Date deleted_date, String rec_status) {
        this.user_name = user_name;
        this.created_date = created_date;
        this.created_by = created_by;
        this.updated_date = updated_date;
        this.updated_by = updated_by;
        this.deleted_date = deleted_date;
        this.rec_status = rec_status;
    }

    public User() {
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public Timestamp getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Timestamp created_date) {
        this.created_date = created_date;
    }

    public int getCreated_by() {
        return created_by;
    }

    public void setCreated_by(int created_by) {
        this.created_by = created_by;
    }

    public Date getUpdated_date() {
        return updated_date;
    }

    public void setUpdated_date(Date updated_date) {
        this.updated_date = updated_date;
    }

    public String getUpdated_by() {
        return updated_by;
    }

    public void setUpdated_by(String updated_by) {
        this.updated_by = updated_by;
    }

    public Date getDeleted_date() {
        return deleted_date;
    }

    public void setDeleted_date(Date deleted_date) {
        this.deleted_date = deleted_date;
    }

    public String getRec_status() {
        return rec_status;
    }

    public void setRec_status(String rec_status) {
        this.rec_status = rec_status;
    }

    // @Override
	// public String toString() {
	// 	return "User [id=" + id + ", user_name=" + user_name + ", created_date=" + created_date + "created_by=" + created_by + ", published=" + published + "]";
	// }
}
