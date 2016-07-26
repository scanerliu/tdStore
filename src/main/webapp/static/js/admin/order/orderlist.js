function searchOrders(f){
	var url = basePath+"/admin/order/search";
	var loadData = "";
	if(f){
		loadData = $("#searchform").serializeArray();
	}else{
		loadData = $("#listform").serializeArray();
	}
	$("#results").loading().load(url,loadData);
}

function fnGotoPageOrders(num){
	searchOrders(false);
}

function orderDetail(num){
	var url = basePath+"/admin/order/details";
	var loadData={"id":num};
	$("#rightform").loading().load(url,loadData);
	showForm();
}

function showForm(){
	$("#rightlist").hide();
	$("#rightform").show();
}
function returnList(){
	$("#rightlist").show();
	$("#rightform").html("").hide();
}
function refreshList(){
	searchOrders(false);
}
function saveOrder(){
	var f = $('#orderForm').form('enableValidation').form('validate');
	if(f){
		$('#orderForm').form('submit',{
			  success : function(data){
				  var result = eval("("+data+")");
				  if(result.code==1){
					  $.messager.alert('消息提醒','订单保存成功。');
					  returnList();
					  refreshList();
				  }else{
					  $.messager.alert('消息提醒','订单保存失败!');
				  }
			  }
			 });
	};
}

function delOrder(id){
	$.messager.confirm('消息提醒', '确定要删掉该订单吗?', function(r){
		if (r){
			var url = basePath+"/admin/order/delete";
			var loadData={"id":id};
			$.post(url,loadData,delOrderCallback,"text");
		}
	});
}

function delOrderCallback(data){
	var result = eval("("+data+")");
	if(result.code==1){
		$.messager.alert('消息提醒','订单删除成功。');
		refreshList();
	}else{
	  $.messager.alert('消息提醒','订单删除失败!');
	}
}
/**
 * 发货按钮弹出发货面板
 * @param num 订单id
 */
function shipOrder(num){
	if(num>0){
		var url = basePath+"/admin/order/shippment?id="+num;
		$('#shipmentwindow').window({
		    title: '订单发货',
		    closed: false,
		    cache: false,
		    minimizable: false,
		    maximizable: false,
		    collapsible: false,
		    href: url,
		    modal: true,
		    onClose : function() {
                $(this).window({closed:true});
            }
		});
		
	}
}
/**
 * 发货操作
 */
function shippmentOrder(){
	var f = $('#shipmentForm').form('enableValidation').form('validate');
	if(f){
		$('#shipmentForm').form('submit',{
			  success : function(data){
				  var result = eval("("+data+")");
				  if(result.code==1){
					  $.messager.alert('消息提醒','订单发货成功。');
					  closeShipmentwin();
					  refreshList();
				  }else{
					  $.messager.alert('消息提醒',result.msg);
				  }
				  
			  }
		});
	}
}
/**
 * 关闭发货面板
 */
function closeShipmentwin(){
	$('#shipmentwindow').window({closed:true});
}

/**
 * 退款按钮弹出退款面板
 * @param num 订单id
 */
function refundOrder(num){
	if(num>0){
		var url = basePath+"/admin/order/refund?id="+num;
		$('#refundwindow').window({
		    title: '订单退款',
		    closed: false,
		    cache: false,
		    minimizable: false,
		    maximizable: false,
		    collapsible: false,
		    href: url,
		    modal: true,
		    onClose : function() {
                $(this).window({closed:true});
            }
		});
		
	}
}
/**
 * 退款操作
 */
function refundop(){
	var f = $('#refundForm').form('enableValidation').form('validate');
	if(f){
		$('#refundForm').form('submit',{
			  success : function(data){
				  var result = eval("("+data+")");
				  if(result.code==1){
					  $.messager.alert('消息提醒','订单退款成功。');
					  closerefundwin();
					  refreshList();
				  }else{
					  $.messager.alert('消息提醒',result.msg);
				  }
				  
			  }
		});
	}
}
/**
 * 关闭发货面板
 */
function closerefundwin(){
	$('#refundwindow').window({closed:true});
}