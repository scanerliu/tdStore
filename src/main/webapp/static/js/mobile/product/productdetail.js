/**
 * 规格选择货品操作
 * @param type
 */
function changeProductSku(skus){
	var selectUL = $("#slect .slect");
	var idkey = "";
	var selected = true;
	selectUL.each(function(){
		var sected = $(this).find("li[class='active']");
		if(sected.length==0){
			selected = false;
			return;
		}else{
			idkey = idkey+"_"+sected.text();
		}
	});
	if(idkey.length>0){
		idkey = idkey.substring(1);
	}
	if(!selected){
		return;
	}
	var skuList = skus.skuList;
	var exist = false;
	var price = 0;
	var stock = 0;
	var skuId = 0;
	$.each(skuList,function(i,o){
		if(o.specificationids==idkey){
			exist = true;
			price = o.salesPrice;
			stock = o.stock;
			skuId = o.id;
		}
	});
	if(exist){
		$("#skustock").val(stock);
		$("#skuId").val(skuId);
		$("#skuPrice").val(price);
		$("#prodprice").html(price);
	}else{
		alert("货品已下架！");
		window.location.reload();
	}
	initprodchecked();
}

function initprodchecked(){
	var selectUL = $("#slect .slect");
	var idkey = "";
	var selected = true;
	selectUL.each(function(){
		var sected = $(this).find("li[class='active']");
		if(sected.length==0){
		}else{
			idkey = idkey+"<span>"+sected.text()+"</span>";
		}
	});
	var num = $("#prodquantity").val();
	idkey = idkey + "<span>"+num+"件</span>";
	$("#prodchecked").html(idkey);
}

/**
 * 购买商品数量增减操作
 * @param type
 */
function additem(type){
	var selectUL = $("#slect .slect");
	var selected = true;
	selectUL.each(function(){
		var sected = $(this).find("li[class='active']");
		if(sected.length==0){
			selected = false;
			return;
		}
	});
	if(!selected){
		alert("请先选择商品规格！");
		return;
	}
	var num = $("#prodquantity").val();
	var numint = parseInt(num);
	var stock = $("#skustock").val();
	var stockint = parseInt(stock);
	if(type==1){//add
		numint = numint+1;
		if(numint>stockint){
			alert("商品库存不足，添加失败！");
			return ;
		}
	}else if(type==2){//sub
		numint = numint-1;
		if(numint<1){
			numint=1;
		}
	}
	$("#prodquantity").val(numint);
	initprodchecked();
}

/**
 * 格式输入
 * @param obj
 */
function formatInputSkuNum(obj){
	var num = obj.value.replace(/\D/g,'');
	obj.value = num;
	var selectUL = $("#slect .slect");
	var selected = true;
	selectUL.each(function(){
		var sected = $(this).find("li[class='active']");
		if(sected.length==0){
			selected = false;
			return;
		}
	});
	if(!selected){
		obj.value = 1;
		alert("请先选择商品规格！");
		return;
	}
	var stock = $("#skustock").val();
	var stockint = parseInt(stock);
	var num = obj.value.replace(/\D/g,'');
	if(num<1){
		obj.value=min;
	}else if(num>stockint){
		obj.value=stockint;
	}else{
		obj.value = num;
	}
	initprodchecked();
}

/**
 * 加入购物车操作
 */
function addToShoppingcart(){
	var skuId = $("#skuId").val();
	var skuPrice = $("#skuPrice").val();
	var productId = $("#productId").val();
	
	if(confirm("确定要移除商品？")){
		var url = basePath+"/mobile/shoppingcart/addsku?ids="+ids;
		var postData = {"ids":ids};
		$.post(url,postData,removeItemsCallback,"text");
	}
	
}

function removeItemsCallback(data){
	var result = eval("("+data+")");
	if(result.code==1){
		alert('商品删除成功。');
		refreshList();
	}else{
		alert('商品删除失败!');
	}
}
//去下单
function nextOrder(){
	var url = basePath+"/mobile/shoppingcart/confirmorder";
	alert("稍等一下，功能开发中...");
}
