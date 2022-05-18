<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<h1 style="text-align: center; color: darkgreen">Departments List</h1>
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
        <th>Created_At</th>
        <th>Edit</th>
        <th>Delete</th>
    </tr>
    <c:forEach var="department" items="${departments}">
        <tr>
            <td>${department.id}</td>
            <td>${department.name}</td>
            <td>${department.createdAt}</td>
            <td><a href="${department.id}/edit">Edit</a></td>
            <td><a href="${department.id}/delete">Delete</a></td>
        </tr>
    </c:forEach>
</table>
<br/>
<a href="/departments/add">Add New Department</a>