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
    <title>我的订单</title>
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
    <a class="hleft hback" href="${app.basePath}/mobile/user/center" title=""></a>
    <span>我的订单</span>
  </div>
  <!-- header_top end -->
  <section class="container my-order">
	<form id="searchform">
		<input type="hidden" id="sc_fliterType" name="fliterType" value="0"/>
	</form>
    <!-- top-nav -->
    <ul class="top-nav">
      <li class="active"><a href="javascript:;" title="">全部订单</a></li>
      <li><a href="javascript:;" title="">待付款</a></li>
      <li><a href="javascript:;" title="">待发货</a></li>
      <li><a href="javascript:;" title="">待收货</a></li>
      <li><a href="javascript:;" title="">待评价</a></li>
    </ul>
    <ol>
      <li>
      	<form id="listform">
        <article id="results">
        </article>
        <input type="hidden" name="fliterType" id="fliterType" value=""/>
        <input type="hidden" name="pageNo" id="pageNo" value=""/>        
        </form>
      </li>
    </ol>
    <script type="text/javascript">
      $(function(){
        function tab(tabTitle,tabList){
          $(tabTitle).on('click','a',function(){
            var $self = $(this);//当前a标签
            var $active = $self.closest('li');//当前点击li
            var index = $active.prevAll('li').length;//当前索引
            $("#sc_fliterType").val(index);
            searchOrders(true);
			
          	$active.addClass('active').siblings('li').removeClass('active');
            
          });
        };
        tab('.my-order ul','');
        searchOrders(true);
      })
    </script>
  </section>
</body>
</html>