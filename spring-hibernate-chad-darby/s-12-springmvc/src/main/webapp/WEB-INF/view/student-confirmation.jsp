<jsp:useBean id="student" scope="request" type="demo.model.Student"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Student Confirmation</title>
</head>
<body>

    <b>The student is confirmed:</b> ${student.toString()}
    <br>
    <b>Country of origin:</b> ${student.country}
    <br>
    <b>Favourite programming language:</b> ${student.favouriteLanguage}
    <br>
    <b>Operating Systems:</b>
    <ul>
        <c:forEach var="os" items="${student.operatingSystems}">

            <li>${os}</li>

        </c:forEach>
    </ul>

</body>
<footer>
    <br><br>
    <a href="${pageContext.request.contextPath}/">Main page</a>
</footer>
</html>
