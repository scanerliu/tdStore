<#import "/common/app.ftl" as app>
<script src="${app.basePath}/static/js/easyui/jquery.easyui.min.js" type="text/javascript"></script>
<script src="${app.basePath}/static/js/easyui/easyui-lang-zh_CN.js" type="text/javascript"></script>
<script src="${app.basePath}/static/js/uploadify/jquery.uploadify.min.js" type="text/javascript"></script>
<#--<script src="${app.basePath}/static/js/jquery-1.7.2.js" type="text/javascript"></script>-->
<link rel="stylesheet" type="text/css" href="${app.basePath}/static/js/uploadify/uploadify.css" />
<div class="subnav"><div class="content_menu ib_a blue line_x"><a href="javascript:;" class="add fb J_showdialog" onclick="returnList()"><em>返回列表</em></a>&#12288;</div></div>
<div class="pad_lr_10">
<form id="productForm" action="${app.basePath}/admin/product/save" class="easyui-form" method="post" data-options="novalidate:true">
<input type="hidden" name="productId" value="<#if tdProduct??>${tdProduct.id?c}</#if>">
<div class="easyui-tabs" style="width:100%； height:750px;">
		<div title="商品信息" style="padding:10px">
			<table class="table_form" width="100%;hright:700px;">
		    <tr>
		        <th width="150">商品分类：</th>
		        <td>
		        	<select id="productTypeSelections" name="typeId" style="width:200px;" onchange="flushHpgg(this)">
		        		<option value="0" <#if !tdProduct?? || tdProduct.typeId == 0>selected="selected"</#if>>无上级分类</option>
		        		<#if productTypeList ??>
		        		<#list productTypeList as pro>
		        			<option value="${pro.id?c}" <#if !pro.subList?? || pro.subList?size == 0>isLastLevel="yes"</#if> <#if tdProduct?? && tdProduct.typeId == pro.id>selected="selected"</#if>>${pro.name!''}</option>
		        			<#if pro.subList??>
		        			<#list pro.subList as spro>
		        				<option value="${spro.id?c}" <#if !spro.subList?? || spro.subList?size == 0>isLastLevel="yes"</#if> <#if tdProduct?? && tdProduct.typeId == spro.id>selected="selected"</#if>>├ ${spro.name!''}</option>
		        				<#if spro.subList??>
			        			<#list spro.subList as tpro>
			        				<option value="${tpro.id?c}" <#if !tpro.subList?? || tpro.subList?size == 0>isLastLevel="yes"</#if> <#if tdProduct?? && tdProduct.typeId == tpro.id>selected="selected"</#if>>&emsp;├ ${tpro.name!''}</option>
			        			</#list>
			        			</#if>
		        			</#list>
		        			</#if>	
		        		</#list>
		        		</#if>
		        	</select>
		        </td>
		    </tr>
		    <tr>
		    	<th>货品规格：</th>
		    	<td id="hpgg">
		    		<#if attributeList??>
			    		<#list attributeList as attribute>
			    			<#if (attribute_index != 0) && (attribute_index lt attributeList?size)><br/></#if>
			    			<label>${attribute.name!''}：<label>
			    			<#if attribute.tdProductAttributeOptionList??>
			    				<#list attribute.tdProductAttributeOptionList as option>
			    					${option.name!''}<input type="checkbox" onchange="flushTable()" id="spe_${attribute.name!''}_${option.name!''}" name="${attribute.name!''}" <#if keyValueStr?? && keyValueStr?contains(attribute.name + "=" + option.name)>checked</#if>>&nbsp;&nbsp;&nbsp;
								</#list>
							</#if>
			    		</#list>	
					</#if>
		    	</td>
		    </tr>
		    <tr>
		    	<th>货品组合：</th>
		    	<td>
		    		<div id="skuAssemble"></div>
		    	</td>
		    </tr>
		    <tr>
		        <th>商品类型：</th>
		        <td>
		            <input type="radio" name="kind" onclick="changekind(1)" value="1" <#if !tdProduct?? || (tdProduct.kind?? && tdProduct.kind==1)>checked</#if>>普通商品&emsp;
		            <input type="radio" name="kind" onclick="changekind(this.value)" value="2" <#if tdProduct?? && tdProduct.kind?? && tdProduct.kind==2>checked</#if>>商品包&emsp;
		            <input type="radio" name="kind" onclick="changekind(this.value)" value="3" <#if tdProduct?? && tdProduct.kind?? && tdProduct.kind==3>checked</#if>>0元购&emsp;
		            <input type="radio" name="kind" onclick="changekind(this.value)" value="4" <#if tdProduct?? && tdProduct.kind?? && tdProduct.kind==4>checked</#if>>10元购&emsp;
		            <input type="radio" name="kind" onclick="changekind(this.value)" value="5" <#if tdProduct?? && tdProduct.kind?? && tdProduct.kind==5>checked</#if>>预售&emsp;
		            <input type="radio" name="kind" onclick="changekind(this.value)" value="6" <#if tdProduct?? && tdProduct.kind?? && tdProduct.kind==6>checked</#if>>秒杀&emsp;
		        </td>
		    </tr>
		     <tr>
		        <th>品牌：</th>
		        <td>
		            <select name="brandId">
		            	<#if brandList??>
		    				<#list brandList as brand>
		    					<option value="${brand.id!''}">${brand.name!''}</option>
							</#list>
						</#if>
		            </select>
		        </td>
		    </tr>
		    <tr>
		        <th width="150">名称：</th>
		        <td><input type="text" name="name" class="easyui-textbox" value="<#if tdProduct??>${tdProduct.name!''}</#if>"  style="width:200px;height:30px" data-options="required:true" validType="length[2,64]"></td>
		    </tr>
		    <tr>
		        <th width="150">副标题：</th>
		        <td><input type="text" name="title" class="easyui-textbox" value="<#if tdProduct??>${tdProduct.title!''}</#if>"  style="width:200px;height:30px"  ></td>
		    </tr>
		    <tr>
		        <th width="150">编号：</th>
		        
		        <td><input type="text" name="code" class="easyui-textbox" value="<#if tdProduct??>${tdProduct.code!''}</#if>"  style="width:200px;height:30px" data-options="required:true" validType="length[2,20]"></td>
		    </tr>
		    <tr>
		        <th width="150">参考价：</th>
		        
		        <td><input type="text" name="price" class="easyui-textbox" value="<#if tdProduct??>${tdProduct.price?string('0.00')}</#if>"  style="width:200px;height:30px" data-options="required:true" validType="currency"></td>
		    </tr>
		    <tr>
		        <th width="150">运费：</th>
		        
		        <td><input type="text" name="postage" class="easyui-textbox" value="<#if tdProduct??>${tdProduct.postage?string('0.00')}</#if>"  style="width:200px;height:30px" data-options="required:true" validType="currency"></td>
		    </tr>
		    <tr>
		        <th width="150">库存：</th>
		        
		        <td><input type="text" name="quantum" class="easyui-textbox" value="<#if tdProduct??>${tdProduct.quantum!'0'}</#if>"  style="width:200px;height:30px" data-options="required:true" ></td>
		    </tr>
		    <tr class="seckill" <#if !tdProduct?? || tdProduct.kind != 5 || tdProduct.kind != 6>style="display:none"</#if>>
		        <th  width="150">开始时间：</th>
		        <td>
	            	<input type="text" name="startTime" class="easyui-datetimebox" value="<#if tdProduct?? && tdProduct.startTime??>${tdProduct.startTime?string('yyyy-MM-dd HH:mm:ss')}</#if>" style="width:200px;height:30px"  data-options="showSeconds:true">
		        </td>
		    </tr>
		     <tr class="seckill" <#if !tdProduct?? || tdProduct.kind != 5 || tdProduct.kind != 6>style="display:none"</#if>>
		        <th  width="150">结算时间：</th>
		        <td>
		            <input type="text" name="endTime" class="easyui-datetimebox" value="<#if tdProduct?? && tdProduct.endTime??>${tdProduct.endTime?string('yyyy-MM-dd HH:mm:ss')}</#if>" style="width:200px;height:30px" data-options="showSeconds:true" >
		        </td>
		    </tr>
		    <tr>
		        <th width="150">封面图片：</th>
		        <td>
		        	<input type="hidden" id="image" name="imageUrl" value="<#if tdProduct??>${tdProduct.imageUrl!''}</#if>">
		        	<input type="file" id="file_upload"/>
		        	<div id="showImg">
		        		<#if tdProduct?? &&  tdProduct.imageUrl??>
		        			<img width='100' height='100' src='${app.basePath}${tdProduct.imageUrl!''}'/>
						</#if>
		        	</div>
		        </td>
		    </tr>
		    <tr>
		        <th>状态：</th>
		        <td>
		            <input type="radio" name="status" value="1" <#if !tdProduct?? || (tdProduct.status?? && tdProduct.status==1)>checked</#if>>正常
		            <input type="radio" name="status" value="2" <#if tdProduct?? && tdProduct.status?? && tdProduct.status==2>checked</#if>>待审核
		            <input type="radio" name="status" value="3" <#if tdProduct?? && tdProduct.status?? && tdProduct.status==3>checked</#if>>审核不通过
		        </td>
		    </tr>
		    <tr>
		        <th>关键字：</th>
		        <td>
	            	<input type="text" name="keyword" class="easyui-textbox" value="<#if tdProduct??>${tdProduct.keyword!''}</#if>" style="width:200px;height:30px" >
		        </td>
		    </tr>
		    <tr>
		        <th>综合排序值：</th>
		        <td>
		            <input type="text" name="sort" class="easyui-textbox" value="<#if tdProduct??>${tdProduct.sort!'0'}<#else>0</#if>" style="width:200px;height:30px" data-options="required:true" >
		            值越大越靠前
		        </td>
		    </tr>
		    <!--
		    <tr>
		        <th>新品推荐值：</th>
		        <td>
		            <input type="text" name="newRecommend" class="easyui-textbox" value="<#if tdProduct??>${tdProduct.newRecommend!'0'}<#else>0</#if>" style="width:200px;height:30px" data-options="required:true" >
		            值越大越靠前
		        </td>
		    </tr>
		    <tr>
		        <th>热销推荐值：</th>
		        <td>
		            <input type="text" name="hotRecommend" class="easyui-textbox" value="<#if tdProduct??>${tdProduct.hotRecommend!'0'}<#else>0</#if>" style="width:200px;height:30px" data-options="required:true" >
		            值越大越靠前
		        </td>
		    </tr>
		    <tr>
		        <th>精品推荐值：</th>
		        <td>
		            <input type="text" name="fineRecommend" class="easyui-textbox" value="<#if tdProduct??>${tdProduct.fineRecommend!'0'}<#else>0</#if>" style="width:200px;height:30px" data-options="required:true" >
		            值越大越靠前
		        </td>
		    </tr>
		    <tr>
		        <th>分类推荐值：</th>
		        <td>
		            <input type="text" name="typeRecommend" class="easyui-textbox" value="<#if tdProduct??>${tdProduct.typeRecommend!'0'}<#else>0</#if>" style="width:200px;height:30px" data-options="required:true" >
		            值越大越靠前
		        </td>
		    </tr>
		    -->
		    <tr>
		        <th>提示：</th>
		        <td>
		            <input type="text" name="tags" class="easyui-textbox" value="<#if tdProduct??>${tdProduct.tags!''}</#if>" style="width:200px;height:30px" >
		        </td>
		    </tr>
		</table>
		<#if !tdProduct??>
			<input type="hidden" name="newRecommend" value="0" >
			<input type="hidden" name="fineRecommend" value="0" >
			<input type="hidden" name="typeRecommend" value="0" >
			<input type="hidden" name="hotRecommend" value="0" >
			<input type="hidden" name="onshelf" value="false" >
		</#if>
		</div>
		<div title="图文信息" style="padding:10px">
			<table class="table_form" width="100%;hright:700px;">
				<tr>
			        <th width="150">展示图片：</th>
			        <td>
			        	<div id="imgs">
			        		<#if imgList??>
			        		<#list imgList as img>
			        			<input type='hidden' name='attId' id="attId${img.id?c}"  value='${img.id?c}'/>
			        		</#list>
			        		</#if>
			        	</div>
			        	<input type="file" id="file_uploads"/>
			        	<div >
			        		<table>
								<tr id="showImgs">
					        		<#if imgList??>
					        		<#list imgList as img>
					        			<td style="text-align: center;" id="img${img.id?c}">
					        			<img width='100' height='100'  src='${app.basePath}${img.attachment!''}'/><br/>
					        			<a href="javascript:removeImg(${img.id?c});">删除</a>
					        			</td>
					        		</#list>
					        		</#if>
				        		</tr>
							</table>
			        	</div>
			        </td>
			    </tr>
			    <tr>
			    	<th width="150">图文详情：</th>
			        <td colspan="2" >
			        	<textarea style="display:none;" rows="5" cols="60" id="ac" name="description"><#if detail??>${detail.description!''}</#if></textarea>
					    <!-- 加载编辑器的容器 -->
					    <script id="detail" name="detail" type="text/plain"><#if detail??>${detail.description!''}</#if></script>
			        </td> 
		    	</tr>
			</table>
		</div>
		<div title="其他信息" style="padding:10px">
		<table class="table_form" width="100%;hright:700px;">
			<tr>
		    	<th width="150">包装配送：</th>
		        <td colspan="2" >
		        	<textarea style="display:none;" rows="5" cols="60" id="ac" name="description"><#if packDetail??>${packDetail.description!''}</#if></textarea>
				    <!-- 加载编辑器的容器 -->
				    <script id="packDetail" name="packDetail" type="text/plain"><#if packDetail??>${packDetail.description!''}</#if></script>
		        </td> 
	    	</tr>
	    	<tr>
		    	<th width="150">售后服务：</th>
		        <td colspan="2" >
		        	<textarea style="display:none;" rows="5" cols="60" id="ac" name="description"><#if afterSale??>${afterSale.description!''}</#if></textarea>
				    <!-- 加载编辑器的容器 -->
				    <script id="afterSale" name="afterSale" type="text/plain"><#if afterSale??>${afterSale.description!''}</#if></script>
		        </td> 
	    	</tr>
		</table>
		</div>
		<div title="销售状况" style="padding:10px">
		<table class="table_form" width="100%;hright:700px;">
			<tr>
				<th width="150">浏览次数：</th>
				<td><#if productStat??>${productStat.viewCount!'0'}<#else>0</#if></td>
			</tr>
			<tr>
				<th width="150">总销售数量：</th>
				<td><#if productStat??>${productStat.buyCount!'0'}<#else>0</#if></td>
			</tr>
			<tr>
				<th width="150">总销售次数：</th>
				<td><#if productStat??>${productStat.buyTimes!'0'}<#else>0</#if></td>
			</tr>
			<tr>
				<th width="150">总评论数：</th>
				<td><#if productStat??>${productStat.reviewCount!'0'}<#else>0</#if></td>
			</tr>
			<tr>
				<th width="150">好评数：</th>
				<td><#if productStat??>${productStat.positiveRate!'0'}<#else>0</#if></td>
			</tr>
			<tr>
				<th width="150">中评数：</th>
				<td><#if productStat??>${productStat.neutralRate!'0'}<#else>0</#if></td>
			</tr>
			<tr>
				<th width="150">差评数：</th>
				<td><#if productStat??>${productStat.negativeRate!'0'}<#else>0</#if></td>
			</tr>
		</table>
		</div>
</div>
 <input type="hidden" name="tableData" value="" id="formTableData">
 <button type="button" class="smt mr10" onclick="saveProduct()">保存</button>
</form>
<#--用作规格顺序-->
<input type="hidden" value="${speOrder!''}" id="speOrder">
<input type="hidden" value="${specifiactionNum!''}" id="speSize">
</div>
<!-- 配置文件 -->
<script type="text/javascript" src="${app.basePath}/static/js/ueditor/ueditor.config.js"></script>
<!-- 编辑器源码文件 -->
<script type="text/javascript" src="${app.basePath}/static/js/ueditor/ueditor.all.js"></script>
<!-- 实例化编辑器 -->
<script type="text/javascript">
	$(document).ready(function(){
	    UE.getEditor('detail');
	    UE.getEditor('packDetail');
	    UE.getEditor('afterSale');	
	});
</script>
<script>

	$(function(){
		<#if tdProduct??>
			makeTable(${tableJsonData}, "skuAssemble");
			<#if specifiactionNum??>
				editText("skuAssemble", ${specifiactionNum});	
			</#if>
		</#if>
		
		// 图片上传
		$('#file_upload').uploadify({
				'multi'    : false, // 限制单图上传
				'formData' : {'type' : "product"},
				'swf'      : basePath+'/static/js/uploadify/uploadify.swf', // swf存放的路径
				'fileObjName' : 'file',
				'uploader' : basePath+'/uploadify/upload/singleFile',    // 处理上传的Servlet
				'buttonText' : '点击选择图片',
				'onUploadSuccess' : function(file, data, response) {
					var result = eval("("+data+")");
					$("#showImg").empty();
					$("#showImg").append("<img width='100' height='100' src='"+basePath+result.savedFile+"'/>");
					$("#image").val(result.savedFile);
					// $.messager.alert('消息提醒','图片' + file.name + ' 上传成功。 ');
		        },
		        'onUploadError' : function(file, errorCode, errorMsg, errorString) {
		        	//alert('The file ' + file.name + ' could not be uploaded: ' + errorString);
		        	$.messager.alert('消息提醒','上传失败。');
		        }
		});
		$('#file_uploads').uploadify({
				'multi'    : true, // 多图上传
				'formData' : {'type' : "product"},
				'swf'      : basePath+'/static/js/uploadify/uploadify.swf', // swf存放的路径
				'fileObjName' : 'file',
				'uploader' : basePath+'/product/upload/singleFile',    // 处理上传的Servlet
				'buttonText' : '点击选择图片',
				'onUploadSuccess' : function(file, data, response) {
					var result = eval("("+data+")");
					$("#showImg").empty();
					$("#showImgs").append("<td style='text-align: center;' id='img"+result.atts+"'><img width='100' height='100'  src='"+basePath+result.savedFile+"'/><br/><a href='javascript:removeImg("+result.atts+");'>删除</a></td>");
					$("#imgs").append("<input type='hidden' name='attId' id='attId"+result.atts+"' value='"+result.atts+"'/>");
				//	$.messager.alert('消息提醒','图片' + file.name + ' 上传成功。 ');
		        },
		        'onUploadError' : function(file, errorCode, errorMsg, errorString) {
		        	//alert('The file ' + file.name + ' could not be uploaded: ' + errorString);
		        	$.messager.alert('消息提醒','上传失败。');
		        }
		});
	});


function removeImg(id)
{
	
	$.ajax({
		type : "post",
		data : {"attId":id},
		url : basePath+"/admin/product/deleteImg",
		success:function(data){
		} 
	})
	
	$("#attId"+id).remove();
	$("#img"+id).remove();
}

// 根据数据产生表
function makeTable(data, divId){
	var content = "<table><thead>";
	// 添加表头
	content = content + "<tr>";
	for(var i = 0; i < data.tableHead.length; i ++){
		content = content + "<th>"+data.tableHead[i]+"</th>";
	}
	content = content + "</tr></thead><tbody>";
	// 添加表体
	for(var i = 0; i < data.tableContent.length; i ++){
		content = content + "<tr id='"+ data.tableContent[i].trId +"'>";
		for(var j = 0; j < data.tableContent[i].trData.length; j ++){
			content = content + "<td>"+data.tableContent[i].trData[j]+"</td>";
		}
		content = content + "</tr>";
	}
	content = content + "</tbody></table>";
	// 添加表
	$("#" + divId).append(content);
}

// 获取表中的数据
function getTableData(divId){
	var headData = $("#" + divId).find("table thead tr th");
	var rowData = $("#" + divId).find("table tbody tr");
	var tableData = "{'tableHead':[";
	// 获取表头数据
	for(var i = 0; i < headData.length; i ++){
		if(i == headData.length - 1){
			tableData = tableData + "'" + headData.eq(i).text() + "'";
		}else{
			tableData = tableData + "'" + headData.eq(i).text() + "',";
		}
	}
	tableData = tableData + "],";
	// 获取表体数据
	tableData = tableData + "'tableContent':[";
	for(var i = 0; i < rowData.length; i ++){
		if(i == rowData.length - 1){
			var trId = rowData.eq(i).attr("id");
			tableData = tableData + "{'trId':'" + trId + "','trData':[";
			var oneRow =  rowData.eq(i).find("td");
			for(var j = 0; j < headData.length; j ++){
				if(j == headData.length - 1){
					tableData = tableData + "'" + oneRow.eq(j).text() + "'";
				}else{
					tableData = tableData + "'" + oneRow.eq(j).text() + "',";
				}
			}
			tableData = tableData + "]}";
		}else{
			var trId = rowData.eq(i).attr("id");
			tableData = tableData + "{'trId':'" + trId + "','trData':[";
			var oneRow =  rowData.eq(i).find("td");
			for(var j = 0; j < headData.length; j ++){
				if(j == headData.length - 1){
					tableData = tableData + "'" + oneRow.eq(j).text() + "'";
				}else{
					tableData = tableData + "'" + oneRow.eq(j).text() + "',";
				}
			}
			tableData = tableData + "]},";
		}
	}
	tableData = tableData + "]}";
	//alert(tableData);
	return tableData;
}

/* 双击修改表格内容 */
function editText(theId, num){
	$("#"+theId).on("dblclick","td",function(){
		if($(this).index() < num || $(this).html().indexOf("input") >= 0){
			//alert(num);
			return;
		}else{
			var tdText = $(this).text();
			var tdWidth = $(this).width();
			var tdHeight = $(this).height();
			$(this).html("<input type=\"text\" value=\""+tdText+"\" style=\"height:"+tdHeight+"px;line-height:"+tdHeight+"px;width:"+tdWidth+"px;\">");
			// 获得焦点
			$(this).find("input").focus();
			//失去焦点
			$("#"+theId).find("table tbody tr input").blur(function(){
				$(this).parent().text($(this).val());
			});
		}
	});
}

//提交前触发该函数可确保表中的input全部消失
function changeInputToText(tableId){
	var theInputs = $("#"+tableId).find("input");
	for(var i = 0; i < theInputs.length; i ++){
		var inputValue = theInputs.eq(i).val();
		theInputs.eq(i).parent().text(inputValue);
	}
}

// 选择规格后刷新表
function flushTable(){
	var checkedSpe = $("#hpgg").find("input:checked");
	var ids = "";
	for(var i = 0; i < checkedSpe.length; i ++){
		ids = ids + checkedSpe.eq(i).attr("id");
		if(i < checkedSpe.length - 1){
			ids = ids + ",";
		}
	}
	var idsArrayWithSuffix = ids.split(",");
	var keyValueStr = "";
	for(var i = 0; i < idsArrayWithSuffix.length; i ++){
		keyValueStr += idsArrayWithSuffix[i].substr(4);
		if(i < idsArrayWithSuffix.length - 1){
			keyValueStr += ",";
		}
	}
	var keyValueArray = keyValueStr.split(",");
	console.log("keyValueArray:" + keyValueArray);
	// 规格类别 格式：类别1_类别2_类别3_
	var keyStr = "";
	for(var i = 0; i < keyValueArray.length; i ++){
		var kv = keyValueArray[i].split("_");
		var k = kv[0];
		if(keyStr.indexOf(k) < 0){
			keyStr = keyStr + k + "_";
		}
	}
	keyStr = keyStr.substr(0, keyStr.length-1);
	console.log("keyStr:" + keyStr);
	// 利用keyStr判断每类规格均处于至少选中一个的状态
	var isAllSpecSelected = 0;
	var theSpeOrder = $("#speOrder").val();
	console.log("theSpeOrder:" + theSpeOrder);
	if(theSpeOrder == keyStr){
		isAllSpecSelected = 1;
	}
	// 若没所有规格类别没有至少选中一个则返回
	if(isAllSpecSelected == 0){
		return;
	}
	var keyArray = keyStr.split("_");
	console.log("keyArray:" + keyArray);
	
	// 希望得到数据格式["颜色_黑色_白色", "尺寸_39_40_41"]
	for(var i = 0; i < keyArray.length; i ++){
		var theKey = keyArray[i];
		for(var j = 0; j < keyValueArray.length; j ++){
			var kv = keyValueArray[j].split("_");
			var k = kv[0];
			var v = kv[1];
			if(theKey == k){
				keyArray[i] = keyArray[i] + "_" + v;
			}
		}
	}
	var ka = Array();
	// 调整规格顺序和后台的规格顺序一致
	var speOrder = $("#speOrder").val();
	var speOrderArray = speOrder.split("_");
	console.log("speOrder:");
	console.log(speOrder);
	for(var i = 0; i < speOrderArray.length; i ++){
		var spe = speOrderArray[i];
		for(var j = 0; j < keyArray.length; j ++){
			if(keyArray[j].indexOf(spe) >= 0){
				ka.push(keyArray[j]);
			}
		}
	}
	console.log("ka:" + ka);
	keyArray = ka;
	console.log(keyArray);
	var rearaay = Array();
	// 格式调整为["黑色_白色", "39_40_41"]
	for(var i = 0; i < keyArray.length; i ++){
		keyArray[i] = keyArray[i].substr(keyArray[i].indexOf("_") + 1);
		var temp = keyArray[i].split("_");
		rearaay.push(temp);
	}
		
	console.log(rearaay);
	
	arr = rearaay;
	var alen = arr.length;
	var result;
	if(alen == 1){
		result = arr[0];
	}else{
		// result是所有属性按照规格顺序的全排列
		result = twoArrayAssembleToTrIds(arr[0],arr[1]);
		var i=2;
		while(i<alen){
			result = twoArrayAssembleToTrIds(result,arr[i]);
			i++;
		}
	}
	
	console.log("result:" + result);
	// 原表中的trIds
	var tableTrIdsArray = preparedStrIdsArray("skuAssemble", result);
	console.log("tableTrIdsArray:");
	console.log(tableTrIdsArray);
	// 根据result产生新的tableTr
	var newTableStr = "";
	for(var i = 0; i < result.length; i ++){
		var r = result[i].toString();
		var isExist = 0;
		for(var j = 0; j < tableTrIdsArray.length; j ++){
			if(tableTrIdsArray[j].toString() == r){
				// 原表中存在该tr
				var originalStr = $("[id='" + r + "']").html();
				newTableStr = newTableStr + "<tr id="+ r +">" + originalStr + "</tr>";
				isExist = 1;
				break;
			}
		}
		// 原表中不存在该tr
		if(isExist == 0){
			var newStr = "";
			var speStrArray = r.split("_");
			for(var k = 0; k < speStrArray.length; k ++){
				newStr = newStr + "<td>"+ speStrArray[k] +"</td>"
			}
			newStr = newStr + "<td></td><td></td><td></td><td></td><td></td><td></td><td></td>";
			newTableStr = newTableStr + "<tr id="+ r +">" + newStr + "</tr>";
		}
	}
	console.log("newTableStr:" + newTableStr);
	$("#skuAssemble table tbody tr").remove();
	$("#skuAssemble table tbody").html(newTableStr);
	
	//var ssize = $("#speSize").val();
	
	return result;
}

// 二维数组全排列
function twoArrayAssembleToTrIds(arr1,arr2){
	var arra = Array();
	for(var m in arr1){
		for(var n in arr2){
			arra.push(arr1[m]+"_"+arr2[n]);
		}
	}
	return arra;
}

// 产生table tbody 内容的tr字符串
function preparedStrIdsArray(divId, assembledStrIds){
	// 获取原表中的所有trId
	var trIds = Array();
	var trs = $("#" + divId).find("table tbody tr");
	for(var i = 0; i < trs.length; i ++){
		trIds.push(trs.eq(i).attr("id"));
	}
	console.log("trIds:");
	console.log(trIds);
	return trIds;
}

// 根据选中的类别刷新货品规格
function flushHpgg(optionValue){
	var selectedOption = $("#productTypeSelections").find("option:selected");
	var isLastLevel = $(selectedOption).attr("isLastLevel");
	if(isLastLevel == "yes"){
		var theId = $(selectedOption).val();
		var loadData = {"typeId": theId};
		var url = basePath+"/admin/product/getHpggPage";
		// 刷新货品规格
		$("#hpgg").load(url,loadData);
		// 刷新货品表
		$("#skuAssemble").html("");
		var url2 = basePath+"/admin/product/getTableHead";
		loadData2={"typeId":theId};
		$.post(url2,loadData2,tableHeadCallback,"text");
	}
}

// 刷新货品规格后根据数据产生表头，并修改对应的规格顺序
function makeTableHead(data, divId){
	var content = "<table><thead>";
	// 添加表头
	content = content + "<tr>";
	for(var i = 0; i < data.tableHead.length; i ++){
		content = content + "<th>"+data.tableHead[i]+"</th>";
	}
	content = content + "</tr></thead><tbody></tbody></table>";
	// 添加表
	$("#" + divId).html(content);
}

function tableHeadCallback(data){
	var result = eval("("+data+")");
	var ssize = result.specSize;
	var sorder = result.specOrder;
	var hjson = eval("("+result.headJson+")");
	if(ssize == 0){
		$.messager.alert('消息提醒','该商品类别未设置规格!');	
		return;
	}
	
	console.log(result);
	console.log(hjson);
	$("#speOrder").val(sorder);
	$("#speSize").val(ssize);
	makeTableHead(hjson, "skuAssemble");
	if($("#skuAssemble").find("td").size>0){
		$("#skuAssemble").find("td").die("dblclick");
	}
	editText("skuAssemble", ssize);
}
 
</script>
