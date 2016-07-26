<#import "/common/app.ftl" as app>
<script src="${app.basePath}/static/js/easyui/jquery.easyui.min.js" type="text/javascript"></script>
<script src="${app.basePath}/static/js/easyui/easyui-lang-zh_CN.js" type="text/javascript"></script>
<div class="subnav"><div class="content_menu ib_a blue line_x"><a href="javascript:;" class="add fb J_showdialog" onclick="returnList()"><em>返回列表</em></a>&#12288;</div></div>
<div class="pad_lr_10">
<form id="userCampaignForm" action="${app.basePath}/admin/usercampaign/save" class="easyui-form" method="post" data-options="novalidate:true">
<input type="hidden" name="campaignId" value="<#if campaign??>${campaign.id?c}</#if>" />
<table class="table_form" width="100%">
	<tr>
    	<th width="150">头像：</th>
    	<td colspan ="3"><img src="${campaign.uavatar!''}" width="100px" height="100px"></td>
    </tr>
	<tr>
        <th width="150">用户：</th>
        <td width="12%"><#if campaign.compaignUser??>${campaign.compaignUser.uname!''}</#if></td>
        <th width="150">竞选名称：</th>
        <td>${campaign.uname!''}</td>
    </tr>
	<tr>
        <th width="150">地区：</th>
        <td width="12%"><#if campaign.district??>${campaign.district.name!''}</#if></td>
        <th width="150">排名：</th>
        <td>${campaign.level!'99999'}</td>
    </tr>
    <tr>
        <th width="150">代理数：</th>
        <td width="12%">${campaign.agentCount!'0'}</td>
        <th width="150">企业级供应商：</th>
        <td>${campaign.enterpriseCount!'0'}</td>
    </tr>
    <tr>
    	<th width="150">竞选宣言：</th>
    	<td colspan ="3">${campaign.declaration!''}</td>
    </tr>
	<tr>
    	<th width="150">个人简历：</th>
    	<td colspan ="3">${campaign.resume!''}</td>
    </tr>
	    <tr>
	        <th width="150">参与时间：</th>
	        <td colspan ="3">
	        	<label><#if campaign.createTime??>${campaign.createTime?string('yyyy-MM-dd HH:mm:ss')}</#if></label>
	        </td>
	    </tr>
	<tr>
		<th width="150">状态：</th>
		<td colspan ="3">
			<input type="radio" name="status" value="1" <#if campaign.status==1>checked</#if>>满足条件
            <input type="radio" name="status" value="2" <#if campaign.status==2>checked</#if>>进行中
		</td>
	</tr>
    <tr>
        <td>
        </td>
        <td colspan ="3">
            <button type="button" class="smt mr10" onclick="saveUserCampaign()">保存</button>
        </td>
    </tr>
</table>
</form>
</div>
