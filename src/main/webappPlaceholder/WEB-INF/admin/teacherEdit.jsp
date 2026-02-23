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
%>
<html>
<head>
    <title>Vidya - Admin</title>
</head>
<body>
<%@include file="../common/error.jsp"%>
<form action="<%=request.getContextPath()%>/adminTeachers?type=updateTeacher" method="post">
    <input name="teacher" id="teacher" value="<%=teacher.getId()%>" type="hidden">
    <table>
        <tr>
            <th><label for="name">Nome:</label></th>
            <td><input value="<%=teacher.getName()%>" name="name" id="name" type="text"></td>
        </tr>
        <tr>
            <th><label for="email">Email:</label></th>
            <td><input value="<%=teacher.getLogin()%>@vidya.org.br" name="email" id="email" type="email"></td>
        </tr>
        <tr>
            <th><label for="phone">Data de nascimento:</label></th>
            <td><input value="<%=teacher.getPhone()%>" name="phone" id="phone" type="date"></td>
        </tr>
        <tr>
            <th><label for="birth">Data de nascimento:</label></th>
            <td><input value="<%=teacher.getBirthDate()%>" name="birth" id="birth" type="date"></td>
        </tr>
        <tr>
            <th><label for="pass">Senha:</label></th>
            <td><input value="" name="pass" id="pass" type="password"></td>
        </tr>
    </table>
    <input value="Enviar" type="submit">
</form>
</body>
</html>
