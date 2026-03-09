package com.school.miniinter.controller.Admin;

import com.school.miniinter.dao.PreRegistrationDAO;
import com.school.miniinter.models.Guardian.Guardian;
import com.school.miniinter.models.PreRegistration.PreRegistration;
import jakarta.servlet.ServletException;
import jakarta.servlet.UnavailableException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(name="PreRegistro", value={"/adminPreRegistration"})
public class adminPreRegistration extends HttpServlet {

    PreRegistrationDAO preDAO = new PreRegistrationDAO();

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
                    editRegistration(req, resp);
                }
                case ("create") -> {
                    createRegistration(req,resp);
                }
                default -> {
                    showRegistrations(req, resp);
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
                    insertRegistration(req,resp);
                }
                case ("update") -> {
                    updateRegistration(req, resp);
                }
                case ("delete") -> {
                    deleteGuardian(req, resp);
                }
                default -> {
                    showRegistrations(req, resp);
                }
            }
        }
    }

    public void showRegistrations(HttpServletRequest req,
                                  HttpServletResponse resp) throws ServletException, IOException{
        List<PreRegistration> list = preDAO.read();
        String search = req.getParameter("search");
        if (search != null && !search.isBlank()) {
            list = filter(list, search);
        }
        HttpSession session = req.getSession();
        session.setAttribute("preRegistrations",list);

        req.getRequestDispatcher("WEB-INF/admin/preRegistration" +
                "/preRegistrations.jsp").forward(req, resp);
    }

    public void editRegistration(HttpServletRequest req,
                                 HttpServletResponse resp) throws ServletException, IOException{
        HttpSession session = req.getSession();
        PreRegistration preRegistration =
                preDAO.readById(Integer.parseInt(req.getParameter(
                "id")));
        session.setAttribute("preRegistration",preRegistration);

        req.getRequestDispatcher("WEB-INF/admin/preRegistration" +
                "/preRegistrationsEdit.jsp").forward(req, resp);
    }

    public void createRegistration(HttpServletRequest req,
                                   HttpServletResponse resp) throws ServletException, IOException{
        req.getRequestDispatcher("WEB-INF/admin/preRegistration" +
                "/preRegistrationsInsert.jsp").forward(req, resp);
    }

    public void insertRegistration(HttpServletRequest req,
                                   HttpServletResponse resp) throws ServletException, IOException{
        try {
            String cpf = req.getParameter("cpf");

            PreRegistration preRegistration = new PreRegistration(cpf);
            if (preDAO.insert(preRegistration)) {
                HttpSession session = req.getSession();
                session.setAttribute("success", "Dados do estudante " +
                        "responsável inseridos com sucesso!");
            } else {
                HttpSession session = req.getSession();
                session.setAttribute("error", "Ocorreu um erro ao inserir os " +
                        "dados do responsável,tente novamente!");
            }

            req.getRequestDispatcher("/adminPreRegistration?type=noot").forward(req,
                    resp);
        }catch (NullPointerException exc) {
            HttpSession session = req.getSession();
            session.setAttribute("error", "Alguns dados não foram preenchidos!");
            req.getRequestDispatcher("WEB-INF/admin/preRegistration" +
                    "/preRegistrationInsert.jsp").forward(req, resp);
        }
    }

    public void updateRegistration(HttpServletRequest req,
                                   HttpServletResponse resp) throws ServletException, IOException{
        try {
            String cpf = req.getParameter("cpf");
            PreRegistration preRegistration = preDAO.readById(Integer.parseInt(req.getParameter("id")));

            if (!preRegistration.getCpf().equals(cpf)){
                preRegistration.setCpf(cpf);
            }

            if (preDAO.update(preRegistration)) {
                HttpSession session = req.getSession();
                session.setAttribute("success",
                        "Dados do pré-cadastro " + preRegistration.getId()+
                                "alterados com sucesso!");
            } else {
                HttpSession session = req.getSession();
                session.setAttribute("error", "Dados do pré-cadastro " + preRegistration.getId()+
                        "não foram alterados!");
            }

            req.getRequestDispatcher("/adminPreRegistration?type=noot").forward(req, resp);
        } catch (NullPointerException exc) {
            HttpSession session = req.getSession();
            session.setAttribute("error", "Alguns dados não foram preenchidos!");
            req.getRequestDispatcher("adminPreRegistration?type=edit").forward(req, resp);
        }
    }
    private void deleteGuardian(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
             PreRegistration preRegistration =
                     preDAO.readById(Integer.parseInt(req.getParameter("id")));

            if (preDAO.delete(preRegistration.getId())) {
                req.getRequestDispatcher("/adminGuardians?type=noot").forward(req, resp);

                HttpSession session = req.getSession();
                session.setAttribute("success",
                        "Pré-registro " + preRegistration.getId() + " deletado com" +
                                " sucesso!");
            }
            else
                throw new UnavailableException("Pré-registro " + preRegistration.getId() +
                        " não pôde ser deletado...");
        } catch (NullPointerException exc) {
            HttpSession session = req.getSession();
            session.setAttribute("error", "Alguns dados não foram preenchidos!");
            req.getRequestDispatcher("adminPreRegistration?type=edit").forward(req, resp);
        } catch (UnavailableException exc) {
            HttpSession session = req.getSession();
            session.setAttribute("error", exc.getMessage());
            req.getRequestDispatcher("adminPreRegistration?type=edit").forward(req, resp);
        }
    }

    // Métodos auxiliares
    private List<PreRegistration> filter(List<PreRegistration> preRegs,
                                         String search) {
        List<PreRegistration> newPreRegs = new LinkedList<>();
        Pattern mat = Pattern.compile(search, Pattern.CASE_INSENSITIVE);
        Matcher match;
        for (PreRegistration preReg : preRegs) {
            match = mat.matcher(preReg.getCpf());
            if (match.find()) {
                newPreRegs.add(preReg);
            }
        }
        return newPreRegs;
    }
}
