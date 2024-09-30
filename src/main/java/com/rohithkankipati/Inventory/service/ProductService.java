package com.rohithkankipati.Inventory.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.rohithkankipati.Inventory.dto.ProductCardDTO;
import com.rohithkankipati.Inventory.entity.Product;
import com.rohithkankipati.Inventory.entity.ProductIndex;
import com.rohithkankipati.Inventory.repository.ProductIndexRepository;
import com.rohithkankipati.Inventory.repository.ProductRepository;

@Service
public class ProductService {
	
	@Autowired
    private ProductRepository productRepository;
	
	@Autowired
    private ProductIndexRepository productIndexRepository;
	
	public List<ProductCardDTO> getProducts(String keyword, String category, String subcategory, Double minPrice,
            Double maxPrice, Double minABV, Double maxABV, String size, 
            String packSize, String regionOrigin, Pageable pageable) {

	    List<ProductCardDTO> result = new ArrayList<>();
	
	    if (keyword != null && !keyword.isEmpty()) {
	
	        // Search using Elasticsearch
	        List<ProductIndex> productIndices = productIndexRepository.searchByKeyword(keyword);
	
	        // Fetch product details from the database using the IDs from Elasticsearch
	        List<Integer> productIds = productIndices.stream().map(ProductIndex::getId).collect(Collectors.toList());	        
	
	        // If productIds are empty, return an empty list
	        if (productIds.isEmpty()) {
	            return result;
	        }
	
	        Page<Product> filteredProducts = productRepository.searchProductsWithFilters(
	                productIds, 
	                category != null ? category.toLowerCase() : null, 
	                subcategory != null ? subcategory.toLowerCase() : null, 
	                minPrice, 
	                maxPrice, 
	                minABV, 
	                maxABV, 
	                size != null ? size.toLowerCase() : null, 
	                packSize != null ? packSize.toLowerCase() : null, 
	                regionOrigin != null ? regionOrigin.toLowerCase() : null, 
	                pageable
	        );

	        
	        // Map products to DTO
	        result = filteredProducts.stream()
	                .map(p -> new ProductCardDTO(p.getId(), p.getName(), p.getImageUrl(), p.getPrice()))
	                .collect(Collectors.toList());
	
	    } else {
	
	        // Search using PostgreSQL filtering directly
	        Page<Product> productPage = productRepository.searchProducts(
	        		category != null ? category.toLowerCase() : null, 
	                subcategory != null ? subcategory.toLowerCase() : null, 
	                minPrice, 
	                maxPrice, 
	                minABV, 
	                maxABV, 
	                size != null ? size.toLowerCase() : null, 
	                packSize != null ? packSize.toLowerCase() : null, 
	                regionOrigin != null ? regionOrigin.toLowerCase() : null, 
	                pageable
	        );
	
	        // Convert to DTOs
	        result = productPage.stream()
	                .map(p -> new ProductCardDTO(p.getId(), p.getName(), p.getImageUrl(), p.getPrice()))
	                .collect(Collectors.toList());
	    }
	
	    return result;
	}


	
	
	
//	public List<Product> getAllProducts() {
//        return productRepository.findAll();
//    }
//
//    public Optional<Product> getProductById(Integer id) {
//        return productRepository.findById(id);
//    }
//
//    public Product addProduct(Product product) {
//        return productRepository.save(product);
//    }
//
//    public Product updateProduct(Integer id, Product productDetails) {
//        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
//        product.setName(productDetails.getName());
//        product.setQty(productDetails.getQty());
//        product.setPackSize(productDetails.getPackSize());
//        product.setDescription(productDetails.getDescription());
//        product.setCategory(productDetails.getCategory());
//        product.setSubcategory(productDetails.getSubcategory());
//        product.setPrice(productDetails.getPrice());
//        product.setRegionOrigin(productDetails.getRegionOrigin());
//        product.setBrand(productDetails.getBrand());
//        product.setImageUrl(productDetails.getImageUrl());
//        product.setAbv(productDetails.getAbv());
//        product.setTags(productDetails.getTags());
//        
//        return productRepository.save(product);
//    }
//
//    public void deleteProduct(Integer id) {
//        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
//        productRepository.delete(product);
//    }
//
//    public List<Product> getProductsByCategory(String category) {
//        return productRepository.findByCategory(category);
//    }
//
//    public List<Product> getProductsByBrand(String brand) {
//        return productRepository.findByBrand(brand);
//    }

}
