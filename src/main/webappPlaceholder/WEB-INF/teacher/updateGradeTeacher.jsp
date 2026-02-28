<%@ page import="java.util.List" %>
<%@ page import="com.school.miniinter.models.Class.Class" %>
<%@ page import="com.school.miniinter.models.Students.GradeForStudent" %>
<%@ page import="com.school.miniinter.models.Subject.Subject" %>
<%@ page import="java.util.LinkedList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    List<Class> listClass = (List<Class>) request.getAttribute("listClass");
    List<GradeForStudent> listGradeByStudent = (List<GradeForStudent>)
            request.getAttribute("listGradeByStudent");
    LinkedList<Subject> subjects = (LinkedList<Subject>)
            session.getAttribute("subjects");
    Integer idSubject = (Integer) session.getAttribute("subject");
%>
<html>
<head>
    <title>Vidya - Atualizar notas por aluno</title>
</head>
<body>
    <form action="${pageContext.request.contextPath}/changeSubject" method="post">
        <label for="subject"></label>
        <select name="subject" id="subject">
            <%
                for (Subject subject : subjects) {
                    if (subject.getId()==idSubject) {%>
            <option selected="selected" value="<%=subject.getId()%>"><%=subject.getName()%></option>
                    <%}else{%>
            <option value="<%=subject.getId()%>"><%=subject.getName()%></option>
            <%
                }}
            %>
        </select>
        <input value="Trocar matéria" type="submit">
    </form>
    <table>
        <tr>
            <th>Nome completo</th>
            <th colspan="2">N1</th>
            <th colspan="2">N2</th>
            <th>Média final</th>
        </tr>
        <%
            for(GradeForStudent g: listGradeByStudent){
        %>
        <tr>
            <td><%=g.getFull_name()%></td>
            <form
                    action="<%if(g.getN1().equals(-1.0)){%>addData?idStudent=<%=g.getId_student()%>&n=1&class=<%=request.getAttribute("id_class")%><%}else{%>updateData?idGrade=<%=g.getIdN1()%>&class=<%=request.getAttribute("id_class")%>&idData=<%=g.getIdN1()%><%}%>" method="post">
                <td><input type="text" value="<%if(g.getN1()== -1.0){%>-
                    <%}else{%><%=g.getN1()%><%}%>" name="data"></td>
                <td><input type="submit" value="Salvar"></td>
            </form>
            <form
                    action="<%if(g.getN2().equals(-1.0)){%>addData?idStudent=<%=g.getId_student()%>&n=2&class=<%=request.getAttribute("id_class")%><%}else{%>updateData?idGrade=<%=g.getIdN2()%>&class=<%=request.getAttribute("id_class")%>&idData=<%=g.getIdN2()%><%}%>" method="post">
                <td><input type="text" value="<%if(g.getN2()== -1.0){%>-
                    <%}else{%><%=g.getN2()%><%}%>" name="data"></td>
                <td><input type="submit" value="Salvar"></td>
            </form>
                <td><%if(g.getAverage()== null){%>
                    <p>-</p>
                    <%}else{%><%=g.getAverage()%><%}%></td>
        </tr>
        <%
            }
        %>
    </table>
    <form action="${pageContext.request.contextPath}/updateGrade?changeClass=1"
          method="post">
        <label for="class"></label>
        <select name="class" id="class">
            <%
                for(Class c:listClass){
            %>
            <option value="<%=c.getId()%>"><%=c%></option>
            <%
                }
            %>
        </select>
        <input value="Trocas Classe" type="submit">
    </form>
</body>
</html>
