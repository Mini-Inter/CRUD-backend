<%@ page import="com.school.miniinter.models.Class.Class" %>
<%@ page import="com.school.miniinter.models.TeachingAssignment.Teaching" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: Vini
  Date: 2/21/26
  Time: 4:23 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Class classroom = (Class) session.getAttribute("class");
    Teaching[] aulas = (Teaching[]) session.getAttribute("aulas");
%>
<html>
<head>
    <title>Vidya - Admin</title>
</head>
<body>
<table>
    <tr>
        <th>Série:</th>
        <td colspan="2"><%=classroom.getSeries()%></td>
    </tr>
    <tr>
        <th>Turma:</th>
        <td colspan="2"><%=classroom.getClassroom()%></td>
    </tr>
    <%
        int n = 1;
        for (Teaching aula : aulas) {
    %>
    <tr>
        <th><%=n++%>° Aula :</th>
        <%if(aula.getSubject() == null){%>
        <td colspan="2">Ainda não definido</td>
        <%}else{%>
        <td><%=aula.getSubject()%></td>
        <td><%=aula.getTeacher()%></td>
        <%}%>
    </tr>
    <%
        }
    %>
</table>
</body>
</html>
