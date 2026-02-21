package com.school.miniinter.controller.Teacher;

import com.school.miniinter.dao.ReportsDAO;
import com.school.miniinter.dao.StudentsDAO;
import com.school.miniinter.models.Reports.CompleteInformationReport;
import com.school.miniinter.models.Reports.Reports;
import com.school.miniinter.models.Students.Students;
import com.school.miniinter.models.Students.Summary;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@WebServlet(name = "reports", urlPatterns = "/teacherReports")
public class TeacherReports extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Object idTeacherRaw = session.getAttribute("idTeacher");
        Object subject = session.getAttribute("subject");

        if (idTeacherRaw == null || subject == null) {
            resp.sendRedirect(req.getContextPath()+"/authentication/login.jsp");
        } else {
            int idTeacher = (Integer) idTeacherRaw;
            int idSubject = (Integer) subject;
            String type = "noot noot";
            if (req.getParameter("type") != null) {
                type = req.getParameter("type");
            }
            switch (type) {
                case ("showReports") -> {
                    showReports(req, resp, idTeacher);
                }
                case ("createReport") -> {
                    createReport(req, resp, idTeacher);
                }
                default -> {
                    showCreation(req, resp, idTeacher, idSubject);
                }
            }
        }
    }

    protected void showReports(HttpServletRequest req, HttpServletResponse resp, int idTeacher) throws ServletException, IOException {
        ReportsDAO rep = new ReportsDAO();
        List<CompleteInformationReport> reports = rep.readReportsByTeacher(idTeacher);

        HttpSession session = req.getSession();
        session.setAttribute("reports", reports);

        req.getRequestDispatcher("WEB-INF/teacher/teacherReports.jsp").forward(req, resp);
    }

    protected void showCreation(HttpServletRequest req, HttpServletResponse resp, int idTeacher, int subject) throws ServletException, IOException {
        StudentsDAO stud = new StudentsDAO();
        List<Students> students = stud.readByTeach(idTeacher, subject);

        HttpSession session = req.getSession();
        session.setAttribute("students", students);

        req.getRequestDispatcher("WEB-INF/teacher/createReport.jsp").forward(req, resp);
    }

    protected void createReport(HttpServletRequest req, HttpServletResponse resp, int idTeacher) throws ServletException, IOException {
        String type = req.getParameter("type");
        String description = req.getParameter("description");
        Date sendAt = Date.valueOf(LocalDate.now());
        Reports report = new Reports(idTeacher, type, description, sendAt);
        ReportsDAO rep = new ReportsDAO();
        rep.createReport(report);
    }
}
