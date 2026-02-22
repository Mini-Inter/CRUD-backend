<%@ page import="com.school.miniinter.models.Reports.Reports" %>
<%@ page import="java.util.List" %>
<%@ page import="com.school.miniinter.models.Students.Students" %><%--
  Created by IntelliJ IDEA.
  User: Vini
  Date: 2/21/26
  Time: 4:23 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Reports reports = (Reports) session.getAttribute("reports");

    List<Students> students = (List<Students>) session.getAttribute("students");
%>
<html>
<head>
    <title>Vidya - Admin</title>
</head>
<body>
<%@include file="../common/error.jsp"%>
<form action="<%=request.getContextPath()%>/adminReports?type=updateReport" method="post">
    <input name="reports" id="reports" value="<%=reports.getId()%>" type="hidden">
    <table>
        <tr>
            <th><label for="type">Tipo:</label></th>
            <td><input value="<%=reports.getType()%>" name="type" id="type" type="text"></td>
        </tr>
        <tr>
            <th><label for="description">Descrição:</label></th>
            <td><input value="<%=reports.getDescription()%>" name="description" id="description" type="date"></td>
        </tr>
        <tr>
            <th>
                <select multiple="multiple" name="students" id="students">
                    <%
                        for (Students student : students) {
                    %>
                    <option value="<%=student.getId_student()%>"><%=student.getFull_name()%></option>
                    <%
                        }
                    %>
                </select>
            </th>
        </tr>
    </table>
    <input value="Enviar" type="submit">
</form>
</body>
</html>
