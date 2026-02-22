<%@ page import="com.school.miniinter.models.Subject.Subject" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: Vini
  Date: 2/21/26
  Time: 4:01 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    List<Subject> subjects = (List<Subject>) session.getAttribute("subjects");
%>
<html>
<head>
    <title>Vidya - Admin</title>
</head>
<body>
<%@include file="../common/menuCRUD.jsp"%>
<%@include file="../common/error.jsp"%>
<%
    if (subjects == null || subjects.isEmpty()) {
%>
<h2>Nenhuma matéria encontrada!</h2>
<%
} else {
%>
<table>
    <tr>
        <th>Nome</th>
        <th>Descrição</th>
        <th colspan="3">Ações</th>
    </tr>
    <%
        for (Subject subject : subjects) {
    %>
    <tr>
        <td><%=subject.getName()%></td>
        <td><%=subject.getDescription()%></td>
        <td>
            <form action="<%=request.getContextPath()%>/adminSubjects?type=showSubject" method="post">
                <input name="subject" id="subject" value="<%=subject.getId()%>" type="hidden">
                <input value="Ver" type="submit">
            </form>
        </td>
        <td>
            <form action="<%=request.getContextPath()%>/adminSubjects?type=editSubject" method="post">
                <input name="subject" id="subject" value="<%=subject.getId()%>" type="hidden">
                <input value="Editar" type="submit">
            </form>
        </td>
        <td>
            <form action="<%=request.getContextPath()%>/adminSubjects?type=deleteSubject" method="post">
                <input name="subject" id="subject" value="<%=subject.getId()%>" type="hidden">
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