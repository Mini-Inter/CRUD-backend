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
    String error = (String) session.getAttribute("error");

    List<Students> students = (List<Students>) session.getAttribute("students");
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
<form action="<%=request.getContextPath()%>/adminReports?type=editReports">
    <input name="reports" id="reports" value="<%=reports.getId()%>" type="hidden">
    <table>
        <tr>
            <th><label for="name">Tipo:</label></th>
            <td><input value="<%=reports.getType()%>" name="description" id="description" type="text"></td>
        </tr>
        <tr>
            <th><label for="birth">Descrição:</label></th>
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
</form>
</body>
</html>
