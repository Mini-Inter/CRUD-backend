package com.school.miniinter.controller.Admin;

import com.school.miniinter.dao.GuardiansDAO;
import com.school.miniinter.models.Guardian.Guardian;
import com.school.miniinter.utils.EmailUtils;
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
                case ("showGuardian") -> {
                    showGuardian(req, resp);
                }
                case ("editGuardian") -> {
                    editGuardian(req, resp);
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
        GuardiansDAO gar = new GuardiansDAO();
        Guardian guardian = gar.read((Integer) req.getAttribute("guardian"));
        HttpSession session = req.getSession();
        session.setAttribute("guardian", guardian);

        req.getRequestDispatcher("WEB-INF/admin/guardianShow.jsp");
    }
    private void showGuardians(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        GuardiansDAO gar = new GuardiansDAO();
        List<Guardian> guardians = gar.read();
        HttpSession session = req.getSession();
        session.setAttribute("guardians", guardians);

        req.getRequestDispatcher("WEB-INF/admin/guardians.jsp");
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

            String nome = req.getParameter("nome");
            Date birth =  Date.valueOf(req.getParameter("birth"));

            if (!nome.equals(guardian.getName())) {
                guardian.setName(nome);
            }
            if (!birth.equals(guardian.getBirthDate())) {
                guardian.setBirthDate(birth);
            }

            gar.update(guardian);

            req.getRequestDispatcher("/adminGuardians").forward(req, resp);

            HttpSession session = req.getSession();
            session.setAttribute("success", "Dados do professor" + guardian.getName() + "alterados com sucesso!");
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
                req.getRequestDispatcher("/adminGuardians").forward(req, resp);

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
