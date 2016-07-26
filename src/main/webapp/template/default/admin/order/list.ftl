<#import "/common/app.ftl" as app>
<#include "/common/common.ftl" />
<script src="${app.basePath}/static/js/easyui/jquery.easyui.min.js" type="text/javascript"></script>
<script src="${app.basePath}/static/js/easyui/easyui-lang-zh_CN.js" type="text/javascript"></script>
<script src="${app.basePath}/static/js/admin/order/orderlist.js" type="text/javascript"></script>
<div id="rightlist">
<div class="subnav"></div>
<form id="searchform">
<table width="100%" cellspacing="0" class="search_form">
	<tbody>
		<tr>
			<td>
				<div class="explain_col">
					<!--发布时间：
					<input type="text" value="" size="12" class="date" id="time_start" name="time_start">
					-<input type="text" value="" size="12" class="date" id="time_end" name="time_end">
					&nbsp;&nbsp;-->
					<select name="orderStatus" class="J_cate_select mr10">
						<option value="">-订单状态-</option>
						<option value="-1">已取消</option>
						<option value="1">新订单</option>
						<option value="2">已完成</option>
					</select>
					<select name="payStatus" class="J_cate_select mr10">
						<option value="">-支付状态-</option>
						<option value="1">已支付</option>
						<option value="2">未支付</option>
						<option value="3">部分退款</option>
						<option value="4">全额退款</option>
					</select>
					<select name="shipmentStatus">
						<option value="">-发货状态-</option>
						<option value="1">已发货</option>
						<option value="2">未发货</option>
						<option value="3">部分退货</option>
						<option value="4">全部退货</option>
					</select>
					&nbsp;&nbsp;订单编号:
					<input type="text" value="" size="25" class="input-text" name="orderNo">
					<input type="hidden" value="${sc.orderType!''}" size="25" name="orderType">
					<input type="button" value="搜索" class="btn" name="search" onclick="searchOrders(true)">
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
<div id="shipmentwindow" class="easyui-window" closed="true" style="width:600px;height:300px;padding:10px"></div>
<div id="refundwindow" class="easyui-window" closed="true" style="width:600px;height:300px;padding:10px"></div>
<div id="paywindow" class="easyui-window" closed="true" style="width:600px;height:300px;padding:10px"></div>
<script type="text/javascript">
$(function(){
    searchOrders(true);
});
</script>
