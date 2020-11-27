<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" +
            request.getServerPort() + request.getContextPath() + "/";
%>
<html>
<head>
    <title>首界面</title>
    <base href="<%=basePath%>">
    <script type="text/javascript" src="static/js/Jquery%203.5.1.js"></script>
    <script type="text/javascript">
        $(function (){
            $("#btn").click(function (){
                $.ajax({
                    url:"return",
                    type:"get",
                    dataType:"json",
                    //async:true,
                    success:function (data){
                        $.each(data,function (i,n){
                            alert(n.id + n.name + n.age)
                        })
                        // alert(data.id + data.name + data.age)
                    }
                })
            })
        })
    </script>
</head>
<body>
    <button id="btn">点击</button>
    <div id="msg" align="center" style="width: 100px;height: 100px;background-color: pink"></div>
    <div id="msg2" align="center" style="width: 200px;height: 200px;background-color: pink">fskafhksaf</div>
</body>
</html>
