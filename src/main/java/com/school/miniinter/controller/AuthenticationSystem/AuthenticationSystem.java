package com.school.miniinter.controller.AuthenticationSystem;

import com.school.miniinter.dao.*;
import com.school.miniinter.models.Subject.Subject;
import com.school.miniinter.utils.EmailUtils;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(name="auth", value = {"/auth"})
public class AuthenticationSystem extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        auth(req, resp);
    }

    // Métodos principais
    private void auth(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        String type = req.getParameter("type");
        switch (type) {
            case "login" -> {
                login(req, resp);
            }
            case "signup" -> {
                boolean pre = req.getParameter("pre").equals("true");
                if (pre) {
                    preRegister(req, resp);
                }
                else {
                    signUp(req, resp);
                }
            }
            case "loginAA" -> {
                loginRest(req, resp);
            }
        }
    }
    private void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        String login = req.getParameter("login").toLowerCase().strip();
        String password = req.getParameter("pw");

        try {
            if (!EmailUtils.verifyEmail(login))
                throw new RuntimeException("Email não foi digitado corretamente! Siga a sintaxe 'nome.sobrenome@vidya.org.br'");
            login = login.substring(0, login.indexOf("@"));

            switch (verifyLogin(login, password)) {
                case (2) -> {
                    TeachersDAO teach = new TeachersDAO();
                    int idTeacher = teach.readByLogin(login).getId();
                    HttpSession session = req.getSession();
                    session.setAttribute("idTeacher", idTeacher);

                    List<Subject> subjects;
                    SubjectsDAO subDAO = new SubjectsDAO();
                    subjects = subDAO.readByTeacher(idTeacher);
                    session.setAttribute("subjects", subjects);
                    session.setAttribute("subject", subjects.get(0).getId());

                    req.getRequestDispatcher("/homeTeacher").forward(req, resp);
                }
                case (1) -> {
                    StudentsDAO stud = new StudentsDAO();
                    int idStudent = stud.readByLogin(login).getId_student();
                    HttpSession session = req.getSession();
                    session.setAttribute("idStudent", idStudent);
                    session.setAttribute("email", login);
                    req.getRequestDispatcher("/homeStudent").forward(req, resp);
                }
                case (0) -> {
                    throw new RuntimeException("login não pertence a nenhuma conta!");
                }
            }
        } catch (NullPointerException exc) {
            String error = "Alguns dados não foram preenchidos!";
            HttpSession session = req.getSession();
            session.setAttribute("error", error);
            session.setAttribute("login", login);
            session.setAttribute("password", password);
            resp.sendRedirect(req.getContextPath() + "/authentication/login.jsp");
        }
        catch (RuntimeException exc) {
            String error = exc.getMessage();
            HttpSession session = req.getSession();
            session.setAttribute("error", error);
            session.setAttribute("login", login);
            session.setAttribute("password", password);
            resp.sendRedirect(req.getContextPath() + "/authentication/login.jsp");
        }
    }
    private void loginRest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login").toLowerCase().strip();
        String password = req.getParameter("pw");

        try {
            if (!EmailUtils.verifyEmail(login))
                throw new RuntimeException("Email não foi digitado corretamente! Siga a sintaxe 'nome.sobrenome@vidya.org.br'");
            login = login.substring(0, login.indexOf("@"));

            if (isAdmin(login, password)) {
                HttpSession session = req.getSession();
                session.setAttribute("admin", login);
                req.getRequestDispatcher("/homeAdmin").forward(req, resp);
            } else {
                throw new RuntimeException("login não pertence a nenhuma conta!");
            }
        } catch (NullPointerException exc) {
            String error = "Alguns dados não foram preenchidos!";
            HttpSession session = req.getSession();
            session.setAttribute("error", error);
            session.setAttribute("login", login);
            session.setAttribute("password", password);
            resp.sendRedirect(req.getContextPath() + "/authentication/loginaa.jsp");
        }
        catch (RuntimeException exc) {
            String error = exc.getMessage();
            HttpSession session = req.getSession();
            session.setAttribute("error", error);
            session.setAttribute("login", login);
            session.setAttribute("password", password);
            resp.sendRedirect(req.getContextPath() + "/authentication/loginaa.jsp");
        }
    }
    private void preRegister(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        PreRegistrationDAO preDAO = new PreRegistrationDAO();
        String cpf = req.getParameter("cpf");

        try {
            if (preDAO.read(cpf) != null) {
                HttpSession session = req.getSession();
                session.setAttribute("preRegistered", true);
                req.getRequestDispatcher("/WEB-INF/student/signUp.jsp").forward(req, resp);
            } else
                throw new RuntimeException("CPF não cadastrado para registro!");
        } catch (RuntimeException exc) {
            String error = exc.getMessage();
            HttpSession session = req.getSession();
            session.setAttribute("error", error);
            session.setAttribute("cpf", cpf);
            resp.sendRedirect(req.getContextPath() + "/authentication/cpf.jsp");
        }
    }
    private void signUp(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{

        if (req.getSession().getAttribute("preRegistered")!=null && req.getSession().getAttribute("preRegistered").equals(true)) {
            String name = req.getParameter("name");
            String birthDateString = req.getParameter("birth");
            String login = req.getParameter("email");
            String password = req.getParameter("password");
            String passConf = req.getParameter("passwordConf");
            Date createdAt = Date.valueOf(LocalDate.now());

            try {
                if ( name == null || birthDateString == null || login == null || password == null || passConf == null)
                    throw new NullPointerException("Alguns dados não foram preenchidos!");
                if ( name.isEmpty() || birthDateString.isEmpty() || login.isEmpty() || password.isEmpty() || passConf.isEmpty())
                    throw new NullPointerException("Alguns dados não foram preenchidos!");

                Date birthDate = Date.valueOf(birthDateString);
                int idade =LocalDate.now().getYear()-birthDate.toLocalDate().getYear();

                Pattern fullName = Pattern.compile("^[A-Za-z]+ [A-Za-z]+ ?[A-Za-z ]*?");
                Matcher match = fullName.matcher(name);
                if (!match.find())
                    throw new RuntimeException("Nome digitado incorretamente");
                if (5>idade || idade>20)
                    throw new RuntimeException("Data de nascimento preenchida incorretamente");
                if (!EmailUtils.verifyEmail(login))
                    throw new RuntimeException("Email não foi digitado corretamente! Siga a sintaxe 'nome.sobrenome@vidya.org.br'");
                if (!password.equals(passConf))
                    throw new RuntimeException("Senhas não são iguais");

                String firstName = name.substring(0, name.indexOf(" "));
                String lastName = name.substring(name.lastIndexOf(" "));

                // ADICIONAR INSERÇÃO NO BANCO AQUI
                // + relacionamento com Pre_registration quando for adicionado

                resp.sendRedirect(req.getContextPath()+"/authentication/login.jsp");

            } catch (RuntimeException exc) {
                String error = exc.getMessage();
                HttpSession session = req.getSession();
                session.setAttribute("error", error);
                session.setAttribute("name", name);
                session.setAttribute("birth", birthDateString);
                session.setAttribute("email", login);
                session.setAttribute("pw", password);
                session.setAttribute("pc", passConf);
                req.getRequestDispatcher("/WEB-INF/student/signUp.jsp").forward(req, resp);
            }
        } else {
            HttpSession session = req.getSession();
            session.setAttribute("error", "Passe pelo pré-registro antes do cadastro");
            req.getRequestDispatcher(req.getContextPath() + "/authentication/cpf.jsp").forward(req, resp);
        }
    }


    // Métodos auxiliares
    private int verifyLogin(String login, String pw) throws NullPointerException {
        if (login == null || pw == null) {
            throw new NullPointerException();
        }
        StudentsDAO stud = new StudentsDAO();
        TeachersDAO teach = new TeachersDAO();
        if (stud.isStudent(login, pw)) {
            return 1;
        } else if (teach.isTeacher(login, pw))
            return 2;
        else
            return 0;
    }
    private boolean isAdmin(String login, String pw) throws NullPointerException {
        if (login == null || pw == null) {
            throw new NullPointerException();
        }
        AdministratorsDAO adm = new AdministratorsDAO();
        return  (adm.isAdmin(login, pw));
    }
}
