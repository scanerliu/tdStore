<#import "/common/app.ftl" as app>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
    <meta http-equiv="Content-Language" content="zh-CN">
    <meta name="keywords" content="">
    <meta name="description" content="">
    <meta name="copyright" content="" />
    <meta name="viewport" content="initial-scale=1,maximum-scale=1,minimum-scale=1">
    <meta content="yes" name="apple-mobile-web-app-capable">
    <meta content="black" name="apple-mobile-web-app-status-bar-style">
    <meta content="telephone=no" name="format-detection">
    <title>订单详情</title>
    <#include "/common/common.ftl" />
    <link rel="shortcut icon" href="${app.basePath}/static/default/images/icon.ico" />
    <link rel="stylesheet" href="${app.basePath}/static/default/mobile/css/common.css"/>
    <link rel="stylesheet" href="${app.basePath}/static/default/mobile/css/main.css"/>
    <link rel="stylesheet" href="${app.basePath}/static/default/mobile/css/x_pc.css"/>
    <script src="${app.basePath}/static/js/jquery-1.12.3.min.js" type="text/javascript"></script>
    <script type="text/javascript" src="${app.basePath}/static/js/mobile/common.js"></script>
    <script type="text/javascript" src="${app.basePath}/static/js/mobile/core.js"></script>
    <script src="${app.basePath}/static/js/mobile/order/orderlist.js" type="text/javascript"></script>
</head>
<body class="body_gray">
  <!-- header_top -->
  <div class="top_heater">
    <a class="hleft hback" href="javascript:;" onclick="window.history.go(-1)" title=""></a>
    <span>订单详情</span>
  </div>
  <!-- header_top end -->
  <section class="container order-details">
	
<div class="order_detail p">
	<label>周大江</label>
	<span>1388***5583</span>
	<p>重庆市渝中区解放碑街道邹容路50号半岛国际大厦18-A</p>
</div>
<!-- order_detail_title_end -->
<!-- order_detail_title -->
<ul class="order_goods">
	<li class="order_name p">
		<p class="left">
			<label>订单号：</label>
			<span>8552424524215</span>
		</p>
		<p class="right">
			<label>交易状态：</label>
			<span>已完成</span>
		</p>
	</li>
	<li class="goods_box p">
		<img alt="" src="../images/goods01.png"/>
		<section>
			<h3>016春季新款单鞋女平底浅口玛丽珍鞋圆头低跟坡跟洛包邮</h3>
			<div>
				<span><label for="">颜色：</label><font>卡其色</font></span>
				<span><label for="">尺码：</label><font>均码</font></span>
				<span><label for="">数量：</label><font>X1</font></span>
			</div>
			<p>￥168.00</p>
			<div class="button-group">
				<!--
            立即退货 ：
             点第一次的时候，提交退货申请
             点第二次的时候，提交物流信息
              第二次点完之后，按钮变成待商家收货，商家收货成功后变成退货成功
            如果商家拒绝退货，立即退货按钮旁边有个投诉建议按钮 点进去是投诉建议页面
            -->
				<a href="../申请退货.html" class="alinkb">申请退货</a>
				<!--↑点击之后变成↓-->
				<!--<a href="../提交物流信息.html" class="alinkb">提交物流信息</a>-->
				<!-- ↑点击之后变成↓ -->
				<!--<a href="../申请退货.html" class="alinkb">等待商家收货</a>-->


				<a href="../申请退货.html" class="alinkb">投诉建议</a>
			</div>

		</section>
	</li>
	<li class="order_foot p">
		共 <label for="">2 </label>件商品，总价：<span>￥545.00</span>
	</li>
</ul>
<!-- order_detail_title_end -->
<!-- order_time -->
<ul class="detail_order">
	<li class="title">
		物流详情
	</li>
	<li class="name">
		<label>物流公司：</label>
		<span>顺丰速递</span>
		<label>物流单号：</label>
		<span>102543245842513</span>
	</li>
	<li class="dleft">
		<div><span class="active"></span></div>
		<div><span></span></div>
		<div><span></span><span></span></div>
	</li>
	<li class="dright">
		<p class="active">重庆市渝中区派件员：河流（13883845552）正在派件<span>2015-11-30  14:30:21</span></p>
		<p>包裹已到达：重庆市渝中区网点<span>2015-11-30  14:30:21</span></p>
		<p>已发货<span>2015-11-30  14:30:21</span></p>
		<p>已发货<span>2015-11-30  14:30:21</span></p>
	</li>
</ul>
<!-- order_time_end -->
</section>
</body>
</html>