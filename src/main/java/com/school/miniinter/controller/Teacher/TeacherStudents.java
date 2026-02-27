package com.school.miniinter.controller.Teacher;

import com.school.miniinter.dao.StudentsDAO;
import com.school.miniinter.models.Students.Students;
import com.school.miniinter.models.Students.Summary;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(name = "Students", urlPatterns = "/teacherStudents")
public class TeacherStudents extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Object idTeacherRaw = session.getAttribute("idTeacher");

        if (idTeacherRaw == null) {
            resp.sendRedirect(req.getContextPath()+"/authentication/login.jsp");
        } else {
            int idTeacher = (Integer) idTeacherRaw;
            List<Summary> summaries = new LinkedList<>();
            int idSubject = (Integer) session.getAttribute("subject");
            String idStudentRaw = req.getParameter("matricula");

            StudentsDAO stud = new StudentsDAO();
            List<Students> students = stud.readByTeach(idTeacher, idSubject);
            if (idStudentRaw != null && !idStudentRaw.isBlank()) {
                int idStudent = Integer.parseInt(idStudentRaw);
                students = filterById(students, idStudent);
            }
            for (Students student : students)
                summaries.add(stud.readSummary(student.getId_student(), idSubject));
            session.setAttribute("students", summaries);

            req.getRequestDispatcher("/WEB-INF/Teacher/studentsTeacher.jsp").forward(req, resp);
        }
    }

    private List<Students> filterById(List<Students> students, int id) {
        List<Students> newStudents = new LinkedList<>();
        Pattern mat = Pattern.compile(String.valueOf(id));
        Matcher match;
        for (Students student : students) {
            match = mat.matcher(String.valueOf(student.getId_student()));
            if (match.find()) {
                newStudents.add(student);
            }
        }
        return newStudents;
    }
}
