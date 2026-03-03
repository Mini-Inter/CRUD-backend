<%@ page import="com.school.miniinter.models.Subject.Subject" %>
<%@ page import="java.util.List" %>
<%@ page import="com.school.miniinter.models.Students.Summary" %>
<%@ page import="com.school.miniinter.models.Students.Students" %><%--
  Created by IntelliJ IDEA.
  User: Vini
  Date: 2/21/26
  Time: 1:42 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    List<Subject> subjects = (List<Subject>) session.getAttribute("subjects");
    int idSubject = (Integer) session.getAttribute("subject");

    List<Students> students = (List<Students>) session.getAttribute("students");
%>
<html>
<head>
    <title>Vidya - Report</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/geral/link.css">
</head>
<body>
    <%
        if (students.isEmpty()) {
    %>
    <h1>Nenhum aluno encontrado!</h1>
    <%
        } else {
    %>
    <form action="${pageContext.request.contextPath}/teacherReports?type=createReport">
        <label for="student">Aluno:</label>
        <select name="student" id="student">
            <%
                for (Students student : students) {
            %>
            <option value="<%=student.getId_student()%>"><%=student.getFull_name()%></option>
            <%
                }
            %>
        </select>
        <label for="type">Tipo de observação:</label>
        <select name="type" id="type">
            <option value="praise">Elogio</option>
            <option value="notice">Aviso</option>
            <option value="info">Informativa</option>
        </select>
        <label for="description">Observação</label>
        <input name="description" id="description" type="text">
    </form>
    <%
        }
    %>

    <form action="${pageContext.request.contextPath}/teacherReports?type=showReports" method="post">
        <input value="Observações" type="submit">
    </form>
    <form action="${pageContext.request.contextPath}/teacherReports" method="post">
        <input value="Observações" type="submit">
    </form>

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