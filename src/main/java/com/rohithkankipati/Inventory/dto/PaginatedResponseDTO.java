package com.rohithkankipati.Inventory.dto;

import java.util.List;

public class PaginatedResponseDTO {

    private int page;
    private int perPage;
    private long total;
    private int totalPages;
    private List<ProductCardDTO> data;

    public PaginatedResponseDTO(int page, int perPage, long total, List<ProductCardDTO> data) {
        this.page = page;
        this.perPage = perPage;
        this.total = total;
        this.data = data;
        this.totalPages = (int) Math.ceil((double) total / perPage);
    }

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPerPage() {
		return perPage;
	}

	public void setPerPage(int perPage) {
		this.perPage = perPage;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public List<ProductCardDTO> getData() {
		return data;
	}

	public void setData(List<ProductCardDTO> data) {
		this.data = data;
	}

    
}
