<%@ page import="com.school.miniinter.models.Subject.Subject" %>
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
    Subject subject = (Subject) session.getAttribute("subject");

    List<Class> classes = (List<Class>)  session.getAttribute("classes");
%>
<html>
<head>
    <title>Vidya - Admin</title>
</head>
<body>
<%@include file="../common/error.jsp"%>
<form action="<%=request.getContextPath()%>/adminSubject?type=updateSubject" method="post">
    <input name="subject" id="subject" value="<%=subject.getId()%>" type="hidden">
    <table>
<%--        Era para ter essas informações mesmo Vini?--%>
<%--        <tr>--%>
<%--            <th><label for="classroom">Turma:</label></th>--%>
<%--            <td>--%>
<%--                <select name="classroom" id="classroom">--%>
<%--                    <%--%>
<%--                        for (Class classroom : classes) {--%>
<%--                    %>--%>
<%--                    <option value="<%=classroom.getId()%>"><%=classroom.getSeries()%>°<%=classroom.getClassroom()%></option>--%>
<%--                    <%--%>
<%--                        }--%>
<%--                    %>--%>
<%--                </select>--%>
<%--            </td>--%>
<%--        </tr>--%>
        <tr>
            <th><label for="name">Nome:</label></th>
            <td><input value="<%=subject.getName()%>" name="name" id="name" type="text"></td>
        </tr>
        <tr>
            <th><label for="description">Descrição:</label></th>
            <td><input value="<%=subject.getDescription()%>" name="description" id="description" type="text"></td>
        </tr>
    </table>
    <input value="Enviar" type="submit">
</form>
</body>
</html>
