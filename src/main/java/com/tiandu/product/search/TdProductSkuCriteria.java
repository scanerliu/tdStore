package com.tiandu.product.search;

import java.math.BigDecimal;

import com.tiandu.common.search.SearchCriteria;
import com.tiandu.product.entity.TdProduct;

public class TdProductSkuCriteria extends SearchCriteria {
	private Integer id;
	private String productName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

}
