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
<%@include file="../common/error.jsp"%>
<form action="<%=request.getContextPath()%>/adminGuardians?type=updateGuardian" method="post">
    <input name="guardian" id="guardian" value="<%=guardian.getId()%>" type="hidden">
    <table>
        <tr>
            <th><label for="name">Nome:</label></th>
            <td><input value="<%=guardian.getName()%>" name="name" id="name" type="text"></td>
        </tr>
        <tr>
            <th><label for="birth">Data de nascimento:</label></th>
            <td><input value="<%=guardian.getBirthDate()%>" name="birth" id="birth" type="date"></td>
        </tr>
    </table>
    <input value="Enviar" type="submit">
</form>
</body>
</html>
