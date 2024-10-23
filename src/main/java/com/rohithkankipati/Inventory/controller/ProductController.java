package com.rohithkankipati.Inventory.controller;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rohithkankipati.Inventory.dto.PaginatedResponseDTO;
import com.rohithkankipati.Inventory.service.ProductService;

@CrossOrigin
@RestController
@RequestMapping("/products")
public class ProductController {
	
	@Autowired
    private ProductService productService;
	
	private Pageable createPageRequest(int page, int sizePerPage, String sortBy) {
	    Sort sort;
	    
	    switch (sortBy.toLowerCase()) {
	        case "price_low_to_high":
	            sort = Sort.by(Sort.Direction.ASC, "price");
	            break;
	        case "price_high_to_low":
	            sort = Sort.by(Sort.Direction.DESC, "price");
	            break;
	        case "name":
	        default:
	            sort = Sort.by(Sort.Direction.ASC, "name");
	            break;
	    }
	    
	    return PageRequest.of(page, sizePerPage, sort);
	}

	
	@GetMapping
    public PaginatedResponseDTO getCombinedProducts(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String subcategory,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) Double minABV,
            @RequestParam(required = false) Double maxABV,
            @RequestParam(required = false) String size,
            @RequestParam(required = false) String packSize,
            @RequestParam(required = false) String regionOrigin,
            @RequestParam(required = false) Boolean onSale,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int sizePerPage,
            @RequestParam(defaultValue = "name") String sortBy) {

		Pageable pageable = createPageRequest(page, sizePerPage, sortBy);
        return productService.getProducts(keyword, category, subcategory, minPrice, maxPrice,
                minABV, maxABV, size, packSize, regionOrigin, pageable);
    }

//    @GetMapping("/all")
//    public List<Product> getAllProducts() {
//    	
//        return productService.getAllProducts();
//    }
//
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getProductById(@PathVariable Integer id) {
        
    	try {
    		
    		Map<String, Object> response = productService.getProductById(id);
    		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
        } catch (Exception e) {
        	
        	Map<String, Object> response = new HashMap<>();
        	response.put("error", e);
        	return new ResponseEntity<Map<String,Object>>(response, HttpStatus.BAD_REQUEST);
        	
        }
                
    }
//
//    @PostMapping
//    public Product addProduct(@RequestBody Product product) {
//        return productService.addProduct(product);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<Product> updateProduct(@PathVariable Integer id, @RequestBody Product productDetails) {
//        try {
//            return ResponseEntity.ok(productService.updateProduct(id, productDetails));
//        } catch (RuntimeException e) {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteProduct(@PathVariable Integer id) {
//        try {
//            productService.deleteProduct(id);
//            return ResponseEntity.noContent().build();
//        } catch (RuntimeException e) {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//    @GetMapping("/category/{category}")
//    public List<Product> getProductsByCategory(@PathVariable String category) {
//        return productService.getProductsByCategory(category);
//    }
//
//    @GetMapping("/brand/{brand}")
//    public List<Product> getProductsByBrand(@PathVariable String brand) {
//        return productService.getProductsByBrand(brand);
//    }
	

}
