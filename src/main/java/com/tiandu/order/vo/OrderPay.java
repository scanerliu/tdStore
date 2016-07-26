package com.tiandu.order.vo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单收款操作实体类
 * @author liuxinbing
 *
 */
public class OrderPay {

	private Integer orderId;	//订单id
	
	private BigDecimal payAmount;  //收款金额
	
	private Byte paymentId;  //收款方式 1-支付宝，2-微信支付，3-银联支付，4-钱包余额，5-后台手工
	
	private Date createTime;	//操作时间
	
	private Integer createBy;	//操作人
	
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	
	public BigDecimal getPayAmount() {
		return payAmount;
	}
	public void setPayAmount(BigDecimal payAmount) {
		this.payAmount = payAmount;
	}
	public Byte getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(Byte paymentId) {
		this.paymentId = paymentId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getCreateBy() {
		return createBy;
	}
	public void setCreateBy(Integer createBy) {
		this.createBy = createBy;
	}
	
	
}
