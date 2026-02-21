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
    if (subjects.isEmpty()) {
%>
<h2>Nenhum professor encontrado!</h2>
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
        <td><%=subject.getDescription()%>@vidya.org.br</td>
        <td>
            <form action="<%=request.getContextPath()%>/adminSubjects?type=showSubject" method="post">
                <input name="subject" id="subject" value="<%=subject.getId()%>" type="hidden">
                <input value="Ver" type="submit">
            </form>
            <form action="<%=request.getContextPath()%>/adminSubjects?type=editSubject" method="post">
                <input name="subject" id="subject" value="<%=subject.getId()%>" type="hidden">
                <input value="Editar" type="submit">
            </form>
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
