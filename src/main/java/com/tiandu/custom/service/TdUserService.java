package com.tiandu.custom.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.tiandu.custom.entity.TdExperienceStore;
import com.tiandu.custom.entity.TdUser;
import com.tiandu.custom.entity.TdUserMessage;
import com.tiandu.custom.search.TdUserSearchCriteria;

public interface TdUserService {

	public int insert(TdUser u);
	public int updateByPrimaryKeySelective(TdUser record);
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
	/**
	 * 保存会员员信息
	 * @param user
	 * @return
	 */
	public Integer saveCustomer(TdUser user);
	public TdUser findOne(Integer id);
	public TdUser findOneWithAccount(Integer id);
	public TdUser selectByUname(String uname);
	public List<TdUser> findBySearchCriteria(TdUserSearchCriteria sc);
	public TdUser findOneWithRoles(Integer id);
	/**
	 * 查找用户详情
	 * @param id
	 * @return
	 */
	public TdUser findDetail(Integer id);
	/**
	 * 保存管理员角色信息
	 * @param user
	 * @return
	 */
	public Integer saveUserRole(TdUser user);
	/**
	 * 保存用户密码
	 * @param user
	 * @return
	 */
	public Integer saveUserPassword(TdUser user);
	/**
	 * 保存用户状态
	 * @param user
	 * @return
	 */
	public Integer saveUserStatus(TdUser user);
	
	
	/**
	 * 用户签到
	 * @param uid
	 * @return
	 */
	public Map<String, String> saveSign(Integer uid) throws ParseException;
	
	/*
	 * 获取会员消息列表
	 */
	public Map<String, List<TdUserMessage>> getMessageList(Integer uid);
	
	/*
	 * 获取指定类型会员消息列表
	 */
	public List<TdUserMessage> getMessageListByMsgType(Integer uid, Byte msgType);
	
	/*
	 *	保存用户个人信息 
	 */
	public int saveUserInfo(TdUser user);
	
	/*
	 *	体验店申请审核 
	 */
	public int saveVerifyExperienceStoreApply(TdExperienceStore experienceStore, Byte status);
		
}
