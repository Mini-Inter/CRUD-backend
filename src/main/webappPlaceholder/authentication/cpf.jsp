<%--
  Created by IntelliJ IDEA.
  User: Vini
  Date: 2/19/26
  Time: 8:19 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String error = String.valueOf(session.getAttribute("error"));
    String cpf = "";
    if (session.getAttribute("cpf") != null) {
        cpf = String.valueOf(session.getAttribute("cpf"));
    }
%>
<html>
<head>
    <title>Vidya - Cadastro</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/geral/link.css">
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
    <form action="${pageContext.request.contextPath}/auth?type=signup&pre=true" method="post">
        <label for="cpf">CPF:</label>
        <input value="<%=cpf%>" name="cpf" id="cpf" type="text">
        <input value="Enviar" type="submit">
    </form>
    <%
        session.invalidate();
    %>
</body>
</html>
