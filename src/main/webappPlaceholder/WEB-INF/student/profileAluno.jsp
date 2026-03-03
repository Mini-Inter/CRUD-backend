<%@ page import="com.school.miniinter.models.Students.CompleteInfo" %><%
    CompleteInfo completeInfo = (CompleteInfo)
            request.getAttribute("completeInfoStudent");
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/geral/link.css">
</head>
<body>
    <p><%=completeInfo%></p>
</body>
</html>
