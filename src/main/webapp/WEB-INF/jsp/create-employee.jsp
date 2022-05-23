<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
</head>
<body>
<h1 style="text-align: center; color: darkgreen">New Employee</h1>
<form:form method="POST" action="/employees/create" modelAttribute="employee">
    <style>
        table {
            margin: auto;
        }

        body {
            background: #e6f6d9;
        }

        input:invalid:required {
            border: 1px solid grey;
            background-image: linear-gradient(to right, white, lightyellow);
        }

        input:valid {
            border: 2px solid darkgreen;
            background-image: linear-gradient(to right, white, lightgreen);
            text-align: center;
        }

        .error {
            color: red;
            font-size: smaller;
        }
    </style>
    <table>
        <tr>
            <td><form:label path="name">Name</form:label></td>
            <td><form:input path="name"/></td>
            <td><form:errors path="name" cssClass="error"/></td>
        </tr>
        <tr>
            <td><form:label path="phone">Phone</form:label></td>
            <td><form:input path="phone"/></td>
            <td><form:errors path="phone" cssClass="error"/></td>
        </tr>
        <tr>
            <td><form:label path="email">Email</form:label></td>
            <td><form:input path="email"/></td>
            <td><form:errors path="email" cssClass="error"/></td>
        </tr>
        <td><form:label path="departmentId">Department</form:label></td>
        <td>
            <label>
                <select name="departmentId">
                    <c:forEach items="${departments}" var="department">
                        <option value="${department.id}">${department.name}</option>
                    </c:forEach>
                </select>
            </label></td>
        </tr>
        <tr>
            <td colspan="2">
                <input type="submit" value="Submit"/>
            </td>
        </tr>
    </table>

</form:form>
</body>
</html>