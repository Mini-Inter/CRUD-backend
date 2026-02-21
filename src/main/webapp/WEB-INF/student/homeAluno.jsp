<%@ page import="com.school.miniinter.models.Students.BasicInfo" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    BasicInfo basicInfo = (BasicInfo) request.getAttribute("basicInfo");
%>
<html>
<head>
    <title>Home Aluno</title>
</head>
<body>
    <h1>Informações básicas</h1>
    <p>Turma: <%=basicInfo.getSeries()%>°<%=basicInfo.getClassroom()%></p>
    <p>Total de matérias: <%=request.getAttribute("amountSubjects")%></p>
    <p>Média total: <%=request.getAttribute("avgGrade")%></p>
    <p>Total de observações: <%=request.getAttribute("amountReports")%></p>

    <form action="<%=request.getContextPath()%>/homeStudent" method="post"><input value="Home" type="submit"></form>
    <form action="<%=request.getContextPath()%>/gradeCard" method="post"><input value="Boletim" type="submit"></form>
    <form action="<%=request.getContextPath()%>/studentSubjects" method="post"><input value="Matérias" type="submit"></form>
    <form action="<%=request.getContextPath()%>/observations" method="post"><input value="Observações" type="submit"></form>
</body>
</html>
