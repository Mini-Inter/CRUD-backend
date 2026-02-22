<%@ page import="com.school.miniinter.models.Reports.Reports" %>
<%@ page import="com.school.miniinter.models.Reports.CompleteReport" %><%--
  Created by IntelliJ IDEA.
  User: Vini
  Date: 2/21/26
  Time: 4:23 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    CompleteReport report = (CompleteReport) session.getAttribute("report");
%>
<html>
<head>
    <title>Vidya - Admin</title>
</head>
<body>
<table>
    <tr>
        <th>Descrição:</th>
        <td><%=report.getDescription()%></td>
    </tr>
    <tr>
        <th>Tipo:</th>
        <td><%=report.getType()%></td>
    </tr>
    <tr>
        <th>Professor:</th>
        <td><%=report.getTeacher()%></td>
    </tr>
    <tr>
        <th>Estudantes:</th>
        <td>
            <%
            for (String student : report.getStudents()) {
            %>
            <p><%=student%></p>
            <%
                }
            %>
        </td>
    </tr>
    <tr>
        <th>Data de Envio:</th>
        <td><%=report.getSend_at()%></td>
    </tr>
</table>
</body>
</html>
