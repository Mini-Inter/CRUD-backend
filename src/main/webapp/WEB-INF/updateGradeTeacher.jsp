<%@ page import="java.util.List" %>
<%@ page import="com.school.miniinter.models.Class.Class" %>
<%@ page import="com.school.miniinter.models.Students.GradeForStudent" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    List<Class> listClass = (List<Class>) request.getAttribute("listClass");
    List<GradeForStudent> listGradeByStudent = (List<GradeForStudent>)
            request.getAttribute("listGradeByStudent");
%>
<html>
<head>
    <title>Vidya - Atualizar notas por aluno</title>
</head>
<body>
    <form action="<%=request.getContextPath()%>/changeSubject" method="post">
        <label for="subject"></label>
        <select name="subject" id="subject">
            <%
                for (Subject subject : subjects) {
            %>
            <option value="<%=subject.getId()%>"><%=subject.getName()%></option>
            <%
                }
            %>
        </select>
        <input value="Trocar matéria" type="submit">
    </form>
</body>
</html>
