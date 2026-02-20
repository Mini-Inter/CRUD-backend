<%@ page import="java.util.List" %>
<%@ page import="com.school.miniinter.models.Students.Summary" %>
<%@ page import="com.school.miniinter.models.Subject.Subject" %><%--
  Created by IntelliJ IDEA.
  User: Vini
  Date: 2/20/26
  Time: 8:49 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    List<Summary> students = (List<Summary>) session.getAttribute("students");
    List<Subject> subjects = (List<Subject>) session.getAttribute("subjects");

%>
<html>
<head>
    <title>Vidya - Students</title>
</head>
<body>
    <h1>Meus Alunos</h1>
    <form action="<%=request.getContextPath()%>/teacherStudents" method="post">
        <input value="" name="matricula" id="matricula" placeholder="Buscar por matrícula" type="number">
        <input value="Buscar" type="submit">
    </form>


    <%
        if (students.isEmpty()) {
    %>
        <p>Sorry! We couldn't find any students.</p>
    <%
        } else {
    %>
        <table>
            <tr>
                <th>Nome</th>
                <th>Matrícula</th>
                <th>Turma</th>
                <th>Média</th>
                <th>Situação</th>
            </tr>
    <%
        for (Summary student : students) {
    %>
            <form action="">

            </form>
                <tr>
                    <td><%=student.getName()%></td>
                    <td><%=student.getMatricula()%></td>
                    <td><%=student.getSeries()%>°<%=student.getClassroom()%></td>
                    <td><%=student.getAverage()%></td>
                    <%
                        if (student.getSituation()) {
                    %>
                        <td style="color: green">Aprovado</td>
                    <%
                        } else {
                    %>
                        <td style="color: red;">Reprovado</td>
                    <%
                        }
                    %>
                </tr>
            <%
                }
            %>
        </table>
    <%
        }
    %>
    <form action="<%=request.getContextPath()%>/changeSubject" method="post">
        <label for="subject"></label>
        <select name="subject" id="subject">
            <%
                for (Subject subject : subjects) {
            %>
            <option value="<%=subject.getId()%>"><%=subject.getName()%></option>
            <%
                }
            %>
        </select>
        <input value="Trocar matéria" type="submit">
    </form>
</body>
</html>
