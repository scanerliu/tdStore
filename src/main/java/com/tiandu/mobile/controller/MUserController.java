package com.tiandu.mobile.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tiandu.common.controller.BaseController;
import com.tiandu.common.utils.MessageSender;
import com.tiandu.common.utils.WebUtils;
import com.tiandu.custom.entity.TdExperienceStore;
import com.tiandu.custom.entity.TdMembership;
import com.tiandu.custom.entity.TdUser;
import com.tiandu.custom.entity.TdUserMessage;
import com.tiandu.custom.search.TdUserSearchCriteria;
import com.tiandu.custom.service.TdExperienceStoreService;
import com.tiandu.custom.service.TdMembershipService;
import com.tiandu.custom.service.TdUserMessageService;
import com.tiandu.custom.service.TdUserSignService;
import com.tiandu.order.entity.TdShoppingcartItem;
import com.tiandu.order.search.TdShoppingcartSearchCriteria;
import com.tiandu.order.service.TdShoppingcartItemService;
import com.tiandu.order.vo.ShoppingcartVO;

/**
 * 
 * @author liuxinbing
 *
 */
@Controller
@RequestMapping("/mobile/user")
public class MUserController extends BaseController {
	
	private final Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private TdMembershipService tdMembershipService;
	
	@Autowired
	private TdUserSignService tdUserSignService;
	
	@Autowired
	private TdShoppingcartItemService tdShoppingcartItemService;
	
	@Autowired
	private TdUserMessageService tdUserMessageService;
	
	@Autowired
	private TdExperienceStoreService tdExperienceStoreService;
	
	// 个人中心
	@RequestMapping("/center")
	public String center(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		TdUser currentUser = this.getCurrentUser();
		modelMap.addAttribute("currentUser", currentUser);
		TdMembership membership = tdMembershipService.findOne(currentUser.getMembershipId());
		modelMap.addAttribute("membership", membership);
		// 系统配置
		modelMap.addAttribute("system", getSystem());
	    return "/mobile/user/center";
	}
	
	// 用户签到
	@RequestMapping(value="/sign", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> sign(Integer uid, HttpServletRequest request, HttpServletResponse response) {
		Map<String,String> res = new HashMap<String,String>(); 
		Map<String, String> signBackData;
		try {
			signBackData = tdUserService.saveSign(uid);
		} catch (ParseException e) {
			logger.error("签到失败!");
			res.put("code", "0");
			res.put("msg", "签到失败。");
			e.printStackTrace();
			return res;
		}
		if(signBackData.get("code").equals("0")){
			logger.error("签到失败!");
			res.put("code", "0");
			res.put("msg", signBackData.get("msg"));
		}else if(signBackData.get("code").equals("1")){
			res.put("code", "1");
			res.put("msg", "签到成功。");
			res.put("gettedIntegral", signBackData.get("signIntegral"));
		}
		return res;
	}
	
	// 保存修改密码
	@RequestMapping(value="/savePassword", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> sign(String oldPassword, String valideCode, String newPassword, HttpServletRequest request, HttpServletResponse response) {
		Map<String,String> res = new HashMap<String,String>();
		String changePasswordValidCode = (String) request.getSession().getAttribute("changePasswordValidCode");
		if(!valideCode.equals(changePasswordValidCode)){
			res.put("info", "验证码错误！");
			res.put("status", "n");
//			return res;
		}
		
		TdUser currentUser = this.getCurrentUser();
		currentUser = tdUserService.findOne(currentUser.getUid());
		String md5OldPassword = WebUtils.generatePassword(currentUser.getUname(), oldPassword);
		if(!md5OldPassword.equals(currentUser.getUpassword())){
			res.put("info", "原始密码错误！");
			res.put("status", "n");
			return res;
		}
		Date now = new Date();
		currentUser.setUpdateBy(currentUser.getUid());
		currentUser.setUpdateTime(now);
		currentUser.setCreateTime(now);
		currentUser.setUpassword(newPassword);
    	tdUserService.saveUserPassword(currentUser);
    	res.put("info", "密码修改成功！");
		res.put("status", "y");
		return res;
	}
	
	@RequestMapping(value="/getChangeUserPasswordValidCode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> getChangeUserPasswordValidCode(HttpServletRequest request) {
		Map<String,String> res = new HashMap<String,String>();
		Random random = new Random();
		String smscode = String.format("%04d", random.nextInt(9999));
		request.getSession().setAttribute("changePasswordValidCode", smscode);
		List<String> phoneNums =new ArrayList<>();
		phoneNums.add(this.getCurrentUser().getUtel());
		List<String> datas =new ArrayList<>();
		datas.add(smscode);
		datas.add("1");
		MessageSender ms = new MessageSender();
		ms.init();
		boolean isSendSuccess = ms.send(phoneNums, "1", datas);
		if(isSendSuccess){
			res.put("code", "1");
			res.put("msg", "发送验证码成功!");			
		}else{
			res.put("code", "0");
			res.put("msg", "验证码获取失败!");
		}
		return res;
	}
	
	
	
	
	/*
	 * 我的购物车
	 */
	@RequestMapping("/list")
	public String list(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		TdUser currUser = this.getCurrentUser();
		//获取购物车
		ShoppingcartVO shoppingcart  = getShoppingcart(currUser.getUid());
		modelMap.addAttribute("shoppingcart", shoppingcart) ;
	    return "/mobile/shoppingcart/list";
	}
	
	/*
	 * 消息列表
	 */
	@RequestMapping("/messageList")
	public String messageList(Byte msgType, String active, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		TdUser currentUser = this.getCurrentUser();
		if(msgType == null){
			//获取购物车
			Map<String, List<TdUserMessage>> messageMap = tdUserService.getMessageList(currentUser.getUid());
			List<TdUserMessage> systemMessageList = messageMap.get("systemMessageList");
			List<TdUserMessage> orderMessageList = messageMap.get("orderMessageList");
			List<TdUserMessage> experienceStoreMessageList = messageMap.get("experienceStoreMessageList");
			modelMap.addAttribute("systemMessageList", systemMessageList);
			modelMap.addAttribute("orderMessageList", orderMessageList);
			modelMap.addAttribute("experienceStoreMessageList", experienceStoreMessageList);
		}
		if(active != null && !active.equals("")){
			modelMap.addAttribute("active", active);			
		}
	    return "/mobile/user/MessageList";
	}
	
	/*
	 * 消息详情
	 */
	@RequestMapping("/messageDetail")
	public String messageDetail(Integer messageId, String active, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		TdUser currentUser = this.getCurrentUser();
		if(active != null && !active.equals("")){
			modelMap.addAttribute("active", active);
		}
		TdUserMessage message = tdUserMessageService.findOne(messageId);
		if(message.getStatus().equals(Byte.valueOf("1"))){
			message.setStatus(Byte.valueOf("2"));
			tdUserMessageService.save(message);			
		}
		modelMap.addAttribute("message", tdUserMessageService.findOne(messageId));
		if(!message.getMsgType().equals(Byte.valueOf("3"))){
			return "/mobile/user/systemOrOrderMessageDetail";		
		}else{
			TdExperienceStore experienceStore = tdExperienceStoreService.findOne(message.getRelateId());
			
			modelMap.addAttribute("experienceStore", experienceStore);
			return "/mobile/user/experienceStoreMessageDetail";
		}
	}
	
	/*
	 * 用户信息
	 */
	@RequestMapping("/info")
	public String info(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		TdUser currentUser = this.getCurrentUser();
		modelMap.addAttribute("currentUser", currentUser);
		return "/mobile/user/info";		
	}
	
	/*
	 * 修改密码
	 */
	@RequestMapping("/changePassword")
	public String changePassword(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		return "/mobile/user/changePassword";		
	}
	
	
	@RequestMapping(value="/saveInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> saveInfo(TdUser user, HttpServletRequest request, HttpServletResponse response) {
		Map<String,String> res = new HashMap<String,String>();
		res.put("msg", "个人信息修改失败！");
		TdUser currentUser = this.getCurrentUser();
		currentUser.setUavatar(user.getUavatar());
		currentUser.setUnick(user.getUnick());
		currentUser.setUgenter(user.getUgenter());
		currentUser.setUbirthday(user.getUbirthday());
		currentUser.setUtel(user.getUtel());
		if(tdUserService.saveUserInfo(currentUser) == 1){
			res.put("msg", "个人信息修改成功！");			
		}
		return res;
	}
	
	
	
	
	@RequestMapping(value="/add", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,String> add(TdShoppingcartItem item, HttpServletRequest request, HttpServletResponse response) {
		Map<String,String> res = new HashMap<String,String>(); 
		if(null!=item && null!=item.getId() && null!=item.getOptype()){
			try {
				TdUser currUser = this.getCurrentUser();
				item.setUid(currUser.getUid());
				tdShoppingcartItemService.addShoppingcartItemNum(item);
				//获取购物车
				ShoppingcartVO shoppingcart  = getShoppingcart(currUser.getUid());
				
				res.put("code", "1");
				res.put("totalAmount", shoppingcart.getTotalAmount().toString());
				res.put("totalPostage", shoppingcart.getTotalPostage().toString());
				return res;
			}catch (Exception e) {
				logger.error("购物车增减失败错误信息:"+e);
				res.put("code", "0");
				return res;
			}
		}else{
			res.put("code", "0");
			return res;
		}
	}
	@RequestMapping(value="/change", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,String> change(TdShoppingcartItem item, HttpServletRequest request, HttpServletResponse response) {
		Map<String,String> res = new HashMap<String,String>(); 
		if(null!=item){
			try {
				TdUser currUser = this.getCurrentUser();
				item.setUid(currUser.getUid());
				tdShoppingcartItemService.updateShoppingcartItem(item);
				//重新计算
				//获取购物车
				ShoppingcartVO shoppingcart  = getShoppingcart(currUser.getUid());
				
				res.put("code", "1");
				res.put("totalAmount", shoppingcart.getTotalAmount().toString());
				res.put("totalPostage", shoppingcart.getTotalPostage().toString());
				return res;
			}catch (Exception e) {
				logger.error("购物车更新失败错误信息:"+e);
				res.put("code", "0");
				return res;
			}
		}else{
			res.put("code", "0");
			return res;
		}
	}
	@RequestMapping(value="/remove", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,String> remove(String ids, HttpServletRequest request, HttpServletResponse response) {
		Map<String,String> res = new HashMap<String,String>(); 
		if(StringUtils.isNotEmpty(ids)){
			try {
				TdUser currUser = this.getCurrentUser();
				tdShoppingcartItemService.removeItemsFromShoppingcart(currUser.getUid(), ids);
				res.put("code", "1");
				return res;
			}catch (Exception e) {
				logger.error("购物车删除失败错误信息:"+e);
				res.put("code", "0");
				return res;
			}
		}else{
			res.put("code", "0");
			return res;
		}
	}
	
	private ShoppingcartVO getShoppingcart(Integer uid){
		ShoppingcartVO cart = new ShoppingcartVO();
		//重新计算
		TdShoppingcartSearchCriteria sc = new TdShoppingcartSearchCriteria();
		sc.setFlag(false);
		sc.setUid(uid);
		List<TdShoppingcartItem> itemList = tdShoppingcartItemService.findBySearchCriteria(sc);
		cart.setItemList(itemList);
		if(null!=itemList && itemList.size()>0){
			for(TdShoppingcartItem item : itemList){
				//累加每个商品的运费
				BigDecimal postage = (null!=item.getPostage())?item.getPostage():BigDecimal.ZERO;
				cart.setTotalPostage(postage.add(cart.getTotalPostage()));
				//累加单个商品总金额（价格*数量）
				BigDecimal quantity = new BigDecimal(item.getQuantity());
				BigDecimal amount = item.getPrice().multiply(quantity);
				cart.setTotalAmount(amount.add(cart.getTotalAmount()).add(postage));
			}
		}
		return cart;
	}
	
	@RequestMapping(value="/verifyExperienceStoreApply", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,String> verifyExperienceStoreApply(TdExperienceStore estore, Byte status, HttpServletRequest request, HttpServletResponse response) {
		Map<String,String> res = new HashMap<String,String>(); 
		estore.setStatus(status);
		int affectedNum = tdUserService.saveVerifyExperienceStoreApply(estore, status);
		if(affectedNum == 1){
			res.put("code", "1");
			res.put("msg", "审核成功！");
		}else{
			res.put("code", "0");
			res.put("msg", "审核失败！");
		}
		return res;
	}
	
	/*
	 * 推荐人
	 */
	@RequestMapping("/recommendPeople")
	public String recommendPeople(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		TdUser currentUser = this.getCurrentUser();
		TdUser upOneRecommendPerson;
		TdUser upTwoRecommendPerson;
		TdUser upThreeRecommendPerson;
		if(currentUser.getUparentId() != null){
			upOneRecommendPerson = tdUserService.findOne(currentUser.getUparentId());
			modelMap.addAttribute("upOneRecommendPerson", upOneRecommendPerson);
			if(upOneRecommendPerson!= null && upOneRecommendPerson.getUparentId() != null){
				upTwoRecommendPerson = tdUserService.findOne(upOneRecommendPerson.getUparentId());
				modelMap.addAttribute("upTwoRecommendPerson", upTwoRecommendPerson);
				if(upTwoRecommendPerson != null && upTwoRecommendPerson.getUparentId() != null){
					upThreeRecommendPerson = tdUserService.findOne(upTwoRecommendPerson.getUparentId());
					modelMap.addAttribute("upThreeRecommendPerson", upThreeRecommendPerson);
				}
			}
		}
		return "/mobile/user/recommendPeople";		
	}
	
	/*
	 * 推荐人
	 */
	@RequestMapping("/downUserList")
	public String downThreeUserList(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		modelMap.addAttribute("isTheFirstTime", "yes");
		modelMap.addAttribute("pageNo", "1");
		return "/mobile/user/downUserList";	
	}
	
	@RequestMapping(value="/getDownUsers", method = RequestMethod.POST)
	@ResponseBody
	public String getDownUsers(Integer pageNo, HttpServletRequest request, HttpServletResponse response){
		TdUser currentUser = this.getCurrentUser();
		String parentIdsStr = "["+ currentUser.getUid() +"]"; // 下一二三级的id
		String oneIdsStr = ""; // 下一级的id
		String twoIdsStr = ""; // 下二级的id
		String threeIdsStr = "";// 下三级的id
		TdUserSearchCriteria sc = new TdUserSearchCriteria();
		sc.setFlag(false);
		sc.setParentIdsStr(parentIdsStr);
		List<TdUser> downOneUserList = tdUserService.findBySearchCriteria(sc);
		if(downOneUserList != null){
			for(TdUser user : downOneUserList){
				parentIdsStr += "["+ user.getUid() +"]";
				oneIdsStr += "["+ user.getUid() +"]";
			}
		}
		sc.setParentIdsStr(oneIdsStr);
		List<TdUser> downTwoUserList = tdUserService.findBySearchCriteria(sc);
		if(downTwoUserList != null){
			for(TdUser user : downTwoUserList){
				parentIdsStr += "["+ user.getUid() +"]";
				twoIdsStr += "["+ user.getUid() +"]";
			}
		}
		sc.setParentIdsStr(twoIdsStr);
		List<TdUser> downThreeUserList = tdUserService.findBySearchCriteria(sc);
		if(downThreeUserList != null){
			for(TdUser user : downThreeUserList){
				threeIdsStr += "["+ user.getUid() +"]";
			}
		}
		
		sc.setParentIdsStr(parentIdsStr);
		sc.setPageNo(pageNo);
		sc.setFlag(true);
		sc.setPageSize(3);
		
		List<TdUser> downUserList = tdUserService.findBySearchCriteria(sc); // 下一二三级分页会员
		
		// 生成json数据
		JSONObject jsonData = new JSONObject();
		try {
			jsonData.put("hasData", "yes");
			if(sc.getPageNo()!=pageNo){
				jsonData.put("hasData", "no");
				return jsonData.toString();
			}else{
				jsonData.put("totalCount", sc.getTotalCount());
			}
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		JSONArray contentJsonArray = new JSONArray();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for(TdUser user : downUserList){
			JSONObject jsonObject = new JSONObject();
			try {
				jsonObject.put("uavatar", user.getUavatar()!=null?user.getUavatar():"");
				jsonObject.put("unick", user.getUnick()!=null?user.getUnick():"");
				jsonObject.put("ugenter", user.getUgenter()!=null?user.getUgenter().toString():"");
				if(user.getUgenter() != null){
					if(user.getUgenter().equals(Byte.valueOf("1"))){
						jsonObject.put("gender", "man");
					}else if(user.getUgenter().equals(Byte.valueOf("2"))){
						jsonObject.put("gender", "woman");
					}else{
						jsonObject.put("gender", "");
					}
				}
				jsonObject.put("ugenterStr", user.getUgenterStr());
				jsonObject.put("uname", user.getUname());
				jsonObject.put("ubirthday", user.getUbirthdayStr()!=null?user.getUbirthdayStr():"");
				if(oneIdsStr.contains(user.getUid().toString())){
					jsonObject.put("level", "1");
				}else if(twoIdsStr.contains(user.getUid().toString())){
					jsonObject.put("level", "2");
				}else if(threeIdsStr.contains(user.getUid().toString())){
					jsonObject.put("level", "3");
				}
			} catch (JSONException e) {
				logger.error("下级会员解析失败");
				e.printStackTrace();
			}
			contentJsonArray.put(jsonObject);
		}
		try {
			jsonData.put("addedData", contentJsonArray);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonData.toString();
	}
	
}
