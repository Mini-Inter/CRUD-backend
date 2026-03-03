<%--
  Created by IntelliJ IDEA.
  User: Vini
  Date: 2/19/26
  Time: 8:20 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String error = String.valueOf(session.getAttribute("error"));
    String name = "";
    String email = "";
    String birth = "";
    String pw = "";
    String pc = "";
    if (session.getAttribute("name")!=null)
        name = String.valueOf(session.getAttribute("name"));
    if (session.getAttribute("email")!=null)
        email = String.valueOf(session.getAttribute("email"));
    if (session.getAttribute("birth")!=null)
        birth = String.valueOf(session.getAttribute("birth"));
    if (session.getAttribute("pw")!=null)
        pw = String.valueOf(session.getAttribute("pw"));
    if (session.getAttribute("pc")!=null)
        pc = String.valueOf(session.getAttribute("pc"));
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
    <form action="${pageContext.request.contextPath}/auth?type=signup&pre=false" method="post">
        <label for="name">Nome Completo:</label>
        <input value="<%=name%>" id="name" name="name" type="text">
        <label for="birth">Data de Nascimento:</label>
        <input value="<%=birth%>" name="birth" id="birth" type="date">
        <label for="email">Email:</label>
        <input value="<%=email%>" id="email" name="email" type="email">
        <label for="password">Crie sua senha:</label>
        <input value="<%=pw%>" id="password" name="password" type="password">
        <label for="passwordConf">Confirme sua senha:</label>
        <input value="<%=pc%>" name="passwordConf" id="passwordConf" type="password">
        <input value="Enviar" type="submit">
    </form>
    <%
        session.setAttribute("name", null);
        session.setAttribute("birth", null);
        session.setAttribute("email", null);
        session.setAttribute("pw", null);
        session.setAttribute("pc", null);
    %>
</body>
</html>
