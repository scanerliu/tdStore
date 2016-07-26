<#import "/common/app.ftl" as app> 
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="description" content=""/>
    <meta name="keywords" content=""/>
    <meta name="author" content=""/>
    <meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1"/>
    <title>首页</title>
    <link rel="shortcut icon" href="${app.basePath}/static/default/images/icon.ico" />
    <link rel="stylesheet" href="${app.basePath}/static/default/css/global.css"/>
    <link rel="stylesheet" href="${app.basePath}/static/js/easyui/easyui.css"/>
    <script src="${app.basePath}/static/js/jquery-1.12.3.min.js" type="text/javascript"></script>
    <script src="${app.basePath}/static/js/easyui/jquery.easyui.min.js" type="text/javascript"></script>
</head>
<body>
<script>
function goadmin(){
    var url = "${app.basePath}/admin/index";
    $.post(url,null,gocallback,'json');
}
function gocallback(data){
    alert(data.msg);
}
</script>
${user.uname!''}丰盛的
<div id="result" onclick="goadmin()">go
</div>
</body>  
</html>