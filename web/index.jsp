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
<div id="bg" class="gradient"></div>
<div class="progress-bar">
    <div id="bar"></div>
</div>
<div id="percent" class="percent">
    <span id="num" class="num">21.2</span>
    <span class="fix">%</span>
</div>

<div class="container">
    <div class="mask">
        <div class="loading"></div>
    </div>
    <span class="info">i</span>

    <h1>Upload</h1>
    <form id="upload-form" action="upload.do" method="post" enctype="multipart/form-data">
        <input id="number" type="password" class="input-area" name="number">
        <input id="file" type="file" name="file">
        <input type="submit" value="UPLOAD">
        <div id="up-info" class="tip"></div>
    </form>

    <h1>Download</h1>
    <form id="download-form" action="download.do" method="get" onSubmit="return checkDownlod();" target="#do-info">
        <input id="do-number" type="password" class="input-area" name="number">
        <input type="text" class="input-area" name="fname">
        <input type="submit" value="DOWNLOAD">
        <div id="do-info" class="tip"></div>
    </form>
</div>
</body>
<script type="text/javascript" src="vendor/jquery-2.2.4.min.js"></script>
<script type="text/javascript" src="vendor/jquery.form.js"></script>
<script type="text/javascript" src="js/index.js"></script>
</html>
