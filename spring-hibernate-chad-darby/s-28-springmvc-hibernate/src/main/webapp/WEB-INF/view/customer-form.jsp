<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
    <head>
        <title>Add Customer</title>

        <link
            type="text/css"
            rel="stylesheet"
            href="${pageContext.request.contextPath}/resources/css/style.css">
        <link
                type="text/css"
                rel="stylesheet"
                href="${pageContext.request.contextPath}/resources/css/add-customer-style.css">
    </head>
    <body>
        <div id="wrapper">
            <div id="header">
                <h2>CRM - Customer Relationship Manager</h2>
            </div>
        </div>

        <div id="container">
            <h3>Save customer</h3>

            <%-- using the model form will be populated with customer details using the getters.
            When we are using the form for a new customer, form will be empty because the model is empty
             (newCustomer has null attributes). After we are submitting the data, Spring will use setters
              from Customer entity. --%>

            <form:form
                    action="saveCustomer"
                    modelAttribute="customer"
                    method="POST">

                <%-- define a new hidden field to hold the id (used for updating customer).
                When we press Save button we will send also this attribute to the controller.
                If the controller receives a non null id, it will use it to update the customer--%>
                <form:hidden path="id" />

                <table>
                    <tbody>
                        <tr>
                            <td><label>First name:</label></td>
                            <td><form:input path="firstName" /></td>
                        </tr>
                        <tr>
                            <td><label>Last name:</label></td>
                            <td><form:input path="lastName" /></td>
                        </tr>
                        <tr>
                            <td><label>Email:</label></td>
                            <td><form:input path="email" /></td>
                        </tr>
                        <tr>
                            <td><input
                                    type="submit"
                                    value="Save"
                                    class="save"/></td>
                        </tr>
                    </tbody>
                </table>

            </form:form>

            <%-- save button --%>

            <div style="clear:both;">
                <p>
                    <a href="${pageContext.request.contextPath}/customer/list">Back to the list of customers</a>
                </p>
            </div>

        </div>
    </body>
</html>
