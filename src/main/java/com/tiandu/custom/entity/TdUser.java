package com.tiandu.custom.entity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.tiandu.district.entity.TdDistrict;
import com.tiandu.order.vo.ShoppingcartVO;

public class TdUser {
	
	/**
	 * 用户状态2 锁定
	 */
	public final static Byte USTATUS_FORBBIDEN = 2;
	/**
	 * 用户状态1 正常
	 */
	public final static Byte USTATUS_ACTIVE = 1;
	
	private Integer uid;

    private String uname;

    private String upassword;

    private String jointId;

    private String unick;

    private String utel;

    private Byte uverification;

    private Byte ustatus;

    private Byte utype;

    private Integer uparentId;

    private Date createTime;

    private Integer updateBy;

    private Date updateTime;

    private Date lastLoginTime;

    private String lastLoginIp;

    private String uavatar;

    private Integer uregionId;

    private Integer uprovinceId;

    private String uregionPath;

    private String uaddress;

    private Date ubirthday;

    private Byte ugenter;

    private Integer membershipId;

    private Byte supplierType;
    
    private Set<TdRole> roleSet = new HashSet<TdRole>();
    /**
	 * 后台保存角色使用
	 */
	private String roleIds; //多个roleId 用逗号隔开
	
	private TdUser updateUser;
	/**
	 * 注册地区，显示用
	 */
	private TdDistrict region;
	/**
	 * 购物车，显示用
	 */
	private TdUserAccount userAccount;

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname == null ? null : uname.trim();
    }

    public String getUpassword() {
        return upassword;
    }

    public void setUpassword(String upassword) {
        this.upassword = upassword == null ? null : upassword.trim();
    }

    public String getJointId() {
        return jointId;
    }

    public void setJointId(String jointId) {
        this.jointId = jointId == null ? null : jointId.trim();
    }

    public String getUnick() {
        return unick;
    }

    public void setUnick(String unick) {
        this.unick = unick == null ? null : unick.trim();
    }

    public String getUtel() {
        return utel;
    }

    public void setUtel(String utel) {
        this.utel = utel == null ? null : utel.trim();
    }

    public Byte getUverification() {
        return uverification;
    }

    public void setUverification(Byte uverification) {
        this.uverification = uverification;
    }

    public Byte getUstatus() {
        return ustatus;
    }

    public void setUstatus(Byte ustatus) {
        this.ustatus = ustatus;
    }

    public Byte getUtype() {
        return utype;
    }

    public void setUtype(Byte utype) {
        this.utype = utype;
    }

    public Integer getUparentId() {
        return uparentId;
    }

    public void setUparentId(Integer uparentId) {
        this.uparentId = uparentId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Integer updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp == null ? null : lastLoginIp.trim();
    }

    public String getUavatar() {
        return uavatar;
    }

    public void setUavatar(String uavatar) {
        this.uavatar = uavatar == null ? null : uavatar.trim();
    }

    public Integer getUregionId() {
        return uregionId;
    }

    public void setUregionId(Integer uregionId) {
        this.uregionId = uregionId;
    }

    public Integer getUprovinceId() {
        return uprovinceId;
    }

    public void setUprovinceId(Integer uprovinceId) {
        this.uprovinceId = uprovinceId;
    }

    public String getUregionPath() {
        return uregionPath;
    }

    public void setUregionPath(String uregionPath) {
        this.uregionPath = uregionPath == null ? null : uregionPath.trim();
    }

    public String getUaddress() {
        return uaddress;
    }

    public void setUaddress(String uaddress) {
        this.uaddress = uaddress == null ? null : uaddress.trim();
    }

    public Date getUbirthday() {
        return ubirthday;
    }

    public void setUbirthday(Date ubirthday) {
        this.ubirthday = ubirthday;
    }

    public Byte getUgenter() {
        return ugenter;
    }

    public void setUgenter(Byte ugenter) {
        this.ugenter = ugenter;
    }

    public Integer getMembershipId() {
        return membershipId;
    }

    public void setMembershipId(Integer membershipId) {
        this.membershipId = membershipId;
    }

    public Byte getSupplierType() {
        return supplierType;
    }

    public void setSupplierType(Byte supplierType) {
        this.supplierType = supplierType;
    }

	public Set<TdRole> getRoleSet() {
		return roleSet;
	}

	public void setRoleSet(Set<TdRole> roleSet) {
		this.roleSet = roleSet;
	}
	
	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}
	
	public TdUser getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(TdUser updateUser) {
		this.updateUser = updateUser;
	}

	public TdDistrict getRegion() {
		return region;
	}

	public void setRegion(TdDistrict region) {
		this.region = region;
	}

	
	public TdUserAccount getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(TdUserAccount userAccount) {
		this.userAccount = userAccount;
	}

	public Boolean isLocked(){
		if(TdUser.USTATUS_FORBBIDEN==this.getUstatus()){
			return true;
		}
		return false;
	}
	
	public String getUgenderStr(){
		if(this.getUgenter().equals(Byte.valueOf("1"))){
			return "男";
		}else{
			return "女";
		}
	}
	
	public String getUbirthdayStr(){
		if(this.getUbirthday() == null){
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(this.getUbirthday());
	}
	
    /**
     * 获取用户状态文字说明
     * @return
     */
	public String getUstatusStr(){
		StringBuffer sb = new StringBuffer(); 
		if(null!=this.getUstatus()){
			if(this.getUstatus().equals(TdUser.USTATUS_ACTIVE)){
				sb.append("启用");
			}else{
				sb.append("锁定");
			}
		}
		return sb.toString();
	}
	/**
	 * 获取用户性别文字说明
	 * @return
	 */
	public String getUgenterStr(){
		StringBuffer sb = new StringBuffer(); 
		if(null!=this.getUgenter()){
			if(this.getUgenter().equals(Byte.valueOf("2"))){
				sb.append("女");
			}else if(this.getUgenter().equals(Byte.valueOf("1"))){
				sb.append("男");
			}
		}
		if(null == this.getUgenter()){
			sb.append("保密");
		}
		return sb.toString();
	}
	/**
	 * 获取用户验证状态文字说明
	 * @return
	 */
	public String getUverificationStr(){
		StringBuffer sb = new StringBuffer(); 
		if(null!=this.getUverification()){
			if(this.getUverification().equals(Byte.valueOf("1"))){
				sb.append("已验证");
			}else{
				sb.append("未验证");
			}
		}
		return sb.toString();
	}
	/**
	 * 判断是否角色id
	 * @param roleId 角色id
	 * @return
	 */
	public Boolean hasRoleId(Integer roleId){
		if(null!=this.getRoleSet()){
			for(TdRole role : this.getRoleSet()){
				if(role.getId().equals(roleId)){
					return true;
				}
			}
		}
		return false;
	}
}