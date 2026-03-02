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
    SubjectsDAO sub = new SubjectsDAO();

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
                    editSubject(req, resp);
                }
                case ("create") -> {
                    createSubject(req,resp);
                }
                default -> {
                    showSubjects(req, resp);
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
                    insertSubject(req,resp);
                }
                case ("update") -> {
                    updateSubject(req, resp);
                }
                case ("delete") -> {
                    deleteSubject(req, resp);
                }
                default -> {
                    showSubjects(req, resp);
                }
            }
        }
    }

    // Métodos de redirecionamento
    private void showSubjects(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Subject> subjects = sub.read();
        HttpSession session = req.getSession();
        session.setAttribute("subjects", subjects);

        req.getRequestDispatcher("WEB-INF/admin/subject/subjects.jsp").forward(req, resp);
    }
    private void editSubject(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Subject subject = sub.read(Integer.parseInt(req.getParameter("subject")));
        HttpSession session = req.getSession();
        session.setAttribute("subject", subject);

        req.getRequestDispatcher("WEB-INF/admin/subject/subjectEdit.jsp").forward(req, resp);
    }
    private void createSubject(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        req.getRequestDispatcher("WEB-INF/admin/subject/subjectInsert.jsp").forward(req,resp);
    }

    // Métodos de acesso ao banco
    private void insertSubject(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        try{
            String description = req.getParameter("description");
            String name = req.getParameter("name");

            Subject subject = new Subject(description,name);

            if(sub.insert(subject)){
                HttpSession session = req.getSession();
                session.setAttribute("success", "Dados da matéira criados " +
                        "com sucesso!");
            } else {
                HttpSession session = req.getSession();
                session.setAttribute("error", "Ocorreu um erro ao criar os " +
                        "dados de matéria, tente novamente!");
            }
            req.getRequestDispatcher("/adminSubjects?type=noot").forward(req,
                    resp);

        } catch (NullPointerException exc) {
            HttpSession session = req.getSession();
            session.setAttribute("error", "Alguns dados não foram preenchidos!");
            req.getRequestDispatcher("adminSubjects?type=create").forward(req, resp);
        }
    }
    private void updateSubject(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Subject subject = sub.read(Integer.parseInt(req.getParameter("subject")));

            String nome = req.getParameter("name");
            String descricao = req.getParameter("description");

            if (!nome.equals(subject.getName())) {
                subject.setName(nome);
            }
            if (!descricao.equals(subject.getDescription())) {
                subject.setDescription(descricao);
            }

            if (sub.update(subject)) {
                HttpSession session = req.getSession();
                session.setAttribute("success", "Dados da  matéria " + subject.getName() + " alterados com sucesso!");
            } else {
                HttpSession session = req.getSession();
                session.setAttribute("error", "Dados do matéria " + subject.getName() + " não foram alterados!");
            }

            req.getRequestDispatcher("/adminSubjects?type=noot").forward(req, resp);
        } catch (NullPointerException exc) {
            HttpSession session = req.getSession();
            session.setAttribute("error", "Alguns dados não foram preenchidos!");
            req.getRequestDispatcher("adminSubjects?type=edit").forward(req, resp);
        }
    }
    private void deleteSubject(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Subject subject = sub.read(Integer.parseInt(req.getParameter("subject")));

            if (sub.delete(subject.getId()) == 1) {
                req.getRequestDispatcher("/adminSubjects?type=noot").forward(req, resp);

                HttpSession session = req.getSession();
                session.setAttribute("success", "Matéria " + subject.getName() + " deletado com sucesso!");
            }
            else
                throw new UnavailableException("Matéria " + subject.getName() + " não pôde ser deletado...");
        } catch (NullPointerException exc) {
            HttpSession session = req.getSession();
            session.setAttribute("error", "Alguns dados não foram preenchidos!");
            req.getRequestDispatcher("adminSubjects?type=edit").forward(req, resp);
        } catch (UnavailableException exc) {
            HttpSession session = req.getSession();
            session.setAttribute("error", exc.getMessage());
            req.getRequestDispatcher("adminSubjects?type=edit").forward(req, resp);
        }
    }
}
