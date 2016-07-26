package com.tiandu.mobile.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tiandu.comment.search.TdProductCommentCrateria;
import com.tiandu.comment.service.TdProductCommentService;
import com.tiandu.common.controller.BaseController;
import com.tiandu.custom.entity.TdUser;
/**
 * 
 * @author Max
 * 会员中心—我的评价
 * 
 * 创建时间：2016年7月21日 下午3:46:02
 * 
 */
@Controller
@RequestMapping("/mobile/user")
public class MobileUserCommentController extends BaseController{

	@Autowired
	private TdProductCommentService tdProductCommentService; 
	
	@RequestMapping("/productComment")
	public String comment(HttpServletRequest req,ModelMap map)
	{
		// 系统配置
		map.addAttribute("system", getSystem());
		TdUser user = getCurrentUser();
		if(null==user)
		{
			return "redirect:/mobile/login";
		}
		
		TdProductCommentCrateria sc = new TdProductCommentCrateria();
		sc.setUid(user.getUid());
		
		
		map.addAttribute("commentList", tdProductCommentService.findBySearchCriteria(sc));
		return "/mobile/user/comment/comment";	
	}
	
	@RequestMapping("/comment/search")
	public String commentSearch(TdProductCommentCrateria sc,Integer page,HttpServletRequest req,ModelMap map)
	{
		sc.setPageNo(page);
		
		int count = tdProductCommentService.getTotalPageCount(sc);
		if(count >= page)
		{
			map.addAttribute("commentList", tdProductCommentService.findBySearchCriteria(sc));
			map.addAttribute("sc", sc);
		}
		
		return "/mobile/user/comment/commentmore";
	}
}
