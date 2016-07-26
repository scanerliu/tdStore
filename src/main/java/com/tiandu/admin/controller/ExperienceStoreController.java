package com.tiandu.admin.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tiandu.common.controller.BaseController;
import com.tiandu.custom.entity.TdExperienceStore;
import com.tiandu.custom.entity.TdUserSupplier;
import com.tiandu.custom.search.TdExperienceStoreSearchCriteria;
import com.tiandu.custom.service.TdExperienceStoreService;
import com.tiandu.custom.service.TdUserService;

/**
 * 
 * @author L. Gao
 *
 */
@Controller
@RequestMapping("/admin/experiencestore")
public class ExperienceStoreController extends BaseController {

	private final Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private TdExperienceStoreService tdExperienceStoreService;
	
	@Autowired
	private TdUserService tdUserService;

	@RequestMapping("/list")
	public String list(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		return "/admin/experienceStore/experienceStoreList";
	}
	
	@RequestMapping("/search")
	public String search(TdExperienceStoreSearchCriteria sc, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		List<TdExperienceStore> experienceStoreList = tdExperienceStoreService.findBySearchCriteria(sc);
		
	    modelMap.addAttribute("experienceStoreList", experienceStoreList);
	    modelMap.addAttribute("sc", sc) ;
		return "/admin/experienceStore/experienceStoreListbody";
	}
	
	@RequestMapping("/edit")
	public String edit(Integer id, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		TdExperienceStore tdExperienceStore = null;
		if(null!=id && id != 0){
			tdExperienceStore = tdExperienceStoreService.findOne(id);
			if(tdExperienceStore.getUid() != null){
				tdExperienceStore.setUser(tdUserService.findOne(tdExperienceStore.getUid()));
			}
			if(tdExperienceStore.getUpdateBy() != null){
				tdExperienceStore.setUpdatePerson(tdUserService.findOne(tdExperienceStore.getUpdateBy()));
			}
			if(tdExperienceStore.getStoreImages() != null){
				String[] imgArray = tdExperienceStore.getStoreImages().split(":");
				List<String> imgList = new ArrayList<>();
				for(String img : imgArray){
					imgList.add(img);
				}
				modelMap.addAttribute("imgList", imgList);
			}
		}else{
			tdExperienceStore = new TdExperienceStore();	
		}
		modelMap.addAttribute("tdExperienceStore", tdExperienceStore);
		return "/admin/experienceStore/experienceStoreForm";
	}
	
	@RequestMapping(value="/save", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,String> save(TdExperienceStore experienceStore, HttpServletRequest request, HttpServletResponse response) {
		Map<String,String> res = new HashMap<String,String>(); 
		if(null!=experienceStore){
			try {
				TdExperienceStore tes = tdExperienceStoreService.findOne(experienceStore.getId());
				tes.setUpdateBy(this.getCurrentUser().getUid());
				tes.setUpdateTime(new Date());
				tes.setStatus(experienceStore.getStatus());
				tdExperienceStoreService.save(tes);
				res.put("msg", "供应商资质审核成功。");
				res.put("code", "1");
				return res;
			}catch (Exception e) {
				logger.error("供应商资质审核失败错误信息:"+e);
				res.put("code", "0");
				res.put("msg", "供应商资质审核失败。");
				e.printStackTrace();
				return res;
			}
		}else{
			res.put("code", "0");
			res.put("msg", "供应商资质审核失败。");
			return res;
		}
	}
	
	@RequestMapping(value="/goCheck", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,String> goCheck(Integer id, Boolean isPass, HttpServletRequest request, HttpServletResponse response) {
		Map<String,String> res = new HashMap<String,String>(); 
		if(id == null){
			res.put("code", "0");
			res.put("msg", "体验店审核失败。");
			logger.error("由于审核Id为空，体验店审核失败。");
			return res;
		}
		TdExperienceStore tes = tdExperienceStoreService.findOne(id);
		if(isPass){
			tes.setStatus(Byte.valueOf("2"));
		}else{
			tes.setStatus(Byte.valueOf("3"));
		}
		tes.setUpdateTime(new Date());
		tes.setUpdateBy(this.getCurrentUser().getUid());
		tdExperienceStoreService.save(tes);
		res.put("code", "1");
		res.put("msg", "体验店审核成功。");
		return res;
	}
	
	@RequestMapping(value="/changeSort", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,String> changeSort(Integer id, Integer sort, HttpServletRequest request, HttpServletResponse response) {
		Map<String,String> res = new HashMap<String,String>(); 
		if(id == null){
			res.put("code", "0");
			res.put("msg", "体验店修改排序失败。");
			logger.error("由于审核Id为空，体验店修改排序失败。");
			return res;
		}
		TdExperienceStore tes = tdExperienceStoreService.findOne(id);
		if(sort == null){
			res.put("code", "0");
			res.put("msg", "体验店修改排序失败。");
			logger.error("由于排序字段为空，体验店修改排序失败。");
			return res;
		}else{
			tes.setSort(sort);
			tdExperienceStoreService.save(tes);
			res.put("code", "1");
			res.put("msg", "体验店修改排序成功。");
			return res;
		}
	}
}
