<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<form action="<%=request.getContextPath()%>/adminClasses" method="post"><input value="Turmas" type="submit"></form>
<form action="<%=request.getContextPath()%>/adminStudents" method="post"><input value="Alunos" type="submit"></form>
<form action="<%=request.getContextPath()%>/adminGuardians" method="post"><input value="Responsáveis" type="submit"></form>
<form action="<%=request.getContextPath()%>/adminReports" method="post"><input value="Observações" type="submit"></form>
<form action="<%=request.getContextPath()%>/adminSubjects" method="post"><input value="Matérias" type="submit"></form>
<form action="<%=request.getContextPath()%>/adminTeachers" method="post"><input value="Professores" type="submit"></form>