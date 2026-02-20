package com.school.miniinter.controller.Teacher;

import com.school.miniinter.dao.StudentsDAO;
import com.school.miniinter.dao.SubjectsDAO;
import com.school.miniinter.models.Students.Students;
import com.school.miniinter.models.Students.Summary;
import com.school.miniinter.models.Subject.Subject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@WebServlet(name = "Students", urlPatterns = "/teacherStudents")
public class TeacherStudents extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Object idTeacherRaw = session.getAttribute("idTeacher");

        if (idTeacherRaw == null) {
            resp.sendRedirect(req.getContextPath()+"/authentication/login.jsp");
        } else {
            int idTeacher = (Integer) idTeacherRaw;
            List<Summary> summaries = new LinkedList<>();
            List<Subject> subjects;
            int idSubject;

            SubjectsDAO subDAO = new SubjectsDAO();
            subjects = subDAO.readByTeacher(idTeacher);
            session.setAttribute("subjects", subjects);

            if (session.getAttribute("subject") == null) idSubject = subjects.get(0).getId();
            else idSubject = (Integer) session.getAttribute("subject");
            StudentsDAO stud = new StudentsDAO();
            List<Students> students = stud.readByTeach(idTeacher, idSubject);
            for (Students student : students)
                summaries.add(stud.readSummary(student.getId_student()));
            session.setAttribute("students", summaries);

            req.getRequestDispatcher("/WEB-INF/teacherStudents.jsp").forward(req, resp);
        }
    }
}
