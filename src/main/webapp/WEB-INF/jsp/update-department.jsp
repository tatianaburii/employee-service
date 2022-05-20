<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
</head>
<body>
<h1 style="text-align: center; color: darkgreen">Edit Department</h1>
<form:form method="POST" action="/departments/save" modelAttribute="department">
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
    }
</style>
<table>
    <tr>
        <td><form:hidden path="id"/></td>
        <td><form:label path="name">Name</form:label></td>
        <td><form:input path="name"/></td>
        <td><form:errors path="name" cssClass="error"/></td>
    </tr>
    <tr>
        <td><input type="submit" value="Update Department" class="btn btn-warning"></td>
    </tr>
</table>
</form:form>
