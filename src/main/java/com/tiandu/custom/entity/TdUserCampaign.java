package com.tiandu.custom.entity;

import java.util.Date;

import com.tiandu.district.entity.TdDistrict;

public class TdUserCampaign {
    private Integer id;

    private Integer uid;

    private Integer cid;

    private Integer level;

    private Integer regionId;

    private String uavatar;

    private String uname;

    private String declaration;

    private Integer agentCount;

    private Integer enterpriseCount;

    private Date createTime;

    private Byte status;

    private String resume;
    
    // 用户
    private TdUser compaignUser;
    
    // 地区
    private TdDistrict district;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getRegionId() {
        return regionId;
    }

    public void setRegionId(Integer regionId) {
        this.regionId = regionId;
    }

    public String getUavatar() {
        return uavatar;
    }

    public void setUavatar(String uavatar) {
        this.uavatar = uavatar == null ? null : uavatar.trim();
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname == null ? null : uname.trim();
    }

    public String getDeclaration() {
        return declaration;
    }

    public void setDeclaration(String declaration) {
        this.declaration = declaration == null ? null : declaration.trim();
    }

    public Integer getAgentCount() {
        return agentCount;
    }

    public void setAgentCount(Integer agentCount) {
        this.agentCount = agentCount;
    }

    public Integer getEnterpriseCount() {
        return enterpriseCount;
    }

    public void setEnterpriseCount(Integer enterpriseCount) {
        this.enterpriseCount = enterpriseCount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume == null ? null : resume.trim();
    }

	public TdUser getCompaignUser() {
		return compaignUser;
	}

	public void setCompaignUser(TdUser compaignUser) {
		this.compaignUser = compaignUser;
	}

	public TdDistrict getDistrict() {
		return district;
	}

	public void setDistrict(TdDistrict district) {
		this.district = district;
	}
    
    
}