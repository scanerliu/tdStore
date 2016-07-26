package com.tiandu.order.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.tiandu.common.utils.ConstantsUtils;

public class TdOrderProduct {
    private Integer id;

    private Integer orderId;

    private Byte itemType;

    private Integer itemId;

    private BigDecimal itemPrice;

    private Integer quantity;

    private String title;

    private String attachment;
    
    private Integer productTypeId;
    
    private Integer regionId;
    
    private Integer level;
    
    private String[] attachments;
    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Byte getItemType() {
        return itemType;
    }

    public void setItemType(Byte itemType) {
        this.itemType = itemType;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public BigDecimal getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(BigDecimal itemPrice) {
        this.itemPrice = itemPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment == null ? null : attachment.trim();
    }
    
    public void setAttachments(String[] attachments) {
		this.attachments = attachments;
	}

	public Integer getProductTypeId() {
		return productTypeId;
	}

	public void setProductTypeId(Integer productTypeId) {
		this.productTypeId = productTypeId;
	}

	public Integer getRegionId() {
		return regionId;
	}

	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	/**
     * 图片列表（图片订单展示时使用）
     */
    public String[] getAttachments(){
    	if(null!=this.attachments){
    		return this.attachments;
    	}else{
    		if(null!=this.getAttachment()){
    			attachments = this.getAttachment().split(":");
    		}
    	}
    	return attachments;
    }
    
    public String getItemTypeStr(){
    	StringBuffer sb = new StringBuffer();
    	if(null!=this.getItemType()){
    		if(ConstantsUtils.ORDER_PRODUCT_TYPE_AGENT.equals(this.getItemType())){
    			sb.append("代理产品");
    		}else if(ConstantsUtils.ORDER_PRODUCT_TYPE_PAKAGE.equals(this.getItemType())){
    			sb.append("商品包");
    		}else if(ConstantsUtils.ORDER_PRODUCT_TYPE_IMAGE.equals(this.getItemType())){
    			sb.append("图片处理");
    		}
    	}
    	return sb.toString();
    }
}