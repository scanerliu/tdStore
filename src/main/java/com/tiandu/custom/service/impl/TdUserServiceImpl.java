package com.tiandu.custom.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tiandu.common.utils.WebUtils;
import com.tiandu.custom.entity.TdExperienceStore;
import com.tiandu.custom.entity.TdRole;
import com.tiandu.custom.entity.TdUser;
import com.tiandu.custom.entity.TdUserAccount;
import com.tiandu.custom.entity.TdUserIntegral;
import com.tiandu.custom.entity.TdUserIntegralLog;
import com.tiandu.custom.entity.TdUserMessage;
import com.tiandu.custom.entity.TdUserSign;
import com.tiandu.custom.entity.mapper.TdUserMapper;
import com.tiandu.custom.entity.mapper.TdUserRoleMapper;
import com.tiandu.custom.search.TdUserMessageSearchCriteria;
import com.tiandu.custom.search.TdUserSearchCriteria;
import com.tiandu.custom.search.TdUserSignSearchCriteria;
import com.tiandu.custom.service.TdExperienceStoreService;
import com.tiandu.custom.service.TdUserAccountService;
import com.tiandu.custom.service.TdUserIntegralLogService;
import com.tiandu.custom.service.TdUserIntegralService;
import com.tiandu.custom.service.TdUserMessageService;
import com.tiandu.custom.service.TdUserService;
import com.tiandu.custom.service.TdUserSignService;
import com.tiandu.district.entity.TdDistrict;
import com.tiandu.district.service.TdDistrictService;
import com.tiandu.system.service.TdSystemConfigService;

@Service("tdUserService")
public class TdUserServiceImpl implements TdUserService {

	@Autowired
	private TdUserMapper userMapper;
	
	@Autowired
	private TdUserRoleMapper userRoleMapper;
	
	@Autowired
	private TdDistrictService tdDistrictService;

	@Autowired
	private TdUserIntegralService tdUserIntegralService;
	
	@Autowired
	private TdUserAccountService tdUserAccountService;
	
	@Autowired
	private TdUserSignService tdUserSignService;
	
	@Autowired
	TdSystemConfigService tdSystemConfigService;
	
	@Autowired
	TdUserIntegralLogService  tdUserIntegralLogService;
	
	@Autowired
	TdUserMessageService tdUserMessageService;
	
	@Autowired
	TdExperienceStoreService tdExperienceStoreService;
	
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
	@Override
	public TdUser findOneWithAccount(Integer id) {
		TdUser user = userMapper.selectDetailByPrimaryKey(id);
		//查询账户信息
		TdUserAccount account = tdUserAccountService.findOne(user.getUid());
		user.setUserAccount(account);
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
				int result = userMapper.insert(user);
				//保存积分信息
				TdUserIntegral integral = new TdUserIntegral();
				integral.setIntegral(0);
				integral.setTotalIntegral(0);
				integral.setUid(user.getUid());
				integral.setUpdateTime(user.getUpdateTime());
				integral.setUpdateBy(user.getUpdateBy());
				tdUserIntegralService.insert(integral);
				//保存钱包信息
				TdUserAccount account = new TdUserAccount();
				account.setAmount(BigDecimal.ZERO);
				account.setStatus(TdUserAccount.ACCOUNT_STATUS_ACTIVE);
				account.setUid(user.getUid());
				account.setUpdateBy(user.getUpdateBy());
				account.setUpdateTime(user.getUpdateTime());
				tdUserAccountService.insert(account);
				
				return result;
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

	@Override
	public Map<String, String> saveSign(Integer uid) throws ParseException {
		Map<String, String> res = new HashMap<String, String>();
		res.put("code", "0");
		res.put("msg", "签到失败");
		TdUserSignSearchCriteria sc = new TdUserSignSearchCriteria();
		sc.setFlag(false);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date nowDate = sdf.parse(sdf.format(new Date()));
		sc.setCreateDate(nowDate);
		sc.setUid(uid);
		List<TdUserSign> userSignList = tdUserSignService.findBySearchCriteria(sc);
		if(userSignList != null && userSignList.size() > 0){
			res.put("code", "0");
			res.put("msg", "已签到");
			return res;
		}
		// 去签名
		TdUserSign userSign = new TdUserSign();
		userSign.setUid(uid);
		userSign.setCreateDate(nowDate);
		tdUserSignService.save(userSign);
		// 查询签到积分
		Integer signIntegral = Integer.parseInt(tdSystemConfigService.getConfigMap().get("signdeliveryintegral"));
		// 修改积分	
		TdUserIntegral userIntegral = tdUserIntegralService.findOne(uid);
		int integralBeforeUpdate = userIntegral.getTotalIntegral();
		userIntegral.setTotalIntegral(userIntegral.getTotalIntegral() + signIntegral);
		userIntegral.setIntegral(userIntegral.getIntegral() + signIntegral);
		userIntegral.setUpdateTime(new Date());
		userIntegral.setUpdateBy(0); // 系统修改积分设为0
		tdUserIntegralService.save(userIntegral);
		// 添加积分日志
		TdUserIntegralLog userIntegralLog = new TdUserIntegralLog();
		userIntegralLog.setUid(uid);
		userIntegralLog.setPreintegral(integralBeforeUpdate);
		userIntegralLog.setType(Byte.valueOf("1"));
		userIntegralLog.setIntegral(signIntegral);
		userIntegralLog.setCreateTime(new Date());
		userIntegralLog.setNote("系统修改签到积分");
		userIntegralLog.setRelation(null);
		tdUserIntegralLogService.save(userIntegralLog);
		res.put("code", "1");
		res.put("signIntegral", signIntegral.toString());
		return res;
	}
	
	@Override
	public Map<String, List<TdUserMessage>> getMessageList(Integer uid){
		Map<String, List<TdUserMessage>> res =  new HashMap<>();
		TdUserMessageSearchCriteria sc = new TdUserMessageSearchCriteria();
		sc.setUid(uid);
		sc.setMsgType(Byte.valueOf("1"));
		res.put("systemMessageList", tdUserMessageService.findBySearchCriteria(sc));
		sc.setMsgType(Byte.valueOf("2"));
		res.put("orderMessageList", tdUserMessageService.findBySearchCriteria(sc));
		sc.setMsgType(Byte.valueOf("3"));
		res.put("experienceStoreMessageList", tdUserMessageService.findBySearchCriteria(sc));
		return res;
	}

	@Override
	public List<TdUserMessage> getMessageListByMsgType(Integer uid, Byte msgType) {
		TdUserMessageSearchCriteria sc = new TdUserMessageSearchCriteria();
		sc.setUid(uid);
		sc.setMsgType(msgType);
		return tdUserMessageService.findBySearchCriteria(sc);
	}

	@Override
	public int saveUserInfo(TdUser user) {
		return userMapper.updateByPrimaryKeySelective(user);
	}

	@Override
	public int saveVerifyExperienceStoreApply(TdExperienceStore experienceStore, Byte status) {
		TdExperienceStore estore =  tdExperienceStoreService.findOne(experienceStore.getId());
		estore.setStatus(status);
		return tdExperienceStoreService.save(estore);
	}
	
	
}
