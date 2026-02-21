package com.school.miniinter.controller.Admin;

import com.school.miniinter.dao.SubjectsDAO;
import com.school.miniinter.models.Subject.Subject;
import jakarta.servlet.ServletException;
import jakarta.servlet.UnavailableException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet(name="adminSubjects", urlPatterns = "/adminSubjects")
public class AdminSubjects extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Object idAdmin = session.getAttribute("idAdmin");
        String type = "noot noot";
        if (req.getParameter("type") != null) {
            type = req.getParameter("type");
        }

        if (idAdmin == null) {
            resp.sendRedirect(req.getContextPath()+"/authentication/loginaa.jsp");
        } else {
            switch (type) {
                case ("showSubject") -> {
                    showSubject(req, resp);
                }
                case ("editSubject") -> {
                    editSubject(req, resp);
                }
                case ("updateSubject") -> {
                    updateSubject(req, resp);
                }
                case ("deleteSubject") -> {
                    deleteSubject(req, resp);
                }
                default -> {
                    showSubjects(req, resp);
                }
            }
        }
    }

    private void showSubject(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SubjectsDAO sub = new SubjectsDAO();
        Subject subject = sub.read((Integer) req.getAttribute("subject"));
        HttpSession session = req.getSession();
        session.setAttribute("subject", subject);

        req.getRequestDispatcher("WEB-INF/admin/subjectShow.jsp");
    }

    private void showSubjects(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SubjectsDAO sub = new SubjectsDAO();
        List<Subject> subjects = sub.read();
        HttpSession session = req.getSession();
        session.setAttribute("subjects", subjects);

        req.getRequestDispatcher("WEB-INF/admin/subjects.jsp");
    }

    private void editSubject(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SubjectsDAO sub = new SubjectsDAO();
        Subject subject = sub.read(Integer.parseInt(req.getParameter("subject")));
        HttpSession session = req.getSession();
        session.setAttribute("subject", subject);

        req.getRequestDispatcher("WEB-INF/admin/subjectEdit.jsp").forward(req, resp);
    }

    private void updateSubject(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            SubjectsDAO sub = new SubjectsDAO();
            Subject subject = sub.read(Integer.parseInt(req.getParameter("subject")));

            String nome = req.getParameter("nome");
            String descricao = req.getParameter("description");

            if (!nome.equals(subject.getName())) {
                subject.setName(nome);
            }
            if (!descricao.equals(subject.getDescription())) {
                subject.setDescription(descricao);
            }

            sub.update(subject);

            req.getRequestDispatcher("/adminSubjects").forward(req, resp);

            HttpSession session = req.getSession();
            session.setAttribute("success", "Dados do professor" + subject.getName() + "alterados com sucesso!");
        } catch (NullPointerException exc) {
            HttpSession session = req.getSession();
            session.setAttribute("error", "Alguns dados não foram preenchidos!");
            req.getRequestDispatcher("adminSubjects?type=editSubject").forward(req, resp);
        }
    }

    private void deleteSubject(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            SubjectsDAO sub = new SubjectsDAO();
            Subject subject = sub.read(Integer.parseInt(req.getParameter("subject")));

            if (sub.delete(subject.getId()) == 1) {
                req.getRequestDispatcher("/adminSubjects").forward(req, resp);

                HttpSession session = req.getSession();
                session.setAttribute("success", "Matéria " + subject.getName() + " deletado com sucesso!");
            }
            else
                throw new UnavailableException("Matéria " + subject.getName() + " não pôde ser deletado...");
        } catch (NullPointerException exc) {
            HttpSession session = req.getSession();
            session.setAttribute("error", "Alguns dados não foram preenchidos!");
            req.getRequestDispatcher("adminSubjects?type=editSubject").forward(req, resp);
        } catch (UnavailableException exc) {
            HttpSession session = req.getSession();
            session.setAttribute("error", exc.getMessage());
            req.getRequestDispatcher("adminSubjects?type=editSubject").forward(req, resp);
        }
    }
}
