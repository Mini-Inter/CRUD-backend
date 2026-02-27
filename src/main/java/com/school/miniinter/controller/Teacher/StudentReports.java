package com.school.miniinter.controller.Teacher;

import com.school.miniinter.dao.ReportsDAO;
import com.school.miniinter.dao.StudentsDAO;
import com.school.miniinter.models.Reports.CompleteInformationReport;
import com.school.miniinter.models.Students.Summary;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "studentReports",  urlPatterns = "/studentReports")
public class StudentReports extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String idStudentRaw = req.getParameter("student");

        if (idStudentRaw == null || idStudentRaw.isBlank() || idStudentRaw.equals("null")) {
            resp.sendRedirect(req.getContextPath()+"/authentication/login.jsp");
        } else {
            ReportsDAO rep = new ReportsDAO();

            int idStudent = Integer.parseInt(idStudentRaw);
            List<CompleteInformationReport> reports = rep.readAllCompleteInfoReport(idStudent);
            session.setAttribute("reports", reports);

            StudentsDAO stud = new StudentsDAO();
            Summary student = stud.readSummary(idStudent);
            session.setAttribute("student", student);

            req.getRequestDispatcher("/WEB-INF/Teacher/studentReports.jsp").forward(req, resp);
        }
    }
}
