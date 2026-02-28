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

    <form action="${pageContext.request.contextPath}/homeStudent" method="post"><input value="Home" type="submit"></form>
    <form action="${pageContext.request.contextPath}/generateGradeCard" method="post"><input value="Gerar " type="submit"></form>
    <form action="${pageContext.request.contextPath}/gradeCard" method="post"><input value="Boletim" type="submit"></form>
    <form action="${pageContext.request.contextPath}/studentSubjects" method="post"><input value="Matérias" type="submit"></form>
    <form action="${pageContext.request.contextPath}/observations" method="post"><input value="Observações" type="submit"></form>
    <form action="${pageContext.request.contextPath}/profileStudent" method="post"><input value="Profile" type="submit"></form>
</body>
</html>
