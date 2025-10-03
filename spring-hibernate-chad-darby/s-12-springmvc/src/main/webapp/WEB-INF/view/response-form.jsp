<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Response form</title>
</head>
<h1>Response</h1>
<br><br>
<body>
    <h3>Hello ${param.userName}. Welcome to Spring MVC!</h3>
    <br>
    MESSAGE: ${userMessage}
</body>
<footer>
    <br><br>
    <a href="${pageContext.request.contextPath}/">Main page</a>
</footer>
</html>
