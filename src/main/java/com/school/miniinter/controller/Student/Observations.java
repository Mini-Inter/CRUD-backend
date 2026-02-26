package com.school.miniinter.controller.Student;

import com.school.miniinter.dao.ReportsDAO;
import com.school.miniinter.models.Reports.CompleteInformationReport;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet(name="ObservacoesEstudante",value = {"/observations"})
public class Observations extends HttpServlet {

    ReportsDAO reportsDAO = new ReportsDAO();

    public void doGet(HttpServletRequest request,
                     HttpServletResponse response) throws ServletException, IOException{
        HttpSession session = request.getSession();
        Object idStudentRaw = session.getAttribute("idStudent");

        if (idStudentRaw == null) {
            response.sendRedirect(request.getContextPath()+"/Inicio/login.jsp");
        } else {
            int idStudent = (Integer) idStudentRaw;
            String type = request.getParameter("filter");

            if (type == null) {
                readAllReports(idStudent, request, response);
            } else {
                readReportsByType(idStudent, type, request, response);
            }
        }
    }

    public void readAllReports(int id_student, HttpServletRequest request,
                               HttpServletResponse response) throws ServletException, IOException{
        List<CompleteInformationReport> list =
                reportsDAO.readAllCompleteInfoReport(id_student);

        request.setAttribute("List",list);

        request.getRequestDispatcher("WEB-INF/student/studentObservations" +
                ".jsp").forward(request,
                response);
    }

    public void readReportsByType(int id_student,
                                  String type, HttpServletRequest request,
                           HttpServletResponse response) throws ServletException, IOException{
        List<CompleteInformationReport> list =
                reportsDAO.readCompleteInfoReportByType(id_student,type);
        request.setAttribute("List",list);

        request.getRequestDispatcher("WEB-INF/Student" +
                "/studentObservations.jsp").forward(request,
                response);
    }
}
