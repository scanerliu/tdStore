<#import "/common/app.ftl" as app>
<div class="subnav"><div class="content_menu ib_a blue line_x">
</div></div>
<div class="pad_lr_10">
<select style="width: 100px;" name="status">
    <option value="-1">请选择状态...</option>
    <option value="1" <#if sc.status?? && sc.status==1>selected="selected"</#if>>未审核</option>
    <option value="2" <#if sc.status?? && sc.status==2>selected="selected"</#if>>已通过</option>
    <option value="3" <#if sc.status?? && sc.status==3>selected="selected"</#if>>未通过</option>
</select>
<input type="text" name="searchName" value="${sc.searchName!''}"><input type="button" value="搜索" onclick="searchExperienceStore(false)">
<div class="J_tablelist table_list">
<table width="100%" cellspacing="0">
<thead>
<tr>
	<th>ID</th>
	<th align="center">用户</th>
	<th align="center">地区</th>
	<th align="center">地址</th>
	<th align="center">电话</th>
	<th align="center">经度</th>
	<th align="center">维度</th>
	<th align="center">产品类</th>
	<th align="center">状态</th>
	<th align="center">申请时间</th>
	<th align="center">审核时间</th>
	<th align="center">审核人</th>
	<th align="center">排序</th>
	<th align="center">操作</th>
	
</tr>
</thead>
<tbody>
<#if experienceStoreList??>
<#list experienceStoreList as experienceStore>
    <tr>
        <td align="center">${experienceStore.id?c}</td>
        <td align="center">${experienceStore.user.uname!''}</td>
        <td align="center">${experienceStore.regionFullName!''}</td>
        <td align="center">${experienceStore.address!''}</td>
        <td align="center">${experienceStore.telphone!''}</td>
        <td align="center">${experienceStore.lng?c!''}</td>
        <td align="center">${experienceStore.lat?c!''}</td>
        <td align="center">${experienceStore.storeTypeNames!''}</td>
        <td align="center">${experienceStore.statusStr!''}</td>
        <td align="center">${experienceStore.createTime?string('yyyy-MM-dd HH:mm:ss')!''}</td>
        <td align="center"><#if experienceStore.updateTime??>${experienceStore.updateTime?string('yyyy-MM-dd HH:mm:ss')!''}</#if></td>
        <td align="center"><#if experienceStore.updatePerson??>${experienceStore.updatePerson.uname!''}</#if></td>
        <td align="center">${experienceStore.sort!''}</td>
        <td align="center">
            <a class="J_showdialog" href="javascript:;" onclick="editExperienceStore(${experienceStore.id?c})">详情</a>
        </td>
    </tr>
</#list>
</#if>
</tbody>
</table>
</div>
<div class="btn_wrap_fixed">
</div>
</div>
<div class="btn_wrap_fixed">
<#assign pageId="ExperienceStore" />
<#include "/admin/common/common_postpage.ftl" />
</div>
<script>
$(function(){
    $("#J_checkall").click(function() {
        $('input[name="subbox"]').prop("checked",this.checked); 
    });
    var $subBox = $("input[name='subbox']");
    $subBox.click(function(){
        $("#J_checkall").prop("checked",$subBox.length == $("input[name='subbox']:checked").length ? true : false);
    });   
});
</script>
