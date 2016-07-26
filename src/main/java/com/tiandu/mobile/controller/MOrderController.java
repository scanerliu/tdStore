package com.tiandu.mobile.controller;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tiandu.common.controller.BaseController;
import com.tiandu.complaint.entity.TdComplaint;
import com.tiandu.custom.entity.TdUser;
import com.tiandu.express.entity.TdExpress;
import com.tiandu.express.search.TdExpressSearchCriteria;
import com.tiandu.express.service.TdExpressService;
import com.tiandu.order.entity.TdJointOrder;
import com.tiandu.order.entity.TdOrder;
import com.tiandu.order.entity.TdOrderShipment;
import com.tiandu.order.search.TdOrderSearchCriteria;
import com.tiandu.order.search.TdOrderShipmentSearchCriteria;
import com.tiandu.order.service.TdOrderLogService;
import com.tiandu.order.service.TdOrderService;
import com.tiandu.order.service.TdOrderShipmentService;
import com.tiandu.order.vo.OperResult;

/**
 * 
 * @author liuxinbing
 *
 */
@Controller
@RequestMapping("/mobile/order")
public class MOrderController extends BaseController {
	
	private final Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private TdOrderService tdOrderService;
	
	@Autowired
	private TdExpressService tdExpressService;
	
	@Autowired
	private TdOrderShipmentService tdOrderShipmentService;
	
	@Autowired
	private TdOrderLogService tdOrderLogService;
	
	@RequestMapping("/list")
	public String list(TdOrderSearchCriteria sc, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		modelMap.addAttribute("sc", sc) ;
	    return "/mobile/order/list";
	}
	
	@RequestMapping("/search")
	public String search(TdOrderSearchCriteria sc, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		TdUser currUser = this.getCurrentUser();
		//获取主菜单
		sc.setUid(currUser.getUid());
		sc.setGetProductSku(true);
		sc.setPageSize(3);
		List<TdOrder> orderList = tdOrderService.findBySearchCriteria(sc);
	    modelMap.addAttribute("orderList", orderList) ;
	    modelMap.addAttribute("sc", sc) ;
		return "/mobile/order/listbody";
	}
	
	
	@RequestMapping("/refundlist")
	public String refundlist(TdOrderShipmentSearchCriteria sc, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		modelMap.addAttribute("sc", sc) ;
	    return "/mobile/order/refundlist";
	}
	
	@RequestMapping("/searchreturn")
	public String searchreturn(TdOrderShipmentSearchCriteria sc, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		TdUser currUser = this.getCurrentUser();
		//
		sc.setGetOrder(true);
		sc.setUid(currUser.getUid());
		sc.setType(Byte.valueOf("2"));
		sc.setPageSize(3);
		List<TdOrderShipment> shipList = tdOrderShipmentService.findBySearchCriteria(sc);
	    modelMap.addAttribute("shipList", shipList) ;
	    modelMap.addAttribute("sc", sc) ;
		return "/mobile/order/returnlistbody";
	}
	
	@RequestMapping("/detail{orderId}")
	public String detail(@PathVariable("orderId") Integer orderId, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		TdUser currUser = this.getCurrentUser();
		TdOrder order = null;
		if(null!=orderId && orderId>0){
			order = tdOrderService.findDetail(orderId);
		}
		if(null==order || !order.getUserId().equals(currUser.getUid())){
		    return "redirect:404";
		}
		modelMap.addAttribute("order", order) ;
		return "/mobile/order/detail";
	}
	
	@RequestMapping("/applyrefund{orderId}")
	public String applyRefund(@PathVariable("orderId") Integer orderId, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		TdUser currUser = this.getCurrentUser();
		TdOrder order = null;
		if(null!=orderId && orderId>0){
			order = tdOrderService.findDetail(orderId);
		}
		if(null==order || !order.getUserId().equals(currUser.getUid())){
		    return "redirect:404";
		}
		modelMap.addAttribute("order", order) ;
		return "/mobile/order/applyrefund";
	}
	
	@RequestMapping("/refund")
	public String refund(TdOrderShipment shipment, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		TdUser currUser = this.getCurrentUser();
		TdOrder order = null;
		if(null!=shipment.getOrderId() && shipment.getOrderId()>0){
			order = tdOrderService.findDetail(shipment.getOrderId());
		}
		if(null==order || !order.getUserId().equals(currUser.getUid())){
		    return "redirect:404";
		}
		OperResult result = tdOrderService.applyRefundOrder(order, shipment);
		modelMap.addAttribute("result", result) ;
		return "/mobile/order/refund";
	}
	
	@RequestMapping("/refundtract{shipId}")
	public String refundtract(@PathVariable("shipId") Integer shipId, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		TdUser currUser = this.getCurrentUser();
		TdOrderShipment shipment = null;
		TdOrder order = null;
		if(null!=shipId && shipId>0){
			shipment = tdOrderShipmentService.findOne(shipId);
		}
		if(null==shipment && null!= shipment.getOrderId()){
		    return "redirect:404";
		}
		order = tdOrderService.findOne(shipment.getOrderId());
		if(null==order || !order.getUserId().equals(currUser.getUid())){
			return "redirect:404";
		}		
		//查询物流公司
		TdExpressSearchCriteria sc = new TdExpressSearchCriteria();
		sc.setFlag(false);
		List<TdExpress> expressList = tdExpressService.findBySearchCriteria(sc);
		modelMap.addAttribute("expressList", expressList);		
	    modelMap.addAttribute("shipment", shipment) ;
		return "/mobile/order/refundtract";
	}
	@RequestMapping("/savetract")
	@ResponseBody
	public Map<String,String> savetract(TdOrderShipment ship, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		Map<String, String> map = new HashMap<String, String>();
		TdUser currUser = this.getCurrentUser();
		Date now = new Date();
		TdOrder order = null;
		TdOrderShipment shipment = null;
		if(null!=ship.getId() && ship.getId()>0){
			shipment = tdOrderShipmentService.findOne(ship.getId());
		}
		if(null==shipment || null== shipment.getOrderId()){
			map.put("code", "0");
			map.put("msg", "录入物流单号失败：退款申请不存在！");
			return map;
		}
		order = tdOrderService.findOne(shipment.getOrderId());
		if(null==order || !order.getUserId().equals(currUser.getUid())){
			map.put("code", "0");
			map.put("msg", "录入物流单号失败：退款申请不存在！");
			return map;
		}
		TdOrderShipment saverecord = new TdOrderShipment();
		saverecord.setId(shipment.getId());
		saverecord.setTrackingId(ship.getTrackingId());
		saverecord.setTrackingNo(ship.getTrackingNo());
		saverecord.setUpdateBy(currUser.getUid());
		saverecord.setUpdateTime(now);
		saverecord.setStatus(Byte.valueOf("4"));
		tdOrderShipmentService.save(saverecord);
		map.put("code", "1");
		map.put("msg", "录入物流单号成功。");
		return map;
	}
	
	@RequestMapping("/complaint{orderId}")
	public String complaint(@PathVariable("orderId") Integer orderId, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		TdUser currUser = this.getCurrentUser();
		TdOrder order = null;
		if(null!=orderId && orderId>0){
			order = tdOrderService.findDetail(orderId);
		}
		if(null==order || !order.getUserId().equals(currUser.getUid())){
			return "redirect:404";
		}
		modelMap.addAttribute("order", order) ;
		return "/mobile/order/applycomplaint";
	}
	
	@RequestMapping("/complaintorder")
	public String complaintOrder(TdComplaint complaint, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		TdUser currUser = this.getCurrentUser();
		TdOrder order = null;
		if(null!=complaint.getOrderId() && complaint.getOrderId()>0){
			order = tdOrderService.findDetail(complaint.getOrderId());
		}
		if(null==order || !order.getUserId().equals(currUser.getUid())){
			return "redirect:404";
		}
		OperResult result = tdOrderService.complaintOrder(order, complaint);
		modelMap.addAttribute("result", result) ;
		return "/mobile/order/complaint";
	}
	
	/**
	 * 确认收货操作
	 * @param ship
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/receiptorder")
	@ResponseBody
	public Map<String,String> receiptOrder(Integer orderId, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		Map<String, String> map = new HashMap<String, String>();
		TdUser currUser = this.getCurrentUser();
		TdOrder order = null;
		if(null!=orderId && orderId>0){
			order = tdOrderService.findOne(orderId);
		}
		if(null==order || null== order.getOrderId() || order.getUserId()!=currUser.getUid()){
			map.put("code", "0");
			map.put("msg", "确认收货操作失败：订单申请不存在！");
			return map;
		}
		tdOrderService.receiptOrder(order, currUser);
		map.put("code", "1");
		map.put("msg", "确认收货操作成功。");
		return map;
	}
	
}
