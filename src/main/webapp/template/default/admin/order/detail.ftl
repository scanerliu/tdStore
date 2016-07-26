<#import "/common/app.ftl" as app>
<td colspan="10">
<div class="J_tablelist table_list table_list2">
<div class="content_menu ib_a blue line_x">
	<#if order.orderStatus!=2>
	<#if order.payStatus==2><span>|</span><a href="javascript:;" onclick="payOrder(${order.orderId!''})" ><em>支付</em></a></#if>
	<#if order.shipmentStatus==2><span>|</span><a href="javascript:;" onclick="shipOrder(${order.orderId!''})" ><em>发货</em></a></#if>
	<#if order.orderType!=2 && (order.payStatus==1||order.payStatus==3)><span>|</span><a href="javascript:;" onclick="refundOrder(${order.orderId!''})" ><em>退款</em></a></#if>
	</#if>
</div>
<div id="dg_${order.orderId}" style="width:800px;height:350px;">
    <div title="基本信息" style="width:800px;height:350px;padding:0px;">
    	<table width="100%" cellspacing="0">
        <tr>
            <td width="50%" valign="top">
            	<#if order?? && order.productList?? && (order.productList?size > 0)>
            	<table width="100%">
            		<tr>
            			<th>商品名称</th>
            			<th>商品类型</th>
            			<th>商品价格</th>
            			<th>商品数量</th>
            		</tr>
            		<#list order.productList as product>
            		<tr>
            			<td>${product.title!''}</td>
            			<td>${product.getItemTypeStr()!''}</td>
            			<td>${product.itemPrice!''}</td>
            			<td>${product.quantity!''}</td>
            		</tr>
            		<#if product.attachments??>
            		<tr>
            			<td colspan="4">
        				<#list product.attachments as atta>
	            			<a href="${app.basePath}${atta}" target="_blank"><img src="${app.basePath}${atta}" alt="图片" width="60"/></a>
	            		</#list>            				
        				</td>
        			</tr>
        			</#if>
            		</#list>
            	</table>
            	<br/>
            	</#if>
            	<table width="100%">
            		<#if order?? && order.skuList?? &&(order.skuList?size>0)>
            		<tr>
            			<th>商品名称</th>
            			<th>商品价格</th>
            			<th>商品数量</th>
            		</tr>
            		<#list order.skuList as sku>
            		<tr>
            			<td>${sku.productName!''} ${sku.displaySpecifications!''}</td>
            			<td>${sku.price!''}</td>
            			<td>${sku.quantity!''}</td>
            		</tr>
            		</#list>
            		</#if>
            	</table>
            </td>
            <td width="20%" valign="top">
            	<table class="table_form" width="100%">
            		<tr>
            			<th width="40%">商品总金额：</th>
            			<td>${order.productAmount!'0'}</td>
            	    </tr>
            	    <tr>            	    
            			<th>邮费：</th>
            			<td>${order.postage!'0'}</td>
            	    </tr>
            	    <tr>
            			<th>订单总金额：</th>
            			<td>${order.totalAmount!'0'}</td>
            	    </tr>
            	    <tr>
            			<th>积分抵扣金额；</th>
            			<td>${order.pointAmount!'0'}</td>
            	    </tr>
            	    <tr>
            			<th>支付金额：</th>
            			<td>${order.payAmount!'0'}</td>
            	    </tr>
            	    <tr>
            			<th>退款金额：</th>
            			<td>-${order.refundAmount!'0'}</td>
            	    </tr>
            	    <tr>
            			<th>订单获得积分：</th>
            			<td>${order.gainPoints!'0'}</td>
            	    </tr>
            	</table>
            </td>
            <td width="30%" valign="top">
            	<table class="table_form" width="100%">
            	    <tr>            	    
            			<th width="30%">供应商账号：</th>
            			<td><#if order.supplierUser??>${order.supplierUser.unick!''}</#if></td>
            	    </tr>
            		<tr>
            			<th>下单账号：</th>
            			<td><#if order.orderUser??>${order.orderUser.unick!''}</#if></td>
            	    </tr>
            		<tr>
            			<th>支付方式：</th>
            			<td>${order.getPaymentStr()!''}</td>
            	    </tr>
            		<tr>
            			<th>联系人：</th>
            			<td><#if order.orderAddress??>${order.orderAddress.customerName!''}</#if></td>
            	    </tr>
            		<tr>
            			<th>联系电话：</th>
            			<td><#if order.orderAddress??>${order.orderAddress.telphone!''}</#if></td>
            	    </tr>
            	    <tr>            	    
            			<th>收货地址：</th>
            			<td><#if order.orderAddress??>${order.orderAddress.regionFullName!''} ${order.orderAddress.address!''}</#if></td>
            	    </tr>
            	</table>
            </td>
        </tr>
        </table>
    </div>
    <div title="发货信息" style="width:800px;height:350px;padding:0px;" href="${app.basePath}/admin/order/shippments?orderId=${order.orderId!''}&type=1">
    </div>
    <div title="退货信息" style="width:800px;height:350px;padding:0px;" href="${app.basePath}/admin/order/shippments?orderId=${order.orderId!''}&type=2">
    </div>
    <div title="操作日志" style="width:800px;height:350px;padding:0px;" href="${app.basePath}/admin/order/logs?orderId=${order.orderId!''}">
    </div>
</div>
</div>
<script>
$(function(){
     $('#dg_${order.orderId}').tabs({
	    border:false,
	    fit:true,
	    tabPosition:"left"
	});
});
</script>
</td>