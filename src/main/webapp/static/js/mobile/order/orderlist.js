function searchOrders(f){
	var url = basePath+"/mobile/order/search";
	var loadData = "";
	$(window).off("scroll", scrollHandler);
	if(f){
		loadData = $("#searchform").serializeArray();
		$("#results").loading().load(url,loadData);
	}else{
		loadData = $("#listform").serializeArray();
		$.get(url, loadData, function(html){
	  		  $(html).appendTo("#results");
	  	});
	}
}

var scrollHandler = function(){
    if ($(document).height() - $(this).scrollTop() - $(this).height()<100){
    	searchOrders(false);
    } 
};

function applyRefund(){
	var amount = $("#returnAmount").val();
	if(amount==""||amount==0){
		alert("请填写退款金额！");
		return ;
	}
	$("#refundForm").submit();
}

function applyComplaint(){
	var complaint = $("#complaint").val();
	if(complaint==""||complaint.length<10){
		alert("请正确填写投诉内容：10至100个文字！");
		return ;
	}
	$("#complaintForm").submit();
}