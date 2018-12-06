<%@ page import="com.mcy.utils.FilePathUtil" %><%--
  Created by IntelliJ IDEA.
  User: mcy
  Date: 18-12-3
  Time: 下午7:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>测试</title>
</head>
<body>
<form action="uploadPicture" enctype="multipart/form-data" method="post">
    <input type="file" name="photo">
    <input type="submit" value="提交">
</form>
</body>
</html>
