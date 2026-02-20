package com.school.miniinter.controller.Teacher;

import com.school.miniinter.dao.TeachersDAO;
import com.school.miniinter.models.Teacher.HomeTeacherInfo;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


import java.io.IOException;
import java.rmi.ServerException;

@WebServlet(name="HomeTeacher", value={"/homeTeacher"})
public class HomeTeacher extends HttpServlet {

    TeachersDAO teachersDAO = new TeachersDAO();
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
            IOException{

        HttpSession session =  request.getSession();
        Object idTeacherRaw = session.getAttribute("idTeacher");

        if(idTeacherRaw == null){
            response.sendRedirect( request.getContextPath()+"/authentication/login.jsp");
        }else{
            int id_teacher = Integer.parseInt(String.valueOf(idTeacherRaw));
            HomeTeacherInfo homeTeacherInfo =
                    teachersDAO.readHomeInfoTeacher(id_teacher);

            request.setAttribute("homeTeacherInfo",homeTeacherInfo);

            request.getRequestDispatcher("WEB-INF/homeProfessor.jsp").forward(request,response);
        }
    }
}
