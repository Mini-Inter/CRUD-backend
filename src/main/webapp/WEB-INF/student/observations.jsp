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
    <form action="observations?filter=Elogio" method="post"><input type="submit" value="Filtrar por Elogio"></form>
    <form action="observations?filter=Aviso" method="post"><input type="submit" value="Filtrar por Aviso"></form>
    <form action="observations?filter=Informativo" method="post"><input type="submit" value="Filtrar por Informativo"></form>
</body>
</html>
