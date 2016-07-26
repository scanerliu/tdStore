<#import "/common/app.ftl" as app>
<script src="${app.basePath}/static/js/admin/core.js" type="text/javascript"></script>
<form id="payForm" action="${app.basePath}/admin/order/payorder" class="easyui-form" method="post" data-options="novalidate:true">
<div class="pad_lr_10">
<div class="J_tablelist table_list">
<input type="hidden" name="orderId" value="${order.orderId!''}">
<table width="100%" cellspacing="0">
<tbody>
<tr>
	<th width="20%">订单编号：</th>
	<td>${order.orderNo!''}</td>
</tr>
<tr>
	<th width="20%">订单应支付金额：</th>
	<td>${order.getUnPayAmount()!'0'}</td>
</tr>
<tr>
	<th>支付金额：</th>
	<td><input type="text" name="payAmount" class="easyui-textbox" value="${order.getUnPayAmount()!'0'}" style="width:200px;height:30px" data-options="required:true,validType:'price',editable:false"></td>
</tr>
</tbody>
</table>
</div>
</div>
<div data-options="region:'south',border:false" style="text-align:center;padding:5px 0 0;">
    <a class="easyui-linkbutton" href="javascript:;" onclick="payop()" style="width:80px">提交</a>
    <a class="easyui-linkbutton" href="javascript:;" onclick="closerepaywin()" style="width:80px">取消</a>
</div>
</form>
<script>
$(function(){
});
</script>