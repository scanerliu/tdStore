<#import "/common/app.ftl" as app>
<script src="${app.basePath}/static/js/easyui/easyui-lang-zh_CN.js" type="text/javascript"></script>
<div class="subnav"><div class="content_menu ib_a blue line_x"><a href="javascript:;" class="add fb J_showdialog" onclick="returnList()"><em>返回列表</em></a><#if tdExperienceStore.status == 1>&nbsp;&nbsp;&nbsp;<input type="button" class="btn" value="通过" onclick="goCheck(${tdExperienceStore.id!''}, true)">&nbsp;&nbsp;&nbsp;<input type="button" class="btn" value="拒绝" onclick="goCheck(${tdExperienceStore.id!''}, false)"></#if>&#12288;</div></div>

<div class="pad_lr_10">
<form id="userSupplierForm" action="${app.basePath}/admin/experiencestore/save" class="easyui-form" method="post" data-options="novalidate:true">
<table class="table_form" width="100%">
	<tr>
        <th>用户：</th>
        <td>${tdExperienceStore.user.uname!''}</td>
    </tr>
    <tr>
        <th>地区：</th>
        <td>${tdExperienceStore.regionFullName!''}</td>
    </tr>
     <tr>
        <th>地址：</th>
        <td>${tdExperienceStore.address!''}</td>
    </tr>
	<tr>
        <th>电话：</th>
        <td>${tdExperienceStore.telphone!''}</td>
    </tr>
    <tr>
        <th width="150">图片：</th>
        <td>
    		<#if imgList??>
    			<#list imgList as img>
    				<#if img_index != 0 && img_index%3 == 0><br/></#if>	    			
	    			<img width='200' height='200' src="${img!''}"/>
				</#list>
			</#if>
        </td>
    </tr>
    <tr>
        <th>经度：</th>
        <td>
        	<input type="text" name="lng" class="easyui-textbox" value="<#if tdExperienceStore.lng??>${tdExperienceStore.lng?c!''}</#if>"  style="width:200px;height:30px" data-options="required:true" validType="length[2,64]">
        	<a href="http://api.map.baidu.com/lbsapi/getpoint/" target="_blank">坐标拾取</a>
        </td>
    </tr>
    <tr>
        <th>维度：</th>
        <td>
        	<input type="text" name="lat" class="easyui-textbox" value="<#if tdExperienceStore.lat??>${tdExperienceStore.lat?c!''}</#if>"  style="width:200px;height:30px" data-options="required:true" validType="length[2,64]">
        	<a href="http://api.map.baidu.com/lbsapi/getpoint/" target="_blank">坐标拾取</a>
        </td>
    </tr>
    <tr>
        <th>产品类：</th>
        <td>${tdExperienceStore.storeTypeNames!''}</td>
    </tr>
    <tr>
        <th>状态：</th>
        <td>${tdExperienceStore.statusStr!''}</td>
    </tr>
	<tr>
        <th>申请时间：</th>
		<td>${tdExperienceStore.createTime?string('yyyy-MM-dd HH:mm:ss')}</td>
    </tr>
    <#if tdExperienceStore.updateTime??>
		 <tr>
	        <th>审核时间：</th>
	        <td>${tdExperienceStore.updateTime?string('yyyy-MM-dd HH:mm:ss')}</td>
	    </tr>   
	</#if>
	<#if tdExperienceStore.updatePerson??>
		 <tr>
	        <th>审核人：</th>
	        <td>${tdExperienceStore.updatePerson.uname!''}</td>
	    </tr>   
	</#if>
	<tr>
        <th>排序：</th>
        <td><input type="text" id="sort" onkeydown="return allowKeybordJustForNumAndBackSpace(event)" style="width:50px;" value="${tdExperienceStore.sort?c!''}"><input type="button" value="修改" onclick="changeSort(${tdExperienceStore.id?c})"></td>
    </tr>
</table>
</form>
</div>