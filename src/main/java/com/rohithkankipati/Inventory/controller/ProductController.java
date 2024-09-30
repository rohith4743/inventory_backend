package com.rohithkankipati.Inventory.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rohithkankipati.Inventory.dto.ProductCardDTO;
import com.rohithkankipati.Inventory.service.ProductService;

@CrossOrigin
@RestController
@RequestMapping("/products")
public class ProductController {
	
	@Autowired
    private ProductService productService;
	
	@GetMapping
    public List<ProductCardDTO> getCombinedProducts(
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
            @RequestParam(defaultValue = "10") int sizePerPage) {

        Pageable pageable = PageRequest.of(page, sizePerPage);
        return productService.getProducts(keyword, category, subcategory, minPrice, maxPrice,
                minABV, maxABV, size, packSize, regionOrigin, pageable);
    }

//    @GetMapping("/all")
//    public List<Product> getAllProducts() {
//    	
//        return productService.getAllProducts();
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<Product> getProductById(@PathVariable Integer id) {
//        return productService.getProductById(id)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }
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
