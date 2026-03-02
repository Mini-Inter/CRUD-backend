package com.school.miniinter.controller.authenticationSystem;

import jakarta.servlet.Servlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.rmi.ServerException;

@WebServlet(name="logout", value={"/logout"})
public class logout extends HttpServlet {

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
            IOException{

        HttpSession session = request.getSession();
        String type = request.getParameter("type");

        switch (type){
            case("student")->{
                Object idStudentRaw = session.getAttribute("idStudent");
                if(idStudentRaw == null){
                    response.sendRedirect( request.getContextPath()+"/Inicio/login" +
                            ".jsp");
                }
                session.setAttribute("idStudent",null);
                response.sendRedirect( request.getContextPath()+"/Inicio/login" +
                        ".jsp");
            }
            default -> {
                Object idTeacherRaw = session.getAttribute("idTeacher");
                if(idTeacherRaw == null){
                    response.sendRedirect( request.getContextPath()+"/Inicio/login" +
                            ".jsp");
                }
                session.setAttribute("idTeacher",null);
                response.sendRedirect( request.getContextPath()+"/Inicio/login" +
                        ".jsp");
            }
        }
    }
}
