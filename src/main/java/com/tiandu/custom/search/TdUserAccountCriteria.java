package com.tiandu.custom.search;

public class TdUserAccountCriteria {
	
	private Integer uid; //钱包用户id
	
	private Byte status; //钱包状态
	
	private Integer updateBy; //更新人

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public Integer getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(Integer updateBy) {
		this.updateBy = updateBy;
	}

	@Override
	public String toString() {
		return "TdUserAccountCriteria [uid=" + uid + ", status=" + status + ", updateBy=" + updateBy + "]";
	}
	
}
