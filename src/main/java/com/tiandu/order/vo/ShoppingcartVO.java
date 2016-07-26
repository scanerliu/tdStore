package com.tiandu.order.vo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import com.tiandu.custom.entity.TdAgent;
import com.tiandu.custom.entity.TdBrancheCompany;
import com.tiandu.order.entity.TdShoppingcartItem;
import com.tiandu.product.entity.TdAgentProduct;

public class ShoppingcartVO {
	/**
	 * 商品个数
	 */
	private Integer totalcount = 0;
	/**
	 * 总金额
	 */
	private BigDecimal totalAmount = BigDecimal.ZERO;
	/**
	 * 总运费
	 */
	private BigDecimal totalPostage = BigDecimal.ZERO;
	/**
	 * 总商品金额
	 */
	private BigDecimal totalProductAmount = BigDecimal.ZERO;
	/**
	 * 普通商品可以积分抵扣总金额
	 */
	private BigDecimal totalCommonPointAmount = BigDecimal.ZERO;
	/**
	 * 部分积分兑换商品可积分抵扣总金额
	 */
	private BigDecimal totalPartPointAmount = BigDecimal.ZERO;
	/**
	 * 抵扣可以使用积分总数
	 */
	private Integer totalPointsUsed = 0;
	/**
	 * 可以使用积分抵扣金额
	 */
	private BigDecimal totalPointAmount = BigDecimal.ZERO;
	
	/**
	 * 是否可以使用钱包余额支付
	 */
	private Boolean canUserAccount = false;
	
	private List<TdShoppingcartItem> itemList;
	
	/**
	 * 代理商
	 */
	private TdAgent agent;
	/**
	 * 分公司
	 */
	private TdBrancheCompany branch;
	
	/**
	 * 代理产品
	 */
	private TdAgentProduct agentProduct;
	
	/**
	 * 购买类型1-普通商品，2-单代产品，3-供应商，4-图片订单
	 */
	private Integer ptype;
	/**
	 * 是否组合订单（如果包含多个供应商，是组合订单），用作拆单操作标记
	 */
	private Boolean combiningOrder = false;
	/**
	 * 供应商id
	 */
	private Integer supplierId;
	/**
	 * 供应商ids
	 */
	private Set<Integer> supplierIds;
	
    /**
     * 获得赠送总积分
     */
    private Integer gainPoints = 0;
	
	public Integer getTotalcount() {
		return totalcount;
	}

	public void setTotalcount(Integer totalcount) {
		this.totalcount = totalcount;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	public BigDecimal getTotalPostage() {
		return totalPostage;
	}
	
	public void setTotalPostage(BigDecimal totalPostage) {
		this.totalPostage = totalPostage;
	}
	
	public Integer getTotalPointsUsed() {
		return totalPointsUsed;
	}

	public void setTotalPointsUsed(Integer totalPointsUsed) {
		this.totalPointsUsed = totalPointsUsed;
	}

	public BigDecimal getTotalPointAmount() {
		return totalPointAmount;
	}

	public void setTotalPointAmount(BigDecimal totalPointAmount) {
		this.totalPointAmount = totalPointAmount;
	}

	public BigDecimal getTotalCommonPointAmount() {
		return totalCommonPointAmount;
	}

	public void setTotalCommonPointAmount(BigDecimal totalCommonPointAmount) {
		this.totalCommonPointAmount = totalCommonPointAmount;
	}

	public BigDecimal getTotalPartPointAmount() {
		return totalPartPointAmount;
	}

	public void setTotalPartPointAmount(BigDecimal totalPartPointAmount) {
		this.totalPartPointAmount = totalPartPointAmount;
	}

	public BigDecimal getTotalProductAmount() {
		return totalProductAmount;
	}

	public void setTotalProductAmount(BigDecimal totalProductAmount) {
		this.totalProductAmount = totalProductAmount;
	}

	public Boolean getCombiningOrder() {
		return combiningOrder;
	}

	public void setCombiningOrder(Boolean combiningOrder) {
		this.combiningOrder = combiningOrder;
	}

	public List<TdShoppingcartItem> getItemList() {
		return itemList;
	}
	
	public void setItemList(List<TdShoppingcartItem> itemList) {
		this.itemList = itemList;
	}

	public Integer getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	public Set<Integer> getSupplierIds() {
		return supplierIds;
	}

	public void setSupplierIds(Set<Integer> supplierIds) {
		this.supplierIds = supplierIds;
	}

	public Boolean getCanUserAccount() {
		return canUserAccount;
	}

	public void setCanUserAccount(Boolean canUserAccount) {
		this.canUserAccount = canUserAccount;
	}

	public Integer getGainPoints() {
		return gainPoints;
	}

	public void setGainPoints(Integer gainPoints) {
		this.gainPoints = gainPoints;
	}

	public TdAgent getAgent() {
		return agent;
	}

	public void setAgent(TdAgent agent) {
		this.agent = agent;
	}

	public TdBrancheCompany getBranch() {
		return branch;
	}

	public void setBranch(TdBrancheCompany branch) {
		this.branch = branch;
	}

	public Integer getPtype() {
		return ptype;
	}

	public void setPtype(Integer ptype) {
		this.ptype = ptype;
	}

	public TdAgentProduct getAgentProduct() {
		return agentProduct;
	}

	public void setAgentProduct(TdAgentProduct agentProduct) {
		this.agentProduct = agentProduct;
	}
	
	
}
