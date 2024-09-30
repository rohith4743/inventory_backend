package com.rohithkankipati.Inventory.dto;

public class ProductCardDTO {

	private Integer id;
    private String name;
    private String imageUrl;
    private Double price;
	
    public ProductCardDTO(Integer id, String name, String imageUrl, Double price) {
		super();
		this.id = id;
		this.name = name;
		this.imageUrl = imageUrl;
		this.price = price;
	}

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

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
    
}
