package com.tiandu.custom.service;

import java.util.List;

import com.tiandu.custom.entity.TdUser;
import com.tiandu.custom.search.TdUserSearchCriteria;

public interface TdUserService {

	public int insert(TdUser u);
	/**
	 * 删除用户所有信息
	 * @param id
	 * @return
	 */
	public int deleteUserAll(Integer id);
	/**
	 * 保存管理员信息
	 * @param user
	 * @return
	 */
	public Integer saveManager(TdUser user);
	public TdUser findOne(Integer id);
	public TdUser selectByUname(String uname);
	public List<TdUser> findBySearchCriteria(TdUserSearchCriteria sc);
	public TdUser findOneWithRoles(Integer id);
	
	/**
	 * 保存管理员角色信息
	 * @param user
	 * @return
	 */
	public Integer saveUserRole(TdUser user);
}
