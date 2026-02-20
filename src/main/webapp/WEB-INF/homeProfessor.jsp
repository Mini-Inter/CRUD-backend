<%@ page import="com.school.miniinter.models.Teacher.HomeTeacherInfo" %>
<%@ page import="com.school.miniinter.models.Subject.Subject" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    HomeTeacherInfo homeTeacherInfo =
            (HomeTeacherInfo) request.getAttribute("homeTeacherInfo");
    List<Subject> subjects = (List<Subject>) session.getAttribute("subjects");
    int idSubject = (Integer) session.getAttribute("subject");

%>
<html>
<head>
    <title>Vidya - Professor</title>
</head>
<body>
    <form action="<%=request.getContextPath()%>/teacherStudents" method="post"><input value="Alunos" type="submit"></form>
    <p><%=homeTeacherInfo%></p>
    <form action="<%=request.getContextPath()%>/changeSubject" method="post">
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
