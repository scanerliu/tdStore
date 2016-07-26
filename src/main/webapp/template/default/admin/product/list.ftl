<#import "/common/app.ftl" as app>
<#include "/common/common.ftl" />
<link rel="stylesheet" href="${app.basePath}/static/js/easyui/easyui.css"/>
<script src="${app.basePath}/static/js/easyui/jquery.easyui.min.js" type="text/javascript"></script>
<script src="${app.basePath}/static/js/easyui/easyui-lang-zh_CN.js" type="text/javascript"></script>
<script src="${app.basePath}/static/js/admin/core.js" type="text/javascript"></script>
<script src="${app.basePath}/static/js/admin/product/product.js" type="text/javascript"></script>
<script src="${app.basePath}/static/js/easyui/validator.js" type="text/javascript"></script>
<div id="rightlist">
	<div class="subnav">
		<div class="content_menu ib_a blue line_x">
			<a data-height="190" data-width="450" data-id="add" data-title="添加商品" href="javascript:;" class="add fb J_showdialog" onclick="editProduct(0)">
			<em>添加商品</em></a>&#12288;
			<input type="button" onclick="bacthOperProducts(1)" value="上架" class="btn" title="批量上架"/>
			<input type="button" onclick="bacthOperProducts(2)" value="下架" class="btn" title="批量下架"/>
			<input type="button" onclick="bacthOperProducts(3)" value="热门推荐" class="btn" title="批量热门推荐"/>
			<input type="button" onclick="bacthOperProducts(4)" value="取消热门推荐" class="btn" title="批量取消热门推荐"/>
			<input type="button" onclick="bacthOperProducts(5)" value="新品推荐" class="btn" title="批量新品推荐"/>
			<input type="button" onclick="bacthOperProducts(6)" value="取消新品推荐" class="btn" title="批量取消新品推荐"/>
			<input type="button" onclick="bacthOperProducts(7)" value="精品推荐" class="btn" title="批量精品推荐"/>
			<input type="button" onclick="bacthOperProducts(8)" value="取消精品推荐" class="btn" title="批量取消精品推荐"/>
			<input type="button" onclick="bacthOperProducts(9)" value="分类推荐" class="btn" title="批量分类推荐"/>
			<input type="button" onclick="bacthOperProducts(10)" value="取消分类推荐" class="btn" title="批量取消分类推荐"/>
		</div>
	</div>
	<input type="hidden" id="isFromProductPackage" value="<#if isFromProductPackage??>yes<#else>no</#if>">
	<form id="searchform">
	<input type="hidden" value="${sc.kind!''}" name="kind">
	<table width="100%" cellspacing="0" class="search_form">
		<tbody>
			<tr>
				<td>
					<div class="explain_col">
						<!--发布时间：
						<input type="text" value="" size="12" class="date" id="time_start" name="time_start">
						-<input type="text" value="" size="12" class="date" id="time_end" name="time_end">
						&nbsp;&nbsp;-->
						商品名称:<input type="text" value="" size="25" class="input-text" name="name">
						&nbsp;&nbsp;供应商ID:<input type="text" value="" size="25" class="input-text" name="uid">
						&nbsp;&nbsp;
						<select name="status" class="J_cate_select mr10">
							<option value="">-商品状态-</option>
							<option value="1">正常</option>
							<option value="2">待审核</option>
							<option value="3">审核不通过</option>
						</select>
						<select name="onshelf" class="J_cate_select mr10">
							<option value="">-商品上架状态-</option>
							<option value="true">上架中</option>
							<option value="false">已下架</option>
						</select>
						&nbsp;&nbsp;<label><input type="checkbox"  value="1" name="newRecommend">新品推荐</label>
						&nbsp;&nbsp;<label><input type="checkbox" value="1" name="hotRecommend">热门推荐</label>
						&nbsp;&nbsp;<label><input type="checkbox" value="1" name="fineRecommend">精品推荐</label>
						&nbsp;&nbsp;<label><input type="checkbox" value="1" name="typeRecommend">分类推荐</label>
						&nbsp;&nbsp;
						<input type="button" value="搜索" class="btn" name="search" onclick="searchProduct(true)">
					</div>
				</td>
			</tr>
		</tbody>
	</table>
	</form>
	<form id="listform">
		<div id="results"></div>
	</form>
</div>
<div id="rightform"></div>

<script type="text/javascript">
	$(function(){
	    searchProduct(true);
	});
</script>
