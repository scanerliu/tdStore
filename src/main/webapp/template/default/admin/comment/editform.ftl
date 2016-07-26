<#import "/common/app.ftl" as app>
<script src="${app.basePath}/static/js/easyui/jquery.easyui.min.js" type="text/javascript"></script>
<script src="${app.basePath}/static/js/easyui/easyui-lang-zh_CN.js" type="text/javascript"></script>
<div class="subnav"><div class="content_menu ib_a blue line_x"><a href="javascript:;" class="add fb J_showdialog" onclick="returnList()"><em>返回列表</em></a>&#12288;</div></div>
<div class="pad_lr_10">
<form id="commentForm" action="${app.basePath}/admin/productcomment/save" class="easyui-form" method="post" data-options="novalidate:true">
<input type="hidden" name="commentId" value="<#if comment??>${comment.id?c}</#if>">
<table class="table_form" width="100%">
    <tr>
        <th width="150">评论会员：</th>
        <td width="12%"><#if comment.commentUser??>${comment.commentUser.uname!''}</#if><#if comment.anonymous?? && comment.anonymous>（匿名）<#else>（未匿名）</#if></td>
        <th width="150">商品：</th>
        <td><#if comment.product??>${comment.product.name!''}</#if></td>
    </tr>
    <tr>
        <th width="150">商品属性：</th>
        <td colspan ="3">${comment.specifications!''}</td>
    </tr>
    <tr>
        <th width="150">评论时间：</th>
        <td><#if comment.createTime??>${comment.createTime?string('yyyy-MM-dd')}</#if></td>
        <th width="150">评分：</th>
        <td>${comment.score!'0'}</td>
    </tr>
    <tr>
    	<th width="150">评价内容：</th>
    	<td colspan ="3">${comment.content!''}</td>
    </tr>
    <tr>
    	<th>图片</th>
    	<td colspan ="3">
    		<img width='100' height='100' src='${comment.imageUrl!''}'/>
    	</td>
    </tr>
    <tr>
        <th>状态：</th>
        <td>
            <input type="radio" name="status" value="1" <#if !comment?? || (comment.status?? && comment.status==1)>checked</#if>>前端显示
            <input type="radio" name="status" value="2" <#if comment?? && comment.status?? && comment.status==2>checked</#if>>屏蔽
        </td>
    </tr>
    <tr>
        <td colspan ="4" align="center">
            <button type="button" class="smt mr10" onclick="saveComment()">保存</button>
        </td>
    </tr>
</table>
</form>
</div>
<script>
$(function(){
});
</script>
