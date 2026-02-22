<%@ page import="com.school.miniinter.models.Guardian.Guardian" %><%--
  Created by IntelliJ IDEA.
  User: Vini
  Date: 2/21/26
  Time: 4:23 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Guardian guardian = (Guardian) session.getAttribute("guardian");
%>
<html>
<head>
    <title>Vidya - Admin</title>
</head>
<body>
<form action="<%=request.getContextPath()%>/adminClasses"></form>
<form action="<%=request.getContextPath()%>/adminGuardians"></form>
<form action="<%=request.getContextPath()%>/adminReports"></form>
<form action="<%=request.getContextPath()%>/adminStudents"></form>
<form action="<%=request.getContextPath()%>/adminSubjects"></form>
<form action="<%=request.getContextPath()%>/adminTeachers"></form>
<table>
    <tr>
        <th>Nome:</th>
        <td><%=guardian.getName()%></td>
    </tr>
    <tr>
        <th>Data de nascimento:</th>
        <td><%=guardian.getBirthDate()%></td>
    </tr>
</table>
</body>
</html>
