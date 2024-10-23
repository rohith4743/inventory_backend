package com.rohithkankipati.Inventory.repository;

import java.util.List;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.rohithkankipati.Inventory.entity.ProductIndex;

public interface ProductIndexRepository extends ElasticsearchRepository<ProductIndex, Integer>{

	
	@Query("{"
		    + "\"bool\": {"
		    + "  \"should\": ["
		    + "    {\"match\": {\"name\": {\"query\": \"?0\", \"boost\": 5}}},"
		    + "    {\"match\": {\"brand\": {\"query\": \"?0\", \"boost\": 4}}},"
		    + "    {\"match\": {\"category\": {\"query\": \"?0\", \"boost\": 3}}},"
		    + "    {\"match\": {\"subcategory\": {\"query\": \"?0\", \"boost\": 2}}},"
		    + "    {\"match\": {\"tags\": {\"query\": \"?0\", \"boost\": 1}}}"
		    + "  ]"
		    + "}"
		    + "}")
		List<ProductIndex> searchByKeyword(String keyword);
}
