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
    <title>addGoods</title>
    <meta http-equiv="CONTENT-TYPE" content="text/xml" charset="utf-8">
    <link href="${pageContext.request.contextPath}/static/css/application.css" rel="stylesheet">
</head>
<body>
    <form action="${pageContext.request.contextPath}/goods/add" method="post">
        <table class="table">
            <tr>
                <td colspan="2" align="center">商品新增</td>
            </tr>
            <tr>
                <td>商品名称</td>
                <td><input type="text" name="name"></td>
            </tr>
            <tr>
                <td>商品描述</td>
                <td><input type="text" name="description"></td>
            </tr>
            <tr>
                <td>商品图片</td>
                <td><input type="text" name="imgPath"></td>
            </tr>
            <tr>
                <td colspan="2" align="center"><input type="submit" value="新增" ></td>
            </tr>
        </table>
    </form>

</body>
</html>
