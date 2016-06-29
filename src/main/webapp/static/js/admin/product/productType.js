function searchProductType(t){
	var url = basePath + "/admin/producttype/search";
	var loadData = null;
	
	if(t){
		loadData = null;
	}else{
		loadData = $("#productTypeform").serializeArray();
	}
	$("#results").load(url,loadData);
}

function editProductType(id){
	var url = basePath + "/admin/producttype/edit";
	var loadData = {"id" : id};
	$("#rightform").load(url,loadData);
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
	searchProductType(false);
}

// 提交新增、修改广告为内容
function saveProductType(){
	var f = $('#productForm').form('enableValidation').form('validate');
	if(f){
		$("#productForm").form("submit",{
			success : function(data){
				 var result = eval("("+data+")");
				  if(result.code==1){
					  $.messager.alert('消息提醒','分类保存成功。');
					  returnList();
					  refreshList();
				  }else{
					  $.messager.alert('消息提醒','分类保存失败!');
				  }
			}
		});
		
	}
}

function deleteProduct(id){
	$.messager.confirm('消息提醒', '删除后其下级分类也同步删除，确定要继续删除吗?', function(r){
		if (r){
			var url = basePath+"/admin/producttype/delete";
			var loadData={"id":id};
			$.post(url,loadData,delProductTypeCallback,"text");
		}
	});
}

function delProductTypeCallback(data){
	var result = eval("("+data+")");
	if(result.code==1){
		$.messager.alert('消息提醒','删除成功。');
		refreshList();
	}else{
		$.messager.alert('消息提醒','删除失败!');
	}
}


/*
function myLoadFilter(data,parentId){
	concole.debug("json in")
function setData(){
	 var todo = [];
	 for(var i=0; i<data.length; i++){
		 todo.push(data[i]);
	 }
	 while(todo.length){
		 var node = todo.shift();
		 if (node.children){
		  node.state = 'closed';
		  node.children1 = node.children;
		  node.children = undefined;
		  todo = todo.concat(node.children1);
		 }
	 }
}

setData(data);

	var tg = $(this);
	var opts = tg.treegrid('options');
	opts.onBeforeExpand = function(row){
	 if (row.children1){
		 tg.treegrid('append',{
			  parent: row[opts.idField],
			  data: row.children1
		 });
		 row.children1 = undefined;
		 tg.treegrid('expand', row[opts.idField]);
	 }
	 return row.children1 == undefined;
	};
	return data;
}

*/