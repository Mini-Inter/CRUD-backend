
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Vidya - Inserir professor</title>
</head>
<body>
<%@include file="../common/error.jsp"%>
<form action="<%=request.getContextPath()%>/adminTeachers?type=insertTeacher"
      method="post">
  <table>
    <tr>
      <th><label for="name">Nome completo:</label></th>
      <td><input name="name" id="name" type="text"></td>
    </tr>
    <tr>
      <th><label for="birth">Data de nascimento:</label></th>
      <td><input name="birth" id="birth" type="date"></td>
    </tr>
    <tr>
      <th><label for="phone">Telefone (com DDD):</label></th>
      <td><input type="text" name="prone" id="phone"></td>
    </tr>
    <tr>
      <th><label for="email">Email:</label></th>
      <td><input name="email" id="email" type="email"></td>
    </tr>
    <tr>
      <th><label for="pass">Senha:</label></th>
      <td><input name="pass" id="pass" type="password"></td>
    </tr>
  </table>
  <input  type="submit">
</form>
</body>
</html>
