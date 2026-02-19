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
    String login = "";
    String password = "";
    if (session.getAttribute("login")!=null) {
        login = String.valueOf(session.getAttribute("login"));
    }
    if (session.getAttribute("password")!=null) {
        password = String.valueOf(session.getAttribute("password"));
    }
%>
<html>
<head>
    <title>Vidya - Log In</title>
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
    <form action="<%=request.getContextPath()%>/auth?type=login" method="post">
        <label for="login">Email:</label>
        <input value="<%=login%>" required name="login" id="login" type="text">
        <label for="pw">Senha:</label>
        <input value="<%=password%>" required name="pw" id="pw" type="password">
        <input value="Login" type="submit">
    </form>
    <%
        session.invalidate();
    %>
    <p>Ainda não tem conta? <a href="./cpf.jsp">Cadastre-se</a></p>
</body>
</html>
