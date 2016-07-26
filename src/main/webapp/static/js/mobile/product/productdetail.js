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
	var idkeys = idkey.split("_");
	idkeys.sort();
	var skuList = skus.skuList;
	var exist = false;
	var price = 0;
	var stock = 0;
	var skuId = 0;
	$.each(skuList,function(i,o){
		var specifica = o.specificationids.split("_");
		specifica.sort();
		if(specifica.toString()==idkeys.toString()){
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
	var postage = $("#propostage").val();
	var quantity = $("#prodquantity").val();
	var itemType = $("#productKind").val();
	
	if(skuId>0){
		var url = basePath+"/mobile/shoppingcart/addsku";
		var postData = {"productId":productId,"productSkuId":skuId,"price":skuPrice,"postage":postage,"quantity":quantity,"itemType":itemType};
		$.post(url,postData,addToShoppingcartCallback,"text");
	}else{
		alert("请先选择商品规格！");
	}
	
}

function addToShoppingcartCallback(data){
	var result = eval("("+data+")");
	if(result.code==1){
		alert('加入购物车成功。');
	}else{
		alert('加入购物车失败!');
	}
}
//立即购买
function buyNow(){
	var skuId = $("#skuId").val();
	var skuPrice = $("#skuPrice").val();
	var productId = $("#productId").val();
	var postage = $("#propostage").val();
	var quantity = $("#prodquantity").val();
	var itemType = $("#productKind").val();
	
	if(skuId>0){
		var url = basePath+"/mobile/shoppingcart/buynow";
		var postData = {"productId":productId,"productSkuId":skuId,"quantity":quantity,"productType":itemType};
		window.location.href= url+"?productId="+productId+"&productSkuId="+skuId+"&quantity="+quantity;
	}else{
		alert("请先选择商品规格！");
	}
}


/**
 * 点击收藏
 */
function addCollect(productId)
{
    if (undefined == productId)
    {
        return;
    }
    
    $.ajax({
        type:"post",
        url:basePath+"/mobile/product/collect",
        data:{"productId": productId},
        dataType: "json",
        success:function(data){
            // 需登录
            if (data.code==0)
            {
                alert(data.msg);
                setTimeout(function(){
                    window.location.href = basePath+"/mobile/login";
                }, 1000); 
            }
        }
    });
}

