package com.tiandu.order.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tiandu.common.utils.ConstantsUtils;
import com.tiandu.common.utils.WebUtils;
import com.tiandu.complaint.entity.TdComplaint;
import com.tiandu.complaint.service.TdComplaintService;
import com.tiandu.custom.entity.TdAgent;
import com.tiandu.custom.entity.TdBrancheCompany;
import com.tiandu.custom.entity.TdUser;
import com.tiandu.custom.entity.TdUserAccount;
import com.tiandu.custom.entity.TdUserAccountLog;
import com.tiandu.custom.entity.TdUserAddress;
import com.tiandu.custom.entity.TdUserIntegral;
import com.tiandu.custom.entity.TdUserIntegralLog;
import com.tiandu.custom.search.TdAgentSearchCriteria;
import com.tiandu.custom.search.TdBrancheCompanySearchCriteria;
import com.tiandu.custom.service.TdAgentService;
import com.tiandu.custom.service.TdBrancheCompanyService;
import com.tiandu.custom.service.TdUserAccountService;
import com.tiandu.custom.service.TdUserAddressService;
import com.tiandu.custom.service.TdUserIntegralService;
import com.tiandu.custom.service.TdUserService;
import com.tiandu.district.entity.TdDistrict;
import com.tiandu.district.service.TdDistrictService;
import com.tiandu.order.entity.TdJointOrder;
import com.tiandu.order.entity.TdOrder;
import com.tiandu.order.entity.TdOrderAddress;
import com.tiandu.order.entity.TdOrderLog;
import com.tiandu.order.entity.TdOrderProduct;
import com.tiandu.order.entity.TdOrderShipment;
import com.tiandu.order.entity.TdOrderShipmentItem;
import com.tiandu.order.entity.TdOrderSku;
import com.tiandu.order.entity.TdShoppingcartItem;
import com.tiandu.order.entity.mapper.TdJointOrderMapper;
import com.tiandu.order.entity.mapper.TdOrderAddressMapper;
import com.tiandu.order.entity.mapper.TdOrderLogMapper;
import com.tiandu.order.entity.mapper.TdOrderMapper;
import com.tiandu.order.entity.mapper.TdOrderProductMapper;
import com.tiandu.order.entity.mapper.TdOrderShipmentItemMapper;
import com.tiandu.order.entity.mapper.TdOrderShipmentMapper;
import com.tiandu.order.entity.mapper.TdOrderSkuMapper;
import com.tiandu.order.search.TdOrderSearchCriteria;
import com.tiandu.order.service.TdOrderService;
import com.tiandu.order.vo.OperResult;
import com.tiandu.order.vo.OrderForm;
import com.tiandu.order.vo.OrderPay;
import com.tiandu.order.vo.OrderRefund;
import com.tiandu.order.vo.ShoppingcartVO;
import com.tiandu.product.entity.TdAgentProduct;
import com.tiandu.product.service.TdAgentProductService;
import com.tiandu.system.entity.TdBenefit;
import com.tiandu.system.search.TdBenefitSearchCriteria;
import com.tiandu.system.service.TdBenefitService;
import com.tiandu.system.utils.ConfigUtil;

/**
 * 
 * @author liuxinbing
 *
 */
@Service("tdOrderService")
public class TdOrderServiceImpl implements TdOrderService{

	@Autowired
	private TdOrderMapper tdOrderMapper;
	@Autowired
	private TdOrderSkuMapper tdOrderSkuMapper;
	@Autowired
	private TdOrderAddressMapper tdOrderAddressMapper;
	@Autowired
	private TdOrderShipmentMapper tdOrderShipmentMapper;
	@Autowired
	private TdOrderShipmentItemMapper tdOrderShipmentItemMapper;
	@Autowired
	private TdOrderLogMapper tdOrderLogMapper;
	@Autowired
	private TdOrderProductMapper tdOrderProductMapper;
	@Autowired
	private TdJointOrderMapper tdJointOrderMapper;
	@Autowired
	private TdUserIntegralService tdUserIntegralService;
	@Autowired
	private TdUserAddressService tdUserAddressService;
	@Autowired
	private TdComplaintService tdComplaintService;
	@Autowired
	private TdBrancheCompanyService tdBrancheCompanyService;
	@Autowired
	private TdAgentService tdAgentService;
	@Autowired
	private TdAgentProductService tdAgentProductService;
	@Autowired
	private TdBenefitService tdBenefitService;
	@Autowired
	private TdUserService tdUserService;
	@Autowired
	private TdDistrictService tdDistrictService;
	@Autowired
	private TdUserAccountService tdUserAccountService;	
	@Autowired
	private ConfigUtil configUtil;
	
	private final Logger logger = Logger.getLogger(getClass());

	@Override
	public Integer deleteByPrimaryKey(Integer orderId) {
		return tdOrderMapper.deleteByPrimaryKey(orderId);
	}

	@Override
	public Integer save(TdOrder e) {
		if(null!=e.getOrderId()){//更新
			return tdOrderMapper.updateByPrimaryKey(e);
		}else{
			return tdOrderMapper.insert(e);
		}
	}

	@Override
	public TdOrder findOne(Integer id) {
		return tdOrderMapper.selectByPrimaryKey(id);
	}

	@Override
	public TdOrder findDetail(Integer id) {
		TdOrder order = tdOrderMapper.findDetail(id);
		if(null!=order && null!=order.getOrderId()){
			//查询订单货品
			List<TdOrderSku> skuList = tdOrderSkuMapper.findByOrderId(id);
			order.setSkuList(skuList);
			//查询订单收货地址
			TdOrderAddress address = tdOrderAddressMapper.selectByPrimaryKey(id);
			order.setOrderAddress(address);
			//代理商产品订单、图片订单查询商品详情
			if(ConstantsUtils.ORDER_KIND_AGENTPRODUCT.equals(order.getOrderType())||ConstantsUtils.ORDER_KIND_IMAGEPRODUCT.equals(order.getOrderType())){
				List<TdOrderProduct> productList = tdOrderProductMapper.findByOrderId(order.getOrderId());
				order.setProductList(productList);
			}
		}
		return order;
	}

	@Override
	public TdOrder findOrderWithOrderSku(Integer id) {
		TdOrder order = tdOrderMapper.findDetail(id);
		if(null!=order && null!=order.getOrderId()){
			//查询订单货品
			List<TdOrderSku> skuList = tdOrderSkuMapper.findByOrderId(id);
			order.setSkuList(skuList);
		}
		return order;
	}

	@Override
	public List<TdOrder> findBySearchCriteria(TdOrderSearchCriteria sc) {
		if(sc.isFlag()){
			Integer count = tdOrderMapper.countByCriteria(sc);
			sc.setTotalCount(count);
		}
		return tdOrderMapper.findBySearchCriteria(sc);
	}

	@Override
	public OperResult shiporder(TdOrderShipment shipment) {
		OperResult result = new OperResult();
		TdOrder order = this.findOrderWithOrderSku(shipment.getOrderId());
		//已发货的不能再次发货
		if(null==order||null==order.getOrderId() || ConstantsUtils.ORDER_SHIPMENT_STATUS_SHIPPED.equals(order.getShipmentStatus())){
			result.setFailMsg("已发货的不能再次发货！");
			return result;
		}
		List<TdOrderSku> skuList = order.getSkuList();
		if(null==skuList || skuList.size()<1){
			result.setFailMsg("数据错误，请重新操作！");
			return result;
		}
		shipment.setType(ConstantsUtils.ORDERSHIPMENT_TYPE_SHIP);
		tdOrderShipmentMapper.insert(shipment);
		List<TdOrderShipmentItem> itemList = new ArrayList<TdOrderShipmentItem>();
		for(TdOrderSku sku : skuList){
			TdOrderShipmentItem item = new TdOrderShipmentItem();
			item.setOrderSkuId(sku.getOrderSkuId());
			item.setQuantity(sku.getQuantity());
			item.setShipmentId(shipment.getId());
			itemList.add(item);
		}
		shipment.setItemList(itemList);
		tdOrderShipmentItemMapper.insertOrderShipmentItems(shipment);
		//未发货订单更新发货状态
		if(ConstantsUtils.ORDER_SHIPMENT_STATUS_UNSHIPPED.equals(order.getShipmentStatus())){
			TdOrder uporder = new TdOrder();
			uporder.setOrderId(order.getOrderId());
			uporder.setUpdateBy(shipment.getUpdateBy());
			uporder.setUpdateTime(shipment.getUpdateTime());
			uporder.setShipmentStatus(ConstantsUtils.ORDER_SHIPMENT_STATUS_SHIPPED);
			tdOrderMapper.updateByPrimaryKeySelective(uporder);
		}
		//订单操作日志
		TdOrderLog log = new TdOrderLog();
		log.setOrderId(order.getOrderId());
		log.setCreateBy(shipment.getUpdateBy());
		log.setCreateTime(shipment.getUpdateTime());
		log.setOperType(ConstantsUtils.ORDER_LOG_TYPE_SHIP);
		log.setNote("订单进行发货操作");
		tdOrderLogMapper.insert(log);
		result.setFlag(true);
		return result;
	}

	@Override
	public OperResult refundorder(OrderRefund refund) {
		OperResult result = new OperResult();
		TdOrder order = this.findOne(refund.getOrderId());
		//未支付、全部退款的不能再次退款
		if(null==order||null==order.getOrderId() || ConstantsUtils.ORDER_PAY_STATUS_UNPAY.equals(order.getPayStatus()) || ConstantsUtils.ORDER_PAY_STATUS_ALL_REFUND.equals(order.getPayStatus())){
			result.setFailMsg("未支付、全部退款的不能再次退款！");
			return result;
		}
		
		TdOrder uporder = new TdOrder();
		uporder.setOrderId(order.getOrderId());
		uporder.setUpdateBy(refund.getCreateBy());
		uporder.setUpdateTime(refund.getCreateTime());
		BigDecimal refundAmount = order.getRefundAmount().add(refund.getRefundAmount());
		if(BigDecimal.ZERO.compareTo(order.getPayAmount())>0){
			result.setFailMsg("退款金额不能小于0！");
			return result;
		}
		if(refundAmount.compareTo(order.getPayAmount())>0){
			result.setFailMsg("退款金额不能大于支付金额！");
			return result;
		}else if(refundAmount.compareTo(order.getPayAmount())==0){
			uporder.setPayStatus(ConstantsUtils.ORDER_PAY_STATUS_ALL_REFUND);
		}else{
			uporder.setPayStatus(ConstantsUtils.ORDER_PAY_STATUS_PART_REFUND);
		}
		uporder.setRefundAmount(refundAmount);
		tdOrderMapper.updateByPrimaryKeySelective(uporder);
		//订单操作日志
		TdOrderLog log = new TdOrderLog();
		log.setOrderId(order.getOrderId());
		log.setCreateBy(refund.getCreateBy());
		log.setCreateTime(refund.getCreateTime());
		log.setOperType(ConstantsUtils.ORDER_LOG_TYPE_REFUND);
		log.setNote("订单进行退款操作：退款金额 "+refund.getRefundAmount());
		tdOrderLogMapper.insert(log);
		result.setFlag(true);
		return result;
	}

	@Override
	public OperResult completeorder(Integer id) {
		OperResult result = new OperResult();
		TdOrder order = this.findOne(id);
		//订单不存在
		if(null==order||null==order.getOrderId()){
			result.setFailMsg("订单不存在！");
			return result;
		}
		//已完成的订单的不能进行完成操作
		if(ConstantsUtils.ORDER_STATUS_COMPLETE.equals(order.getOrderStatus())){
			result.setFailMsg("已完成的订单的不能进行完成操作！");
			return result;
		}
		
		//未支付的订单的不能进行完成操作
		if(ConstantsUtils.ORDER_PAY_STATUS_UNPAY.equals(order.getPayStatus())){
			result.setFailMsg("未支付的订单的不能进行完成操作！");
			return result;
		}
		
		//开始操作
		Date now  = new Date();
		TdOrder uporder = new TdOrder();
		uporder.setOrderId(order.getOrderId());
		uporder.setUpdateBy(0);
		uporder.setUpdateTime(now);
		uporder.setOrderStatus(ConstantsUtils.ORDER_STATUS_COMPLETE);
		tdOrderMapper.updateByPrimaryKeySelective(uporder);
		
		//订单操作日志
		TdOrderLog log = new TdOrderLog();
		log.setOrderId(order.getOrderId());
		log.setCreateBy(0);
		log.setCreateTime(now);
		log.setOperType(ConstantsUtils.ORDER_LOG_TYPE_COMPLETE);
		log.setNote("订单进行完成操作");
		tdOrderLogMapper.insert(log);
		
		//分润开始
		//
		
		result.setFlag(true);
		return result;
	}

	/**
	 * 保存完整订单生态
	 */
	@Override
	public TdJointOrder saveOrderFull(TdUser currUser, OrderForm orderForm, ShoppingcartVO shoppingcart) throws RuntimeException{
		Date now = new Date();
		Integer orderdeliveryintegral = configUtil.getOrderDeliveryIntegral(); //订单金额获取积分比例
		TdJointOrder torder = new TdJointOrder();
		torder.setAmount(shoppingcart.getTotalAmount().subtract(shoppingcart.getTotalPointAmount()));
		//赠送积分
		if(orderdeliveryintegral>0){
			torder.setGainPoints(torder.getAmount().divide(new BigDecimal(orderdeliveryintegral)).setScale(0, BigDecimal.ROUND_FLOOR).intValue());
		}else{
			torder.setGainPoints(0);
		}
		shoppingcart.setGainPoints(torder.getGainPoints());
		//收货地址
		if(null!=orderForm.getAddressId() && orderForm.getAddressId()>0){
			TdUserAddress address = tdUserAddressService.findOne(orderForm.getAddressId());
			orderForm.setUserAddress(address);
		}
		if(shoppingcart.getCombiningOrder()){//拆单
			torder.setJno(WebUtils.generateJointOrderNo());
			torder.setAmount(shoppingcart.getTotalAmount().subtract(shoppingcart.getTotalPointAmount()));
			torder.setPaymentId(orderForm.getPaymentId());
			torder.setStatus(ConstantsUtils.ORDER_PAY_STATUS_UNPAY);
			torder.setCreateTime(now);
			torder.setUpdateTime(now);
			tdJointOrderMapper.insert(torder);
			//拆分购物车
			List<ShoppingcartVO> cartList = this.splitShoppingcart(shoppingcart);
			//分别生成订单
			for(ShoppingcartVO cart : cartList){
				this.insertOrder(currUser, orderForm, cart, torder, now);
			}
		}else{
			torder.setId(0);
			//普通订单
			if(shoppingcart.getPtype()==1){
				TdOrder order = this.insertOrder(currUser, orderForm, shoppingcart, torder, now);
				torder.setJno(order.getOrderNo());
			
			}else if(shoppingcart.getPtype()==2){//单代订单
				TdOrder order = this.insertAgentOrder(currUser, orderForm, shoppingcart, torder, now);
				torder.setJno(order.getOrderNo());
			}else if(shoppingcart.getPtype()==3){//分公司订单
				TdOrder order = this.insertBrachOrder(currUser, orderForm, shoppingcart, torder, now);
				torder.setJno(order.getOrderNo());
			}
			
			torder.setPaymentId(orderForm.getPaymentId());
		}		
		return torder;
	}
	/**
	 * 生成订单
	 * @param currUser
	 * @param orderForm
	 * @param shoppingcart
	 * @param torder
	 * @param now
	 * @return
	 */
	private TdOrder insertOrder(TdUser currUser, OrderForm orderForm, ShoppingcartVO shoppingcart,TdJointOrder torder, Date now) throws RuntimeException {
		TdOrder order = new TdOrder();
		order.setCreateTime(now);
		order.setGainPoints(shoppingcart.getGainPoints());
		order.setCommented(false);
		order.setBenefited(false);
		order.setItemNum(shoppingcart.getTotalcount());
		order.setJointId(torder.getId());//联合订单
		order.setOrderNo(WebUtils.generateOrderNo());
		order.setOrderStatus(ConstantsUtils.ORDER_STATUS_NEW);
		order.setOrderType(ConstantsUtils.ORDER_KIND_COMMON);
		order.setPaymentId(orderForm.getPaymentId());
		if(orderForm.getUsePoints()){
			order.setUsedPoint(shoppingcart.getTotalPointsUsed());
			order.setPointAmount(shoppingcart.getTotalPointAmount());
		}else{
			order.setUsedPoint(0);
			order.setPointAmount(BigDecimal.ZERO);
		}
		order.setPostage(shoppingcart.getTotalPostage());
		order.setPayStatus(ConstantsUtils.ORDER_PAY_STATUS_UNPAY);
		order.setPaymentId(orderForm.getPaymentId());
		order.setProductAmount(shoppingcart.getTotalProductAmount());
		order.setRefundAmount(BigDecimal.ZERO);
		order.setPayAmount(BigDecimal.ZERO);
		order.setShipmentStatus(ConstantsUtils.ORDER_SHIPMENT_STATUS_UNSHIPPED);
		order.setTotalAmount(shoppingcart.getTotalAmount());
		order.setUpdateBy(currUser.getUid());
		order.setUpdateTime(now);
		order.setUserId(currUser.getUid());
		order.setUserMessage(orderForm.getUserMsg());
		order.setSupplierId(shoppingcart.getSupplierId());
		this.save(order);
		//保存订单货品
		for(TdShoppingcartItem item : shoppingcart.getItemList()){
			if(item.getQuantity()>item.getProductSku().getStock()){
				throw new RuntimeException("商品"+item.getProduct().getName()+"库存不足!");
			}
			if(!item.getProduct().getOnshelf()||!item.getProduct().getStatus().equals(Byte.valueOf("1"))){
				throw new RuntimeException("商品"+item.getProduct().getName()+"已经下架!");
			}
			TdOrderSku sku = new TdOrderSku();
			sku.setDisplaySpecifications(item.getProductSku().getSpecifications());
			sku.setItemType(item.getProduct().getKind());
			sku.setOrderId(order.getOrderId());
			sku.setProductSkuId(item.getProductSkuId());
			sku.setProductId(item.getProductId());
			sku.setPrice(item.getProductSku().getSalesPrice());
			sku.setProductName(item.getProduct().getName());
			sku.setProductSkuCode(item.getProductSku().getSkuCode());
			sku.setQuantity(item.getQuantity());
			tdOrderSkuMapper.insert(sku);
			//更新货品库存
			//TODO:
		}
		//扣除抵扣积分
		if(order.getUsedPoint()>0){
			TdUserIntegral userIntegral = tdUserIntegralService.findOne(currUser.getUid());
			if(null==userIntegral||userIntegral.getIntegral()<order.getUsedPoint()){
				throw new RuntimeException("积分不足，不能抵扣金额！");
			}
			userIntegral.setUpdateBy(0);
			userIntegral.setUpdateTime(now);
			TdUserIntegralLog integralLog = new TdUserIntegralLog();
			integralLog.setType(Byte.valueOf("4"));
			integralLog.setCreateTime(now);
			integralLog.setPreintegral(userIntegral.getIntegral());
			integralLog.setIntegral(-order.getUsedPoint());
			integralLog.setUid(currUser.getUid());
			integralLog.setNote("积分抵扣订单金额,订单编号："+order.getOrderNo()+" 消耗积分数量："+order.getUsedPoint()+" 抵扣金额：￥"+order.getPointAmount());
			integralLog.setRelation("");
			tdUserIntegralService.addIntegral(userIntegral, integralLog);
		}
		//保存收货地址
		if(null!=orderForm.getUserAddress()){
			TdUserAddress address = orderForm.getUserAddress();
			TdOrderAddress orderAddress = new TdOrderAddress();
			orderAddress.setOrderId(order.getOrderId());
			orderAddress.setAddress(address.getAddress());
			orderAddress.setCustomerName(address.getName());
			orderAddress.setRegionFullName(address.getFullAddress());
			orderAddress.setTelphone(address.getTelphone());
			orderAddress.setRegionId(address.getRegionId());
			tdOrderAddressMapper.insert(orderAddress);			
		}
		//保存日志
		
		return order;
	}
	/**
	 * 按供应商拆分购物车
	 * @param shoppingcart
	 * @return
	 */
	private List<ShoppingcartVO> splitShoppingcart(ShoppingcartVO shoppingcart) throws RuntimeException{
		Integer integralexchangerate = configUtil.getIntegralExchangerate(); //积分抵扣金额比例
		Integer commonproductpointpercent = configUtil.getCommonProductPointPercent(); //普通商品可积分抵扣的比例
		Integer partproductpointpercent = configUtil.getPartProductPointPercent(); //部分积分兑换商品可积分抵扣的比例
		
		List<ShoppingcartVO> cartList = new ArrayList<ShoppingcartVO>();
		for(Integer supplierId : shoppingcart.getSupplierIds()){
			ShoppingcartVO scart = new ShoppingcartVO();
			List<TdShoppingcartItem> itemList = new ArrayList<TdShoppingcartItem>();
			//获取货品
			for(TdShoppingcartItem item : shoppingcart.getItemList()){
				if(item.getQuantity()>item.getProductSku().getStock()){
					throw new RuntimeException("商品"+item.getProduct().getName()+"库存不足!");
				}
				if(item.getProduct().getUid().equals(supplierId)){
					itemList.add(item);
				}
			}
			scart.setItemList(itemList);
			//重新计算新购物车
			computershoppingcart(scart, integralexchangerate, commonproductpointpercent, partproductpointpercent);
			//计算获得积分
			if(shoppingcart.getGainPoints()>0){
				Integer point = scart.getTotalAmount().subtract(scart.getTotalPointAmount()).divide(shoppingcart.getTotalAmount().subtract(shoppingcart.getTotalPointAmount())).multiply(new BigDecimal(shoppingcart.getGainPoints())).setScale(0, BigDecimal.ROUND_FLOOR).intValue();
				scart.setGainPoints(point);
			}else{
				scart.setGainPoints(0);
			}
			cartList.add(scart);
		}
		
		return cartList;
	}

	/**
	 * 重新计算新购物车
	 * @param cart
	 * @param integralexchangerate
	 * @param commonproductpointpercent
	 * @param partproductpointpercent
	 */
	private void computershoppingcart(ShoppingcartVO cart, Integer integralexchangerate, Integer commonproductpointpercent, Integer partproductpointpercent){
		List<TdShoppingcartItem> itemList = cart.getItemList();
		if(null!=itemList && itemList.size()>0){
			for(TdShoppingcartItem item : itemList){
				//累加每个商品的运费
				BigDecimal postage = (null!=item.getProduct() && null!=item.getProduct().getPostage())?item.getProduct().getPostage():BigDecimal.ZERO;
				cart.setTotalPostage(postage.add(cart.getTotalPostage()));
				//累加单个商品总金额（价格*数量）
				BigDecimal quantity = new BigDecimal(item.getQuantity());
				BigDecimal amount = item.getProductSku().getSalesPrice().multiply(quantity);
				cart.setTotalProductAmount(amount.add(cart.getTotalProductAmount()));
				cart.setTotalAmount(amount.add(cart.getTotalAmount()).add(postage));
				//计算可以积分抵扣金额
				BigDecimal pointAmount =BigDecimal.ZERO;
				if(ConstantsUtils.PRODUCT_KIND_PART_POINT_EXCHANGE.equals(item.getProduct().getKind()) && partproductpointpercent>0 && integralexchangerate>0){
					pointAmount =  amount.multiply(new BigDecimal(partproductpointpercent)).divide(new BigDecimal(100));
					cart.setTotalPartPointAmount(pointAmount.add(cart.getTotalPartPointAmount()));
				}else if(commonproductpointpercent>0 && integralexchangerate>0){
					pointAmount =  amount.multiply(new BigDecimal(commonproductpointpercent)).divide(new BigDecimal(100));
					cart.setTotalCommonPointAmount(pointAmount.add(cart.getTotalCommonPointAmount()));
				}
			}
			//计算总积分抵扣金额和
			cart.setTotalPointAmount(cart.getTotalCommonPointAmount().add(cart.getTotalPartPointAmount()));
			Integer commonproductpoint = cart.getTotalCommonPointAmount().multiply(new BigDecimal(integralexchangerate)).setScale(0, BigDecimal.ROUND_FLOOR).intValue();
			Integer partproductpoint = cart.getTotalPartPointAmount().multiply(new BigDecimal(integralexchangerate)).setScale(0, BigDecimal.ROUND_FLOOR).intValue();
			cart.setTotalPointsUsed(commonproductpoint+partproductpoint);
		}
		cart.setTotalcount(itemList.size());
	}
	
	/**
	 * 生成单代订单
	 * @param currUser
	 * @param orderForm
	 * @param shoppingcart
	 * @param torder
	 * @param now
	 * @return
	 */
	private TdOrder insertAgentOrder(TdUser currUser, OrderForm orderForm, ShoppingcartVO shoppingcart,TdJointOrder torder, Date now) throws RuntimeException {
		TdOrder order = new TdOrder();
		order.setCreateTime(now);
		order.setGainPoints(shoppingcart.getGainPoints());
		order.setCommented(true);
		order.setBenefited(false);
		order.setItemNum(shoppingcart.getTotalcount());
		order.setJointId(torder.getId());//联合订单
		order.setOrderNo(WebUtils.generateOrderNo());
		order.setOrderStatus(ConstantsUtils.ORDER_STATUS_NEW);
		order.setOrderType(ConstantsUtils.ORDER_KIND_AGENTPRODUCT);
		order.setPaymentId(orderForm.getPaymentId());
		if(orderForm.getUsePoints()){
			order.setUsedPoint(shoppingcart.getTotalPointsUsed());
			order.setPointAmount(shoppingcart.getTotalPointAmount());
		}else{
			order.setUsedPoint(0);
			order.setPointAmount(BigDecimal.ZERO);
		}
		order.setPostage(shoppingcart.getTotalPostage());
		order.setPayStatus(ConstantsUtils.ORDER_PAY_STATUS_UNPAY);
		order.setPaymentId(orderForm.getPaymentId());
		order.setProductAmount(shoppingcart.getTotalProductAmount());
		order.setRefundAmount(BigDecimal.ZERO);
		order.setPayAmount(BigDecimal.ZERO);
		order.setShipmentStatus(ConstantsUtils.ORDER_SHIPMENT_STATUS_UNSHIPPED);
		order.setTotalAmount(shoppingcart.getTotalAmount());
		order.setUpdateBy(currUser.getUid());
		order.setUpdateTime(now);
		order.setUserId(currUser.getUid());
		order.setUserMessage(orderForm.getUserMsg());
		order.setSupplierId(1);
		//保存订单详情
		TdAgent agent = shoppingcart.getAgent();
		TdAgentSearchCriteria sc = new TdAgentSearchCriteria();
		sc.setLevel(agent.getLevel());
		sc.setRegionId(agent.getRegionId());
		sc.setProductTypeId(agent.getProductTypeId());
		int count = tdAgentService.countByCriteria(sc);
		if(count>0){
			throw new RuntimeException("分公司已存在，不能加入!");
		}
		this.save(order);
		TdOrderProduct orderproduct = new TdOrderProduct();
		orderproduct.setItemType(Byte.valueOf("1"));
		orderproduct.setItemId(orderForm.getAgentProductId());
		orderproduct.setItemPrice(shoppingcart.getAgentProduct().getSalesPrice());
		orderproduct.setRegionId(agent.getRegionId());
		orderproduct.setLevel(agent.getLevel());
		orderproduct.setQuantity(1);
		orderproduct.setTitle(shoppingcart.getAgentProduct().getTitle());
		orderproduct.setProductTypeId(agent.getProductTypeId());
		orderproduct.setOrderId(order.getOrderId());
		tdOrderProductMapper.insert(orderproduct);
		//保存订单货品
		if(null!=shoppingcart.getItemList()){
			for(TdShoppingcartItem item : shoppingcart.getItemList()){
				TdOrderSku sku = new TdOrderSku();
				sku.setDisplaySpecifications(item.getProductSku().getSpecifications());
				sku.setItemType(item.getProduct().getKind());
				sku.setOrderId(order.getOrderId());
				sku.setProductSkuId(item.getProductSkuId());
				sku.setProductId(item.getProductId());
				sku.setPrice(item.getProductSku().getSalesPrice());
				sku.setProductName(item.getProduct().getName());
				sku.setProductSkuCode(item.getProductSku().getSkuCode());
				sku.setQuantity(item.getQuantity());
				tdOrderSkuMapper.insert(sku);
				//更新货品库存
				//TODO:
			}
			//保存收货地址
			if(null!=orderForm.getUserAddress()){
				TdUserAddress address = orderForm.getUserAddress();
				TdOrderAddress orderAddress = new TdOrderAddress();
				orderAddress.setOrderId(order.getOrderId());
				orderAddress.setAddress(address.getAddress());
				orderAddress.setCustomerName(address.getName());
				orderAddress.setRegionFullName(address.getFullAddress());
				orderAddress.setTelphone(address.getTelphone());
				orderAddress.setRegionId(address.getRegionId());
				tdOrderAddressMapper.insert(orderAddress);			
			}
		}
		//保存日志
		
		return order;
	}
	/**
	 * 生成分公司订单
	 * @param currUser
	 * @param orderForm
	 * @param shoppingcart
	 * @param torder
	 * @param now
	 * @return
	 */
	private TdOrder insertBrachOrder(TdUser currUser, OrderForm orderForm, ShoppingcartVO shoppingcart,TdJointOrder torder, Date now) throws RuntimeException {
		TdOrder order = new TdOrder();
		order.setCreateTime(now);
		order.setGainPoints(shoppingcart.getGainPoints());
		order.setCommented(true);
		order.setBenefited(false);
		order.setItemNum(shoppingcart.getTotalcount());
		order.setJointId(torder.getId());//联合订单
		order.setOrderNo(WebUtils.generateOrderNo());
		order.setOrderStatus(ConstantsUtils.ORDER_STATUS_NEW);
		order.setOrderType(ConstantsUtils.ORDER_KIND_AGENTPRODUCT);
		order.setPaymentId(orderForm.getPaymentId());
		if(orderForm.getUsePoints()){
			order.setUsedPoint(shoppingcart.getTotalPointsUsed());
			order.setPointAmount(shoppingcart.getTotalPointAmount());
		}else{
			order.setUsedPoint(0);
			order.setPointAmount(BigDecimal.ZERO);
		}
		order.setPostage(shoppingcart.getTotalPostage());
		order.setPayStatus(ConstantsUtils.ORDER_PAY_STATUS_UNPAY);
		order.setPaymentId(orderForm.getPaymentId());
		order.setProductAmount(shoppingcart.getTotalProductAmount());
		order.setRefundAmount(BigDecimal.ZERO);
		order.setPayAmount(BigDecimal.ZERO);
		order.setShipmentStatus(ConstantsUtils.ORDER_SHIPMENT_STATUS_UNSHIPPED);
		order.setTotalAmount(shoppingcart.getTotalAmount());
		order.setUpdateBy(currUser.getUid());
		order.setUpdateTime(now);
		order.setUserId(currUser.getUid());
		order.setUserMessage(orderForm.getUserMsg());
		order.setSupplierId(1);
		this.save(order);
		//保存订单货品
		TdBrancheCompany branch = shoppingcart.getBranch();
		TdBrancheCompanySearchCriteria sc = new TdBrancheCompanySearchCriteria();
		sc.setLevel(branch.getLevel());
		sc.setRegionId(branch.getRegionId());
		int count = tdBrancheCompanyService.countByCriteria(sc);
		if(count>0){
			throw new RuntimeException("分公司已存在，不能加入!");
		}
		TdOrderProduct orderproduct = new TdOrderProduct();
		orderproduct.setItemType(Byte.valueOf("1"));
		orderproduct.setRegionId(branch.getRegionId());
		orderproduct.setLevel(branch.getLevel());
		orderproduct.setOrderId(order.getOrderId());
		tdOrderProductMapper.insert(orderproduct);
		return order;
	}

	@Override
	public OperResult applyRefundOrder(TdOrder order, TdOrderShipment shipment) {
		OperResult result = new OperResult();
		if(order.getPayAmount().subtract(order.getRefundAmount()).compareTo(shipment.getReturnAmount())>=0){
			Date now = new Date();
			shipment.setStatus(Byte.valueOf("1"));
			shipment.setType(Byte.valueOf("2"));
			shipment.setCreateBy(order.getUserId());
			shipment.setUpdateBy(order.getUserId());
			shipment.setCreateTime(now);
			shipment.setUpdateTime(now);
			tdOrderShipmentMapper.insert(shipment);
			result.setFlag(true);
			result.setFailMsg("退款申请成功，请等待商户确认。");
		}else{
			result.setFailMsg("退款申请失败：退款金额大于订单金额！");
		}
		return result;
	}

	@Override
	public OperResult complaintOrder(TdOrder order, TdComplaint complaint) {
		OperResult result = new OperResult();
		if(order.getOrderId()>0){
			Date now = new Date();
			complaint.setCreateTime(now);
			complaint.setUpdateBy(order.getUserId());
			complaint.setUpdateDate(now);
			complaint.setCuid(order.getSupplierId());
			complaint.setUid(order.getUserId());
			complaint.setStatus(Byte.valueOf("1"));
			tdComplaintService.insert(complaint);
			result.setFlag(true);
			result.setFailMsg("投诉已经提交，请等待平台处理。");
		}else{
			result.setFailMsg("投诉提交失败!");
		}
		return result;
	}

	@Override
	public OperResult receiptOrder(TdOrder order, TdUser user) {
		OperResult result = new OperResult();
		//订单不存在
		if(null==order||null==order.getOrderId()){
			result.setFailMsg("订单不存在！");
			return result;
		}
		//已完成的订单的不能进行收货操作
		if(ConstantsUtils.ORDER_STATUS_COMPLETE.equals(order.getOrderStatus())){
			result.setFailMsg("已完成的订单的不能进行收货操作！");
			return result;
		}
		
		//未支付的订单的不能进行收货操作
		if(ConstantsUtils.ORDER_PAY_STATUS_UNPAY.equals(order.getPayStatus())){
			result.setFailMsg("未支付的订单的不能进行收货操作！");
			return result;
		}
		//已收货的订单的不能进行收货操作
		if(ConstantsUtils.ORDER_PAY_STATUS_UNPAY.equals(order.getPayStatus())){
			result.setFailMsg("未支付的订单的不能进行收货操作！");
			return result;
		}
		
		//开始操作
		Date now  = new Date();
		TdOrder uporder = new TdOrder();
		uporder.setOrderId(order.getOrderId());
		uporder.setUpdateBy(user.getUid());
		uporder.setUpdateTime(now);
		uporder.setShipmentStatus(ConstantsUtils.ORDER_SHIPMENT_STATUS_RECEIPT);
		tdOrderMapper.updateByPrimaryKeySelective(uporder);
		
		//订单操作日志
		TdOrderLog log = new TdOrderLog();
		log.setOrderId(order.getOrderId());
		log.setCreateBy(user.getUid());
		log.setCreateTime(now);
		log.setOperType(ConstantsUtils.ORDER_LOG_TYPE_RECEIPT);
		log.setNote("订单进行收货操作");
		tdOrderLogMapper.insert(log);
		
		result.setFlag(true);
		return result;
	}

	@Override
	public OperResult payOrder(TdOrder order, OrderPay orderPay) throws RuntimeException {
		OperResult result = new OperResult();
		//订单不存在
		if(null==order||null==order.getOrderId()){
			result.setFailMsg("订单不存在！");
			return result;
		}
		//已完成的订单的不能进行支付操作
		if(ConstantsUtils.ORDER_STATUS_COMPLETE.equals(order.getOrderStatus())){
			result.setFailMsg("已完成的订单的不能进行收货操作！");
			return result;
		}
		
		//已支付的订单的不能进行支付操作
		if(ConstantsUtils.ORDER_PAY_STATUS_PAYED.equals(order.getPayStatus())){
			result.setFailMsg("已支付的订单的不能进行支付操作！");
			return result;
		}
		//金额不等不能进行支付操作
		if(order.getUnPayAmount().compareTo(orderPay.getPayAmount())!=0){
			result.setFailMsg("支付金额与订单金额不匹配不能进行支付操作！");
			return result;
		}
		//开始操作
		Date now  = new Date();
		TdOrder uporder = new TdOrder();
		uporder.setOrderId(order.getOrderId());
		uporder.setUpdateBy(orderPay.getCreateBy());
		uporder.setUpdateTime(now);
		uporder.setPayStatus(ConstantsUtils.ORDER_PAY_STATUS_PAYED);
		uporder.setPaymentId(orderPay.getPaymentId());
		tdOrderMapper.updateByPrimaryKeySelective(uporder);
		
		//订单操作日志
		TdOrderLog log = new TdOrderLog();
		log.setOrderId(order.getOrderId());
		log.setCreateBy(orderPay.getCreateBy());
		log.setCreateTime(now);
		log.setOperType(ConstantsUtils.ORDER_LOG_TYPE_PAY);
		log.setNote("订单进行收款操作");
		tdOrderLogMapper.insert(log);
		
		//代理产品订单添加代理和计算分润
		if(ConstantsUtils.ORDER_KIND_AGENTPRODUCT.equals(order.getOrderType())){
			//代理产品添加代理
			TdOrderProduct orderProduct = tdOrderProductMapper.findByOrderIdAndTypeId(order.getOrderId(), Byte.valueOf("1"));
			TdOrderLog log2 = new TdOrderLog();
			log2.setOrderId(order.getOrderId());
			log2.setCreateBy(orderPay.getCreateBy());
			log2.setCreateTime(now);
			log2.setOperType(ConstantsUtils.ORDER_LOG_TYPE_AGENT);
			if(null!=orderProduct){
				TdAgentProduct agentProduct = tdAgentProductService.findOne(orderProduct.getItemId());
				if(null!=agentProduct){
					if(ConstantsUtils.AGENT_GROUPID_AGENT.equals(agentProduct.getGroupId())){//单代代理
						TdAgentSearchCriteria sc = new TdAgentSearchCriteria();
						sc.setLevel(agentProduct.getLevel());
						sc.setRegionId(orderProduct.getRegionId());
						sc.setProductTypeId(orderProduct.getProductTypeId());
						int count = tdAgentService.countByCriteria(sc);
						if(count>0){
							log2.setNote("订单生成代理操作失败：地区已经有单类代理了!");
						}else{
							TdAgent agent = tdAgentService.findByUid(order.getUserId());
							if(null!=agent){//用户已经购买代理
								if(agent.getLevel()>agentProduct.getLevel()){//已有代理级别大于先购买的代理级别，覆盖失败
									log2.setNote("订单生成代理操作失败：用户已拥有代理级别比现购买的代理级别更高，不能降级处理!");
								}else{
									agent.setLevel(agentProduct.getLevel());
									agent.setProductTypeId(orderProduct.getProductTypeId());
									agent.setRegionId(orderProduct.getRegionId());
									agent.setUpdateDate(now);
									tdAgentService.save(agent);
									log2.setNote("订单生成代理操作成功。");
								}
							}else{
								agent = new TdAgent();
								agent.setUid(order.getUserId());
								agent.setUpdateBy(1);
								agent.setLevel(agentProduct.getLevel());
								agent.setProductTypeId(orderProduct.getProductTypeId());
								agent.setRegionId(orderProduct.getRegionId());
								agent.setUpdateDate(now);
								tdAgentService.save(agent);
								log2.setNote("订单生成代理操作成功。");
							}							
						}
						
					}else if(ConstantsUtils.AGENT_GROUPID_BRANCH.equals(agentProduct.getGroupId())){//分公司
						TdBrancheCompanySearchCriteria sc = new TdBrancheCompanySearchCriteria();
						sc.setLevel(agentProduct.getLevel());
						sc.setRegionId(orderProduct.getRegionId());
						int count = tdBrancheCompanyService.countByCriteria(sc);
						if(count>0){
							log2.setNote("订单生成代理操作失败：地区已经有分公司了!");
						}else{
							TdBrancheCompany branch = tdBrancheCompanyService.findByUid(order.getUserId());
							if(null!=branch){//用户已经购买代理
								if(branch.getLevel()>agentProduct.getLevel()){//已有分公司级别大于现购买的分公司级别，覆盖失败
									log2.setNote("订单生成分公司操作失败：用户已拥有分公司级别比现购买的分公司级别更高，不能降级处理!");
								}else{
									branch.setLevel(agentProduct.getLevel());
									branch.setRegionId(orderProduct.getRegionId());
									branch.setUpdateTime(now);
									branch.setUpdateBy(1);
									tdBrancheCompanyService.save(branch);
									log2.setNote("订单生成分公司操作成功。");
								}
							}else{
								branch = new TdBrancheCompany();
								branch.setUid(order.getUserId());
								branch.setLevel(agentProduct.getLevel());
								branch.setRegionId(orderProduct.getRegionId());
								branch.setUpdateTime(now);
								branch.setUpdateBy(1);
								branch.setStatus(Byte.valueOf("1"));
								tdBrancheCompanyService.save(branch);
								log2.setNote("订单生成分公司操作成功。");
							}							
						}
					}
				}else{
					log2.setNote("订单生成代理操作失败：代理产品未找到!");
				}
				//分润开始
				BigDecimal amount = orderProduct.getItemPrice().subtract(agentProduct.getSupplierPrice());
				TdBenefitSearchCriteria sc = new TdBenefitSearchCriteria();
				sc.setFlag(false);
				sc.setTypeId(agentProduct.getId());
				List<TdBenefit> benefitList = tdBenefitService.findBySearchCriteria(sc);
				if(null!=benefitList && benefitList.size()>0){
					TdUser orderUser = tdUserService.findOneWithAccount(order.getUserId());
					if(null==orderUser||null==orderUser.getUregionId()){
						//订单操作日志
						TdOrderLog log3 = new TdOrderLog();
						log3.setOrderId(order.getOrderId());
						log3.setCreateBy(1);
						log3.setCreateTime(now);
						log3.setOperType(ConstantsUtils.ORDER_LOG_TYPE_BENEFIT);
						log3.setNote("订单进行分润操作失败:订单用户信息不全。");
						tdOrderLogMapper.insert(log3);
					}
					TdDistrict district = tdDistrictService.findOneFull(orderUser.getUregionId());
					if(null!=district){//地区分公司分润
						int size=3;
						for(int i=0; i<size; i++){
							int level = i+1;
							//分销设置
							TdBenefit  benefit = this.getBenefitConfig(benefitList, ConstantsUtils.AGENT_GROUPID_BRANCH, level);
							if(null==benefit){
								logger.error("分公司分润操作错误：分公司分润级别"+level+"设置未找到，");
								continue;
							}
							if(level==1){//分公司第一级为平台
								String note = "";
								TdUserAccount account = tdUserAccountService.findOne(1);
								saveBenefit(account, amount, order, benefit, now, note);								
							}else{
								TdBrancheCompanySearchCriteria bsc = new TdBrancheCompanySearchCriteria();
								bsc.setLevel(level);
								bsc.setRegionId(district.getId());
								bsc.setFlag(false);
								List<TdBrancheCompany> branchList = tdBrancheCompanyService.findBySearchCriteria(bsc);
								if(null!= branchList && branchList.size()>0){
									String note = "";
									TdBrancheCompany branch = branchList.get(0);
									TdUserAccount account = tdUserAccountService.findOne(branch.getUid());
									saveBenefit(account, amount, order, benefit, now, note);
								}else{//未找到分公司，分润转给平台
									String note = "";
									TdUserAccount account = tdUserAccountService.findOne(1);
									saveBenefit(account, amount, order, benefit, now, note);								
								}
							}
						}
					}
					//三级分销分润
					displayDistributionBenefit(amount, order, orderUser, benefitList, 3, now);
				}
				Date now2  = new Date();
				TdOrder uporder2 = new TdOrder();
				uporder2.setOrderId(order.getOrderId());
				uporder2.setUpdateBy(1);
				uporder2.setUpdateTime(now2);
				uporder2.setBenefited(true);
				tdOrderMapper.updateByPrimaryKeySelective(uporder2);
				
			}else{
				log2.setNote("订单生成代理操作失败：订单详情未找到!");
			}			
			tdOrderLogMapper.insert(log2);
		}
		
		result.setFlag(true);
		return result;
	}
	
	
	@Override
	public void benefitOrder(TdOrder order) throws RuntimeException {
		Date now  = new Date();
		if(!order.getBenefited()){
			if(ConstantsUtils.ORDER_KIND_AGENTPRODUCT.equals(order.getOrderType())){//代理产品分润
				//代理产品添加代理
				TdOrderProduct orderProduct = tdOrderProductMapper.findByOrderIdAndTypeId(order.getOrderId(), Byte.valueOf("1"));
				if(null!=orderProduct){
					TdAgentProduct agentProduct = tdAgentProductService.findOne(orderProduct.getItemId());
					if(null!=agentProduct){
						BigDecimal amount = orderProduct.getItemPrice().subtract(agentProduct.getSupplierPrice());
						TdBenefitSearchCriteria sc = new TdBenefitSearchCriteria();
						sc.setFlag(false);
						sc.setTypeId(agentProduct.getId());
						List<TdBenefit> benefitList = tdBenefitService.findBySearchCriteria(sc);
						if(null!=benefitList && benefitList.size()>0){
							TdUser orderUser = tdUserService.findOneWithAccount(order.getUserId());
							if(null==orderUser||null==orderUser.getUregionId()){
								//订单操作日志
								TdOrderLog log3 = new TdOrderLog();
								log3.setOrderId(order.getOrderId());
								log3.setCreateBy(1);
								log3.setCreateTime(now);
								log3.setOperType(ConstantsUtils.ORDER_LOG_TYPE_BENEFIT);
								log3.setNote("订单进行分润操作失败:订单用户信息不全。");
								tdOrderLogMapper.insert(log3);
							}
							TdDistrict district = tdDistrictService.findOneFull(orderUser.getUregionId());
							if(null!=district){//地区分公司分润
								int size=3;
								for(int i=0; i<size; i++){
									int level = i+1;
									//分销设置
									TdBenefit  benefit = this.getBenefitConfig(benefitList, ConstantsUtils.AGENT_GROUPID_BRANCH, level);
									if(null==benefit){
										logger.error("分公司分润操作错误：分公司分润级别"+level+"设置未找到，");
										continue;
									}
									if(level==1){//分公司第一级为平台
										String note = "";
										TdUserAccount account = tdUserAccountService.findOne(1);
										saveBenefit(account, amount, order, benefit, now, note);								
									}else{
										TdBrancheCompanySearchCriteria bsc = new TdBrancheCompanySearchCriteria();
										bsc.setLevel(level);
										bsc.setRegionId(district.getId());
										bsc.setFlag(false);
										List<TdBrancheCompany> branchList = tdBrancheCompanyService.findBySearchCriteria(bsc);
										if(null!= branchList && branchList.size()>0){
											String note = "";
											TdBrancheCompany branch = branchList.get(0);
											TdUserAccount account = tdUserAccountService.findOne(branch.getUid());
											saveBenefit(account, amount, order, benefit, now, note);
										}else{//未找到分公司，分润转给平台
											String note = "";
											TdUserAccount account = tdUserAccountService.findOne(1);
											saveBenefit(account, amount, order, benefit, now, note);								
										}
									}
								}
							}
							//三级分销分润
							displayDistributionBenefit(amount, order, orderUser, benefitList, 3, now);
						}
						TdOrder uporder2 = new TdOrder();
						uporder2.setOrderId(order.getOrderId());
						uporder2.setUpdateBy(1);
						uporder2.setUpdateTime(now);
						uporder2.setBenefited(true);
						tdOrderMapper.updateByPrimaryKeySelective(uporder2);
					}
				}
			}else if(ConstantsUtils.ORDER_KIND_COMMON.equals(order.getOrderType())){//普通商品分润
				
			}
		}
		
	}

	/**
	 * 三级分销分润
	 * @param order
	 * @param user
	 * @param benefitList
	 * @param size 分几级
	 */
	private void displayDistributionBenefit(BigDecimal amount, TdOrder order, TdUser user, List<TdBenefit> benefitList, int size, Date now) throws RuntimeException{
		int userId = user.getUparentId();
		for(int i=0; i<size;i++){
			int level = i+1;
			//分销设置
			TdBenefit  benefit = this.getBenefitConfig(benefitList, ConstantsUtils.AGENT_GROUPID_DISTRIBUTION, level);
			if(null==benefit){
				logger.error("三级分销分润操作错误：三级分销分润级别"+(i+1)+"设置未找到，");
				throw new RuntimeException("三级分销分润操作错误：三级分销分润级别"+(i+1)+"设置未找到，");
			}
			if(i==3 && size==4){//自己购买分润
				String note = "";
				TdUserAccount account = user.getUserAccount();
				saveBenefit(account, amount, order, benefit, now, note);
			}else{
				//三级分销用户
				if(userId>0){//上级分销用户id 存在
					TdUser buser = tdUserService.findOneWithAccount(userId);
					if(null!=buser && null!=buser.getUserAccount()){
						userId = buser.getUparentId();
						TdUserAccount account = buser.getUserAccount();
						//分润
						String note = "";
						saveBenefit(account, amount, order, benefit, now, note);
					}else{//未找到分公司，分润转给平台
						userId = 0;
						TdUserAccount account = tdUserAccountService.findOne(1);
						//分润
						String note = "未找到"+level+"级分销  ，当前用户userid="+user.getUid()+" ,分润划归归平台";
						saveBenefit(account, amount, order, benefit, now, note);
					}
				}else{//上级分销用户id 不存在
					TdUserAccount account = tdUserAccountService.findOne(1);
					//分润
					String note = "未找到"+level+"级分销  ,当前用户userid="+user.getUid()+" ,分润划归归平台";
					saveBenefit(account, amount, order, benefit, now, note);
				}
			}
		}
	}
	
	/**
	 * 匹配分润设置
	 * @param benefitList
	 * @param groupId
	 * @param level
	 * @return
	 */
	private TdBenefit getBenefitConfig(List<TdBenefit> benefitList, Integer groupId, Integer level){
		for(TdBenefit benefit : benefitList){
			if(groupId.equals(benefit.getGroupId()) && level.equals(benefit.getLevel())){
				return benefit;
			}
		}
		return null;
	}
	/**
	 * 分润操作
	 * @param account
	 * @param amount
	 * @param order
	 * @param benefit
	 * @param now
	 */
	private void saveBenefit(TdUserAccount account, BigDecimal amount, TdOrder order, TdBenefit benefit, Date now, String note){
		account.setUpdateBy(1);
		account.setUpdateTime(now);
		TdUserAccountLog alog = new TdUserAccountLog();
		alog.setPreamount(account.getAmount());
		alog.setUid(account.getUid());
		alog.setUpamount(amount.multiply(new BigDecimal(benefit.getPercent()).divide(new BigDecimal(100))));
		alog.setType(TdUserAccountLog.USERACCOUNTLOG_TYPE_PROFIT_INCOME);
    	alog.setCreateTime(now);
    	alog.setNote("订单分润收入，订单编号："+order.getOrderNo()+" "+note);
		tdUserAccountService.addAmount(account, alog);	
	}
	
}
