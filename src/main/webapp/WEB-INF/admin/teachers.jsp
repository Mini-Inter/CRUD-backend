<%@ page import="com.school.miniinter.models.Teacher.Teacher" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: Vini
  Date: 2/21/26
  Time: 4:01 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    List<Teacher> teachers = (List<Teacher>) session.getAttribute("teachers");
%>
<html>
<head>
    <title>Vidya - Admin</title>
</head>
<body>
<%@include file="../common/menuCRUD.jsp"%>
<%@include file="../common/error.jsp"%>

    <%
    if (teachers == null || teachers.isEmpty()) {
    %>
    <h2>Nenhum professor encontrado!</h2>
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
            for (Teacher teacher : teachers) {
        %>
        <tr>
            <td><%=teacher.getName()%></td>
            <td><%=teacher.getLogin()%>@vidya.org.br</td>
            <td><%=teacher.getBirthDate()%></td>
            <td>
                <form action="<%=request.getContextPath()%>/adminTeachers?type=showTeacher" method="post">
                    <input name="teacher" id="teacher" value="<%=teacher.getId()%>" type="hidden">
                    <input value="Ver" type="submit">
                </form>
                <form action="<%=request.getContextPath()%>/adminTeachers?type=editTeacher" method="post">
                    <input name="teacher" id="teacher" value="<%=teacher.getId()%>" type="hidden">
                    <input value="Editar" type="submit">
                </form>
                <form action="<%=request.getContextPath()%>/adminTeachers?type=deleteTeacher" method="post">
                    <input name="teacher" id="teacher" value="<%=teacher.getId()%>" type="hidden">
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