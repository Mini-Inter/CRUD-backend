<%--
  Created by IntelliJ IDEA.
  User: Vini
  Date: 2/19/26
  Time: 8:19 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Vidya - Cadastro</title>
</head>
<body>
<form action="<%=request.getContextPath()%>/auth?type=signup" method="post">
    <label for="cpf">CPF</label>
    <input name="cpf" id="cpf" type="text">
    <input value="Enviar" type="submit">
</form>
</body>
</html>
