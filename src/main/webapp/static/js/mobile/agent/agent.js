function searcAgent(agentId){
	var url = basePath + "/mobile/agent/search/distric";
	var loadData = {"agentId":agentId};
	
	$("#results").load(url,loadData);
}

function searchDis()
{
	var url = basePath + "/mobile/agent/search/distric";
	var loadData = $("#form").serializeArray();
	
	$("#results").load(url,loadData);
}

function subAgent()
{
	var typeId = $("#typeId").val();
	var regionId = $("#regionId").val();
	var disLevel = $("#disLevel").val();
	var level = $("#level").val();
	var agentId = $("#agentid").val();
	
	// 分类ID为空，
	if(undefined == typeId || typeId == "")
	{
		alert("请选择代理类别");
		return ;
	}
	if(null == agentId || null == level)
	{
		alert("参数错误");
		return ;
	}
	
	// 非全国代理 未选择地区提示
	if(level != 1 )
	{
		if(null == regionId || regionId == 0)
		{
			alert("请选择代理地区");
			return ;
		}
		// 区县级代理，只选择一级地区提示
		if(level != 2)
		{
			if(disLevel == 1)
			{
				alert("请选择下级地区");
				return ;
			}
		}
	}
	
	
	$("#sub_form").submit();
	
}


function cleft_height(){
	var dHeight=document.documentElement.clientHeight;
	var cla_left=document.getElementById('cla_left');
	var cla_right=document.getElementById('cla_right');
	var top_heater=document.getElementById('top_heater');
	var three3=document.getElementById('three3');
	cla_left.style.height=dHeight-top_heater.offsetHeight-three3.offsetHeight+'px';
	cla_right.style.height=dHeight-top_heater.offsetHeight-three3.offsetHeight-14+'px';
}

function left_right(){
	var cla_left=document.getElementById('cla_left');
	var oA=cla_left.getElementsByTagName('a');
	var cla_right=document.getElementById('cla_right');
	var arr_a=cla_right.getElementsByTagName('a');
	var arr_i=cla_right.getElementsByTagName('i');
	// alert(arr_i.length);
	var oUl=cla_right.getElementsByTagName('ul');
	var arr=[],oindex=[];
	for(var i=0;i<oA.length;i++){
		oA[i].index=i;
		oA[i].onclick=function(){
			for(var i=0;i<oA.length;i++){
				oA[i].className='';
				oUl[i].className='';
			}
			this.className='active';
			oUl[this.index].className='active';
		}
	}
	for(i=0;i<arr_a.length;i++){
		if(arr_a[i].className=='active'){
			arr.push(arr_a[i]);
			oindex.push(i);
		}
	}
	for(var i=0;i<arr.length;i++){
		arr[i].index=i;
		arr[i].onclick=function(){
			for(var i=0;i<arr.length;i++){
				arr_i[i].style.display='none';
			}
			arr_i[oindex[this.index]].style.display='block';
		}
	}
	// alert(arr.length);
	for(i=0;i<oUl.length;i++){
		var oLi=oUl[i].getElementsByTagName('li');
		oLichange(oLi);
	}
	function oLichange(obj){
		for(var j=0;j<obj.length;j++){
			obj[j].onclick=function(){
				for(j=0;j<obj.length;j++){
					obj[j].className='';
				}
				this.className='active';
			}
		}
	}
}

function three_bing(){
	// 三级联动
	var three3=document.getElementById('three3');
	var oSec=three3.getElementsByTagName('section');
	var oSpan=three3.getElementsByTagName('span');
	var oSelect=three3.getElementsByTagName('select');
	for(i=0;i<oSec.length;i++){
		oSec[i].index=i;
		oSec[i].onclick=function(){
			// alert(this.index);
			choose(this.index);
		}
	}
	function choose(num){
		oSelect[num].style.display="block";
		oSelect[num].addEventListener("click",function(){},false);
		oSelect[num].onchange=function(){
			oSpan[num].innerHTML=oSelect[num].value;
			oSelect[num].style.display="none";
			searchDis();
		}
	}
}