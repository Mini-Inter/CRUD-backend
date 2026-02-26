package com.school.miniinter.controller.Student;

import com.school.miniinter.dao.StudentsDAO;
import com.school.miniinter.models.Students.CompleteInfo;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name="PerfilEstudante", value={"/profileStudent"})
public class ProfileStudent extends HttpServlet {

    StudentsDAO studentsDAO = new StudentsDAO();
    public void doGet(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
            IOException{
        HttpSession session = request.getSession();
        Integer id_student = (Integer) session.getAttribute("idStudent");
        System.out.println(id_student);

        CompleteInfo completeInfo =
                studentsDAO.readCompleteInfoStudent(id_student);

        request.setAttribute("completeInfoStudent",completeInfo);

        request.getRequestDispatcher("WEB-INF/Student/studentProfile.jsp").forward(request,response);
    }
}
