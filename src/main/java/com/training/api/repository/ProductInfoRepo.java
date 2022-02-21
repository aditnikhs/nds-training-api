package com.training.api.repository;

import java.util.List;

import com.training.api.entity.ProductEntityInfo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductInfoRepo extends JpaRepository<ProductEntityInfo, Integer>,
    JpaSpecificationExecutor<ProductEntityInfo>{
    
    @Query(value = "SELECT p.*, pc.product_category_name, '' AS user_name "
                + "FROM ms_product p JOIN ms_product_category pc "
                + "ON p.product_category_id = pc.product_category_id "
                + "WHERE p.product_id = ?1 "
                , nativeQuery = true)
    ProductEntityInfo findProductById(Integer productId);

    @Query(value = "SELECT p.*, pc.product_category_name, '' AS user_name "
                + "FROM ms_product p JOIN ms_product_category pc "
                + "ON p.product_category_id = pc.product_category_id "
                + "WHERE p.product_category_id = ?1 "
                , nativeQuery = true)
    List<ProductEntityInfo> findByCategory(String productCategoryId);
    
    @Query(value = ""
			+ " SELECT p.*, pc.product_category_name,"
			+ "  COALESCE(c.user_name,'-') as user_name "
			+ " FROM MS_PRODUCT p "
			+ " JOIN MS_PRODUCT_CATEGORY pc"
			+ "  ON p.product_category_id = pc.product_category_id "
			+ " LEFT JOIN MS_USER c on p.created_by = c.user_id"
			+ " WHERE p.product_category_id = :prodCatId"
			+ " "
			, nativeQuery = true)
	List<ProductEntityInfo> findByCategory2(
			@Param("prodCatId") String productCategoryId);
}
