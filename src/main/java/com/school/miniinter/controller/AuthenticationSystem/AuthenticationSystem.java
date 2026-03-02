package com.school.miniinter.controller.authenticationSystem;

import com.school.miniinter.config.HashConfig;
import com.school.miniinter.dao.*;
import com.school.miniinter.models.PreRegistration.PreRegistration;
import com.school.miniinter.models.Students.Students;
import com.school.miniinter.models.Subject.Subject;
import com.school.miniinter.utils.EmailUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(name="auth", value = {"/auth"})
public class AuthenticationSystem extends HttpServlet {

    PreRegistrationDAO preDAO = new PreRegistrationDAO();
    StudentsDAO stud = new StudentsDAO();
    TeachersDAO teach = new TeachersDAO();
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
        String hashedPassword = "";
        try {
            hashedPassword = HashConfig.hashSenha(password);
        }catch(NoSuchAlgorithmException nsae){
            nsae.printStackTrace();
        }

        try {
            if (!EmailUtils.verifyEmail(login)) {
                login = login.substring(0, login.indexOf("@"));
                throw new RuntimeException("Email não foi digitado corretamente! Siga a sintaxe 'nome.sobrenome@vidya.org.br'");
            }
            login = login.substring(0, login.indexOf("@"));

            switch (verifyLogin(login, hashedPassword)) {
                case (2) -> {
                    int idTeacher = teach.readByLogin(login).getId();
                    HttpSession session = req.getSession();
                    session.setAttribute("idTeacher", idTeacher);

                    List<Subject> subjects;
                    SubjectsDAO subDAO = new SubjectsDAO();
                    subjects = subDAO.readByTeacher(idTeacher);
                    session.setAttribute("subjects", subjects);
                    session.setAttribute("subject", subjects.get(0).getId());

                    resp.sendRedirect(req.getContextPath()+"/homeTeacher");
                }
                case (1) -> {
                    int idStudent = stud.readByLogin(login).getId_student();
                    HttpSession session = req.getSession();
                    session.setAttribute("idStudent", idStudent);
                    session.setAttribute("email", login);
                    resp.sendRedirect(req.getContextPath()+"/homeStudent");
                }
                case (0) -> {
                    throw new RuntimeException("login não pertence a nenhuma conta!");
                }
            }
        } catch (NullPointerException exc) {
            String error = "Alguns dados não foram preenchidos!";
            HttpSession session = req.getSession();
            session.setAttribute("error", error);
            session.setAttribute("login", login+"@vidya.org.br");
            session.setAttribute("password", password);
            resp.sendRedirect("/Inicio/login.jsp");
        }
        catch (RuntimeException exc) {
            String error = exc.getMessage();
            HttpSession session = req.getSession();
            session.setAttribute("error", error);
            session.setAttribute("login", login+"@vidya.org.br");
            session.setAttribute("password", password);
            resp.sendRedirect(req.getContextPath() + "/Inicio/login.jsp");
        }
    }
    private void loginRest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login").toLowerCase().strip();
        String password = req.getParameter("pw");
        String hashedPassword = "";

        try {
            hashedPassword = HashConfig.hashSenha(password);
        }catch(NoSuchAlgorithmException nsae){
            nsae.printStackTrace();
        }

        try {
            if (!EmailUtils.verifyEmail(login)){
                login = login.substring(0, login.indexOf("@"));
                throw new RuntimeException("Email não foi digitado corretamente! Siga a sintaxe 'nome.sobrenome@vidya.org.br'");
            }
            login = login.substring(0, login.indexOf("@"));

            if (isAdmin(login, hashedPassword)) {
                HttpSession session = req.getSession();
                session.setAttribute("admin", login);
                resp.sendRedirect(req.getContextPath()+"/adminStudent");
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
        String cpf = req.getParameter("cpf");

        try {
            if (preDAO.read(cpf) != null) {
                HttpSession session = req.getSession();
                session.setAttribute("cpf", cpf);
                session.setAttribute("preRegistered", true);
                req.getRequestDispatcher( "/Inicio" +
                        "/cadastro.jsp").forward(req,
                        resp);
            } else
                throw new RuntimeException("CPF não cadastrado para registro!");
        } catch (RuntimeException exc) {
            String error = exc.getMessage();
            HttpSession session = req.getSession();
            session.setAttribute("error", error);
            session.setAttribute("cpf", cpf);
            resp.sendRedirect(req.getContextPath() + "/Inicio/login.jsp");
        }
    }
    private void signUp(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{

        HttpSession session = req.getSession();
        if (session.getAttribute("preRegistered")!=null && session.getAttribute(
                "preRegistered").equals(true)) {
            String name = req.getParameter("name");
            String birthDateString = req.getParameter("birth");
            String login = req.getParameter("email");
            String password = req.getParameter("password");
            String passConf = req.getParameter("passwordConf");

            try {
                if ( name == null || birthDateString == null || login == null || password == null || passConf == null)
                    throw new NullPointerException("Alguns dados não foram preenchidos!");
                if ( name.isEmpty() || birthDateString.isEmpty() || login.isEmpty() || password.isEmpty() || passConf.isEmpty())
                    throw new NullPointerException("Alguns dados não foram preenchidos!");

                Date birthDate = Date.valueOf(LocalDate.parse(birthDateString));
                int idade = LocalDate.now().getYear()-birthDate.toLocalDate().getYear();

                Pattern fullName = Pattern.compile("^[A-Za-z]+ [A-Za-z]+ ?[A-Za-z ]*?");
                Matcher match = fullName.matcher(name);
                if (!match.find()) {
                    throw new RuntimeException("Nome digitado incorretamente");
                }
                if (5>idade || idade>20) {
                    throw new RuntimeException("Data de nascimento preenchida incorretamente");
                }
                if (!EmailUtils.verifyEmail(login)){
                    throw new RuntimeException("Email não foi digitado corretamente! Siga a sintaxe 'nome.sobrenome@vidya.org.br'");
                }
                if (!password.equals(passConf)) {
                    throw new RuntimeException("Senhas não são iguais");
                }

                PreRegistration preModel =
                        preDAO.read((String)session.getAttribute(
                        "cpf"));
                login = login.substring(0,login.indexOf('@'));
                String hasedPassword = HashConfig.hashSenha(password);
                Students student = new Students(name,birthDate,login,hasedPassword);
                stud.insertInitial(student);
                Integer id_student = stud.readIdByName(name);
                preDAO.insertIdStudentOnCpf(id_student,preModel.getId());

                resp.sendRedirect(req.getContextPath()+"/Inicio/login.jsp");

            } catch (RuntimeException exc) {
                String error = exc.getMessage();
                session.setAttribute("error", error);
                session.setAttribute("name", name);
                session.setAttribute("birth", birthDateString);
                session.setAttribute("email", login);
                session.setAttribute("pw", password);
                session.setAttribute("pc", passConf);
                resp.sendRedirect(req.getContextPath()+"/Inicio/cadastro.jsp");
            } catch(NoSuchAlgorithmException nsae){
                nsae.printStackTrace();
            }
        } else {
            session.setAttribute("error", "Passe pelo pré-registro antes do cadastro");
            resp.sendRedirect(req.getContextPath() + "/Inicio/login" +
                    ".jsp");
        }
    }


    // Métodos auxiliares
    private int verifyLogin(String login, String pw) throws NullPointerException {
        if (login == null || pw == null) {
            throw new NullPointerException();
        }
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
