package com.rohithkankipati.Inventory.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "products")
public class Product implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", length = 255, nullable = true)
    private String name;

    @Column(name = "qty", length = 50, nullable = true)
    private String qty;

    @Column(name = "pack_size", length = 100, nullable = true)
    private String packSize;

    @Column(name = "description", columnDefinition = "text", nullable = true)
    private String description;

    @Column(name = "category", length = 100, nullable = true)
    private String category;

    @Column(name = "subcategory", length = 100, nullable = true)
    private String subcategory;

    @Column(name = "price", nullable = true)
    private Double price;

    @Column(name = "region_origin", columnDefinition = "text", nullable = true)
    private String regionOrigin;

    @Column(name = "brand", length = 100, nullable = true)
    private String brand;

    @Column(name = "image_url", columnDefinition = "text", nullable = true)
    private String imageUrl;

    @Column(name = "abv", nullable = true)
    private Double abv;

    @Column(name = "tags", columnDefinition = "text", nullable = true)
    private String tags;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getQty() {
		return qty;
	}

	public void setQty(String qty) {
		this.qty = qty;
	}

	public String getPackSize() {
		return packSize;
	}

	public void setPackSize(String packSize) {
		this.packSize = packSize;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSubcategory() {
		return subcategory;
	}

	public void setSubcategory(String subcategory) {
		this.subcategory = subcategory;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getRegionOrigin() {
		return regionOrigin;
	}

	public void setRegionOrigin(String regionOrigin) {
		this.regionOrigin = regionOrigin;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Double getAbv() {
		return abv;
	}

	public void setAbv(Double abv) {
		this.abv = abv;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
    
    

}
