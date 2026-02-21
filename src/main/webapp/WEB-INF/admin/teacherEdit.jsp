<%@ page import="com.school.miniinter.models.Teacher.Teacher" %><%--
  Created by IntelliJ IDEA.
  User: Vini
  Date: 2/21/26
  Time: 4:23 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Teacher teacher = (Teacher) session.getAttribute("teacher");
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
<form action="<%=request.getContextPath()%>/adminTeachers?type=editTeacher">
    <input name="teacher" id="teacher" value="<%=teacher.getId()%>" type="hidden">
    <table>
        <tr>
            <th><label for="name">Nome:</label></th>
            <td><input value="<%=teacher.getName()%>" name="name" id="name" type="text"></td>
        </tr>
        <tr>
            <th><label for="email">Email:</label></th>
            <td><input value="<%=teacher.getLogin()%>" name="email" id="email" type="email"></td>
        </tr>
        <tr>
            <th><label for="birth">Data de nascimento:</label></th>
            <td><input value="<%=teacher.getBirthDate()%>" name="birth" id="birth" type="date"></td>
        </tr>
        <tr>
            <th><label for="pass">Senha:</label></th>
            <td><input value="<%=teacher.getPassword()%>" name="pass" id="pass" type="password"></td>
        </tr>
    </table>
</form>
</body>
</html>
