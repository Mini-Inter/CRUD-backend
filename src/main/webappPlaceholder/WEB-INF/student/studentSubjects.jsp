<%@ page import="com.school.miniinter.models.Subject.Subject" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    List<Subject> subjects = (List<Subject>) request.getAttribute("subjects");
%>
<html>
<head>
    <title>Subjects</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/geral/link.css">
</head>
<body>
    <table>
        <tr>
            <th>Disciplinas</th>
        </tr>
        <%
            for (Subject s : subjects) {
        %>
            <tr>
                <td><%=s.getName()%></td>
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
