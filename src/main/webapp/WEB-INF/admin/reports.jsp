<%@ page import="com.school.miniinter.models.Reports.Reports" %>
<%@ page import="java.util.List" %>
<%@ page import="com.school.miniinter.models.Reports.CompleteReport" %><%--
  Created by IntelliJ IDEA.
  User: Vini
  Date: 2/21/26
  Time: 4:01 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    List<CompleteReport> reports = (List<CompleteReport>) session.getAttribute("reports");
%>
<html>
<head>
    <title>Vidya - Admin</title>
</head>
<body>
<%@include file="../common/menuCRUD.jsp"%>
<%@include file="../common/error.jsp"%>
<form action="adminReports?type=createReport" method="post"><input type="submit" value="Criar Observação"></form>
<%
    if (reports == null || reports.isEmpty()) {
%>
<h2>Nenhuma observação encontrada!</h2>
<%
} else {
%>
<table>
    <tr>
        <th>Professor</th>
        <th>Description</th>
        <th>Type</th>
        <th>Estudantes</th>
        <th>Data de Envio</th>
        <th colspan="3">Ações</th>
    </tr>
    <%
        for (CompleteReport report : reports) {
    %>
    <tr>
        <td><%=report.getTeacher()%></td>
        <td><%=report.getDescription()%></td>
        <td><%=report.getType()%></td>
        <td>
        <%
            for (String student : report.getStudents()) {
        %>
            <p><%=student%></p>
        <%
            }
        %>
        </td>
        <td><%=report.getSend_at()%></td>
        <td>
            <form
                    action="<%=request.getContextPath()%>/adminReports?type=showReport" method="post">
                <input name="report" id="report" value="<%=report.getId()%>" type="hidden">
                <input value="Ver" type="submit">
            </form>
        </td>
        <td>
            <form action="<%=request.getContextPath()%>/adminReports?type=editReport" method="post">
                <input name="report" id="report" value="<%=report.getId()%>" type="hidden">
                <input value="Editar" type="submit">
            </form>
        </td>
        <td>
            <form action="<%=request.getContextPath()%>/adminReports?type=deleteReport" method="post">
                <input name="report" id="report" value="<%=report.getId()%>" type="hidden">
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