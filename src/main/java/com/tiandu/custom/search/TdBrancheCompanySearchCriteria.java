package com.tiandu.custom.search;

import com.tiandu.common.search.SearchCriteria;
import com.tiandu.district.entity.TdDistrict;

public class TdBrancheCompanySearchCriteria extends SearchCriteria {
	private Integer id;

	private String searchName;

	private Byte status;

	private Integer level;
	
	private Integer regionId;

	private String districtName;

	private boolean isAssociationUser;

	private boolean isAssociationDistrict;

	private boolean isAssociationUpdatePerson;

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

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public boolean isAssociationUser() {
		return isAssociationUser;
	}

	public void setAssociationUser(boolean isAssociationUser) {
		this.isAssociationUser = isAssociationUser;
	}

	public boolean isAssociationDistrict() {
		return isAssociationDistrict;
	}

	public void setAssociationDistrict(boolean isAssociationDistrict) {
		this.isAssociationDistrict = isAssociationDistrict;
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

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public Integer getRegionId() {
		return regionId;
	}

	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}

}
