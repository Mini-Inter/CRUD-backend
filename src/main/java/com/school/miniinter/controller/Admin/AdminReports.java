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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Object admin = session.getAttribute("admin");
        String type = "noot noot";
        if (req.getParameter("type") != null) {
            type = req.getParameter("type");
        }

        if (admin == null) {
            resp.sendRedirect(req.getContextPath()+"/Inicio/loginaa.jsp");
        } else {
            switch (type) {
                case ("edit") -> {
                    editReport(req, resp);
                }
                case ("create") -> {
                    createReport(req,resp);
                }
                default -> {
                    showReports(req, resp);
                }
            }
        }
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Object admin = session.getAttribute("admin");
        String type = "noot noot";
        if (req.getParameter("type") != null) {
            type = req.getParameter("type");
        }

        if (admin == null) {
            resp.sendRedirect(req.getContextPath()+"/Inicio/loginaa.jsp");
        } else {
            switch (type) {
                case ("insert") -> {
                    insertReport(req,resp);
                }
                case ("update") -> {
                    updateReport(req, resp);
                }
                case ("delete") -> {
                    deleteReport(req, resp);
                }
                default -> {
                    showReports(req, resp);
                }
            }
        }
    }

    // Métodos de redirecionamento
    private void showReports(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<CompleteReport> reports = rep.readCompleteReport();
        HttpSession session = req.getSession();
        session.setAttribute("reports", reports);

        req.getRequestDispatcher("WEB-INF/admin/report/reports.jsp").forward(req, resp);
    }
    private void editReport(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer id_report = Integer.parseInt(req.getParameter("report"));
        Reports report = rep.read(id_report);
        HttpSession session = req.getSession();
        session.setAttribute("report", report);

        StudentsDAO studentsDAO = new StudentsDAO();
        List<Students> students = studentsDAO.readAll();
        session.setAttribute("students",students);

        TeachersDAO teachersDAO = new TeachersDAO();
        List<Teacher> teachers = teachersDAO.read();
        session.setAttribute("teachers",teachers);

        String[] students_report = rep.readCompleteReport(report.getId()).getStudents();
        session.setAttribute("students_report", students_report);

        req.getRequestDispatcher("WEB-INF/admin/report/reportEdit.jsp").forward(req, resp);
    }
    private void createReport(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        TeachersDAO te = new TeachersDAO();
        List<Teacher> listTeachers =  te.read();

        req.setAttribute("listTeachers",listTeachers);

        StudentsDAO stud = new StudentsDAO();
        List<Students> listStudents = stud.readAll();

        req.setAttribute("listStudents",listStudents);

        req.getRequestDispatcher("WEB-INF/admin/report/reportInsert.jsp").forward(req,resp);

    }

    // Métodos de acesso ao banco
    private void insertReport(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        ReceiveDAO receiveDAO = new ReceiveDAO();
        try{
            String[] fk_students = req.getParameterValues("students");
            int[] fk_students_int = new int[fk_students.length];
            if(fk_students != null){
                for(int i = 0; i<fk_students.length; i++){
                    fk_students_int[i] = Integer.parseInt(fk_students[i]);
                }
            }
            Integer fk_teacher = Integer.parseInt(req.getParameter("teacher"));
            String description = req.getParameter("description");
            String type = req.getParameter("classification");
            Reports insertReport = new Reports(fk_teacher,
                    description,type);
            if (!rep.insert(insertReport)) {
                Integer id_report = rep.readIdByDescriptionAndTeacher(description, fk_teacher);
                Receive receive = new Receive();
                receive.setFk_report(id_report);
                receive.setFk_students(fk_students_int);
                if(!receiveDAO.insert(receive)) {
                    HttpSession session = req.getSession();
                    session.setAttribute("success", "Dados de observação criados " +
                            "com sucesso!");
                }
                else {
                    HttpSession session = req.getSession();
                    session.setAttribute("error", "Ocorreu um erro criar a " +
                            "observação, tente novamente!");
                }
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
            req.getRequestDispatcher("adminReports?type=create").forward(req, resp);
        }
    }
    private void updateReport(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Integer id_report = Integer.parseInt(req.getParameter("report"));
            Reports report = rep.read(id_report);
            String[] students_before = rep.readCompleteReport(id_report).getStudents();
            String[] students_before_id = new String[students_before.length];
            StudentsDAO stud = new StudentsDAO();
            for(int i = 0; i<students_before.length; i++) {
                students_before_id[i] = String.valueOf(stud.readIdByName(students_before[i]));
            }

            String type = req.getParameter("typeReport");
            String desc =  req.getParameter("description");
            String[] students = req.getParameterValues("students");
            Integer teacher = Integer.parseInt(req.getParameter("teacher"));

            if (!desc.equals(report.getDescription())) {
                report.setDescription(desc);
            }
            if (!type.equals(report.getType())) {
                report.setType(type);
            }
            if (!students.equals(students_before_id)) {
                report.setFk_students(students);
            }
            if(teacher != report.getFk_teachers()){
                report.setFk_teachers(teacher);
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
            req.getRequestDispatcher("adminReports?type=edit").forward(req, resp);
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
            req.getRequestDispatcher("adminReports?type=edit").forward(req, resp);
        } catch (UnavailableException exc) {
            HttpSession session = req.getSession();
            session.setAttribute("error", exc.getMessage());
            req.getRequestDispatcher("adminReports?type=edit").forward(req, resp);
        }
    }
}
