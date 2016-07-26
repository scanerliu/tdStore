package com.tiandu.custom.search;

import com.tiandu.common.search.SearchCriteria;

public class TdUserCampaignCriteria extends SearchCriteria {

	private Integer regionId; // 参与查询的地区ID

	private Integer provinceId; // 省id
	private Integer cityId; // 市id
	private Integer region; // 区id
	private Integer uid;

	private Integer cid; // 活动Id

	public Integer getRegionId() {
		return regionId;
	}

	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}

	public Integer getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public Integer getRegion() {
		return region;
	}

	public void setRegion(Integer region) {
		this.region = region;
	}

	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

}
