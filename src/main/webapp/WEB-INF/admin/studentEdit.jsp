<%@ page import="com.school.miniinter.models.Students.Students" %>
<%@ page import="com.school.miniinter.models.Class.Class" %>
<%@ page import="java.util.List" %>
<%--
  Created by IntelliJ IDEA.
  User: Vini
  Date: 2/21/26
  Time: 4:23 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Students student = (Students) session.getAttribute("student");
    String error = (String) session.getAttribute("error");

    List<Class> classes = (List<Class>)  session.getAttribute("classes");
%>
<html>
<head>
    <title>Vidya - Admin</title>
</head>
<body>
<%
    if (!error.equals("null")) {
%>
<p style="color: red"><%=error%></p>
<%
        session.setAttribute("error", null);
    }
%>
<form action="<%=request.getContextPath()%>/adminStudents?type=editStudents">
    <input name="student" id="student" value="<%=student.getId_student()%>" type="hidden">
    <table>
        <tr>
            <th><label for="classroom">Turma:</label></th>
            <td>
                <select name="classroom" id="classroom">
                    <%
                        for (Class classroom : classes) {
                    %>
                    <option value="<%=classroom.getId()%>"><%=classroom.getSeries()%>°<%=classroom.getClassroom()%></option>
                    <%
                        }
                    %>
                </select>
            </td>
        </tr>
        <tr>
            <th><label for="name">Nome:</label></th>
            <td><input value="<%=student.getFull_name()%>" name="name" id="name" type="text"></td>
        </tr>
        <tr>
            <th><label for="email">Email:</label></th>
            <td><input value="<%=student.getLogin()%>" name="email" id="email" type="email"></td>
        </tr>
        <tr>
            <th><label for="birth">Data de nascimento:</label></th>
            <td><input value="<%=student.getBirth_date()%>" name="birth" id="birth" type="date"></td>
        </tr>
        <tr>
            <th><label for="pass">Senha:</label></th>
            <td><input value="<%=student.getPassword()%>" name="pass" id="pass" type="password"></td>
        </tr>
    </table>
</form>
</body>
</html>
