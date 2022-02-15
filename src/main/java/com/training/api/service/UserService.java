package com.training.api.service;

import com.training.api.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserService extends JpaRepository<User,Long>{
}
