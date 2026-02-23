<%@ page import="com.school.miniinter.models.Teacher.CompleteInfo" %>
<%@ page import="com.school.miniinter.models.Teacher.AmountStudentByTeacher" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    CompleteInfo completeInfo =
            (CompleteInfo) request.getAttribute("completeInfoTeacher");
    List<AmountStudentByTeacher> amountStudentByTeacher = (List<AmountStudentByTeacher>)
            request.getAttribute("amountStudentByTeacher");
%>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <p><%=completeInfo%></p>
    <%
        for (AmountStudentByTeacher amount : amountStudentByTeacher) {
    %>
    <p><%=amount%></p>
    <%
        }
    %>
</body>
</html>
