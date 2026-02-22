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
    String success = (String) session.getAttribute("success");
    String error = (String) session.getAttribute("error");
%>
<html>
<head>
    <title>Vidya - Admin</title>
</head>
<body>
<%
    if (!success.equals("null")) {
%>
<p style="color: green"><%=success%></p>
<%
        session.setAttribute("error", null);
    }
%>
<%
    if (!error.equals("null")) {
%>
<p style="color: red"><%=error%></p>
<%
        session.setAttribute("error", null);
    }
%>
<%
    if (reports.isEmpty()) {
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
            <form action="<%=request.getContextPath()%>/adminReports?type=showReports" method="post">
                <input name="report" id="report" value="<%=report.getId()%>" type="hidden">
                <input value="Ver" type="submit">
            </form>
            <form action="<%=request.getContextPath()%>/adminReports?type=editReports" method="post">
                <input name="report" id="report" value="<%=report.getId()%>" type="hidden">
                <input value="Editar" type="submit">
            </form>
            <form action="<%=request.getContextPath()%>/adminReports?type=deleteReports" method="post">
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
