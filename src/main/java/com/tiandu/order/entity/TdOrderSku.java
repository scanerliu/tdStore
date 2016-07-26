package com.tiandu.order.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.tiandu.order.vo.SkuSpecialVO;

public class TdOrderSku {
    private Integer orderSkuId;

    private Integer orderId;

    private Integer productId;

    private Integer productSkuId;

    private Byte itemType;

    private String productName;

    private String productSkuCode;

    private String displaySpecifications;

    private Integer quantity;

    private BigDecimal price;
    
    private BigDecimal supplierPrice;

    private Integer backQuantity;
    
    private String productImage;
    
    /**
     * 属性键值对，显示时候使用
     */
    private List<SkuSpecialVO> specialList;

    public Integer getOrderSkuId() {
        return orderSkuId;
    }

    public void setOrderSkuId(Integer orderSkuId) {
        this.orderSkuId = orderSkuId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getProductSkuId() {
        return productSkuId;
    }

    public void setProductSkuId(Integer productSkuId) {
        this.productSkuId = productSkuId;
    }

    public Byte getItemType() {
        return itemType;
    }

    public void setItemType(Byte itemType) {
        this.itemType = itemType;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public String getProductSkuCode() {
        return productSkuCode;
    }

    public void setProductSkuCode(String productSkuCode) {
        this.productSkuCode = productSkuCode == null ? null : productSkuCode.trim();
    }

    public String getDisplaySpecifications() {
        return displaySpecifications;
    }

    public void setDisplaySpecifications(String displaySpecifications) {
        this.displaySpecifications = displaySpecifications == null ? null : displaySpecifications.trim();
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getSupplierPrice() {
		return supplierPrice;
	}

	public void setSupplierPrice(BigDecimal supplierPrice) {
		this.supplierPrice = supplierPrice;
	}

	public Integer getBackQuantity() {
        return backQuantity;
    }

    public void setBackQuantity(Integer backQuantity) {
        this.backQuantity = backQuantity;
    }

	public String getProductImage() {
		return productImage;
	}

	public void setProductImage(String produtImage) {
		this.productImage = produtImage;
	}

	public List<SkuSpecialVO> getSpecialList() {
		if(null!=this.specialList){
			return specialList;
		}
		List<SkuSpecialVO> slist = new ArrayList<SkuSpecialVO>();
		if(StringUtils.isNotEmpty(this.getDisplaySpecifications())){
			String opt = this.getDisplaySpecifications();
			JSONObject json;
			try {
				json = new JSONObject(opt);
				String[] keys = json.getNames(json);
				for(String key : keys){
					SkuSpecialVO special = new SkuSpecialVO();
					String val = (String) json.get(key);
					special.setSname(key);
					special.setSoption(val);
					slist.add(special);						
				}
				this.setSpecialList(slist);
			} catch (JSONException e) {
				
				e.printStackTrace();
			}
		}
		return specialList;
	}

	public void setSpecialList(List<SkuSpecialVO> specialList) {
		this.specialList = specialList;
	}
    
}