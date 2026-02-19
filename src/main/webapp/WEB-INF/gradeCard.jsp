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
            <td><%=g.getN1()%></td>
            <td><%=g.getN2()%></td>
            <td><%=g.getAverage()%></td>
            <td><%=g.getSituation()%></td>
        </tr>
        <%
            }
        %>
    </table>
    <a
            href="generateGradeCard?idStudent=<%=request.getAttribute("idStudent")%>">gerar boletim</a>
    <a href="observations">Observações</a>
</body>
</html>
