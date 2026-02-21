<%@ page
        import="com.school.miniinter.models.Reports.CompleteInformationReport" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    List<CompleteInformationReport> list =
            (List<CompleteInformationReport>) request.getAttribute("List");
%>
<html>
<head>
    <title>Observacoes</title>
</head>
<body>
    <%
        for(CompleteInformationReport c: list){%>
    <hr>
        <p><%=c.getTeacher_name()%></p>
        <p><%=c.getDescription()%></p>
        <p><%=c.getType()%></p>
        <p><%=c.getSend_at()%></p>
    <hr>
    <%}%>
    <a href="observations?filter=Elogio">Filtrar por Elogio</a>
    <a href="observations?filter=Aviso">Filtrar por Aviso</a>
    <a href="observations?filter=Informativo">Filtrar por Informativo</a>
</body>
</html>
