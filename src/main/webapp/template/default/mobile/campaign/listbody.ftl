<#import "/common/app.ftl" as app>
<!-- header_top -->
<div class="top_heater">
    <a href="javascript:history.go(-1);" title="" class="hleft hback"></a>
    <span>排行榜</span>
</div>
<!-- header_top end -->
<script>
    $(function(){
        ranktop();
        three_bing();
    })
</script>
<!-- 地区三级联动 -->
<form  id="form">
<div class="three3" id="three3">
    <section>
        <span><#if province??>${province.name!''}<#else>请选择</#if></span>
        <select name="provinceId" id="" >
            <option value="">请选择</option>
        	<#if districtList??>
        	<#list districtList as dis>
            	<option value="${dis.id?c}" <#if dissc.provinceId?? && dissc.provinceId = dis.id>selected</#if>>${dis.name!''}</option>
            </#list>
            </#if>
        </select>
    </section>
    <section>
        <span><#if city??>${city.name!''}<#else>请选择</#if></span>
        <select name="cityId" id="">
            <option value="">请选择</option>
            <#if cityList??>
        	<#list cityList as dis>
            	<option value="${dis.id?c}" <#if dissc.cityId?? && dissc.cityId = dis.id>selected</#if>>${dis.name!''}</option>
            </#list>
            </#if>
        </select>
    </section>
    <section>
        <span><#if regin??>${regin.name!''}<#else>请选择</#if></span>
        <select name="regionId" id="">
            <option value="">请选择</option>
            <#if regionList??>
        	<#list regionList as dis>
            	<option value="${dis.id?c}" <#if dissc.regionId?? && dissc.regionId = dis.id>selected</#if>>${dis.name!''}</option>
            </#list>
            </#if>
        </select>
    </section>
</div>
<!-- 地区三级联动-结束 -->

<!-- Center Start -->
<section class="container">
    <ul class="rank_top" id="rank_top">
        <#if campaignList??>
        <#list campaignList as comp>
        <li>
            <a class="fl">
                <img src="${app.basePath}${comp.uavatar!''}" alt="">
                <i></i>
            </a>
            <section class="fr">
                <aside class="as1">${comp.uname!''}</aside>
                <aside class="as2">${comp.resume!''}</aside>
                <aside class="as3"><menu>1962</menu>人</aside>
            </section>
        </li>
        </#list>
        </#if>
    </ul>
    <ul class="rank_match" id="rank_match">
    	<#if campaignList??>
        <#list campaignList as comp>
        <li class="">
            <div class="opa_bg" id="opa_bg">
                <section><img src="${app.basePath}${comp.uavatar!''}" alt="${comp.uname!''}"></section>
                <section>${comp.uname!''}</section>
                <section><span></span></section>
                <section>${comp.declaration!''}</section>
                <menu></menu>
            </div>
        </li>
        </#list>
     	</#if>
    </ul>
</section>
<!-- Center End -->
