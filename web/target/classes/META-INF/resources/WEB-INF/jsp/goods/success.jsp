<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2017/5/18
  Time: 17:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>success</title>
</head>
<body>
    <h1>操作成功</h1>
    <a href="${pageContext.request.contextPath}/goods/toList">3秒后系统会自动跳转，也可点击本处直接跳</a> </div></div></div>
    <script>
        function jumpurl(){
            location='${pageContext.request.contextPath}/goods/toList';
        }
        setTimeout('jumpurl()',3000);
    </script>
</body>
</html>
