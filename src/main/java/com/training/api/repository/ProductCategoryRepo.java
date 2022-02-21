package com.training.api.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import com.training.api.entity.ProductCategoryEntity;
import com.training.api.model.ProductCategoryModel;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCategoryRepo extends JpaRepository<ProductCategoryEntity,String>,
    JpaSpecificationExecutor<ProductCategoryEntity> {

    static Specification<ProductCategoryEntity> specification(
			ProductCategoryModel model){
		return (root, query, criteriaBuilder) -> {
			
			List <Predicate> predicates = new ArrayList<>();
			
			if (model.getProductCategoryId() != null && !model.getProductCategoryId().isEmpty() ) {
				predicates.add(criteriaBuilder.equal(root.get("productCategoryId"), model.getProductCategoryId()));
			}
			
			if (model.getProductCategoryName() != null && !model.getProductCategoryName().isEmpty()) {
				predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("productCategoryName")), "%" + model.getProductCategoryName().toLowerCase() + "%"));
			}

			query.orderBy(criteriaBuilder.asc(root.get("productCategoryName")));
			
			return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
		};
	}

    @Query(value= "SELECT COUNT (*) FROM ms_product_category "
			+ "WHERE LOWER(product_category_name) = LOWER(?1) "
			+ "AND rec_status != 'N'"
			, nativeQuery = true)
	Integer countNameUsed(String name);
	
    @Query(value= "SELECT COUNT (*) FROM ms_product_category "
			+ "WHERE LOWER(product_category_name) = LOWER(?1) "
			+ "AND product_category_id != ?2 "
			+ "AND rec_status != 'N'"
			, nativeQuery = true)
	Integer countNameUsedExclude(String name, String id);

	@Query(value = ""
				+ "SELECT COUNT (*) FROM ms_product_category "
				+ "WHERE LOWER(product_category_id) = LOWER(?1)"
				, nativeQuery = true)
	Integer countIdUsed(String id);
}
