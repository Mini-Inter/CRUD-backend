<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String success = (String) session.getAttribute("success");
    String error = (String) session.getAttribute("error");
    if (success != null && !success.equals("null")) {
%>
<p style="color: green"><%=success%></p>
<%
        session.setAttribute("success", null);
    }
%>
<%
    if (error != null && !error.equals("null")) {
%>
<p style="color: red"><%=error%></p>
<%
        session.setAttribute("error", null);
    }
%>