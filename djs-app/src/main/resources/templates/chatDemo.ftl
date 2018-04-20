<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insert title here</title>
</head>
<script src="${request.contextPath}/static/js/jquery-1.11.0.js"></script>
<script src="${request.contextPath}/static/js/sockjs.min.js"></script>
<script src="${request.contextPath}/static/js/stomp-2.3.3.js"></script>
<body>
<form id="wiselyForm">
    <textarea rows="4" cols="60" name="text" id="text"></textarea>
    <input type="submit" />
</form>
<script type="text/javascript">
    $('#wiselyForm').submit(function(e){
        e.preventDefault();
        var text = $('#text').val();
        sendSpittle(text);
    })

    var sock = new SockJS('${request.contextPath}/websocket/endpointChat');
    var stomp = Stomp.over(sock);
    stomp.connect('guest','guest',function(){
        stomp.subscribe('/topic/notifications/1001',handleNotification);
    });

    function handleNotification(message){
        debugger;
        $('#output').append("<b>"+message.body+"</b><br/>")
    }

    function sendSpittle(text){
        var chatObj = {};
        chatObj.userName='roce';
        chatObj.roomId = 1001;
        chatObj.content = text;
        stomp.send("/chat",{},JSON.stringify(chatObj));
    }
</script>
<div id="output"></div>
</body>
</html>