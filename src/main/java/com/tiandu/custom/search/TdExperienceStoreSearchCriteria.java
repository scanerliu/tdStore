package com.tiandu.custom.search;

import com.tiandu.common.search.SearchCriteria;

/**
 * @author Administrator
 *
 */
public class TdExperienceStoreSearchCriteria extends SearchCriteria {
	private Integer id;

	private Byte status;

	private boolean isAssociationUser;

	private boolean isAssociationUpdatePerson;

	private String searchName;
	
	private Integer regionId;
	
	private Integer provinceId;	//省id
	private Integer cityId;	//市id

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public boolean isAssociationUser() {
		return isAssociationUser;
	}

	public void setAssociationUser(boolean isAssociationUser) {
		this.isAssociationUser = isAssociationUser;
	}

	public boolean isAssociationUpdatePerson() {
		return isAssociationUpdatePerson;
	}

	public void setAssociationUpdatePerson(boolean isAssociationUpdatePerson) {
		this.isAssociationUpdatePerson = isAssociationUpdatePerson;
	}

	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

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

	
	

}
