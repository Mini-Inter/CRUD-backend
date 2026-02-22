package com.school.miniinter.controller.Admin;

import com.school.miniinter.dao.GuardiansDAO;
import com.school.miniinter.models.Guardian.Guardian;
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

@WebServlet(name="adminGuardians", urlPatterns = "/adminGuardians")
public class AdminGuardians extends HttpServlet {

    GuardiansDAO gar = new GuardiansDAO();
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
                case ("showGuardian") -> {
                    showGuardian(req, resp);
                }
                case ("editGuardian") -> {
                    editGuardian(req, resp);
                }
                case ("createGuardian") -> {
                    createGuardian(req,resp);
                }
                case ("insertGuardian") -> {
                    insertGuardian(req,resp);
                }
                case ("updateGuardian") -> {
                    updateGuardian(req, resp);
                }
                case ("deleteGuardian") -> {
                    deleteGuardian(req, resp);
                }
                default -> {
                    showGuardians(req, resp);
                }
            }
        }
    }
    private void showGuardian(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Guardian guardian = gar.read(Integer.parseInt(req.getParameter("guardian")));
        HttpSession session = req.getSession();
        session.setAttribute("guardian", guardian);

        req.getRequestDispatcher("WEB-INF/admin/guardianShow.jsp").forward(req, resp);
    }
    private void showGuardians(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Guardian> guardians = gar.read();
        HttpSession session = req.getSession();
        session.setAttribute("guardians", guardians);

        req.getRequestDispatcher("WEB-INF/admin/guardians.jsp").forward(req, resp);
    }

    private void createGuardian(HttpServletRequest req,
                                HttpServletResponse resp) throws  ServletException, IOException{
        req.getRequestDispatcher("WEB-INF/admin/guardianInsert.jsp").forward(req,resp);
    }
    private void insertGuardian(HttpServletRequest req,
                                HttpServletResponse resp)throws ServletException, IOException{
        try {
            String name = req.getParameter("name");
            Date date = Date.valueOf(req.getParameter("birth"));

            Guardian guardianInsert = new Guardian(name, date);

            if (gar.insert(guardianInsert)) {
                HttpSession session = req.getSession();
                session.setAttribute("success", "Dados do estudante " +
                        "responsável inseridos com sucesso!");
            } else {
                HttpSession session = req.getSession();
                session.setAttribute("error", "Ocorreu um erro ao inserir os " +
                        "dados do responsável,tente novamente!");
            }

            req.getRequestDispatcher("/adminGuardians?type=noot").forward(req,
                    resp);
        }catch (NullPointerException exc) {
            HttpSession session = req.getSession();
            session.setAttribute("error", "Alguns dados não foram preenchidos!");
            req.getRequestDispatcher("WEB-INF/admin/guardianInsert.jsp").forward(req, resp);
        }

    }
    private void editGuardian(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        GuardiansDAO gar = new GuardiansDAO();
        Guardian guardian = gar.read(Integer.parseInt(req.getParameter("guardian")));
        HttpSession session = req.getSession();
        session.setAttribute("guardian", guardian);

        req.getRequestDispatcher("WEB-INF/admin/guardianEdit.jsp").forward(req, resp);
    }
    private void updateGuardian(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            GuardiansDAO gar = new GuardiansDAO();
            Guardian guardian = gar.read(Integer.parseInt(req.getParameter("guardian")));

            String nome = req.getParameter("name");
            Date birth =  Date.valueOf(req.getParameter("birth"));

            if (!nome.equals(guardian.getName())) {
                guardian.setName(nome);
            }
            if (!birth.equals(guardian.getBirthDate())) {
                guardian.setBirthDate(birth);
            }

            if (gar.update(guardian)) {
                HttpSession session = req.getSession();
                session.setAttribute("success", "Dados do professor" + guardian.getName() + "alterados com sucesso!");
            } else {
                HttpSession session = req.getSession();
                session.setAttribute("error", "Dados do professor " + guardian.getName() + " não foram alterados!");
            }

            req.getRequestDispatcher("/adminGuardians?type=noot").forward(req, resp);
        } catch (NullPointerException exc) {
            HttpSession session = req.getSession();
            session.setAttribute("error", "Alguns dados não foram preenchidos!");
            req.getRequestDispatcher("adminGuardians?type=editGuardian").forward(req, resp);
        }
    }
    private void deleteGuardian(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            GuardiansDAO gar = new GuardiansDAO();
            Guardian guardian = gar.read(Integer.parseInt(req.getParameter("guardian")));

            if (gar.delete(guardian.getId())) {
                req.getRequestDispatcher("/adminGuardians?type=noot").forward(req, resp);

                HttpSession session = req.getSession();
                session.setAttribute("success", "Professor " + guardian.getName() + " deletado com sucesso!");
            }
            else
                throw new UnavailableException("Professor " + guardian.getName() + " não pôde ser deletado...");
        } catch (NullPointerException exc) {
            HttpSession session = req.getSession();
            session.setAttribute("error", "Alguns dados não foram preenchidos!");
            req.getRequestDispatcher("adminGuardians?type=editGuardian").forward(req, resp);
        } catch (UnavailableException exc) {
            HttpSession session = req.getSession();
            session.setAttribute("error", exc.getMessage());
            req.getRequestDispatcher("adminGuardians?type=editGuardian").forward(req, resp);
        }
    }
}
