package com.school.miniinter.controller.Admin;

import com.school.miniinter.dao.ReportsDAO;
import com.school.miniinter.models.Reports.CompleteReport;
import com.school.miniinter.models.Reports.Reports;
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
        ReportsDAO rep = new ReportsDAO();
        CompleteReport report = rep.readCompleteReport(Integer.parseInt(req.getParameter("report")));
        HttpSession session = req.getSession();
        session.setAttribute("report", report);

        req.getRequestDispatcher("WEB-INF/admin/reportShow.jsp").forward(req, resp);
    }
    private void showReports(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ReportsDAO rep = new ReportsDAO();
        List<CompleteReport> reports = rep.readCompleteReport();
        HttpSession session = req.getSession();
        session.setAttribute("reports", reports);

        req.getRequestDispatcher("WEB-INF/admin/reports.jsp").forward(req, resp);
    }
    private void editReport(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ReportsDAO rep = new ReportsDAO();
        Reports report = rep.read(Integer.parseInt(req.getParameter("report")));
        HttpSession session = req.getSession();
        session.setAttribute("report", report);

        req.getRequestDispatcher("WEB-INF/admin/reportEdit.jsp").forward(req, resp);
    }
    private void updateReport(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            ReportsDAO rep = new ReportsDAO();
            Reports report = rep.read(Integer.parseInt(req.getParameter("report")));

            String type = req.getParameter("type");
            String desc =  req.getParameter("description");

            if (!desc.equals(report.getDescription())) {
                report.setDescription(desc);
            }
            if (!type.equals(report.getType())) {
                report.setType(type);
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
            ReportsDAO rep = new ReportsDAO();
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
