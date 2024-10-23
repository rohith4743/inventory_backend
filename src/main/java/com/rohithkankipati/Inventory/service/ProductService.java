package com.rohithkankipati.Inventory.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.rohithkankipati.Inventory.dto.PaginatedResponseDTO;
import com.rohithkankipati.Inventory.dto.ProductCardDTO;
import com.rohithkankipati.Inventory.entity.Product;
import com.rohithkankipati.Inventory.entity.ProductIndex;
import com.rohithkankipati.Inventory.repository.ProductIndexRepository;
import com.rohithkankipati.Inventory.repository.ProductRepository;

import io.jsonwebtoken.lang.Arrays;

@Service
public class ProductService {
	
	@Autowired
    private ProductRepository productRepository;
	
	@Autowired
    private ProductIndexRepository productIndexRepository;
	

	public PaginatedResponseDTO getProducts(String keyword, String category, String subcategory, Double minPrice,
            Double maxPrice, Double minABV, Double maxABV, String size, 
            String packSize, String regionOrigin, Pageable pageable) {

	    List<ProductCardDTO> result = new ArrayList<>();
	    Page<Product> filteredProducts;
	    
	    if (keyword != null && !keyword.isEmpty()) {
	        // Search using Elasticsearch
	        List<ProductIndex> productIndices = productIndexRepository.searchByKeyword(keyword);
	
	        // Fetch product details from the database using the IDs from Elasticsearch
	        List<Integer> productIds = productIndices.stream().map(ProductIndex::getId).collect(Collectors.toList());
	
	        // If productIds are empty, return an empty list with pagination metadata
	        if (productIds.isEmpty()) {
	            return new PaginatedResponseDTO(pageable.getPageNumber(), pageable.getPageSize(), 0, result);
	        }
	
	        filteredProducts = productRepository.searchProductsWithFilters(
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
	    } else {
	        // Search using PostgreSQL filtering directly
	        filteredProducts = productRepository.searchProducts(
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
	    }
	
	    // Map products to DTO
	    result = filteredProducts.stream()
	            .map(p -> new ProductCardDTO(p.getId(), p.getName(), p.getImageUrl(), p.getPrice()))
	            .collect(Collectors.toList());
	
	    // Create paginated response with page info
	    return new PaginatedResponseDTO(
	        pageable.getPageNumber(),
	        pageable.getPageSize(),
	        filteredProducts.getTotalElements(),
	        result
	    );
	}
	
	
	public Map<String, Object> getProductById(Integer id) {
        // Fetch product by ID
        Optional<Product> optionalProduct = productRepository.findById(id);

        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();

            // Fetch all products with the same name
            List<Product> productsWithSameName = productRepository.findByName(product.getName());

            // Prepare response map
            Map<String, Object> response = mapToProductResponse(productsWithSameName);

            return response;
        }

        return new HashMap<>();
    }

    private Map<String, Object> mapToProductResponse(List<Product> products) {
        // Assuming all products have the same name, brand, etc., based on the first product
        Product firstProduct = products.get(0);

        // Extract pack sizes, prices, and related details
        List<String> sizes = new ArrayList<>();
        List<Double> prices = new ArrayList<>();
        List<Boolean> onSale = new ArrayList<>();
        List<Double> salePrices = new ArrayList<>();

        for (Product product : products) {
            sizes.add(product.getPackSize());
            prices.add(product.getPrice());
            onSale.add(false); // Default to false for each product
            salePrices.add(0.0); // Default to 0 for each product
        }

        // Create the response map
        Map<String, Object> response = new HashMap<>();
        response.put("id", firstProduct.getId());
        response.put("name", firstProduct.getName());
        response.put("description", firstProduct.getDescription());
        response.put("tags", Arrays.asList(firstProduct.getTags().split(","))); // Split tags by comma
        response.put("category", firstProduct.getCategory());
        response.put("subCategory", firstProduct.getSubcategory());
        response.put("brand", firstProduct.getBrand());
        response.put("imageUrl", "assets/whiskey-b.jpeg"); // Default image URL
        response.put("abv", firstProduct.getAbv());
        response.put("reviews", new ArrayList<>()); // Empty reviews list
        response.put("rating", 0.0); // Default rating to 0
        response.put("coupons", new ArrayList<>()); // Empty coupons list
        response.put("ids", products.stream().map(Product::getId).toList()); // Collect all product IDs
        response.put("sizes", sizes);
        response.put("prices", prices);
        response.put("onSale", onSale);
        response.put("salePrices", salePrices);

        return response;
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
