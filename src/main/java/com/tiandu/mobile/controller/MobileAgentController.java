package com.tiandu.mobile.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tiandu.article.entity.TdArticleCategory;
import com.tiandu.article.entity.TdArticleTitle;
import com.tiandu.article.search.TdArticleCategorySearchCriteria;
import com.tiandu.article.search.TdArticleTitleSearchCriteria;
import com.tiandu.article.service.TdArticleCategoryService;
import com.tiandu.article.service.TdArticleContentService;
import com.tiandu.article.service.TdArticleTitleService;
import com.tiandu.common.controller.BaseController;
import com.tiandu.custom.entity.TdExperienceStore;
import com.tiandu.custom.search.TdExperienceStoreSearchCriteria;
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
		if("单类代理".equals(agent.getGroupIdStr()))
		{
			// 查找所有分类
//			TdProductTypeCriteria sc = new TdProductTypeCriteria();
//			sc.setStatus((byte)1);
//			sc.setOrderBy("2");
//			map.addAttribute("typeList", tdProductTypeService.findAll(sc));
//			
//			TdDistrictSearchCriteria dsc = new TdDistrictSearchCriteria();
//			dsc.setFlag(false);
//			dsc.setUpid(0);
//			map.addAttribute("districtList", tdDistrictService.findBySearchCriteria(dsc));
//			map.addAttribute("dissc", dsc);
//			
//			// 代理查询条件
//			TdExperienceStoreSearchCriteria esc = new TdExperienceStoreSearchCriteria();
//			// 查找全国所有代理
//			esc.setRegionId(0);
//			esc.setFlag(false);
//			List<TdExperienceStore> ecperList = tdExperienceStoreService.findBySearchCriteria(esc);
//			
//			map.addAttribute("experTypeIds",getExperTypeIds(ecperList));
		}
		else if("分公司".equals(agent.getGroupIdStr()))
		{
			return "/mobile/agent/company";
		}
		
		return "/mobile/agent/typelist";
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
				esc.setRegionId(0);
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
				esc.setRegionId(sc.getProvinceId());
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
				esc.setRegionId(sc.getCityId());
			}
			
			// 选择区县级，复制代理查询地区ID参数
			if(null != sc.getRegionId())
			{
				district = tdDistrictService.findOne(sc.getRegionId());
				esc.setRegionId(sc.getRegionId());
				map.addAttribute("regin",district);
			}
		}
		
		// 查找当前选择地区已的所有代理
		esc.setFlag(false);
		List<TdExperienceStore> ecperList = tdExperienceStoreService.findBySearchCriteria(esc);
		
		map.addAttribute("experTypeIds",getExperTypeIds(ecperList));
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
	public String getExperTypeIds(List<TdExperienceStore> experList)
	{
		StringBuffer ids = new StringBuffer();
		
		if(null != experList && experList.size() > 0)
		{
			for (TdExperienceStore tdExperienceStore : experList) {
				if(null != tdExperienceStore.getStoreTypeIds())
				{
					String[] typeids = tdExperienceStore.getStoreTypeIds().split(",");
					for (int i = 0; i < typeids.length; i++) 
					{
						if(!ids.toString().contains(typeids[i]))
						{
							ids.append(typeids[i]);
						}
					}
				}
			}
		}
		return ids.toString();
	}
	
}
