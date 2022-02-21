package com.training.api.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import com.training.api.entity.ProductEntity;
import com.training.api.model.ProductModel;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepo extends JpaRepository<ProductEntity,Integer>,
    JpaSpecificationExecutor<ProductEntity> {
    
	@Query(value= "SELECT * FROM ms_product WHERE product_id = ?1"
	, nativeQuery = true)
	ProductEntity findProductById(Integer id);

    @Query(value= "SELECT COUNT (*) FROM ms_product "
				+ "WHERE LOWER(product_name) = LOWER(?1) "
				+ "AND rec_status != 'N' "
			, nativeQuery = true)
	Integer countNameUsed(String name);
	
    @Query(value= "SELECT COUNT (*) FROM ms_product "
				+ "WHERE LOWER(product_name) = LOWER(?1) "
				+ "AND product_id != ?2 "
				+ "AND rec_status != 'N' "
			, nativeQuery = true)
	Integer countNameUsedExclude(String name, Integer id);

    static Specification<ProductEntity> specification(
			ProductModel model){
		return (root, query, criteriaBuilder) -> {
			
			List <Predicate> predicates = new ArrayList<>();
			
			if (model.getProductCategoryId() != null && !model.getProductCategoryId().isEmpty() ) {
				predicates.add(criteriaBuilder.equal(root.get("productCategoryId"), model.getProductCategoryId()));
			}
			
			query.orderBy(criteriaBuilder.asc(root.get("productName")));
			
			return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
		};
	}
	@Query(value = ""
				+ "SELECT COUNT (*) FROM ms_product "
				+ "WHERE product_id = ?1 "
				, nativeQuery = true)
	Integer countIdUsed(Integer integer);
}
