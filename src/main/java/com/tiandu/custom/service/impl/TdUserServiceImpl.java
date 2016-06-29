package com.tiandu.custom.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tiandu.common.db.DBContextHolder;
import com.tiandu.common.utils.WebUtils;
import com.tiandu.custom.entity.TdRole;
import com.tiandu.custom.entity.TdUser;
import com.tiandu.custom.entity.mapper.TdUserMapper;
import com.tiandu.custom.entity.mapper.TdUserRoleMapper;
import com.tiandu.custom.search.TdUserSearchCriteria;
import com.tiandu.custom.service.TdUserService;
import com.tiandu.district.entity.TdDistrict;
import com.tiandu.district.service.TdDistrictService;
import com.tiandu.menu.entity.TdMenu;

@Service("tdUserService")
public class TdUserServiceImpl implements TdUserService {

	@Autowired
	TdUserMapper userMapper;
	
	@Autowired
	TdUserRoleMapper userRoleMapper;
	
	@Autowired
	TdDistrictService tdDistrictService;

	public int insert(TdUser u) {
		return userMapper.insert(u);
	}
	
	@Override
	public int updateByPrimaryKeySelective(TdUser record) {
		return userMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public TdUser findDetail(Integer id) {
		TdUser user = userMapper.selectDetailByPrimaryKey(id);
		//查询注册地区
		TdDistrict region = tdDistrictService.findOneFull(user.getUregionId());
		user.setRegion(region);
		return user;
	}

	/**
	 * 删除用户所有信息
	 * @param id
	 * @return
	 */
	@Override
	public int deleteUserAll(Integer id) {
		
		return userMapper.deleteByPrimaryKey(id);
	}

	@Override
	public Integer saveManager(TdUser user) {
		if(null!=user){
			if(null!=user.getUid()){//更新
				return userMapper.updateManagerByPrimaryKey(user);
			}else{
				String password = WebUtils.generatePassword(user.getUname(), user.getUpassword());
				user.setUpassword(password);
				user.setJointId("sys_"+user.getUname());
				user.setUparentId(0);
				user.setUtype(Byte.valueOf("2"));//平台管理员
				user.setUverification(Byte.valueOf("1"));
				return userMapper.insert(user);
			}
		}
		return null;
	}
	
	@Override
	public Integer saveCustomer(TdUser user) {
		if(null!=user){
			if(null!=user.getUid()){//更新
				return userMapper.updateCustomerByPrimaryKey(user);
			}else{
				//地区获取
				Integer provinceId = 1;
				String regionPath = "";
				if(null!=user.getUregionId()){
					TdDistrict region = tdDistrictService.findOneFull(user.getUregionId());
					if(null!=region){
						provinceId = region.getRegionProvinceId();
						regionPath = region.getRegionPath();
					}
				}
				String password = WebUtils.generatePassword(user.getUname(), user.getUpassword());
				user.setUpassword(password);
				user.setJointId("sys_"+user.getUname());
				user.setUparentId(0);
				user.setUtype(Byte.valueOf("1"));//普通会员
				user.setUverification(Byte.valueOf("1"));
				user.setMembershipId(1);
				user.setSupplierType(Byte.valueOf("0"));
				user.setUprovinceId(provinceId);
				user.setUregionPath(regionPath);
				return userMapper.insert(user);
			}
		}
		return null;
	}

	@Override
	public Integer saveUserRole(TdUser user) {
		if(null!=user&&null!=user.getUid()){
			userRoleMapper.deleteByUser(user);
			if(StringUtils.isNotEmpty(user.getRoleIds())){
				String[] menuIds =  user.getRoleIds().split(",");
				Set<TdRole> roleSet = new HashSet<TdRole>();
				for(String id : menuIds){
					if(StringUtils.isNotEmpty(id)){
						try {
							Integer mid = Integer.valueOf(id);
							TdRole role = new TdRole();
							role.setId(mid);
							roleSet.add(role);
						} catch (NumberFormatException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				if(roleSet.size()>0){
					user.setRoleSet(roleSet);
					userRoleMapper.insertUserRoles(user);
				}
			}
		}
		return null;
	}
	public TdUser findOne(Integer id) {
		return userMapper.selectByPrimaryKey(id);
	}

	public TdUser selectByUname(String uname) {
		return userMapper.selectByUname(uname);
	}

	@Override
	public List<TdUser> findBySearchCriteria(TdUserSearchCriteria sc) {
		Integer count = userMapper.countByCriteria(sc);
		sc.setTotalCount(count);
		return userMapper.findBySearchCriteria(sc);
	}

	@Override
	public TdUser findOneWithRoles(Integer id) {
		return userMapper.selectByPrimaryKeyWithRoles(id);
	}
	@Override
	public Integer saveUserPassword(TdUser user) {
		String password = WebUtils.generatePassword(user.getUname(), user.getUpassword());
		user.setUpassword(password);
		return userMapper.saveUserPassword(user);
	}
	@Override
	public Integer saveUserStatus(TdUser user) {
		return userMapper.saveUserStatus(user);
	}
	
	
}
