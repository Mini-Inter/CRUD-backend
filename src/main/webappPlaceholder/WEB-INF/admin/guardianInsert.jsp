
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Vidya - Guardian Create</title>
</head>
<body>
<%@include file="../common/error.jsp"%>
<form action="<%=request.getContextPath()%>/adminGuardians?type=insertGuardian"
      method="post">
  <table>
    <tr>
      <th><label for="name">Nome:</label></th>
      <td><input name="name" id="name" type="text"></td>
    </tr>
    <tr>
      <th><label for="birth">Data de nascimento:</label></th>
      <td><input name="birth" id="birth" type="date"></td>
    </tr>
  </table>
  <input  type="submit">
</form>
</body>
</html>
