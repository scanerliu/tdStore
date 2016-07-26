<#import "/common/app.ftl" as app>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="Content-Language" content="zh-CN">
    <meta name="keywords" content="${system.webkeywords!''}">
    <meta name="description" content="${system.webdescription!''}">
    <meta name="copyright" content="${system.webcopyright!''}" />
    <link rel="shortcut icon" href="${app.basePath}/static/default/images/icon.ico" />
    <meta name="viewport" content="initial-scale=1,maximum-scale=1,minimum-scale=1">
    <meta content="yes" name="apple-mobile-web-app-capable">
    <meta content="black" name="apple-mobile-web-app-status-bar-style">
    <meta content="telephone=no" name="format-detection">
    <title>分公司申请</title>
    <!-- css -->
    <link rel="stylesheet" href="${app.basePath}/static/touch/css/common.css" type="text/css" />
    <link rel="stylesheet" href="${app.basePath}/static/touch/css/main.css" type="text/css" />
    <link rel="stylesheet" href="${app.basePath}/static/touch/css/swipe.css" />
    <link rel="stylesheet" href="${app.basePath}/static/touch/css/index.css">
    <!-- js -->
    <script type="text/javascript" src="${app.basePath}/static/touch/js/jquery-1.9.1.min.js"></script> 
    <script type="text/javascript" src="${app.basePath}/static/touch/js/swipe.js"></script> 
    <script type="text/javascript" src="${app.basePath}/static/touch/js/common.js"></script>
    <script type="text/javascript" src="${app.basePath}/static/touch/js/index.js"></script>
</head>
<script>
$(document).ready(function(){
    
    $("#isCheck").change(function(){
        var check = document.getElementById("isCheck");
        if(check.checked){
            $("#sub_btn").css("background","#f23030");
            $("#sub_btn").attr("href","${app.basePath}/mobile/agent/dopay?id="+${agent.id?c});
        }else{
            $("#sub_btn").attr("href","javascript:;");
            $("#sub_btn").css("background","#999999");
        }
     });
    
    
    
});
</script>

<body class="">

<!-- header_top -->
<div class="top_heater">
    <a href="javascript:history.go(-1);" title="返回" class="hleft hback"></a>
    <span>成为代理</span>
</div>
<!-- header_top end -->

<!-- Center Start -->
<section class="container">
    <div class="become">
    	<#if agent??>
        <section class="sec1"><img src="${app.basePath}${agent.imageUrl!''}" alt="${agent.title!''}"></section>
        <section class="sec2">${agent.title!''}</section>
        <section class="sec3">${agent.note!''}</section>
        <section class="sec4">￥<span><#if agent.salesPrice??>${agent.salesPrice?string('0.00')}</#if></span></section>
        </#if>
        <section class="sec5"><input type="checkbox" checked="checked" id="isCheck">我已阅读并同意<a href="${app.basePath}/mobile/agent/article" title="《创客联盟代理条款》">《创客联盟代理条款》</a></section>
        <section class="sec6"><a href="${app.basePath}/mobile/agent/dopay?id=${agent.id?c}" title="立即加入" id="sub_btn">立即加入</a></section>
    </div>
</section>
<!-- Center end -->

</body>
</html>