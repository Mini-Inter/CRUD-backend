package com.school.miniinter.controller.Student;

import com.school.miniinter.dao.SubjectsDAO;
import com.school.miniinter.models.Subject.Subject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet(name="Subjects", urlPatterns = "/studentSubjects")
public class Subjects extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Object idStudentRaw = session.getAttribute("idStudent");

        if (idStudentRaw == null) {
            resp.sendRedirect(req.getContextPath()+"/authentication/login.jsp");
        } else {
            int idStudent = (Integer) idStudentRaw;

            SubjectsDAO subDAO = new SubjectsDAO();
            List<Subject> subjects = subDAO.readByStudent(idStudent);

            req.setAttribute("subjects", subjects);
            req.getRequestDispatcher("/WEB-INF/student/studentSubjects.jsp").forward(req, resp);
        }
    }
}
