<#import "/common/app.ftl" as app> <#include "/common/common.ftl" /><!DOCTYPE html><html lang="en"><head>    <meta charset="UTF-8">    <meta http-equiv="Content-Language" content="zh-CN">    <meta name="keywords" content="">    <meta name="description" content="">    <meta name="copyright" content="" />    <meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">    <meta content="yes" name="apple-mobile-web-app-capable">    <meta content="black" name="apple-mobile-web-app-status-bar-style">    <meta content="telephone=no" name="format-detection">    <title>创客联盟</title>    <!-- css -->    <link href="${app.basePath}/static/touch/css/common.css" rel="stylesheet" type="text/css" />    <link href="${app.basePath}/static/touch/css/main.css" rel="stylesheet" type="text/css" />    <link href="${app.basePath}/static/touch/css/f-personalcenter.css" rel="stylesheet" type="text/css" />    <!-- js -->    <script type="text/javascript" src="${app.basePath}/static/touch/js/jquery-1.9.1.min.js"></script>    <script type="text/javascript" src="${app.basePath}/static/touch/js/common.js"></script></head><body class="body_gray"><!-- header_top --><div class="top_heater">    <a href="${app.basePath}/mobile/user/center" title="返回个人中心" class="hleft hback"></a>    <span>商品管理</span></div><!-- header_top end --><!-- Center Start --><section class="container product-manager">    <article>        <span class="title">全国代理商品管理</span>        <div class="menu">            <a href="${app.basePath}/mobile/user/lookMyProduct" title="查看我的商品">查看我的商品</a>            <a href="javascript:;" onclick="$(this).parent().slideUp();" title="">收起全部</a>        </div>    </article>    <#if isSupplier>	    <article>	        <span class="title">供货商商品管理</span>	        <div class="menu">	            <a href="${app.basePath}/mobile/user/lookSupplierProduct" title="查看我的商品">查看我的商品</a>	            <a href="上传商品.html" title="">上传商品</a>	            <a href="零元商品上传.html" title="">零元商品上传</a>	            <a href="javascript:;" onclick="$(this).parent().slideUp();" title="">收起全部</a>	        </div>	    </article>    	</#if></section><script>    $(function(){        $('.product-manager .title').click(function(){            $(this).siblings('div').slideDown();        });    });</script><!-- Center End --></body></html>