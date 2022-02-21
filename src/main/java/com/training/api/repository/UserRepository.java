package com.training.api.repository;

import com.training.api.entity.UserEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {
    
    UserEntity findByUserId(Long id);

    @Query(value = "SELECT COUNT (*) FROM ms_user "
                + "WHERE LOWER(user_name) = LOWER(?1) "
                + "AND rec_status = 'A'"
                , nativeQuery = true)
    Integer countNameUsed(String userName);

    @Query(value = "SELECT COUNT (*) FROM ms_user "
                + "WHERE LOWER(user_name) = LOWER(?1) "
                + "AND rec_status = 'A' "
                + "AND user_id != ?2"
                , nativeQuery = true)
    Integer countNameUsedExclude(String userName, Long id);

    @Query(value = "SELECT COUNT (*) FROM ms_user "
                + "WHERE user_id = ?1 "
                + "AND rec_status = 'A' "
                , nativeQuery = true)
    Integer countIdUsed(Long userId);
}
