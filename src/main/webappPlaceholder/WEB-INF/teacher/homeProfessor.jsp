
<%@ page import="com.school.miniinter.models.Teacher.HomeTeacherInfo" %>
<%@ page import="com.school.miniinter.models.Subject.Subject" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    HomeTeacherInfo homeTeacherInfo =
            (HomeTeacherInfo) request.getAttribute("homeTeacherInfo");
    List<Subject> subjects = (List<Subject>) session.getAttribute("subjects");
    Integer idSubject = (Integer) session.getAttribute("subject");
%>
<html>
<head>
    <title>Vidya - Professor</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/geral/link.css">
</head>
<body>
    <p><%=homeTeacherInfo%></p>
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
    <form action="${pageContext.request.contextPath}/homeTeacher" method="post"><input type="submit" value="Home"></form>
    <form action="${pageContext.request.contextPath}/updateGrade" method="post"><input type="submit" value="Ver notas alunos"></form>
    <form action="${pageContext.request.contextPath}/teacherStudents" method="post"><input value="Alunos" type="submit"></form>
    <form action="${pageContext.request.contextPath}/teacherReports" method="post"><input value="Observações" type="submit"></form>
    <form action="${pageContext.request.contextPath}/teacherProfile" method="post"><input type="submit" value="Profile"></form>
</body>
</html>