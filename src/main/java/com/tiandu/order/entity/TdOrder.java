package com.tiandu.order.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.tiandu.common.entity.TdBaseEntity;
import com.tiandu.common.utils.ConstantsUtils;
import com.tiandu.custom.entity.TdUser;

public class TdOrder extends TdBaseEntity{
	
    private Integer orderId;

    private String orderNo;

    private Byte orderType;

    private Integer supplierId;

    private Integer userId;

    private Integer itemNum;

    private BigDecimal totalAmount;

    private Integer usedPoint;

    private BigDecimal pointAmount;

    private BigDecimal payAmount;

    private BigDecimal postage;

    private BigDecimal productAmount;
    
    private BigDecimal refundAmount;

    private Byte paymentId;

    private String userMessage;

    private Byte orderStatus;

    private Byte payStatus;

    private Byte shipmentStatus;

    private Date createTime;

    private Date updateTime;

    private Integer updateBy;

    private Integer jointId;

    private Integer gainPoints;
    
    private Boolean  commented;
    /**
     * 购买人信息
     */
    private TdUser orderUser;
    /**
     * 供应商信息
     */
    private TdUser supplierUser;
    
    /**
     * 订单货品集合
     */
    private List<TdOrderSku> skuList;
    /**
     * 送货地址
     */
    private TdOrderAddress orderAddress;
    
    /**
     * 订单商品（供应商产品、产品包使用）
     */
    private List<TdOrderProduct> productList;
    

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public Byte getOrderType() {
        return orderType;
    }

    public void setOrderType(Byte orderType) {
        this.orderType = orderType;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getItemNum() {
        return itemNum;
    }

    public void setItemNum(Integer itemNum) {
        this.itemNum = itemNum;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getUsedPoint() {
        return usedPoint;
    }

    public void setUsedPoint(Integer usedPoint) {
        this.usedPoint = usedPoint;
    }

    public BigDecimal getPointAmount() {
        return pointAmount;
    }

    public void setPointAmount(BigDecimal pointAmount) {
        this.pointAmount = pointAmount;
    }

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    public BigDecimal getPostage() {
        return postage;
    }

    public void setPostage(BigDecimal postage) {
        this.postage = postage;
    }

    public BigDecimal getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(BigDecimal productAmount) {
        this.productAmount = productAmount;
    }

    public BigDecimal getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(BigDecimal refundAmount) {
		this.refundAmount = refundAmount;
	}

	public Byte getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Byte paymentId) {
        this.paymentId = paymentId;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage == null ? null : userMessage.trim();
    }

    public Byte getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Byte orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Byte getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Byte payStatus) {
        this.payStatus = payStatus;
    }

    public Byte getShipmentStatus() {
        return shipmentStatus;
    }

    public void setShipmentStatus(Byte shipmentStatus) {
        this.shipmentStatus = shipmentStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Integer updateBy) {
        this.updateBy = updateBy;
    }

    public Integer getJointId() {
        return jointId;
    }

    public void setJointId(Integer jointId) {
        this.jointId = jointId;
    }

    public Integer getGainPoints() {
        return gainPoints;
    }

    public void setGainPoints(Integer gainPoints) {
        this.gainPoints = gainPoints;
    }
    
    public Boolean getCommented() {
		return commented;
	}

	public void setCommented(Boolean commented) {
		this.commented = commented;
	}

	public TdUser getOrderUser() {
		return orderUser;
	}

	public void setOrderUser(TdUser orderUser) {
		this.orderUser = orderUser;
	}

	public TdUser getSupplierUser() {
		return supplierUser;
	}

	public void setSupplierUser(TdUser supplierUser) {
		this.supplierUser = supplierUser;
	}

	public List<TdOrderSku> getSkuList() {
		return skuList;
	}

	public void setSkuList(List<TdOrderSku> skuList) {
		this.skuList = skuList;
	}

	public TdOrderAddress getOrderAddress() {
		return orderAddress;
	}

	public void setOrderAddress(TdOrderAddress orderAddress) {
		this.orderAddress = orderAddress;
	}

	public List<TdOrderProduct> getProductList() {
		return productList;
	}

	public void setProductList(List<TdOrderProduct> productList) {
		this.productList = productList;
	}

	/**
	 * 获取订单未支付金额
	 * @return
	 */
	public BigDecimal getUnPayAmount(){
		if(null!=this.getTotalAmount()&&null!=this.getPointAmount()&&null!=this.getPayAmount()){
			return this.getTotalAmount().subtract(this.getPayAmount()).subtract(this.getPointAmount());
		}
		return BigDecimal.ZERO;
	}
	/**
     * 获取订单状态文字说明
     * @return
     */
    public String getOrderStatusStr(){
    	StringBuffer sb = new StringBuffer();
    	if(null!=this.getOrderStatus()){
    		if(ConstantsUtils.ORDER_STATUS_NEW.equals(this.getOrderStatus())){
    			sb.append("新订单");
    		}else if(ConstantsUtils.ORDER_STATUS_CANCEL.equals(this.getOrderStatus())){
    			sb.append("已取消");
    		}else if(ConstantsUtils.ORDER_STATUS_COMPLETE.equals(this.getOrderStatus())){
    			sb.append("已完成");
    		}
    	}
    	return sb.toString();
    }
    /**
     * 获取订单支付状态文字说明
     * @return
     */
    public String getPayStatusStr(){
    	StringBuffer sb = new StringBuffer();
    	if(null!=this.getPayStatus()){
    		if(ConstantsUtils.ORDER_PAY_STATUS_UNPAY.equals(this.getPayStatus())){
    			sb.append("未支付");
    		}else if(ConstantsUtils.ORDER_PAY_STATUS_PAYED.equals(this.getPayStatus())){
    			sb.append("已支付");
    		}else if(ConstantsUtils.ORDER_PAY_STATUS_PART_REFUND.equals(this.getPayStatus())){
    			sb.append("部分退款");
    		}else if(ConstantsUtils.ORDER_PAY_STATUS_ALL_REFUND.equals(this.getPayStatus())){
    			sb.append("全额退款");
    		}
    	}
    	return sb.toString();
    }
    /**
     * 获取订单发货状态文字说明
     * @return
     */
    public String getShipmentStatusStr(){
    	StringBuffer sb = new StringBuffer();
    	if(null!=this.getShipmentStatus()){
    		if(ConstantsUtils.ORDER_SHIPMENT_STATUS_UNSHIPPED.equals(this.getShipmentStatus())){
    			sb.append("未发货");
    		}else if(ConstantsUtils.ORDER_SHIPMENT_STATUS_SHIPPED.equals(this.getShipmentStatus())){
    			sb.append("已发货");
    		}else if(ConstantsUtils.ORDER_SHIPMENT_STATUS_PART_RETURN.equals(this.getShipmentStatus())){
    			sb.append("部分退货");
    		}else if(ConstantsUtils.ORDER_SHIPMENT_STATUS_All_RETURN.equals(this.getShipmentStatus())){
    			sb.append("全部退货");
    		}
    	}
    	return sb.toString();
    }
    /**
     * 获取订单支付方式文字说明
     * @return
     */
    public String getPaymentStr(){
    	StringBuffer sb = new StringBuffer();
    	if(null!=this.getPaymentId()){
    		if(ConstantsUtils.ORDER_PAYMENT_ALIPAY.equals(this.getPaymentId())){
    			sb.append("支付宝");
    		}else if(ConstantsUtils.ORDER_PAYMENT_WEIXIN.equals(this.getPaymentId())){
    			sb.append("微信");
    		}else if(ConstantsUtils.ORDER_PAYMENT_UNIONPAY.equals(this.getPaymentId())){
    			sb.append("银联");
    		}
    	}
    	return sb.toString();
    }
}