<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<html>
<head>
    <title>Home Page</title>
</head>
<body>
    <h2>
        HOME PAGE
    </h2>
<hr>
   <p>Welcome to the Home Page</p>

    <hr>
<%--    Display username and roles --%>

    <p>
        User: <security:authentication property="principal.username"/>
        <br><br>
        Role(s): <security:authentication property="principal.authorities"/>
    </p>

    <hr>
<%--    Add a link to point to /leaders path (only for manager roles)--%>
    <security:authorize access="hasRole('MANAGER')" >
    <p>
        <a href="${pageContext.request.contextPath}/leaders" >Leadership Meeting</a>
        (Only for Mangers)
    </p>
    </security:authorize>


    <%--    Add a link to point to /systems path (only for admin roles)--%>
    <security:authorize access="hasRole('ADMIN')" >
    <p>
        <a href="${pageContext.request.contextPath}/systems" >IT Systems Meeting</a>
        (Only for Admin)
    </p>
    </security:authorize>

    <hr>

<%--Logout button--%>
<form:form
        action="${pageContext.request.contextPath}/logout"
        method="post" >

    <input type="submit" value="Logout">

</form:form>

</body>
</html>
