<%@ page import="com.school.miniinter.models.Class.Class" %>
<%@ page import="com.school.miniinter.models.TeachingAssignment.Teaching" %>
<%@ page import="java.util.List" %>
<%@ page import="com.school.miniinter.models.Teacher.Teacher" %>
<%@ page import="com.school.miniinter.models.Subject.Subject" %>
<%@ page import="org.bouncycastle.asn1.x509.SubjectKeyIdentifier" %><%--
  Created by IntelliJ IDEA.
  User: Vini
  Date: 2/21/26
  Time: 4:23 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Class classroom = (Class) session.getAttribute("classroom");

    Teaching[] aulas = (Teaching[]) session.getAttribute("aulas");
    List<Teacher> teachers = (List<Teacher>) session.getAttribute("teachers");
    List<Subject> subjects = (List<Subject>) session.getAttribute("subjects");
%>
<html>
<head>
    <title>Vidya - Admin</title>
</head>
<body>
<%@include file="../common/error.jsp"%>
<form action="<%=request.getContextPath()%>/adminClasses?type=updateClass" method="post">
    <input name="classroom" id="classroom" value="<%=classroom.getId()%>" type="hidden">
    <table>
        <tr>
            <th><label for="series">Série:</label></th>
            <td><input value="<%=classroom.getSeries()%>" name="series" id="series" type="text"></td>
        </tr>
        <tr>
            <th><label for="turma">Turma:</label></th>
            <td><input value="<%=classroom.getClassroom()%>" name="turma" id="turma" type="text"></td>
        </tr>
    </table>
    <h1>Aulas</h1>
    <table>
        <tr>
            <th>Matéria</th>
            <th>Professor</th>
            <th>Horário</th>
        </tr>
        <%
            for (int n = 0; n < 6; n++) {
        %>
        <tr>
            <input value="<%=aulas[n].getIdTeaching()%>" name="aula<%=n%>Id" type="hidden">
            <input value="<%=n%>" name="aula<%=n%>ClassNum" type="hidden">
            <td>
                <select name="aula<%=n%>Subject" id="aula<%=n%>Subject">
                    <%
                        for (Subject subject : subjects) {
                    %>
                    <option value="<%=subject.getId()%>"><%=subject.getName()%></option>
                    <%
                        }
                    %>
                </select>
            </td>
            <td>
                <select name="aula<%=n%>Teacher" id="aula<%=n%>Teacher">
                    <%
                        for (Teacher teacher : teachers) {
                    %>
                    <option value="<%=teacher.getId()%>"><%=teacher.getName()%></option>
                    <%
                        }
                    %>
                </select>
            </td>
        </tr>
        <%
            }
        %>
    </table>
    <input value="Enviar" type="submit">
</form>
</body>
</html>
