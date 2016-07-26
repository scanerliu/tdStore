<#import "/common/app.ftl" as app>
<#if shipList??>
	<#list shipList as ship>
	<section>
	    <div class="top">
	      <p class="p1">订单号：<span>${ship.order.orderNo!''}</span></p>
	      <p class="p2">申请状态：<span>
	      <#if ship.status==1>
	      		待处理
	      <#elseif ship.status==2>
	      		同意退款
	      <#elseif ship.status==3>
	      		不同意退款
	      <#elseif ship.status==4>
	      		完成退款
	      </#if>
	      </span></p>
	    </div>
	    <div class="bottom">
	      	订单交易金额 <span>￥${ship.order.payAmount!'0'}</span> 退款金额<strong class="price">￥<span>${ship.returnAmount!'0'}</span></strong>
	    </div>
	    <div class="btn-group">
	      <#if ship.status==2 && ship.cargoStatus==2><a href="${app.basePath}/mobile/order/refundtract${ship.id!'0'}" title="">录入物流单号</a></#if>
	    </div>
	  </section>
	</#list>
</#if>

<script type="text/javascript">
  $(function(){
   	 var pageno = ${sc.pageNo+1};
   	 $("#pageNo").val(pageno);
   	<#if sc.hasNextPage?? && sc.hasNextPage==true>
   		$(window).scroll(scrollHandler);
   	</#if>
  })
</script>
