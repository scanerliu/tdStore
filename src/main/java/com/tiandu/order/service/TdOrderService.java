package com.tiandu.order.service;

import java.util.List;

import com.tiandu.complaint.entity.TdComplaint;
import com.tiandu.custom.entity.TdUser;
import com.tiandu.order.entity.TdJointOrder;
import com.tiandu.order.entity.TdOrder;
import com.tiandu.order.entity.TdOrderShipment;
import com.tiandu.order.search.TdOrderSearchCriteria;
import com.tiandu.order.vo.OperResult;
import com.tiandu.order.vo.OrderForm;
import com.tiandu.order.vo.OrderPay;
import com.tiandu.order.vo.OrderRefund;
import com.tiandu.order.vo.ShoppingcartVO;

public interface TdOrderService {

	public Integer deleteByPrimaryKey(Integer orderId);

	public Integer save(TdOrder e);
	public TdOrder findOne(Integer id);
	public TdOrder findDetail(Integer id);
	public TdOrder findOrderWithOrderSku(Integer id);
	
	public List<TdOrder> findBySearchCriteria(TdOrderSearchCriteria sc);
	/**
	 * 订单发货操作
	 * @param order
	 * @param shipment
	 * @return
	 */
	public OperResult shiporder(TdOrderShipment shipment);
	/**
	 * 订单退款操作
	 * @param order
	 * @param refund
	 * @return
	 */
	public OperResult refundorder(OrderRefund refund);
	
	/**
	 * 订单完成操作,根据订单类型进行分润处理
	 * @param order
	 * @param refund
	 * @return
	 */
	public OperResult completeorder(Integer id);

	/**
	 * 生成订单
	 * @param currUser
	 * @param orderForm
	 * @param shoppingcart
	 * @return
	 * @throws RuntimeException 
	 */
	public TdJointOrder saveOrderFull(TdUser currUser, OrderForm orderForm, ShoppingcartVO shoppingcart) throws RuntimeException;

	/**
	 * 申请退款
	 * @param order
	 * @param shipment
	 * @return
	 */
	public OperResult applyRefundOrder(TdOrder order, TdOrderShipment shipment);

	/**
	 * 投诉
	 * @param order
	 * @param complaint
	 * @return
	 */
	public OperResult complaintOrder(TdOrder order, TdComplaint complaint);
	/**
	 * 收货
	 * @param order
	 * @param user
	 * @return
	 */
	public OperResult receiptOrder(TdOrder order, TdUser user);
	/**
	 * 订单支付
	 * @param order
	 * @param orderPay
	 * @return
	 */
	public OperResult payOrder(TdOrder order, OrderPay orderPay);
	
	/**
	 * 订单分润
	 * @param order
	 * @return
	 */
	public void benefitOrder(TdOrder order) throws RuntimeException;
}
