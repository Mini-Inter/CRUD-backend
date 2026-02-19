package com.school.miniinter.controller.Student;

import com.school.miniinter.dao.GradeDAO;
import com.school.miniinter.models.Grades.GradeForSubject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name="GradeCard", value = {"/gradeCard"})
public class GradeCard extends HttpServlet {

    GradeDAO gradeDAO = new GradeDAO();
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
            IOException{
        int id_student = Integer.parseInt(request.getParameter("idStudent"));

        request.setAttribute("idStudent",id_student);

        List<GradeForSubject> gradeCard =
                gradeDAO.readAllGradesForStudent(id_student);

        request.setAttribute("GradeCard",gradeCard);

//        Essa caminho deve ser mudado quando chegar o frontend
        request.getRequestDispatcher("WEB-INF/gradeCard.jsp").forward(request,
                response);
    }
}
