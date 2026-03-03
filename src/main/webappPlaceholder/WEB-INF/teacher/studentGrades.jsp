<%@ page import="com.school.miniinter.models.Students.Summary" %>
<%@ page import="com.school.miniinter.models.Subject.Subject" %>
<%@ page import="java.util.List" %>
<%@ page import="com.school.miniinter.models.Grades.GradeForSubject" %>
<%@ page import="java.time.LocalDate" %><%--
  Created by IntelliJ IDEA.
  User: Vini
  Date: 2/21/26
  Time: 10:43 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    List<Subject> subjects = (List<Subject>) session.getAttribute("subjects");
    int idSubject = (Integer) session.getAttribute("subject");

    Summary student = (Summary) session.getAttribute("student");
    List<GradeForSubject> grades = (List<GradeForSubject>) session.getAttribute("grades");
%>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/geral/link.css">
</head>
<body>

    <h1>Aluno: <%=student.getName()%></h1>
    <p>Turma: <%=student.getSeries()%>°<%=student.getClassroom()%></p>
    <p>Matrícula: <%=student.getMatricula()%></p>
    <p>Ano Letivo: <%=LocalDate.now().getYear()%></p>

    <form action="${pageContext.request.contextPath}/studentReports?student=<%=student.getMatricula()%>" method="post"><input value="Observações" type="submit"></form>
    <form action="${pageContext.request.contextPath}/studentGrades?student=<%=student.getMatricula()%>" method="post"><input value="Notas" type="submit"></form>

    <table>
        <tr>
            <th>Disciplina</th>
            <th>N1</th>
            <th>N2</th>
            <th>Média</th>
            <th>Situação</th>
        </tr>
        <%
            for (GradeForSubject grade : grades) {
        %>
        <tr>
            <td><%=grade.getSubject()%></td>
            <%
                if (grade.getN1() == -1) {
            %>
            <td>—</td>
            <%
                } else {
            %>
            <td><%=grade.getN1()%></td>
            <%
                }
                if (grade.getN2() == -1) {
            %>
            <td>—</td>
            <%
            } else {
            %>
            <td><%=grade.getN2()%></td>
            <%
                }
                if (grade.getAverage() == -1) {
            %>
            <td>—</td>
            <%
            } else {
            %>
            <td><%=grade.getAverage()%></td>
            <%
                }
            %>
            <td><%=grade.getSituation()%></td>
        </tr>
        <%
            }
        %>
    </table>

    <form action="${pageContext.request.contextPath}/changeSubject" method="post">
        <label for="subject"></label>
        <select name="subject" id="subject">
            <%
                for (Subject subject : subjects) {
                    if (subject.getId()==idSubject) {
            %>
            <option selected="selected" value="<%=subject.getId()%>"><%=subject.getName()%></option>
            <%
            } else {
            %>
            <option value="<%=subject.getId()%>"><%=subject.getName()%></option>
            <%
                    }
                }
            %>
        </select>
        <input value="Trocar matéria" type="submit">
    </form>
</body>
</html>
