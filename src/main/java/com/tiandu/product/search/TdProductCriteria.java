package com.tiandu.product.search;

import java.util.List;

import java.util.Date;

import com.tiandu.common.search.SearchCriteria;

public class TdProductCriteria extends SearchCriteria {

	private String name; // 商品名称
	private Byte status; // 商品状态，1-正常，2-待审核，3-审核不通过
	private Boolean onshelf; // 是否上架
	private Integer uid; // 供应商id
	private Byte kind; // 商品类型 1-普通商品，2-商品包，3-零元购，4-10元购，5-预售，6-秒杀商品'
	private Integer newRecommend; // 新品推荐
	private Integer hotRecommend; // 热门推荐
	private Integer fineRecommend; // 精品推荐
	private Integer typeRecommend; // 分类推荐
	private Boolean isNormalProduct = false;

	private Integer typeId;

	private List<Integer> productTypeIds;

	// 列表页排序 1-综合升序，2-综合降序，3-销量升序，4-销量降序，5-价格升序，6,-价格降序。
	private Integer orderby;

	// 开始时间
	private Date startTime;
	// 结束时间
	private Date endTime;
	
	private String keyword;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public Boolean getOnshelf() {
		return onshelf;
	}

	public void setOnshelf(Boolean onshelf) {
		this.onshelf = onshelf;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public Byte getKind() {
		return kind;
	}

	public void setKind(Byte kind) {
		this.kind = kind;
	}

	public Integer getNewRecommend() {
		return newRecommend;
	}

	public void setNewRecommend(Integer newRecommend) {
		this.newRecommend = newRecommend;
	}

	public Integer getHotRecommend() {
		return hotRecommend;
	}

	public void setHotRecommend(Integer hotRecommend) {
		this.hotRecommend = hotRecommend;
	}

	public Integer getFineRecommend() {
		return fineRecommend;
	}

	public void setFineRecommend(Integer fineRecommend) {
		this.fineRecommend = fineRecommend;
	}

	public Integer getTypeRecommend() {
		return typeRecommend;
	}

	public void setTypeRecommend(Integer typeRecommend) {
		this.typeRecommend = typeRecommend;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public Integer getOrderby() {
		return orderby;
	}

	public void setOrderby(Integer orderby) {
		this.orderby = orderby;
	}

	public List<Integer> getProductTypeIds() {
		return productTypeIds;
	}

	public void setProductTypeIds(List<Integer> productTypeIds) {
		this.productTypeIds = productTypeIds;
	}


	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	

	public Boolean getIsNormalProduct() {
		return isNormalProduct;
	}

	public void setIsNormalProduct(Boolean isNormalProduct) {
		this.isNormalProduct = isNormalProduct;
	}

	// 列表页排序 1-综合升序，2-综合降序，3-销量升序，4-销量降序，5-价格升序，6,-价格降序。
	public String getOrderBySql() {
		String orderBySql = null;

		Integer orderType = this.orderby;
		switch (orderType) {
		case 1:
			orderBySql = "s.positive_rate desc , s.buy_count desc";
			break;
		// case 2:
		// orderBySql = "s.positive_rate desc and s.buy_count desc";
		// break;
		case 2:
			orderBySql = "s.buy_count desc";
			break;
		// case 4:
		// orderBySql = "s.buy_count desc";
		// break;
		case 3:
			orderBySql = "p.price desc";
			break;
		case 4:
			orderBySql = "p.price asc";
			break;
		default:
			break;
		}
		return orderBySql;
	}

}
