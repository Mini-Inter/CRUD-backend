package com.school.miniinter.controller.Admin;

import com.school.miniinter.dao.ReceiveDAO;
import com.school.miniinter.dao.ReportsDAO;
import com.school.miniinter.dao.StudentsDAO;
import com.school.miniinter.dao.TeachersDAO;
import com.school.miniinter.models.Receive.Receive;
import com.school.miniinter.models.Reports.CompleteReport;
import com.school.miniinter.models.Reports.Reports;
import com.school.miniinter.models.Students.Students;
import com.school.miniinter.models.Teacher.Teacher;
import jakarta.servlet.ServletException;
import jakarta.servlet.UnavailableException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Date;
import java.util.List;
@WebServlet(name="adminReports", urlPatterns = "/adminReports")
public class AdminReports extends HttpServlet {
    ReportsDAO rep = new ReportsDAO();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Object admin = session.getAttribute("admin");
        String type = "noot noot";
        if (req.getParameter("type") != null) {
            type = req.getParameter("type");
        }

        if (admin == null) {
            resp.sendRedirect(req.getContextPath()+"/authentication/loginaa.jsp");
        } else {
            switch (type) {
                case ("showReport") -> {
                    showReport(req, resp);
                }
                case ("editReport") -> {
                    editReport(req, resp);
                }
                case ("createReport") -> {
                    createReport(req,resp);
                }
                case ("insertReport") -> {
                    insertReport(req,resp);
                }
                case ("updateReport") -> {
                    updateReport(req, resp);
                }
                case ("deleteReport") -> {
                    deleteReport(req, resp);
                }
                default -> {
                    showReports(req, resp);
                }
            }
        }
    }
    private void showReport(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CompleteReport report = rep.readCompleteReport(Integer.parseInt(req.getParameter("report")));
        HttpSession session = req.getSession();
        session.setAttribute("report", report);

        req.getRequestDispatcher("WEB-INF/admin/reportShow.jsp").forward(req, resp);
    }
    private void showReports(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<CompleteReport> reports = rep.readCompleteReport();
        HttpSession session = req.getSession();
        session.setAttribute("reports", reports);

        req.getRequestDispatcher("WEB-INF/admin/reports.jsp").forward(req, resp);
    }
    private void editReport(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Reports report = rep.read(Integer.parseInt(req.getParameter("report")));
        HttpSession session = req.getSession();
        session.setAttribute("report", report);

        String[] students_report = rep.readCompleteReport(report.getId()).getStudents();
        session.setAttribute("students_report", students_report);

        req.getRequestDispatcher("WEB-INF/admin/reportEdit.jsp").forward(req, resp);
    }
    private void createReport(HttpServletRequest req,
                              HttpServletResponse resp) throws ServletException, IOException{
        TeachersDAO te = new TeachersDAO();
        List<Teacher> listTeachers =  te.read();

        req.setAttribute("listTeachers",listTeachers);

        StudentsDAO stud = new StudentsDAO();
        List<Students> listStudents = stud.readAll();

        req.setAttribute("listStudents",listStudents);

        req.getRequestDispatcher("WEB-INF/admin/reportInsert.jsp").forward(req,resp);

    }
    private void insertReport(HttpServletRequest req,
                              HttpServletResponse resp) throws ServletException, IOException{
        ReceiveDAO receiveDAO = new ReceiveDAO();
        try{
            Integer fk_student = Integer.parseInt(req.getParameter("student"));
            Integer fk_teacher = Integer.parseInt(req.getParameter("teacher"));
            String description = req.getParameter("description");
            String type = req.getParameter("type");
            Reports insertReport = new Reports(fk_teacher,
                    description,type);
            Integer id_report = rep.readIdByDescription(description);
            Receive receive = new Receive(fk_student,id_report);
            if (rep.insert(insertReport) && receiveDAO.insert(receive)) {
                HttpSession session = req.getSession();
                session.setAttribute("success", "Dados de observação criados " +
                        "com sucesso!");
            } else {
                HttpSession session = req.getSession();
                session.setAttribute("error", "Ocorreu um erro criar a " +
                        "observação, tente novamente!");
            }

            req.getRequestDispatcher("/adminReports?type=noot").forward(req,
                    resp);
        }catch (NullPointerException exc) {
            HttpSession session = req.getSession();
            session.setAttribute("error", "Alguns dados não foram preenchidos!");
            req.getRequestDispatcher("adminReports?type=createReport").forward(req, resp);
        }
    }
    private void updateReport(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Reports report = rep.read(Integer.parseInt(req.getParameter("report")));

            String type = req.getParameter("typeReport");
            String desc =  req.getParameter("description");
            String[] students = req.getParameterValues("students");

            if (!desc.equals(report.getDescription())) {
                report.setDescription(desc);
            }
            if (!type.equals(report.getType())) {
                report.setType(type);
            }
            if (!students.equals(report.getFk_students())) {
                report.setFk_students(students);
            }

            if (rep.update(report)) {
                HttpSession session = req.getSession();
                session.setAttribute("success", "Observação alterada com sucesso!");
            } else {
                HttpSession session = req.getSession();
                session.setAttribute("error", "Observação não foi alterada!");
            }

            req.getRequestDispatcher("/adminReports?type=noot").forward(req, resp);
        } catch (NullPointerException exc) {
            HttpSession session = req.getSession();
            session.setAttribute("error", "Alguns dados não foram preenchidos!");
            req.getRequestDispatcher("adminReports?type=editReports").forward(req, resp);
        }
    }
    private void deleteReport(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {

            Reports report = rep.read(Integer.parseInt(req.getParameter("report")));

            if (rep.delete(report.getId())) {
                req.getRequestDispatcher("/adminReports?type=noot").forward(req, resp);

                HttpSession session = req.getSession();
                session.setAttribute("success", "Observação deletada com sucesso!");
            }
            else
                throw new UnavailableException("Observação não pôde ser deletada...");
        } catch (NullPointerException exc) {
            HttpSession session = req.getSession();
            session.setAttribute("error", "Alguns dados não foram preenchidos!");
            req.getRequestDispatcher("adminReports?type=editReports").forward(req, resp);
        } catch (UnavailableException exc) {
            HttpSession session = req.getSession();
            session.setAttribute("error", exc.getMessage());
            req.getRequestDispatcher("adminReports?type=editReports").forward(req, resp);
        }
    }
}
