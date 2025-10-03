<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
    <head>
        <title>Customers</title>
        <link
                type="text/css"
                rel="stylesheet"
                href="${pageContext.request.contextPath}/resources/css/style.css">
    </head>

    <body>
        <div id="wrapper">
            <div id="header">
                <h2>CRM - Customer Relationship Manager</h2>
            </div>
        </div>

        <div id="container">
            <div id="content">

                <!-- search box -->
                <form:form
                        action="search"
                        method="GET">

                    Search customer: <input type="text" name="searchName" />

                    <input type="submit" value="Search" class="add-button" />

                </form:form>

                <%-- add a button for "add customers --%>
                <input
                        class="add-button"
                        type="button"
                        value="Add Customer"
                        onclick="window.location.href='showFormForAdd'; return false;"/>

                <!-- Add a html table for customers-->
                <table>
                    <tr>
                        <th>First Name</th>
                        <th>Last Name</th>
                        <th>Email</th>
                        <th>Action</th>
                    </tr>
                    <!-- loop over and print our customers (that are coming from the model
                    where we added the list of Customer object in the controller -->
                    <c:forEach var="tempCustomer" items="${customers}">

                        <%-- construct an "update" link using selected customer id --%>
                        <c:url var="updateLink" value="/customer/showFormForUpdate">
                            <c:param name="customerId" value="${tempCustomer.id}" />
                        </c:url>

                        <%-- construct a "delete" link using selected customer id --%>
                        <c:url var="deleteLink" value="/customer/delete">
                            <c:param name="customerId" value="${tempCustomer.id}" />
                        </c:url>
                        
                        <tr>
                            <td>${tempCustomer.firstName}</td>
                            <td>${tempCustomer.lastName}</td>
                            <td>${tempCustomer.email}</td>
                            <td>
                                <a href="${updateLink}">Update</a>
                                |
                                <%-- for the delete, we will insert javascript to get a confirmation message --%>
                                <a href="${deleteLink}"
                                    onclick="if
                                            (!(confirm('Are you sure you want to delete this customer?')))
                                            return false">Delete</a>
                            </td>
                        </tr>

                    </c:forEach>
                </table>

            </div>
        </div>
    </body>
</html>
