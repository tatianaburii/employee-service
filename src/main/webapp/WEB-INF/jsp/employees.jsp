<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<h1 style="text-align: center; color: darkgreen">Employees List</h1>
<style>
    table {
        margin: auto;
        color: black;
        background-image: linear-gradient(to right, white, lightgreen);
        border: 2px solid darkgreen;
        text-align: center;
    }

    body {
        background: #e6f6d9;
    }

</style>
<table border="2" width="60%" cellpadding="2">
    <tr>
        <th>Id</th>
        <th>Name</th>
        <th>Phone</th>
        <th>Email</th>
        <th>Created_At</th>
        <th>Department_id</th>
        <th>Edit</th>
        <th>Delete</th>
    </tr>
    <c:forEach var="employee" items="${employees}">
        <tr>
            <td>${employee.id}</td>
            <td>${employee.name}</td>
            <td>${employee.phone}</td>
            <td>${employee.email}</td>
            <td>${employee.createdAt}</td>
            <td>${employee.departmentId}</td>
            <td><a href="employees/${employee.id}/update">Edit</a></td>
            <td><a href="employees/${employee.id}/delete">Delete</a></td>
        </tr>
    </c:forEach>
</table>
<br/>
<tr>
<a href="/employees/add">Add New Employee</a>
</tr>
<tr>
<a href="/departments">Departments</a>
</tr>