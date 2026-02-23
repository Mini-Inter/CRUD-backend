<%@ page import="com.school.miniinter.models.Students.Students" %>
<%@ page import="com.school.miniinter.models.Class.Class" %>
<%@ page import="java.util.List" %>
<%@ page import="com.school.miniinter.models.PreRegistration.PreRegistration" %>
<%--
  Created by IntelliJ IDEA.
  User: Vini
  Date: 2/21/26
  Time: 4:23 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    List<Class> classes = (List<Class>)  session.getAttribute("classes");

    List<PreRegistration> cpfs = (List<PreRegistration>)
            session.getAttribute("cpfs");
%>
<html>
<head>
    <title>Vidya - Admin</title>
</head>
<body>
<%@include file="../common/error.jsp"%>
<form action="<%=request.getContextPath()%>/adminStudents?type=insertStudent" method="post">
    <table>
        <tr>
            <th><label for="cpf">CPF:</label></th>
            <td>
                <select name="cpf" id="cpf">
                    <%
                        for (PreRegistration cpf: cpfs) {
                    %>
                    <option value="<%=cpf.getId()%>" ><%=cpf.getCpf()%></option>
                    <%
                        }
                    %>
                </select>
            </td>
        </tr>
        <tr>
            <th><label for="classroom">Turma:</label></th>
            <td>
                <select name="classroom" id="classroom">
                    <%
                        for (Class classroom : classes) {
                    %>
                    <option value="<%=classroom.getId()%>">
                        <%=classroom.getSeries()%>°<%=classroom.getClassroom()%></option>
                    <%
                        }
                    %>
                </select>
            </td>
        </tr>
        <tr>
            <th><label for="name">Nome:</label></th>
            <td><input name="name" id="name" type="text"></td>
        </tr>
        <tr>
            <th><label for="birth">Data de nascimento:</label></th>
            <td><input name="birth" id="birth" type="date"></td>
        </tr>
        <tr>
            <th><label for="phone">Telefone:</label></th>
            <td><input name="phone" id="phone" type="tel"></td>
        </tr>
        <tr>
            <th><label for="email">Email:</label></th>
            <td><input name="email" id="email" type="email"></td>
        </tr>
        <tr>
            <th><label for="pass">Senha:</label></th>
            <td><input name="pass" id="pass" type="password"></td>
        </tr>
    </table>
    <input  type="submit">
</form>
</body>
</html>
