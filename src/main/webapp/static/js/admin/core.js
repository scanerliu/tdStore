$.fn.extend({
	loading:function(){
		return this.each(function(){
			$(this).html('数据加载中...');			
		});
	}
});

$.extend($.fn.validatebox.defaults.rules, {  
    /*必须和某个字段相等*/
    equalTo: {
        validator:function(value,param){
            return $(param[0]).val() == value;
        },
        message:'字段不匹配'
    }
           
});