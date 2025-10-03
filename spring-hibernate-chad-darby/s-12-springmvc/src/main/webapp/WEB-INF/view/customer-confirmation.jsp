<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Customer Confirmation</title>
</head>
<body>

<br><br>
<b>The customer is confirmed:</b> ${customer.toString()}

<br><br>
<b>Free passes:</b> ${customer.freePasses}

<br><br>
<b>Postal code:</b> ${customer.postalCode}

<br><br>
<b>Course code:</b> ${customer.courseCode}

</body>
<footer>
    <br><br>
    <a href="${pageContext.request.contextPath}/">Main page</a>
</footer>
</html>
