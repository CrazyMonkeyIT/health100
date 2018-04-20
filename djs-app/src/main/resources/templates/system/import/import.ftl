<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insert title here</title>
</head>
<script src="${request.contextPath}/static/js/jquery-1.11.0.js"></script>
<script type="text/javascript">
    $(function(){
        $('#up').click(excuteup);
    });

    var excuteup = function(){
        $('#progressMsg').text('正在上传文件...');
        $('#upForm').submit();
    }
</script>
<body>
<form id="upForm" action="${request.contextPath}/import/up/12" enctype="multipart/form-data" method='post'>
    <div id="upDiv">
        File to upload: <input type="file" name="file" multiple="multiple"><br />
    </div>
    <br />
    <input type="button" value="up" id="up" /><br /><br />
</form>

<div>
    <table id="fileTable">
        <tr>
            <td width="200px">文件</td>
            <td>操作</td>
        </tr>
    </table>
</div>
</body>
</html>
