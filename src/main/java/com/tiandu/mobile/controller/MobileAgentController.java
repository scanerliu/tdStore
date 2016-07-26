package com.tiandu.mobile.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tiandu.article.entity.TdArticleCategory;
import com.tiandu.article.entity.TdArticleTitle;
import com.tiandu.article.search.TdArticleCategorySearchCriteria;
import com.tiandu.article.search.TdArticleTitleSearchCriteria;
import com.tiandu.article.service.TdArticleCategoryService;
import com.tiandu.article.service.TdArticleContentService;
import com.tiandu.article.service.TdArticleTitleService;
import com.tiandu.common.controller.BaseController;
import com.tiandu.custom.entity.TdAgent;
import com.tiandu.custom.entity.TdExperienceStore;
import com.tiandu.custom.entity.TdUser;
import com.tiandu.custom.search.TdAgentSearchCriteria;
import com.tiandu.custom.search.TdExperienceStoreSearchCriteria;
import com.tiandu.custom.service.TdAgentService;
import com.tiandu.custom.service.TdExperienceStoreService;
import com.tiandu.district.entity.TdDistrict;
import com.tiandu.district.search.TdDistrictSearchCriteria;
import com.tiandu.district.service.TdDistrictService;
import com.tiandu.product.entity.TdAgentProduct;
import com.tiandu.product.search.TdAgentProductSearchCriteria;
import com.tiandu.product.search.TdProductTypeCriteria;
import com.tiandu.product.service.TdAgentProductService;
import com.tiandu.product.service.TdProductTypeService;

/**
 * 
 * @author Max
 * 
 * 修改时间：2016年7月14日 上午11:39:21
 */
@Controller
@RequestMapping("/mobile/agent")
public class MobileAgentController extends BaseController{

	
	@Autowired
	private TdAgentProductService tdAgentProductService;
	
	@Autowired
	private TdProductTypeService tdProductTypeService;
	
	@Autowired
	private TdArticleCategoryService tdArticleCategoryService;
	
	@Autowired
	private TdArticleTitleService tdArticleTitleService;
	
	@Autowired
	private TdArticleContentService tdArticleContentService;
	
	@Autowired
	private TdExperienceStoreService tdExperienceStoreService;
	
	@Autowired
	private TdAgentService tdAgentService;
	
	@Autowired
	private TdDistrictService tdDistrictService;
	
	@RequestMapping("/list")
	public String list(HttpServletRequest req,ModelMap map)
	{
		// 系统配置
		map.addAttribute("system", getSystem());
				
		TdAgentProductSearchCriteria sc = new TdAgentProductSearchCriteria();
		sc.setFlag(false);
		map.addAttribute("agentList", tdAgentProductService.findBySearchCriteria(sc));
		
		return "/mobile/agent/list";
	}
	
	@RequestMapping("/producttype")
	public String agentProductType(Integer agentId,
				HttpServletRequest req,ModelMap map)
	{
		// 系统配置
		map.addAttribute("system", getSystem());
		
		TdAgentProduct agent = tdAgentProductService.findOne(agentId);
		if(null == agent)
		{
			return "";
		}
		
		map.addAttribute("agent", agent);
		
		if("分公司".equals(agent.getGroupIdStr()))
		{
			return "/mobile/agent/company";
		}
		if(agent.getLevel() == 4)
		{
			return "/mobile/agent/agentform";
		}
		
		return "/mobile/agent/typelist";
	}
	
	@RequestMapping("/search/addr")
	public String searchAddr(TdDistrictSearchCriteria sc,HttpServletRequest req,ModelMap map)
	{
		sc.setFlag(false);
		sc.setUpid(0);
		map.addAttribute("districtList", tdDistrictService.findBySearchCriteria(sc));
		map.addAttribute("dissc", sc);
		
		
		// 如果选择省级，查找市级
		if(null != sc.getProvinceId())
		{
			sc.setUpid(sc.getProvinceId());
			map.addAttribute("cityList", tdDistrictService.findBySearchCriteria(sc));
			
			map.addAttribute("province", tdDistrictService.findOne(sc.getProvinceId()));
		}
			
		// 如果选择市级，查找区县级
		if(null != sc.getCityId())
		{
			sc.setUpid(sc.getCityId());
			map.addAttribute("regionList", tdDistrictService.findBySearchCriteria(sc));
			
			map.addAttribute("city",tdDistrictService.findOne(sc.getCityId()));
		}
		
		// 选择区县级，复制代理查询地区ID参数
		if(null != sc.getRegionId())
		{
			map.addAttribute("regin",tdDistrictService.findOne(sc.getRegionId()));
		}
		return "/mobile/agent/agent_addr";
	}
	
	/**
	 * 
	 * @author Max
	 * 
	 * 保存体验店
	 * 
	 */
	@RequestMapping(value= "/addagent", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> agentAdd(TdExperienceStore experience,
				Integer provinceId,Integer cityId,
				HttpServletRequest req,ModelMap map)
	{
		Map<String,Object> res = new HashMap<>();
		res.put("code", 0);
		TdUser user = this.getCurrentUser();
		if(null == user){
			res.put("code", "请重新登录");
			return res;
		}
		
		if(null == experience)
		{
			res.put("msg", "参数错误");
			return res;
		}
		if(null == experience.getRegionId()){
			res.put("msg", "请正确选择地区");
			return res;
		}
		if(null == experience.getAddress() || "".equals(experience.getAddress().trim())){
			res.put("msg", "请输入信息地址");
			return res;
		}
		if(null == experience.getTelphone() || !isMobileNO(experience.getTelphone())){
			res.put("msg", "请正确输入手机号");
			return res;
		}
		
		experience.setUid(user.getUid());
		TdDistrict province = tdDistrictService.findOne(provinceId);
		TdDistrict city = tdDistrictService.findOne(cityId);
		TdDistrict district = tdDistrictService.findOne(experience.getRegionId());
		
		StringBuffer str = new StringBuffer();
		if(null != province){
			str.append(province.getName());
		}
		if(null != city){
			str.append(city.getName());
		}
		if(null != district){
			str.append(district.getName());
		}
		if(null != experience.getAddress()){
			str.append(experience.getAddress());
		}
		// 详细地址
		experience.setRegionFullName(str.toString());
		experience.setStatus((byte)2);
		experience.setCreateTime(new Date());
		experience.setSort(1);
		
		tdExperienceStoreService.save(experience);
		res.put("code", 1);
		
		return res;
	}
	
	/**
	 * @author Max
	 * 
	 * 各级代理选择地区，查询下一级地区
	 * 查询所选地区已代理的分类
	 * 全国代理无需选择地区提示
	 * 一级代理无需选择二级地区提示
	 * 
	 */
	@RequestMapping(value = "/search/distric",method = RequestMethod.POST)
	public String districSearch(Integer agentId,TdDistrictSearchCriteria sc,
			HttpServletRequest req,ModelMap map)
	{
		// 系统配置
		map.addAttribute("system", getSystem());

		// 查找所有分类
		TdProductTypeCriteria tsc = new TdProductTypeCriteria();
		tsc.setStatus((byte)1);
		tsc.setOrderBy("2");
		map.addAttribute("typeList", tdProductTypeService.findAll(tsc));
		
		TdAgentProduct agent = tdAgentProductService.findOne(agentId);
		
		map.addAttribute("agent", agent);
		
		TdDistrict district = new TdDistrict();
		
		// 代理查询条件
		TdExperienceStoreSearchCriteria esc = new TdExperienceStoreSearchCriteria();
		TdAgentSearchCriteria asc = new TdAgentSearchCriteria();
		
		// 一级地区
		sc.setFlag(false);
		sc.setUpid(0);
		map.addAttribute("districtList", tdDistrictService.findBySearchCriteria(sc));
		map.addAttribute("dissc", sc);
		
		
		if(agent.getLevel() == 1)
		{
			// 如果选择为全国代理，并且选择地区，信息提示
			if(null != sc.getProvinceId())
			{
				map.addAttribute("msg", "当前选择为全国代理，无需选择下级地区");
				asc.setRegionId(0);
			}
		}
		else
		{
			// 如果选择省级，查找市级
			if(null != sc.getProvinceId())
			{
				sc.setUpid(sc.getProvinceId());
				map.addAttribute("cityList", tdDistrictService.findBySearchCriteria(sc));
				district = tdDistrictService.findOne(sc.getProvinceId());
				
				map.addAttribute("province", district);
				asc.setRegionId(sc.getProvinceId());
			}
		}
			
		
		if(agent.getLevel() == 2)
		{
			// 如果选择为一级代理，并且选择二级地区，信息提示
			if(null != sc.getCityId())
			{
				map.addAttribute("msg", "当前选择为一级代理，无需选择下级地区");
			}
		}
		else if(agent.getLevel() == 3 || agent.getLevel() == 4)
		{
			// 如果选择市级，查找区县级
			if(null != sc.getCityId())
			{
				sc.setUpid(sc.getCityId());
				map.addAttribute("regionList", tdDistrictService.findBySearchCriteria(sc));
				district = tdDistrictService.findOne(sc.getCityId());
				
				map.addAttribute("city",district);
				asc.setRegionId(sc.getCityId());
			}
			
			// 选择区县级，复制代理查询地区ID参数
			if(null != sc.getRegionId())
			{
				district = tdDistrictService.findOne(sc.getRegionId());
				asc.setRegionId(sc.getRegionId());
				map.addAttribute("regin",district);
			}
		}
		
		// 查找当前选择地区已的所有代理
		esc.setFlag(false);
//		List<TdExperienceStore> ecperList = tdExperienceStoreService.findBySearchCriteria(esc);
		
		List<TdAgent> agentList = tdAgentService.findBySearchCriteria(asc);
		
		map.addAttribute("experTypeIds",getExperTypeIds(agentList));
		map.addAttribute("district", district);
		
		return "/mobile/agent/typelist_body";
	}
	
	@RequestMapping(value="/detail",method=RequestMethod.POST)
	public String agentDetail(Integer agentId,Integer typeId,Integer regionId,
				HttpServletRequest req,ModelMap map)
	{
		// 系统配置
		map.addAttribute("system", getSystem());
		
		map.addAttribute("agent", tdAgentProductService.findOne(agentId));
		map.addAttribute("regionId", regionId);
		map.addAttribute("typeId", typeId);
		
		return "/mobile/agent/agent";
	}
	
	
	@RequestMapping("/article")
	public String article(HttpServletRequest req,ModelMap map)
	{
		// 系统配置
		map.addAttribute("system", getSystem());
		
		// 创客联盟代理条款
		TdArticleCategorySearchCriteria asc = new TdArticleCategorySearchCriteria();
		asc.setName("创客联盟代理条款");
		List<TdArticleCategory> articleCatelist = tdArticleCategoryService.findBySearchCriteria(asc);
		
		if(null != articleCatelist && articleCatelist.size() > 0)
		{
			TdArticleTitleSearchCriteria atsc = new TdArticleTitleSearchCriteria();
			atsc.setCid(articleCatelist.get(0).getCid());
			List<TdArticleTitle> articleList = tdArticleTitleService.findBySearchCriteria(atsc);
			
			if(null != articleList && articleList.size() > 0)
			{
				TdArticleTitle article = articleList.get(0);
				if(null != article)
				{
					article.setArticleContent(tdArticleContentService.findOne(article.getAid()).getContent());
				}
				map.addAttribute("article", articleList.get(0));
			}
		}
		return "/mobile/agent/article";
	}
	
	@RequestMapping("/exper")
	public String exper(Integer id,HttpServletRequest req,ModelMap map)
	{
		TdUser user = getCurrentUser();
		
		if(user == null)
		{
			return "redirect:/mobile/login";
		}
		if(null == id ){
			return "redirect:404";
		}
		
//		new 
		
		
		return "";
	}
	
	
	@RequestMapping("/dopay")
	public String dopay(Integer id,HttpServletRequest req,ModelMap map)
	{
		// 系统配置
		map.addAttribute("system", getSystem());
		
		return "/mobile/agent/paylist";
	}
	
	
	/**
	 * 拼接已代理的分类ID字符串
	 * 
	 * @author Max
	 * 
	 */
	public String getExperTypeIds(List<TdAgent> agentList)
	{
		StringBuffer ids = new StringBuffer();
		
		if(null != agentList && agentList.size() > 0)
		{
			for (TdAgent agent : agentList) {
				if(null != agent.getProductTypeId()){
					Integer typeId= agent.getProductTypeId();
					if(!ids.toString().contains("["+typeId+"]"))
					{
						ids.append("["+typeId+"]");
					}
				}
			}
		}
		return ids.toString();
	}
	
	public boolean isMobileNO(String mobiles) {
		Pattern p = Pattern.compile("^(0|86|17951|[0-9]{3})?([0-9]{8})|((13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8})$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
		}
}
