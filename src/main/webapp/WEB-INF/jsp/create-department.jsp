<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
</head>
<body>
<h1 style="text-align: center; color: black">New Department</h1>
<form:form method = "POST" action = "/departments/create" modelAttribute="department">
    <style>
        table{
            margin: auto;}

        input:invalid:required {
            border: 1px solid grey;
            background-image: linear-gradient(to right, white, lightyellow);
        }

        input:valid {
            border: 2px solid darkgreen;
            background-image: linear-gradient(to right, white, lightgreen)
        }
        .error{
            color: red;
        }
    </style>
        <table>
            <tr>
                <td><form:label path = "name">Name</form:label></td>
                <td><form:input path = "name" /></td>
                <td><form:errors path = "name" cssClass = "error" /></td>
            </tr>
            <tr>
                <td colspan = "2">
                    <input type = "submit" value = "Submit"/>
                </td>
            </tr>
    </table>

</form:form>
</body>
</html>






