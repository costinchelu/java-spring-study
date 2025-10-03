<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet"
          type="text/css"
          href="${pageContext.request.contextPath}/resources/css/style.css">

    <script src="${pageContext.request.contextPath}/resources/js/somelogic.js"></script>

    <title>MVC Demo App</title>
</head>

<div class="header">
    <img src="${pageContext.request.contextPath}/resources/image/springlogo.png"
         alt="Spring Logo"/>
    <h1>
        Udemy Spring (Chad Darby) Course
    </h1>
</div>

<br><br>

<h2>
    Spring MVC Content
</h2>
<br><br>
<body>
    <br><br>

    <button type="button" onclick="doSomeWork()">Do some JS</button>
    <br>
    ----------------------------------------
    <br><br>

    <form action="${pageContext.request.contextPath}/hello/showForm">
        <input type="submit"
               value="Get to the HelloWorld form input" />
    </form>

    <form action="${pageContext.request.contextPath}/student/showForm">
        <input type="submit"
               value="Get to the Student form input" />
    </form>

    <form action="${pageContext.request.contextPath}/customer/showForm">
        <input type="submit"
               value="Get to the Customer form input" />
    </form>
</body>
</html>
