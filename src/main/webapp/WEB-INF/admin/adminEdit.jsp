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
    String error = (String) session.getAttribute("error");
%>
<html>
<head>
    <title>Vidya - Admin</title>
</head>
<body>
<%
    if (!error.equals("null")) {
%>
<p style="color: red"><%=error%></p>
<%
        session.setAttribute("error", null);
    }
%>
<form action="<%=request.getContextPath()%>/adminGuardians?type=editGuardian">
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
</form>
</body>
</html>
