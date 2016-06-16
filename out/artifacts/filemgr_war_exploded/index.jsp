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
    <link rel="stylesheet" href="css/index.css"/>
</head>
<body>
<div>
    <div class="container">
        <span class="info">i</span>

        <h1>Upload</h1>
        <form action="upload" method="post" enctype="multipart/form-data">
            <input type="password" class="input-area" name="number">
            <input type="file" name="file">
            <input type="submit" value="上传">
        </form>

        <h1>Download</h1>
        <form action="download" method="get">
            <input type="password" class="input-area" name="number">
            <input type="text" class="input-area" name="fname">
            <input type="submit" value="下载">
        </form>
    </div>
</div>
</body>
</html>
