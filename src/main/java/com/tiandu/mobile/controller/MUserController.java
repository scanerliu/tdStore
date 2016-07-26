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
import com.tiandu.common.utils.ConstantsUtils;
import com.tiandu.common.utils.MessageSender;
import com.tiandu.common.utils.WebUtils;
import com.tiandu.custom.entity.TdAgent;
import com.tiandu.custom.entity.TdExperienceStore;
import com.tiandu.custom.entity.TdMembership;
import com.tiandu.custom.entity.TdUser;
import com.tiandu.custom.entity.TdUserAccount;
import com.tiandu.custom.entity.TdUserAddress;
import com.tiandu.custom.entity.TdUserMessage;
import com.tiandu.custom.search.TdAgentSearchCriteria;
import com.tiandu.custom.search.TdUserAddressCriteria;
import com.tiandu.custom.search.TdUserSearchCriteria;
import com.tiandu.custom.service.TdAgentService;
import com.tiandu.custom.service.TdExperienceStoreService;
import com.tiandu.custom.service.TdMembershipService;
import com.tiandu.custom.service.TdUserAccountService;
import com.tiandu.custom.service.TdUserAddressService;
import com.tiandu.custom.service.TdUserMessageService;
import com.tiandu.custom.service.TdUserSignService;
import com.tiandu.district.entity.TdDistrict;
import com.tiandu.district.service.TdDistrictService;
import com.tiandu.order.entity.TdShoppingcartItem;
import com.tiandu.order.search.TdShoppingcartSearchCriteria;
import com.tiandu.order.service.TdShoppingcartItemService;
import com.tiandu.order.vo.ShoppingcartVO;
import com.tiandu.product.entity.TdProduct;
import com.tiandu.product.entity.TdProductAttachment;
import com.tiandu.product.entity.TdProductAttribute;
import com.tiandu.product.entity.TdProductAttributeOption;
import com.tiandu.product.entity.TdProductDescription;
import com.tiandu.product.entity.TdProductSku;
import com.tiandu.product.entity.TdProductType;
import com.tiandu.product.entity.TdProductTypeAttribute;
import com.tiandu.product.search.TdProductAttributeOptionCriteria;
import com.tiandu.product.search.TdProductCriteria;
import com.tiandu.product.search.TdProductTypeCriteria;
import com.tiandu.product.service.TdProductAttachmentService;
import com.tiandu.product.service.TdProductAttributeOptionService;
import com.tiandu.product.service.TdProductAttributeService;
import com.tiandu.product.service.TdProductDescriptionService;
import com.tiandu.product.service.TdProductService;
import com.tiandu.product.service.TdProductSkuService;
import com.tiandu.product.service.TdProductStatService;
import com.tiandu.product.service.TdProductTypeAttributeService;
import com.tiandu.product.service.TdProductTypeService;

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
	private TdUserAddressService tdUserAddressService;
	
	@Autowired
	private TdDistrictService tdDistrictService;
	
	@Autowired
	private TdExperienceStoreService tdExperienceStoreService;
	
	@Autowired
	TdAgentService tdAgentService;
	
	@Autowired
	private TdProductService tdProductService;
	
	@Autowired
	TdProductAttributeOptionService tdProductAttributeOptionService; 
	
	@Autowired
	private TdProductTypeService tdProductTypeService;
	
	@Autowired
	private TdProductAttachmentService tdProductAttachmentService;
	
	@Autowired
	private TdProductDescriptionService tdProductDescriptionService; 
	
	@Autowired
	TdProductAttributeService tdProductAttributeService; 
	
	@Autowired
	TdProductTypeAttributeService tdProductTypeAttributeService; 
	
	@Autowired
	private TdProductStatService tdProductStatService;
	
	@Autowired
	TdProductSkuService tdProductSkuService;
	
	@Autowired
	TdUserAccountService tdUserAccountService;
	
	
	// 个人中心
	@RequestMapping("/center")
	public String center(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		TdUser currentUser = this.getCurrentUser();
		if(null==currentUser)
		{
			return "redirect:/mobile/login";
		}
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
	
	/**
	 * 收货地址
	 * @param request
	 * @param response
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/shoppingAddress")
	public String shoppingAddress(HttpServletRequest request,ModelMap map,Integer addressId)
	{
		TdUser tdUser = this.getCurrentUser();
		if(tdUser == null)
		{
			return "redirect:/mobile/login";
		}
		if(addressId != null && addressId > 0)
		{
			tdUserAddressService.setIsDefaultFalse(tdUser.getUid());
			TdUserAddress userAddress = tdUserAddressService.findOne(addressId);
			userAddress.setIsDefault(true);
			tdUserAddressService.save(userAddress);
		}
		if(addressId != null && addressId < 0)
		{
			map.addAttribute("shopping",addressId);
			request.getSession().setAttribute("shopping", addressId);
		}
		Integer randomNo = (Integer)request.getSession().getAttribute("shopping");
		
		if(randomNo != null)
		{
			map.addAttribute("shopping",randomNo);
			if(randomNo > -100)
			{
				map.addAttribute("returnPath", "confirmorder");
			}
			else
			{
				map.addAttribute("returnPath", "buynow");
			}
		}
		// 系统配置
		map.addAttribute("system", getSystem());
		TdUserAddressCriteria sc = new TdUserAddressCriteria();
		sc.setUid(tdUser.getUid());
		List<TdUserAddress> userAddresses = tdUserAddressService.findBySearchCriteria(sc);
		map.addAttribute("address_list", userAddresses);
		return "/mobile/user/shoppingAddress";
	}
	
	/**
	 * 新增收货地址
	 * @param request
	 * @param response
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/shoppingAddressAdd")
	public String shoppingAddressAdd(Integer addressId,ModelMap map,HttpServletRequest request)
	{
		TdUser tdUser = this.getCurrentUser();
		if(tdUser == null)
		{
			return "redirect:/modile/login";
		}
		// 系统配置
		map.addAttribute("system", getSystem());
		if(addressId != null && addressId > 0)
		{
			TdUserAddress userAddress = tdUserAddressService.findOne(addressId);
			if(userAddress != null && userAddress.getUid() == tdUser.getUid())
			{
				map.addAttribute("address",userAddress);
				Integer regionId = userAddress.getRegionId();
				Map<String, Object> regionMap = tdUserAddressService.getUserDistrictIdByRegionId(new HashMap<String,Object>(),regionId);
				map.addAllAttributes(regionMap);
			}
		}
		
		if(addressId != null && addressId < 0)
		{
			map.addAttribute("shopping",addressId);
			request.getSession().setAttribute("shopping", addressId);
		}
		
		return "/mobile/user/shoppingAddressAdd";
	}
	
	/**
	 * 地址保存并设置默认地址 
	 * @param tdUserAddress
	 * @return
	 */
	@RequestMapping(value = "/shoppingAddressSave" ,method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> shoppingAddressSave(TdUserAddress tdUserAddress)
	{
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("code", ConstantsUtils.RETURN_CODE_SUCCESS);
		TdUser tdUser = this.getCurrentUser();
		if(tdUser == null)
		{
			result.put("msg", "未登录");
			return result;
		}
		if(tdUserAddress.getUid() != null && tdUserAddress.getUid() != tdUser.getUid())
		{
			result.put("msg", "参数错误");
			return result;
		}
		tdUserAddress.setUid(tdUser.getUid());
		if(tdUserAddress != null && tdUserAddress.getIsDefault() != null && tdUserAddress.getIsDefault() == true)
		{
			tdUserAddressService.setIsDefaultFalse(tdUser.getUid());
		}
		tdUserAddressService.save(tdUserAddress);
		
		result.put("msg", "成功");
		return result;
	}
	
	/**
	 * 地址的地区选择
	 * @param provinceId 省
	 * @param cityId 市
	 * @return
	 */
	@RequestMapping(value = "/shoppingAddressDistrict",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> shippingAddressDistrict(Integer provinceId,Integer cityId)
	{
		Map<String,Object> resMap = new HashMap<String,Object>();
		resMap.put("code",ConstantsUtils.RETURN_CODE_FAILURE);
		TdUser tdUser = this.getCurrentUser();
		if(tdUser == null)
		{
			resMap.put("msg","请先登录");
			return resMap;
		}
		if(provinceId == null && cityId == null)
		{
			List<TdDistrict> provinceList = tdDistrictService.getDistrictByUpid(0);
			resMap.put("code",ConstantsUtils.RETURN_CODE_SUCCESS);
			resMap.put("provinceList", provinceList);
			return resMap;
		}
		if (provinceId != null && provinceId != -1 && cityId == null)
		{
			List<TdDistrict> cityList = tdDistrictService.getDistrictByUpid(provinceId);
			// 是否是直辖市
			if (tdDistrictService.findOne(provinceId) != null && tdDistrictService.isCentralCity(tdDistrictService.findOne(provinceId).getName()))
			{
				resMap.put("isCentralCity", true);
			}
			else
			{
				resMap.put("isCentralCity", false);
			}
			resMap.put("cityList", cityList);
			resMap.put("code",ConstantsUtils.RETURN_CODE_SUCCESS);
			return resMap;
		}
		if (cityId != null && cityId != -1) {
			List<TdDistrict> areaList = tdDistrictService.getDistrictByUpid(cityId);
			// 若用户在选择了与市不匹配的省时，可视为只选择了省
			if (tdDistrictService.findOne(cityId) != null && provinceId != null && provinceId != -1
					&& tdDistrictService.findOne(cityId).getUpid().equals(provinceId)) {
				resMap.put("districtList", areaList);
				resMap.put("code",ConstantsUtils.RETURN_CODE_SUCCESS);
				return resMap;
			} else {
				cityId = -1;
			}
		}
		
		resMap.put("provinceId", provinceId);
		resMap.put("cityId", cityId);
		resMap.put("code",ConstantsUtils.RETURN_CODE_SUCCESS);
		return resMap;
	}
	
	/**
	 * 用户地址删除
	 * @param addressId
	 * @return
	 */
	@RequestMapping(value="/shoppingAddressDelete",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> shoppingAddressDelete(Integer addressId)
	{
		Map<String,Object> resMap = new HashMap<String,Object>();
		resMap.put("status",ConstantsUtils.RETURN_CODE_FAILURE);
		TdUser tdUser = this.getCurrentUser();
		if(tdUser == null)
		{
			resMap.put("msg","请先登录");
			return resMap;
		}
		TdUserAddress userAddress = tdUserAddressService.findOne(addressId);
		if(addressId == null || userAddress == null)
		{
			resMap.put("msg","参数错误 -1");
			return resMap;
		}
		if(userAddress.getUid() != tdUser.getUid())
		{
			resMap.put("msg","参数错误 -2");
			return resMap;
		}
		tdUserAddressService.delete(addressId);
		resMap.put("status",ConstantsUtils.RETURN_CODE_SUCCESS);
		resMap.put("msg","成功");
		return resMap;
	}
	
	@RequestMapping(value = "/account")
	public String userAccount(ModelMap map)
	{
		TdUser tdUser = this.getCurrentUser();
		// 系统配置
		map.addAttribute("system", getSystem());
		TdUserAccount userAccount = tdUserAccountService.findOne(tdUser.getUid());
		if(userAccount == null)
		{
			userAccount = new TdUserAccount();
			userAccount.setUid(tdUser.getUid());
			userAccount.setAmount(new BigDecimal(0));
			userAccount.setStatus(TdUserAccount.ACCOUNT_STATUS_ACTIVE);
			userAccount.setUpdateBy(0);
			tdUserAccountService.insert(userAccount);
		}
		map.addAttribute("account",userAccount);
		return "/mobile/user/account";
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
	
	/*
	 * 实体店消息审核
	 */
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
	 * 商品管理
	 */
	@RequestMapping("/productManage")
	public String productManage(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		Byte supplierType = this.getCurrentUser().getSupplierType();
		if(supplierType == null || supplierType.equals(Byte.valueOf("0"))){
			modelMap.addAttribute("isSupplier", false);
		}else{
			modelMap.addAttribute("isSupplier", true);
		}
		return "/mobile/user/productManage";	
	}
	
	/*
	 * 查看我的商品
	 */
	@RequestMapping("/lookMyProduct")
	public String lookMyProduct(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		return "/mobile/user/myProduct";	
	}
	
	/*
	 * 搜索我的商品
	 */
	@RequestMapping("/searchMyProduct")
	public String searchMyProduct(TdProductCriteria sc, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		int pageNo = sc.getPageNo();
		TdUser currentUser = this.getCurrentUser();
		sc.setPageSize(3);
		TdAgentSearchCriteria asc = new TdAgentSearchCriteria();
		asc.setFlag(false);
		asc.setUid(currentUser.getUid());
		List<TdAgent> agentList = tdAgentService.findBySearchCriteria(asc);
		List<Integer> productTypeIdList = new ArrayList<Integer>();
		if(agentList != null && agentList.size() > 0){
			for(TdAgent agent : agentList){
				productTypeIdList.add(agent.getProductTypeId());
			}
		}
		if(productTypeIdList.size() > 0){
			sc.setProductTypeIds(productTypeIdList);
		}
		List<TdProduct> productList = tdProductService.findBySearchCriteria(sc);
		if(sc.getPageNo() == pageNo){
			modelMap.addAttribute("productList", productList);			
		}else{
			modelMap.addAttribute("sc", sc);
		}
		return "/mobile/user/myProductTemplate";	
	}
	
	/*
	 * 查看供应商商品
	 */
	@RequestMapping("/lookSupplierProduct")
	public String lookSupplierProduct(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		return "/mobile/user/SupplierProduct";	
	}
	
	/*
	 * 搜索供应商商品
	 */
	@RequestMapping("/searchSupplierProduct")
	public String searchSupplierProduct(TdProductCriteria sc, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		int pageNo = sc.getPageNo();
		TdUser currentUser = this.getCurrentUser();
		sc.setPageSize(3);
		sc.setUid(currentUser.getUid());
		List<TdProduct> productList = tdProductService.findBySearchCriteria(sc);
		if(sc.getPageNo() == pageNo){
			modelMap.addAttribute("productList", productList);			
		}else{
			modelMap.addAttribute("sc", sc);
		}
		return "/mobile/user/myProductTemplate";	
	}
	
	/*
	 * 商品上下架
	 */
	@RequestMapping(value="/goOnshelf", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,String> goOnshelf(Integer id, Boolean onshelf, HttpServletRequest request, HttpServletResponse response) {
		Map<String,String> res = new HashMap<String,String>();
		try{
			TdProduct product = tdProductService.findOne(id);
			product.setOnshelf(onshelf);
			tdProductService.save(product);
			res.put("code", "1");
			if(onshelf){
				res.put("msg", "上架成功。");
			}else{
				res.put("msg", "下架成功。");
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("上下架失败");
			res.put("code", "0");
			if(onshelf){
				res.put("msg", "上架失败。");
			}else{
				res.put("msg", "下架失败。");
			}
		}
		return res;
	}
	
	/*
	 * 商品保存
	 */
	@RequestMapping(value="/saveProduct", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,String> saveProduct(Boolean isFreeProduct, TdProduct product,  String[] attachment, TdProductDescription productDescription, TdProductSku productSku, String attributeAssembleStr, HttpServletRequest request, HttpServletResponse response) {
		Map<String,String> res = new HashMap<String,String>();
		TdUser currentUser = this.getCurrentUser();
		String[] atrributeArray = attributeAssembleStr.split("_");
		try{
			// 商品
			if(isFreeProduct != null && isFreeProduct){
				product.setKind(Byte.valueOf("3"));
			}else{
				product.setKind(Byte.valueOf("1"));
			}
			product.setUid(currentUser.getUid());
			product.setSpecification(true);
			product.setStatus(Byte.valueOf("2"));
			if(attachment != null && attachment.length > 0){
				product.setImageUrl(attachment[0]);
			}
			product.setCreateTime(new Date());
			product.setUpdateTime(new Date());
			product.setUpdateBy(currentUser.getUid());
			product.setQuantum(productSku.getStock() * atrributeArray.length);
			tdProductService.save(product);
			// 商品图片
			if(attachment != null && attachment.length > 0){
				for(String a : attachment){
					TdProductAttachment pa = new TdProductAttachment();
					pa.setAttachment(a);
					// 格式：/tdStore/static/imgs/product/2016/7/21/78c60dcc-0938-4128-85fb-1291d75be098.jpg
					String fileName = a.substring(a.lastIndexOf("/") + 1);
					pa.setFilename(fileName);
					pa.setProductId(product.getId());
					tdProductAttachmentService.save(pa);
				}
			}
			// 商品描述
			productDescription.setType(Byte.valueOf("1"));
			productDescription.setProductId(product.getId());
			productDescription.setUpdateBy(currentUser.getUid());
			productDescription.setUpdateTime(new Date());
			tdProductDescriptionService.save(productDescription);
			// 货品	atrributeArray格式：规格1=gv1,规格2=gv21	 保存规格格式：{"颜色":"红色","尺码":"38"}
			for(String at : atrributeArray){
				TdProductSku ps = new TdProductSku();
				ps.setHighPrice(productSku.getHighPrice());
				ps.setLowPrice(productSku.getLowPrice());
				ps.setMarketPrice(productSku.getMarketPrice());
				ps.setProductId(product.getId());
				ps.setSalesPrice(productSku.getSalesPrice());
				ps.setStatus(Byte.valueOf("2"));
				ps.setStock(productSku.getStock());
				ps.setSupplierPrice(productSku.getSupplierPrice());
				ps.setUpdateBy(currentUser.getUid());
				ps.setUpdateTime(new Date());
				// 设置规格
				JSONObject jo = new JSONObject();
				String[] gga = at.split(",");
				for(String ggas : gga){
					try {
						jo.put(ggas.split("=")[0], ggas.split("=")[1]);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				ps.setSpecifications(jo.toString());
				tdProductSkuService.save(ps);
				
			}
		}catch(Exception e){
			logger.error("商品保存失败。");
			e.printStackTrace();
			res.put("status", "n");
			res.put("info", "商品保存失败。");
		}
		res.put("status", "y");
		res.put("info", "商品保存成功。");
		
		return res;
	}
	
	/*
	 * 下级会员
	 */
	@RequestMapping("/downUserList")
	public String downThreeUserList(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		modelMap.addAttribute("isTheFirstTime", "yes");
		modelMap.addAttribute("pageNo", "1");
		return "/mobile/user/downUserList";	
	}
	
	/*
	 * 加载对应商品类别的规格
	 */
	@RequestMapping("/flushAttribute")
	public String flushAttribute(Integer typeId, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		
		List<TdProductTypeAttribute> typeAttributeList = tdProductTypeAttributeService.findByTypeId(typeId);
		List<TdProductAttribute> attributeList = new ArrayList<>();
		if(typeAttributeList != null){
			for(TdProductTypeAttribute ta : typeAttributeList){
				TdProductAttribute attribute = tdProductAttributeService.findOne(ta.getAttriId());
				// 设置attribute的option
				if(attribute != null){
					TdProductAttributeOptionCriteria sc = new TdProductAttributeOptionCriteria();
					sc.setFlag(false);
					sc.setAttriId(attribute.getAttriId());
					List<TdProductAttributeOption> aoList = tdProductAttributeOptionService.findBySearchCriteria(sc);
					attribute.setTdProductAttributeOptionList(aoList);
				}
				attributeList.add(attribute);
			}
		}
		modelMap.addAttribute("attributeList", attributeList);
		return "/mobile/user/productAttributeTemplate";	
	}
	
	/*
	 * 上传商品
	 */
	@RequestMapping("/addProduct")
	public String addProduct(Boolean isFreeProduct, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		TdProductTypeCriteria tsc = new TdProductTypeCriteria();
		tsc.setStatus((byte) 1);
		List<TdProductType> productTypeList = tdProductTypeService.findAll(tsc);
		modelMap.addAttribute("productTypeList", productTypeList);
		modelMap.addAttribute("isFreeProduct", isFreeProduct);
		return "/mobile/user/addProduct";	
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
