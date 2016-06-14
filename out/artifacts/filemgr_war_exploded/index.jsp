<%--
  Created by IntelliJ IDEA.
  User: AlienZHOU
  Date: 2016/6/13
  Time: 12:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>iioo</title>
</head>
<body>
<div>
  <p>噼里啪啦</p>
  <form action="upload" method="post" enctype="multipart/form-data">
    <input type="password" name="number"><br>
    <input type="file" name="file"><br>
    <input type="submit" value="去吧">
  </form>

  <form action="download" method="get">
    <input type="password" name="number"><br>
    <input type="text" name="fname"><br>
    <input type="submit" value="来吧">
  </form>
</div>
</body>
</html>
