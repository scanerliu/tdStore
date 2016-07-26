package com.tiandu.common.utils;

public class ConstantsUtils {

	public final static String PRIMARY_MEMBER_KEY = "td_key_2016_05_20****15##%%@&50";
	/**
	 * 系统后台过滤地址
	 */
	public final static String TDSTORE_ADMIN_FILTER = "/admin/";
	/**
	 * 系统后台登录地址
	 */
	public final static String TDSTORE_ADMIN_LOGIN_URL = "/admin/login";
	/**
	 * 系统移动端登录地址
	 */
	public final static String TDSTORE_MOBILE_LOGIN_URL = "/mobile/login";
	/**
	 * 系统移动端过滤地址
	 */
	public final static String TDSTORE_MOBILE_FILTER = "/mobile/";
	
	
	public final static Byte CONF_DATA_TYPE_BOOLEAN = 3;
	
	/*******************************订单类型****************************/
	/**
	 * 订单类型1-普通商品订单
	 */
	public static final Byte ORDER_KIND_COMMON = 1;
	/**
	 * 订单类型2-代理产品订单
	 */
	public static final Byte ORDER_KIND_AGENTPRODUCT = 2;
	/**
	 * 订单类型2-图片处理订单
	 */
	public static final Byte ORDER_KIND_IMAGEPRODUCT = 3;
	/*******************************订单商品类型****************************/
	/**
	 * 订单商品类型1-代理产品
	 */
	public static final Byte ORDER_PRODUCT_TYPE_AGENT = 1;
	/**
	 * 订单商品类型2-商品包
	 */
	public static final Byte ORDER_PRODUCT_TYPE_PAKAGE = 2;
	/**
	 * 订单商品类型3-图片
	 */
	public static final Byte ORDER_PRODUCT_TYPE_IMAGE = 3;
	
	/**********************代理类型*********************************/
	/**
	 * 代理类型-1 单类代理  2 分公司 3-三级分销
	 */
	public static final Integer AGENT_GROUPID_AGENT = 1;
	/**
	 * 代理类型-1 单类代理  2 分公司 3-三级分销
	 */
	public static final Integer AGENT_GROUPID_BRANCH = 2;
	/**
	 * 代理类型-1 单类代理  2 分公司 3-三级分销
	 */
	public static final Integer AGENT_GROUPID_DISTRIBUTION = 3;
	
	/**
	 * 分润类型1-区县单类代理(代金券、供应商和分销商)
	 */
	public static final Byte BENEFIT_TYPE_DISTRICT_PRODUCT_GENT_GIFT = 1;
	/**
	 * 分润类型2-省、直辖市级单类产品代理和供应商
	 */
	public static final Byte BENEFIT_TYPE_PROVINCE_PRODUCT_GENT = 2;
	/**
	 * 分润类型3-全国级单类产品代理和供应商
	 */
	public static final Byte BENEFIT_TYPE_COUNTRY_PRODUCT_GENT = 3;
	/**
	 * 分润类型4-区县级单类产品代理和供应商
	 */
	public static final Byte BENEFIT_TYPE_DISTRICT_PRODUCT_GENT = 4;
	/**
	 * 分润类型5-免费申请单类产品社区、村级体验中心
	 */
	public static final Byte BENEFIT_TYPE_EXPERIENCE_STORE_PRODUCT_GENT = 5;
	/**
	 * 分润类型6-申请成为县级分公司
	 */
	public static final Byte BENEFIT_TYPE_DISTRICT_BRANCH = 6;
	/**
	 * 分润类型7-申请成为省级分公司
	 */
	public static final Byte BENEFIT_TYPE_PROVINCE_BRANCH = 7;
	
	
	/**---------------------------订单状态---------------------------**/
	/**
	 * 订单状态：-1 已取消
	 */
	public static final Byte ORDER_STATUS_CANCEL = -1;
	/**
	 * 订单状态：1 新订单
	 */
	public static final Byte ORDER_STATUS_NEW = 1;
	/**
	 * 订单状态：2 已完成
	 */
	public static final Byte ORDER_STATUS_COMPLETE = 2;
	/**
	 * 订单支付状态： 1 已支付
	 */
	public static final Byte ORDER_PAY_STATUS_PAYED = 1;
	/**
	 * 订单支付状态： 2 未支付
	 */
	public static final Byte ORDER_PAY_STATUS_UNPAY = 2;
	/**
	 * 订单支付状态： 3 部分退款
	 */
	public static final Byte ORDER_PAY_STATUS_PART_REFUND = 3;
	/**
	 * 订单支付状态： 4 全部退款
	 */
	public static final Byte ORDER_PAY_STATUS_ALL_REFUND = 4;
	/**
	 * 订单发货状态： 1 已发货
	 */
	public static final Byte ORDER_SHIPMENT_STATUS_SHIPPED = 1;
	/**
	 * 订单发货状态： 2 未发货
	 */
	public static final Byte ORDER_SHIPMENT_STATUS_UNSHIPPED = 2;
	/**
	 * 订单发货状态： 3 部分退货
	 */
	public static final Byte ORDER_SHIPMENT_STATUS_PART_RETURN = 3;
	/**
	 * 订单发货状态： 4 全部退货
	 */
	public static final Byte ORDER_SHIPMENT_STATUS_All_RETURN = 4;
	/**
	 * 订单发货状态： 5 已收货
	 */
	public static final Byte ORDER_SHIPMENT_STATUS_RECEIPT = 5;
	
	/**发货单类型**/
	/**
	 * 发货单类型： 1 发货单
	 */
	public static final Byte ORDERSHIPMENT_TYPE_SHIP = 1;
	/**
	 * 发货单类型：2 退货单
	 */
	public static final Byte ORDERSHIPMENT_TYPE_RETURN = 1;
	/**订单操作日志类型**/
	/**
	 * 订单操作日志类型： 1 发货
	 */
	public static final Byte ORDER_LOG_TYPE_SHIP = 1;
	/**
	 * 订单操作日志类型： 2 退款
	 */
	public static final Byte ORDER_LOG_TYPE_REFUND = 2;
	/**
	 * 订单操作日志类型： 3订单完成
	 */
	public static final Byte ORDER_LOG_TYPE_COMPLETE = 3;
	/**
	 * 订单操作日志类型： 4订单收货
	 */
	public static final Byte ORDER_LOG_TYPE_RECEIPT = 4;
	/**
	 * 订单操作日志类型： 5订单收款
	 */
	public static final Byte ORDER_LOG_TYPE_PAY = 5;
	/**
	 * 订单操作日志类型： 6订单分配代理
	 */
	public static final Byte ORDER_LOG_TYPE_AGENT = 6;
	/**
	 * 订单操作日志类型： 7订单分润
	 */
	public static final Byte ORDER_LOG_TYPE_BENEFIT = 7;
	
	/***************************订单支付方式**************************/
	/**
	 * 订单支付方式： 1 支付宝
	 */
	public static final Byte ORDER_PAYMENT_ALIPAY = 1;
	/**
	 * 订单支付方式： 2 微信
	 */
	public static final Byte ORDER_PAYMENT_WEIXIN = 2;
	/**
	 * 订单支付方式： 3银联
	 */
	public static final Byte ORDER_PAYMENT_UNIONPAY = 3;
	
	
	/***************************商品类型**************************/
	/**
	 * 商品类型： 1 普通商品
	 */
	public static final Byte PRODUCT_KIND_COMMON = 1;
	/**
	 * 商品类型： 2 商品包
	 */
	public static final Byte PRODUCT_KIND_PACKAGE = 2;
	/**
	 * 商品类型：3 零元购
	 */
	public static final Byte PRODUCT_KIND_ZEROBUY = 3;
	/**
	 * 商品类型：4  10元购
	 */
	public static final Byte PRODUCT_KIND_TENBUY = 4;
	/**
	 * 商品类型：5 预售
	 */
	public static final Byte PRODUCT_KIND_PRESALE = 5;
	/**
	 * 商品类型：6 秒杀
	 */
	public static final Byte PRODUCT_KIND_SECKILL = 6;
	/**
	 * 商品类型：7 积分兑换
	 */
	public static final Byte PRODUCT_KIND_POINT_EXCHANGE = 7;
	/**
	 * 商品类型：8 部分积分兑换
	 */
	public static final Byte PRODUCT_KIND_PART_POINT_EXCHANGE = 8;
	
	/**********************返回状态*********************************/
	
	public static final Integer RETURN_CODE_SUCCESS = 1;		//成功
	
	public static final Integer RETURN_CODE_FAILURE = 0;		//失败
	
	/**********************错误描述*********************************/
	
}
