<%@ page import="com.school.miniinter.models.Teacher.CompleteInfo" %>
<%@ page import="com.school.miniinter.models.Teacher.AmountStudentByTeacher" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    CompleteInfo completeInfo =
            (CompleteInfo) request.getAttribute("completeInfoTeacher");
    AmountStudentByTeacher amountStudentByTeacher = (AmountStudentByTeacher)
            request.getAttribute("amountStudentByTeacher");
%>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <p><%=completeInfo%></p>
    <p><%=amountStudentByTeacher%></p>
</body>
</html>
