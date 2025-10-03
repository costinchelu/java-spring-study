<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Input form example</title>
</head>
<h1>Input form:</h1>
<body>
    <form action="${pageContext.request.contextPath}/hello/processFormVersionThree"
          method="GET">

        <label>
            <input type="text"
                   name="userName"
                    placeholder="What is your name?"/>
        </label>

        <input type="submit"/>
    </form>
</body>
</html>
