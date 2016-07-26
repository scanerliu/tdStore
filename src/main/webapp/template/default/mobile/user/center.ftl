<#import "/common/app.ftl" as app> 
<#include "/common/common.ftl" />
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="Content-Language" content="zh-CN">
<title>个人中心</title>
<meta name="keywords" content="${system.webkeywords!''}">
<meta name="description" content="${system.webdescription!''}">
<meta name="copyright" content="${system.webcopyright!''}" />
<link rel="shortcut icon" href="${app.basePath}/static/default/images/icon.ico" />
<meta name="viewport" content="initial-scale=1,maximum-scale=1,minimum-scale=1">
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">
<!-- css -->
<link href="${app.basePath}/static/touch/css/common.css" rel="stylesheet" type="text/css" />
<link href="${app.basePath}/static/touch/css/main.css" rel="stylesheet" type="text/css" />
<link href="${app.basePath}/static/touch/css/x_pc.css" rel="stylesheet" type="text/css" />
<!-- js -->
<script type="text/javascript" src="${app.basePath}/static/touch/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="${app.basePath}/static/touch/js/common.js"></script>
<script type="text/javascript" src="${app.basePath}/static/js/mobile/user/user.js"></script>
</head>
<body class="body_gray">

  <!-- pop-ups -->
  <article class="pop-ups">
    <section class="pop-one">
      <img src="${app.basePath}/static/default/mobile/x-img/icon_pop_success.png" alt="">
      <div>签到成功，恭喜您！</div>
      <div>本次获得<span id="gettedIntegral">0</span>积分</div>
    </section>
  </article>
  <script type="text/javascript">
    $(document).ready(function(){
    	$(document).click(function(){
			$('.pop-ups').hide();
		});
    });
  </script>
  <!-- pop-ups end -->

  <!-- header_top -->
  <header class="pc-head">
    <div class="head-img">
      <img src="${currentUser.uavatar!''}" alt="暂无头像">
    </div>
    <div class="user-name">
      <p class="p1">${currentUser.unick!''}</p>
      <p class="p2">${currentUser.uname!''}</p>
    </div>
    <div class="right-icon">
      <a class="icon-news" href="${app.basePath}/mobile/user/messageList" title=""></a>
      <div class="clear"></div>
      <div class="vip-level"><#if membership??>${membership.name!''}</#if></div>
    </div>
    <a class="btn-sign-in" href="javascript:sign('${currentUser.uid?c}');" title="">签到</a>
  </header>
  <!-- header_top end -->
  <!-- order_detail_title -->
  <section class="container person-center">
  	<#if !currentUser.utel??>
	    <article class="tips">提示：PC上登陆请绑定手机号码</article>
  	</#if>
  	<a class="a_go" href="${app.basePath}/mobile/supply/order" title="去发货">
        <label>去发货</label>
        <i class="icon-next"></i>
    </a>
    <article class="pc-nav">
      <a href="${app.basePath}/mobile/order/list" title="">
        <img src="${app.basePath}/static/default/mobile/x-img/pic_pc_index_nan_1.png" alt="">
        <span>我的订单</span>
      </a>
      <a href="我的钱包.html" title="">
        <img src="${app.basePath}/static/default/mobile/x-img/pic_pc_index_nan_2.png" alt="">
        <span>我的钱包</span>
      </a>
      <a href="我的积分.html" title="">
        <img src="${app.basePath}/static/default/mobile/x-img/pic_pc_index_nan_3.png" alt="">
        <span>我的积分</span>
      </a>
      <a href="我的流水.html" title="">
        <img src="${app.basePath}/static/default/mobile/x-img/pic_pc_index_nan_5.png" alt="">
        <span>我的流水</span>
      </a>
      <a href="我的退货.html" title="">
            <img src="${app.basePath}/static/default/mobile/x-img/pic_pc_index_nan_4.png" alt="">
            <span>我的退货</span>
        </a>
    </article><!-- pc-nav end -->
    <article class="pc-menu">
      <a href="${app.basePath}/mobile/user/info" title="个人信息">
        <i class="icon bg_icon_1"></i>
        <label>个人信息</label>
        <i class="icon-next"></i>
      </a>
      <a href="${app.basePath}/mobile/user/changePassword" title="修改密码">
        <i class="icon bg_icon_2"></i>
        <label>修改密码</label>
        <i class="icon-next"></i>
      </a>
      <a href="${app.basePath}/mobile/user/recommendPeople" title="推荐人">
        <i class="icon bg_icon_3"></i>
        <label>推荐人</label>
        <i class="icon-next"></i>
      </a>
      <a href="${app.basePath}/mobile/user/downUserList" title="联盟创客">
        <i class="icon bg_icon_4"></i>
        <label>联盟创客</label>
        <i class="icon-next"></i>
      </a>
      <a href="${app.basePath}/mobile/user/mySpread" title="我的推广">
        <i class="icon bg_icon_5"></i>
        <label>我的推广</label>
        <i class="icon-next"></i>
      </a>
      <a href="../成为代理-分类列表.html" title="">
        <i class="icon bg_icon_6"></i>
        <label>我的店铺</label>
        <i class="icon-next"></i>
      </a>
      <a href="../成为代理-分类列表.html" title="">
        <i class="icon bg_icon_7"></i>
        <label>成为代理</label>
        <i class="icon-next"></i>
      </a>
      <a href="${app.basePath}/mobile/user/productComment" title="我的评价">
        <i class="icon bg_icon_8"></i>
        <label>我的评价</label>
        <i class="icon-next"></i>
      </a>
      <a href="${app.basePath}/mobile/user/collect/list" title="我的收藏">
        <i class="icon bg_icon_9"></i>
        <label>我的收藏</label>
        <i class="icon-next"></i>
      </a>
      <a href="${app.basePath}/mobile/user/productManage" title="商品管理">
        <i class="icon bg_icon_8"></i>
        <label>商品管理</label>
        <i class="icon-next"></i>
      </a>
      <a href="javascript:goCampaign();" title="参加竞选">
        <i class="icon bg_icon_10"></i>
        <label>参加竞选</label>
        <i class="icon-next"></i>
      </a>
      <a href="下载app.html" title="">
        <i class="icon bg_icon_11"></i>
        <label>下载app</label>
        <i class="icon-next"></i>
      </a>
      <a href="../成为代理-分类列表.html" title="">
        <i class="icon bg_icon_12"></i>
        <label>供应商资质认证</label>
        <i class="icon-next"></i>
      </a>
      <a href="${app.basePath}/mobile/experience/list" title="附近门店">
        <i class="icon bg_icon_13"></i>
        <label>附近门店</label>
        <i class="icon-next"></i>
      </a>
    </article><!-- pc-menu end -->
  </section>
  <a class="btn-sign-out" href="javascript:;" title="">退出登录</a>

  <div class="clear" style="width:100%;height:1rem;"></div>

  <!-- footer -->
  <footer class="common-footer">
    <a href="${app.basePath}/mobile" title="首页">
      <i class="icon">
        <img class="icon-1" src="${app.basePath}/static/default/mobile/x-img/bg_icon_14.png" alt="">
        <img class="icon-2" src="${app.basePath}/static/default/mobile/x-img/bg_icon_14_1.png" alt="">
      </i>
      <span>首页</span>
    </a>
    <a href="${app.basePath}/mobile/productType/list" title="分类">
      <i class="icon">
        <img class="icon-1" src="${app.basePath}/static/default/mobile/x-img/bg_icon_15.png" alt="">
        <img class="icon-2" src="${app.basePath}/static/default/mobile/x-img/bg_icon_15_1.png" alt="">
      </i>
      <span>分类</span>
    </a>
    <a href="${app.basePath}/mobile/shoppingcart/list" title="购物车">
      <i class="icon">
        <img class="icon-1" src="${app.basePath}/static/default/mobile/x-img/bg_icon_16.png" alt="">
        <img class="icon-2" src="${app.basePath}/static/default/mobile/x-img/bg_icon_16_1.png" alt="">
      </i>
      <span>购物车</span>
    </a>
    <a class="active" href="${app.basePath}/mobile/user/center" title="">
      <i class="icon">
        <img class="icon-1" src="${app.basePath}/static/default/mobile/x-img/bg_icon_17.png" alt="个人中心">
        <img class="icon-2" src="${app.basePath}/static/default/mobile/x-img/bg_icon_17_1.png" alt="个人中心">
      </i>
      <span>我的</span>
    </a>
  </footer>
  <script type="text/javascript">
    $(function(){
      $('.common-footer a').click(function(){
        $(this).addClass('active').siblings().removeClass('active');
      })
    });
  </script>
  <script>
  	function goCampaign(){
    	var url = basePath+"/mobile/user/hasJoinedCampaing";
		$.post(url, function(data){
			var jsonData = eval('('+data+')');
			if(jsonData.code == "0"){
				window.location.href=basePath+"/mobile/user/joinElection";
			}else if(jsonData.code == "1"){
				alert(jsonData.msg);
			}
		}, "text");
    }
  </script>
  <!-- footer end -->
</body>
</html>
