package com.rohithkankipati.Inventory.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.rohithkankipati.Inventory.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
	
	
	@Query("SELECT p FROM Product p WHERE "
		     + "(:category IS NULL OR LOWER(p.category) = :category) "
		     + "AND (:subcategory IS NULL OR LOWER(p.subcategory) LIKE cast(CONCAT('%', :subcategory, '%') as string)) "
		     + "AND (:minPrice IS NULL OR p.price >= :minPrice) "
		     + "AND (:maxPrice IS NULL OR p.price <= :maxPrice) "
		     + "AND (:minABV IS NULL OR p.abv >= :minABV) "
		     + "AND (:maxABV IS NULL OR p.abv <= :maxABV) "
		     + "AND (:size IS NULL OR LOWER(p.qty) = :size) "
		     + "AND (:packSize IS NULL OR LOWER(p.packSize) = :packSize) "
		     + "AND (:regionOrigin IS NULL OR LOWER(p.regionOrigin) LIKE cast(CONCAT('%', :regionOrigin, '%') as string))")
		Page<Product> searchProducts(String category, String subcategory, Double minPrice, Double maxPrice,
		                             Double minABV, Double maxABV, String size, String packSize, 
		                             String regionOrigin, Pageable pageable);

	
	@Query("SELECT p FROM Product p WHERE "
	        + "p.id IN :productIds "
	        + "AND (:category IS NULL OR LOWER(p.category) = :category) "
	        + "AND (:subcategory IS NULL OR LOWER(p.subcategory) LIKE cast(CONCAT('%', :subcategory, '%') as string)) "
		     + "AND (:minPrice IS NULL OR p.price >= :minPrice) "
		     + "AND (:maxPrice IS NULL OR p.price <= :maxPrice) "
		     + "AND (:minABV IS NULL OR p.abv >= :minABV) "
		     + "AND (:maxABV IS NULL OR p.abv <= :maxABV) "
		     + "AND (:size IS NULL OR LOWER(p.qty) = :size) "
		     + "AND (:packSize IS NULL OR LOWER(p.packSize) = :packSize) "
		     + "AND (:regionOrigin IS NULL OR LOWER(p.regionOrigin) LIKE cast(CONCAT('%', :regionOrigin, '%')as  string))")
	Page<Product> searchProductsWithFilters(List<Integer> productIds, String category, String subcategory, Double minPrice,
	                                        Double maxPrice, Double minABV, Double maxABV, String size, 
	                                        String packSize, String regionOrigin, Pageable pageable);
	


	
//	List<Product> findByCategory(String category);
//    List<Product> findByBrand(String brand);
//    List<Product> findByNameContaining(String name);

}
