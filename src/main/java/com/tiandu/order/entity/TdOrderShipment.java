package com.tiandu.order.entity;

import java.util.Date;
import java.util.List;

public class TdOrderShipment {
    private Integer id;

    private Integer orderId;

    private Byte type;

    private String trackingNo;

    private Integer trackingId;

    private String returnReason;

    private Byte cargoStatus;

    private Byte status;

    private Date createTime;

    private Integer createBy;

    private Date updateTime;

    private Integer updateBy;
    /**
     * 发货，退货清单
     */
    private List<TdOrderShipmentItem> itemList;

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

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public String getTrackingNo() {
        return trackingNo;
    }

    public void setTrackingNo(String trackingNo) {
        this.trackingNo = trackingNo == null ? null : trackingNo.trim();
    }

    public Integer getTrackingId() {
        return trackingId;
    }

    public void setTrackingId(Integer trackingId) {
        this.trackingId = trackingId;
    }

    public String getReturnReason() {
        return returnReason;
    }

    public void setReturnReason(String returnReason) {
        this.returnReason = returnReason == null ? null : returnReason.trim();
    }

    public Byte getCargoStatus() {
        return cargoStatus;
    }

    public void setCargoStatus(Byte cargoStatus) {
        this.cargoStatus = cargoStatus;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
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

	public List<TdOrderShipmentItem> getItemList() {
		return itemList;
	}

	public void setItemList(List<TdOrderShipmentItem> itemList) {
		this.itemList = itemList;
	}
    
}