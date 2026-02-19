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

    <a href="gradeCard?idStudent=<%=basicInfo.getId_student()%>">boletim</a>
</body>
</html>
