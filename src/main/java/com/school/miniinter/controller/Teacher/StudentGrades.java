package com.school.miniinter.controller.Teacher;

import com.school.miniinter.dao.StudentsDAO;
import com.school.miniinter.dao.TeachersDAO;
import com.school.miniinter.models.Grades.GradeForSubject;
import com.school.miniinter.models.Students.Summary;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "StudentGrades", urlPatterns = "/studentGrades")
public class StudentGrades extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Object idTeacherRaw = session.getAttribute("idTeacher");
        String idStudentRaw = req.getParameter("student");

        if (idTeacherRaw == null || idStudentRaw == null || idStudentRaw.isBlank() || idStudentRaw.equals("null")) {
            resp.sendRedirect(req.getContextPath()+"/authentication/login.jsp");
        } else {
            int idStudent = Integer.parseInt(idStudentRaw);
            StudentsDAO stud = new StudentsDAO();
            TeachersDAO teach = new TeachersDAO();
            Summary student = stud.readSummary(idStudent);
            List<GradeForSubject> grades = teach.readGradesByStudent(idStudent);
            session.setAttribute("student", student);
            session.setAttribute("grades", grades);
            req.getRequestDispatcher("/WEB-INF/studentGrades.jsp").forward(req, resp);
        }
    }
}
