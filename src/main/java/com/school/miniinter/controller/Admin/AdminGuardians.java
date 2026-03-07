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
import java.text.DateFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

@WebServlet(name="adminGuardians", urlPatterns = "/adminGuardians")
public class AdminGuardians extends HttpServlet {

    private final GuardiansDAO gar = new GuardiansDAO();

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
                    editGuardian(req, resp);
                }
                case ("create") -> {
                    createGuardian(req,resp);
                }
                default -> {
                    showGuardians(req, resp);
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
                    insertGuardian(req,resp);
                }
                case ("update") -> {
                    updateGuardian(req, resp);
                }
                case ("delete") -> {
                    deleteGuardian(req, resp);
                }
                default -> {
                    showGuardians(req, resp);
                }
            }
        }
    }

    // Métodos de redirecionamento
    private void showGuardians(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Guardian> guardians = gar.read();
        HttpSession session = req.getSession();
        session.setAttribute("guardians", guardians);

        req.getRequestDispatcher("WEB-INF/admin/guardian/guardians.jsp").forward(req, resp);
    }
    private void editGuardian(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        GuardiansDAO gar = new GuardiansDAO();
        Guardian guardian = gar.read(Integer.parseInt(req.getParameter("guardian")));
        HttpSession session = req.getSession();
        session.setAttribute("guardian", guardian);

        req.getRequestDispatcher("WEB-INF/admin/guardian/guardianEdit.jsp").forward(req, resp);
    }
    private void createGuardian(HttpServletRequest req, HttpServletResponse resp) throws  ServletException, IOException{
        req.getRequestDispatcher("WEB-INF/admin/guardian/guardianInsert.jsp").forward(req,resp);
    }

    // Métodos de acesso ao banco
    private void insertGuardian(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException{
        try {
            String name = req.getParameter("name");
            Date date = Date.valueOf(LocalDate.parse(req.getParameter("birth")));

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
            req.getRequestDispatcher("WEB-INF/admin/guardian/guardianInsert.jsp").forward(req, resp);
        }

    }
    private void updateGuardian(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            GuardiansDAO gar = new GuardiansDAO();
            Guardian guardian = gar.read(Integer.parseInt(req.getParameter("guardian")));

            String name = req.getParameter("name");
            Date birth =  Date.valueOf(LocalDate.parse(req.getParameter(
                    "birth")));
            DateFormat format = DateFormat.getDateInstance(DateFormat.DEFAULT,
                    new Locale("pt","BR"));
            String birthString = format.format(birth);

            if (!name.equals(guardian.getName())) {
                guardian.setName(name);
            }
            if (!birthString.equals(guardian.getBirthDate())) {
                guardian.setBirthDate(birth);
            }

            if (gar.update(guardian)) {
                HttpSession session = req.getSession();
                session.setAttribute("success", "Dados do responsável " + guardian.getName() + " alterados com sucesso!");
            } else {
                HttpSession session = req.getSession();
                session.setAttribute("error", "Dados do responsável  " + guardian.getName() + " não foram alterados!");
            }

            req.getRequestDispatcher("/adminGuardians?type=noot").forward(req, resp);
        } catch (NullPointerException exc) {
            HttpSession session = req.getSession();
            session.setAttribute("error", "Alguns dados não foram preenchidos!");
            req.getRequestDispatcher("adminGuardians?type=edit").forward(req, resp);
        }
    }
    private void deleteGuardian(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            GuardiansDAO gar = new GuardiansDAO();
            Guardian guardian = gar.read(Integer.parseInt(req.getParameter("guardian")));

            if (gar.delete(guardian.getId())) {
                req.getRequestDispatcher("/adminGuardians?type=noot").forward(req, resp);

                HttpSession session = req.getSession();
                session.setAttribute("success", "Responsável " + guardian.getName() + " deletado com sucesso!");
            }
            else
                throw new UnavailableException("Responsável " + guardian.getName() + " não pôde ser deletado...");
        } catch (NullPointerException exc) {
            HttpSession session = req.getSession();
            session.setAttribute("error", "Alguns dados não foram preenchidos!");
            req.getRequestDispatcher("adminGuardians?type=edit").forward(req, resp);
        } catch (UnavailableException exc) {
            HttpSession session = req.getSession();
            session.setAttribute("error", exc.getMessage());
            req.getRequestDispatcher("adminGuardians?type=edit").forward(req, resp);
        }
    }
}
