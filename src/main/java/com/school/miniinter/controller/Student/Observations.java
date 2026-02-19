package com.school.miniinter.controller.Student;

import com.school.miniinter.dao.ReportsDAO;
import com.school.miniinter.models.Reports.CompleteInformationReport;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name="Observations",urlPatterns = {"/observations"})
public class Observations extends HttpServlet {

    ReportsDAO reportsDAO = new ReportsDAO();

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
            IOException{
        String path = request.getRequestURI();
        int id_student = Integer.parseInt(request.getParameter(
                "idStudent"));
        String type = request.getParameter("filter");

        if(type == null){
            readAllReports(id_student,request, response);
        } else{
            readReportsByType(id_student,type,request,response);
        }
    }

    public void readAllReports(int id_student, HttpServletRequest request,
                               HttpServletResponse response) throws ServletException, IOException{
        List<CompleteInformationReport> list =
                reportsDAO.readAllCompleteInfoReport(id_student);

        request.setAttribute("List",list);

        request.getRequestDispatcher("/AllObservations").forward(request,
                response);
    }

    public void readReportsByType(int id_student,
                                  String type, HttpServletRequest request,
                           HttpServletResponse response){
        reportsDAO.readCompleteInfoReportByType(id_student,type);
    }
}
