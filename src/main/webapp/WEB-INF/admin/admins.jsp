<%@ page import="com.school.miniinter.models.Guardian.Guardian" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: Vini
  Date: 2/21/26
  Time: 4:01 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    List<Guardian> guardians = (List<Guardian>) session.getAttribute("guardians");
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
    if (guardians.isEmpty()) {
%>
<h2>Nenhum professor encontrado!</h2>
<%
} else {
%>
<table>
    <tr>
        <th>Nome Completo</th>
        <th>Data de nascimento</th>
        <th colspan="3">Ações</th>
    </tr>
    <%
        for (Guardian guardian : guardians) {
    %>
    <tr>
        <td><%=guardian.getName()%></td>
        <td><%=guardian.getBirthDate()%></td>
        <td>
            <form action="<%=request.getContextPath()%>/adminGuardians?type=showGuardian" method="post">
                <input name="guardian" id="guardian" value="<%=guardian.getId()%>" type="hidden">
                <input value="Ver" type="submit">
            </form>
            <form action="<%=request.getContextPath()%>/adminGuardians?type=editGuardian" method="post">
                <input name="guardian" id="guardian" value="<%=guardian.getId()%>" type="hidden">
                <input value="Editar" type="submit">
            </form>
            <form action="<%=request.getContextPath()%>/adminGuardians?type=deleteGuardian" method="post">
                <input name="guardian" id="guardian" value="<%=guardian.getId()%>" type="hidden">
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
