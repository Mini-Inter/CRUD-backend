package com.school.miniinter.controller.Student;

import com.school.miniinter.dao.StudentsDAO;
import com.school.miniinter.models.Students.BasicInfo;
import com.school.miniinter.models.Students.Students;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name="home", value = {"/homeStudent"})
public class home extends HttpServlet {

    StudentsDAO studentsDAO = new StudentsDAO();
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
            IOException{
        String email = String.valueOf(request.getParameter("email"));

        BasicInfo basicInfo = studentsDAO.readBasicInfoStudent(email);

        request.setAttribute("basicInfo",basicInfo);

        Integer id_student = basicInfo.getId_student();

        Integer amountSubjects =
                studentsDAO.readAmountOfSubjects(id_student);

        request.setAttribute("amountSubjects",amountSubjects);

        Double avgGrade =
                studentsDAO.readAverageGrade(id_student);

        request.setAttribute("avgGrade", avgGrade);

        Integer amountReports = studentsDAO.readAmountReports(id_student);

        request.setAttribute("amountReports",amountReports);

//        Ainda tem que mudar esse caminho quando o fluxo estiver completo
        request.getRequestDispatcher("WEB-INF/homeAluno.jsp").forward(request,
                response);
    }
}
