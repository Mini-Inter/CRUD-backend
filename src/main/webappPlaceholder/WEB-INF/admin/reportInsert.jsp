<%@ page import="java.util.List" %>
<%@ page import="com.school.miniinter.models.Teacher.Teacher" %>
<%@ page import="com.school.miniinter.models.Students.Students" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    List<Teacher> listTeachers = (List<Teacher>)
            request.getAttribute("listTeachers");

    List<Students> listStudents = (List<Students>)
            request.getAttribute("listStudents");
%>
<html>
<head>
    <title>Vidya - Criar Observação</title>
</head>
<body>
<%@include file="../common/error.jsp"%>
<form action="<%=request.getContextPath()%>/adminReports?type=insertReport"
      method="post">
    <table>
        <tr>
            <th><label for="student">Aluno:</label></th>
            <td>
                <select name="student" id="student">
                    <%
                        for (Students s : listStudents) {
                    %>
                    <option value="<%=s.getId_student()%>"><%=s.getFull_name()%></option>
                    <%
                        }
                    %>
                </select>
            </td>
        </tr>
        <tr>
            <th><label for="teacher">Professor:</label></th>
            <td>
                <select name="teacher" id="teacher">
                    <%
                        for (Teacher t : listTeachers) {
                    %>
                    <option value="<%=t.getId()%>"><%=t.getName()%></option>
                    <%
                        }
                    %>
                </select>
            </td>
        </tr>
        <tr>
            <th><label for="description">Descrição:</label></th>
            <td><input name="description" id="description" type="text"></td>
        </tr>
        <tr>
            <th><label for="type">Tipo:</label></th>
            <td><input name="type" id="type" type="text"></td>
        </tr>
    </table>
    <input  type="submit">
</form>
</body>
</html>
