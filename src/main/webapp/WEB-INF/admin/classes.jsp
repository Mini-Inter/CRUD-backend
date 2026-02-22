<%@ page import="com.school.miniinter.models.Class.Class" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: Vini
  Date: 2/21/26
  Time: 4:01 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    List<Class> classes = (List<Class>) session.getAttribute("classes");
%>
<html>
<head>
    <title>Vidya - Admin</title>
</head>
<body>
<%@include file="../common/menuCRUD.jsp"%>
<%@include file="../common/error.jsp"%>
<form action="adminClasses?type=createClass" method="post"><input type="submit" value="Adicionar"></form>
<%
    if (classes.isEmpty()) {
%>
<h2>Nenhuma turma encontrada!</h2>
<%
} else {
%>
<table>
    <tr>
        <th>Série</th>
        <th>Turma</th>
        <th colspan="3">Ações</th>
    </tr>
    <%
        for (Class classroom : classes) {
    %>
    <tr>
        <td><%=classroom.getSeries()%></td>
        <td><%=classroom.getClassroom()%></td>
        <td>
            <form action="<%=request.getContextPath()%>/adminClasses?type=showClass" method="post">
                <input name="classroom" id="classroom" value="<%=classroom.getId()%>" type="hidden">
                <input value="Ver" type="submit">
            </form>
        </td>
        <td>
            <form action="<%=request.getContextPath()%>/adminClasses?type=editClass" method="post">
                <input name="classroom" id="classroom" value="<%=classroom.getId()%>" type="hidden">
                <input value="Editar" type="submit">
            </form>
        </td>
        <td>
            <form action="<%=request.getContextPath()%>/adminClasses?type=deleteClass" method="post">
                <input name="classroom" id="classroom" value="<%=classroom.getId()%>" type="hidden">
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