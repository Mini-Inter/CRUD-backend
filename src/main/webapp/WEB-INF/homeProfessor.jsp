<%@ page import="com.school.miniinter.models.Subject.Subject" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: Vini
  Date: 2/19/26
  Time: 1:10 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    List<Subject> subjects = (List<Subject>) session.getAttribute("subjects");
    int idSubject = (Integer) session.getAttribute("subject");
%>
<html>
<head>
    <title>Vidya - Professor</title>
</head>
<body>
    <form action="<%=request.getContextPath()%>/teacherStudents" method="post"><input value="Alunos" type="submit"></form>
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
