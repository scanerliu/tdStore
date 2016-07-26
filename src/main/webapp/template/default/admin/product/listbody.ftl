<#import "/common/app.ftl" as app>
<div class="pad_lr_10">
<div class="J_tablelist table_list">
	<div id="district">
		<!--
		<input type="button" value="批量删除" onclick="batchDelete()">&nbsp;&nbsp;&nbsp;
		<input type="button" value="添加" onclick="addDistrict()">
		-->
    </div>

<table width="100%" cellspacing="0">
	<thead>
		<tr>
			<th width="4%"><input type="checkbox" class="J_checkall" id="J_checkall" name="checkall"></th>
			<th width="4%">ID</th>
			<th align="center" width="20%">名称</th>
			<th align="center">编号</th>
			<th align="center">类型</th>
			<th align="center">图片</th>
			<th align="center">创建时间</th>
			<th align="center">参考价</th>
			<th align="center">库存</th>
			<th align="center">状态</th>
			<th align="center">上架状态</th>
			<th >操作</th>
		</tr>
	</thead>
<tbody>
<#if productList?? && productList?size gt 0>
		<#list productList as pro>
			<tr>
		        <td align="center">
		        	<input type="checkbox" value="${pro.id?c}" name="subbox" class="J_checkitem">
		        </td>
		        <td align="center">${pro.id?c}</td>
		        <td align="center">
		        ${pro.name!''}<br>
		        <#if pro.newRecommend==1><label class="red" title="新品推荐">New</label></#if>
		        <#if pro.hotRecommend==1>&nbsp;&nbsp;<label class="red" title="热门推荐">Hot</label></#if>
		        <#if pro.fineRecommend==1>&nbsp;&nbsp;<label class="red" title="精品推荐">Fine</label></#if>
		        <#if pro.typeRecommend==1>&nbsp;&nbsp;<label class="red" title="分类推荐">Type</label></#if>
		        </td>
		        <td align="center">${pro.code!''}</td>
		        <td align="center">
		        	<#if pro.kind??>
		        	<#switch pro.kind>
						  <#case 1>普通商品<#break>
						  <#case 2>商品包<#break>
						  <#case 3>零元购<#break>
						  <#case 4>10元购<#break>
						  <#case 5>预售<#break>
						  <#case 6>秒杀<#break>
		        	</#switch>
		        	</#if>
		        </td>
		        <td align="center"><img src="${app.basePath}${pro.imageUrl!''}" style="width:100px;height:50px;"></td>
		        <td align="center"><#if pro.createTime??>${pro.createTime?string('yyyy-MM-dd')}</#if></td>
		        <td align="center"><#if pro.price??>${pro.price?string('0.00')}</#if></td>
		        <td align="center">${pro.quantum!'0'}</td>
		        <td align="center">${pro.getStatusStr()!''}</td>
		        <td align="center">${pro.getOnshelfStr()!''}</td>
		        <td align="center">
		            <a class="J_confirmurl" href="javascript:;" onclick="deleteProduct(${pro.id?c})">删除</a>
		           |<a class="J_confirmurl" href="javascript:;" onclick="editProduct(${pro.id?c})">编辑</a>
		        </td>
	    	</tr>
	    </#list>
	</#if>
</tbody>
</table>
<input type="hidden" value="${sc.kind!''}" name="kind">
<input type="hidden" value="${sc.status!''}" name="status">
<input type="hidden" value="${sc.onshelf!''}" name="onshelf">
<input type="hidden" value="${sc.name!''}" name="name">
<input type="hidden" value="${sc.uid!''}" name="uid">
<input type="hidden" value="${sc.newRecommend!''}" name="newRecommend">
<input type="hidden" value="${sc.hotRecommend!''}" name="hotRecommend">
<input type="hidden" value="${sc.fineRecommend!''}" name="fineRecommend">
<input type="hidden" value="${sc.typeRecommend!''}" name="typeRecommend">
</div>
<div class="btn_wrap_fixed"></div>
<div class="btn_wrap_fixed">
<#assign pageId="products" />
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
