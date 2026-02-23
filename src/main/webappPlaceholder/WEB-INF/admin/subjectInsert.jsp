
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Vidya - Criar matéria</title>
</head>
<body>
<%@include file="../common/error.jsp"%>
<form action="<%=request.getContextPath()%>/adminSubjects?type=insertSubject"
      method="post">
  <table>
    <tr>
      <th><label for="name">Nome da matéria:</label></th>
      <td><input name="name" id="name" type="text"></td>
    </tr>
    <tr>
      <th><label for="description">Descrição:</label></th>
      <td><input name="description" id="description" type="text"></td>
    </tr>
  </table>
  <input  type="submit">
</form>
</body>
</html>
