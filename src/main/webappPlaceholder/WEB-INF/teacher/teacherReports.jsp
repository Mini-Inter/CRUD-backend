<%@ page import="com.school.miniinter.models.Subject.Subject" %>
<%@ page import="java.util.List" %>
<%@ page import="com.school.miniinter.models.Reports.CompleteInformationReport" %><%--
  Created by IntelliJ IDEA.
  User: Vini
  Date: 2/21/26
  Time: 2:32 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
        List<Subject> subjects = (List<Subject>) session.getAttribute("subjects");
        int idSubject = (Integer) session.getAttribute("subject");

        List<CompleteInformationReport> reports = (List<CompleteInformationReport>) session.getAttribute("reports");
%>
<html>
<head>
    <title>Vidya - Reports</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/geral/link.css">
</head>
<body>
    <%
        if (reports.isEmpty()) {
    %>
    <h2>Não encontramos nenhuma observação!</h2>
    <%
    } else {
    %>
    <table>
        <tr>
            <th>Professor</th>
            <th>Descrição</th>
            <th>Tipo</th>
            <th>Data</th>
        </tr>
        <%
            for (CompleteInformationReport report : reports) {
        %>
        <tr>
            <td><%=report.getTeacher_name()%></td>
            <td><%=report.getDescription()%></td>
            <td><%=report.getType()%></td>
            <td><%=report.getSend_at()%></td>
        </tr>
        <%
            }
        %>
    </table>
    <%
        }
    %>

    <form action="${pageContext.request.contextPath}/changeSubject" method="post">
        <label for="subject"></label>
        <select name="subject" id="subject">
            <%
                for (Subject subject : subjects) {
                    if (subject.getId()==idSubject) {
            %>
            <option selected="selected" value="<%=subject.getId()%>"><%=subject.getName()%></option>
            <%
            } else {
            %>
            <option value="<%=subject.getId()%>"><%=subject.getName()%></option>
            <%
                    }
                }
            %>
        </select>
        <input value="Trocar matéria" type="submit">
    </form>
</body>
</html>
