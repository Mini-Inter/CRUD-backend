<%@ page import="com.school.miniinter.models.Students.CompleteInfo" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    CompleteInfo completeInfo =
            (CompleteInfo) request.getAttribute("completeInfoStudent");
%>
<html>
<head>
    <title>Profile Student</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/geral/link.css">
</head>
<body>
    <p><%=completeInfo%></p>
</body>
</html>
