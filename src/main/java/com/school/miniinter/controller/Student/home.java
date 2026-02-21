package com.school.miniinter.controller.Student;

import com.school.miniinter.dao.StudentsDAO;
import com.school.miniinter.models.Students.BasicInfo;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name="home", value = {"/homeStudent"})
public class home extends HttpServlet {

    StudentsDAO studentsDAO = new StudentsDAO();
    public void doPost(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
            IOException{
        HttpSession session =  request.getSession();
        Object idStudentRaw = session.getAttribute("idStudent");

        if (idStudentRaw == null) {
            response.sendRedirect( request.getContextPath()+"/authentication/login.jsp");
        } else {
            int idStudent = (Integer) idStudentRaw;

            String email = String.valueOf(session.getAttribute("email"));

            BasicInfo basicInfo = studentsDAO.readBasicInfoStudent(email);

            request.setAttribute("basicInfo", basicInfo);

            Integer amountSubjects =
                    studentsDAO.readAmountOfSubjects(idStudent);

            request.setAttribute("amountSubjects", amountSubjects);

            Double avgGrade =
                    studentsDAO.readAverageGrade(idStudent);

            request.setAttribute("avgGrade", avgGrade);

            Integer amountReports = studentsDAO.readAmountReports(idStudent);

            request.setAttribute("amountReports", amountReports);

//        Ainda tem que mudar esse caminho quando o fluxo estiver completo
            request.getRequestDispatcher("WEB-INF/student/homeAluno.jsp").forward(request, response);
        }
    }
}
