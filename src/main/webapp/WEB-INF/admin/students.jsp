<%@ page import="com.school.miniinter.models.Students.Students" %>
<%@ page import="java.util.List" %>
<%--
  Created by IntelliJ IDEA.
  User: Vini
  Date: 2/21/26
  Time: 4:01 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    List<Students> students = (List<Students>) session.getAttribute("students");
%>
<html>
<head>
    <title>Vidya - Admin</title>
</head>
<body>
<%@include file="../common/menuCRUD.jsp"%>
<%@include file="../common/error.jsp"%>
<%
    if (students.isEmpty()) {
%>
<h2>Nenhum estudante encontrado!</h2>
<%
} else {
%>
<table>
    <tr>
        <th>Nome Completo</th>
        <th>Email</th>
        <th>Data de nascimento</th>
        <th colspan="3">Ações</th>
    </tr>
    <%
        for (Students student : students) {
    %>
    <tr>
        <td><%=student.getFull_name()%></td>
        <td><%=student.getLogin()%>@vidya.org.br</td>
        <td><%=student.getBirth_date()%></td>
        <td>
            <form action="<%=request.getContextPath()%>/adminStudents?type=showStudent" method="post">
                <input name="student" id="student" value="<%=student.getId_student()%>" type="hidden">
                <input value="Ver" type="submit">
            </form>
        </td>
        <td>
            <form action="<%=request.getContextPath()%>/adminStudents?type=editStudent" method="post">
                <input name="student" id="student" value="<%=student.getId_student()%>" type="hidden">
                <input value="Editar" type="submit">
            </form>
        </td>
        <td>
            <form action="<%=request.getContextPath()%>/adminStudents?type=deleteStudent" method="post">
                <input name="student" id="student" value="<%=student.getId_student()%>" type="hidden">
                <input value="Deletar" type="submit">
            </form>
        </td>
    </tr>
    <%
        }
    %>
</table>
<%
    }
%>
</body>
</html>

<style>
    table, th, td {
        border: 1px solid black;
    }
</style>