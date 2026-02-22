<%@ page import="com.school.miniinter.models.Subject.Subject" %><%--
  Created by IntelliJ IDEA.
  User: Vini
  Date: 2/21/26
  Time: 4:23 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Subject subject = (Subject) session.getAttribute("subject");
%>
<html>
<head>
    <title>Vidya - Admin</title>
</head>
<body>
<table>
    <tr>
        <th>Nome:</th>
        <td><%=subject.getName()%></td>
    </tr>
    <tr>
        <th>Descrição:</th>
        <td><%=subject.getDescription()%></td>
    </tr>
</table>
</body>
</html>
