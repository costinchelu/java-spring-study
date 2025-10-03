<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Student Registration Form</title>
</head>
<body>
    <%--@elvariable id="student" type=""--%>
    <form:form action="processForm" modelAttribute="student">

        First name: <form:input path="firstName"/>
        <br><br>

        Last name: <form:input path="lastName"/>
        <br><br>

        Country:
        <form:select path="country">
            <form:options items="${student.countryOptions}"/>
        </form:select>
        <br><br>

        Favourite Language:<br>
        Java <form:radiobutton path="favouriteLanguage" value="Java" /><br>
        Python <form:radiobutton path="favouriteLanguage" value="Python" /><br>
        Javascript <form:radiobutton path="favouriteLanguage" value="Javascript" /><br>
        C <form:radiobutton path="favouriteLanguage" value="C" /><br>
        C# <form:radiobutton path="favouriteLanguage" value="C#" /><br>
        <br><br>

        Operating Systems:<br>
        Windows <form:checkbox path="operatingSystems" value="Windows"/><br>
        Linux <form:checkbox path="operatingSystems" value="Linux"/><br>
        MacOS <form:checkbox path="operatingSystems" value="MacOS"/><br>
        <br><br>

        <input type="submit" value="Submit">

    </form:form>
</body>
</html>
