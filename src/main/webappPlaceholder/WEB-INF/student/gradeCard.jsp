<%@ page import="java.util.List" %>
<%@ page import="com.school.miniinter.models.Grades.GradeForSubject" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    List<GradeForSubject> gradeForSubjects =
            (List<GradeForSubject>) request.getAttribute("GradeCard");
%>
<html>
<head>
    <title>Grade Card</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/geral/link.css">
</head>
<body>
    <table>
        <tr>
            <th>Subject</th>
            <th>N1</th>
            <th>N2</th>
            <th>FA</th>
            <th>Situation</th>
        </tr>
        <%
            for(GradeForSubject g: gradeForSubjects){
        %>
        <tr>
            <td><%=g.getSubject()%></td>
            <td><%if(g.getN1() == -1.0){%>
                <p>-</p>
            <%}else{%>
                <%=g.getN1()%>
                <%}%></td>

            <td><%if(g.getN2() == -1.0){%>
                <p>-</p>
                <%}else{%>
                <%=g.getN2()%>
                <%}%></td>
            <td><%=g.getAverage()%></td>
            <td><%=g.getSituation()%></td>
        </tr>
        <%
            }
        %>
    </table>

    <form action="${pageContext.request.contextPath}/homeStudent" method="post"><input value="Home" type="submit"></form>
    <form action="${pageContext.request.contextPath}/gradeCard" method="post"><input value="Boletim" type="submit"></form>
    <form action="${pageContext.request.contextPath}/studentSubjects" method="post"><input value="Matérias" type="submit"></form>
    <form action="${pageContext.request.contextPath}/observations" method="post"><input value="Observações" type="submit"></form>
</body>
</html>
