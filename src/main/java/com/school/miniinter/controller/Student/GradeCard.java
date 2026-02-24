package com.school.miniinter.controller.Student;

import com.school.miniinter.dao.GradeDAO;
import com.school.miniinter.models.Grades.GradeForSubject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet(name="BoletimEstudante", value = {"/gradeCard"})
public class GradeCard extends HttpServlet {

    GradeDAO gradeDAO = new GradeDAO();
    public void doPost(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
            IOException{
        HttpSession session = request.getSession();
        Object idStudentRaw = session.getAttribute("idStudent");

        if (idStudentRaw == null) {
            response.sendRedirect(request.getContextPath()+"/authentication/login.jsp");
        } else {
            int idStudent = (Integer) idStudentRaw;

            List<GradeForSubject> gradeCard =
                    gradeDAO.readAllGradesForStudent(idStudent);

            request.setAttribute("GradeCard", gradeCard);

//        Essa caminho deve ser mudado quando chegar o frontend
            request.getRequestDispatcher("WEB-INF/student/studentGradeCard" +
                    ".jsp").forward(request,
                    response);
        }
    }
}
