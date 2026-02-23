
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Vidya - insert Class</title>
</head>
<body>
<%@include file="../common/error.jsp"%>
<form action="<%=request.getContextPath()%>/adminClasses?type=insertClass"
      method="post">
    <table>
        <tr>
            <th><label for="series">Série:</label></th>
            <td><input name="series" id="series" type="text"></td>
        </tr>
        <tr>
            <th><label for="classroom">Turma:</label></th>
            <td><input name="classroom" id="classroom" type="text"></td>
        </tr>
    </table>
    <input  type="submit" value="Enviar dados">
</form>
</body>
</html>
