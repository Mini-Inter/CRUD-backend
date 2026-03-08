package com.school.miniinter.controller.Admin;

import com.school.miniinter.config.HashConfig;
import com.school.miniinter.dao.TeachersDAO;
import com.school.miniinter.models.Teacher.Teacher;
import com.school.miniinter.utils.EmailUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.UnavailableException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(name="adminTeachers", urlPatterns = "/adminTeachers")
public class AdminTeachers extends HttpServlet {
    TeachersDAO teach = new TeachersDAO();

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
                    editTeacher(req, resp);
                }
                case ("create") -> {
                    createTeacher(req,resp);
                }
                default -> {
                    showTeachers(req, resp);
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
                    insertTeacher(req,resp);
                }
                case ("update") -> {
                    updateTeacher(req, resp);
                }
                case ("delete") -> {
                    deleteTeacher(req, resp);
                }
                default -> {
                    showTeachers(req, resp);
                }
            }
        }
    }

    // Métodos de redirecionamento
    private void showTeachers(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Teacher> teachers = teach.read();
        String search = req.getParameter("search");
        if (search != null && !search.isBlank()) {
            teachers = filter(teachers, search);
        }
        HttpSession session = req.getSession();
        session.setAttribute("teachers", teachers);

        req.getRequestDispatcher("WEB-INF/admin/teacher/teachers.jsp").forward(req, resp);
    }
    private void editTeacher(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Teacher teacher = teach.read(Integer.parseInt(req.getParameter("teacher")));
        HttpSession session = req.getSession();
        session.setAttribute("teacher", teacher);

        req.getRequestDispatcher("WEB-INF/admin/teacher/teacherEdit.jsp").forward(req, resp);
    }
    private void createTeacher(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        req.getRequestDispatcher("WEB-INF/admin/teacher/teacherInsert.jsp").forward(req,resp);
    }
    private void insertTeacher(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {

            String name = req.getParameter("name");
            String email = req.getParameter("email");
            Date birth =  Date.valueOf(LocalDate.parse(req.getParameter(
                    "birth")));
            String password = req.getParameter("pass");
            String phone = req.getParameter("phone");

            try{
                password = HashConfig.hashSenha(password);
            }catch(NoSuchAlgorithmException nsae){
                nsae.printStackTrace();
            }
            if (!EmailUtils.verifyEmail(email)) {
                throw new RuntimeException("Email não foi digitado corretamente! Siga a sintaxe 'nome.sobrenome@vidya.org.br'");
            }
            email = email.substring(0, email.indexOf("@"));

            Teacher teacher = new Teacher(name,email,phone,password,birth);

            if (teach.insert(teacher)) {
                HttpSession session = req.getSession();
                session.setAttribute("success", "Dados do professor " + teacher.getName() + " alterados com sucesso!");
            } else {
                HttpSession session = req.getSession();
                session.setAttribute("success", "Dados do professor " + teacher.getName() + " não foram alterados!");
            }

            req.getRequestDispatcher("/adminTeachers?type=noot").forward(req, resp);
        } catch (NullPointerException exc) {
            HttpSession session = req.getSession();
            session.setAttribute("error", "Alguns dados não foram preenchidos!");
            req.getRequestDispatcher("adminTeachers?type=create").forward(req, resp);
        }
    }
    private void updateTeacher(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Teacher teacher = teach.read(Integer.parseInt(req.getParameter("teacher")));

            String nome = req.getParameter("name");
            String email = req.getParameter("email");
            Date birth =  Date.valueOf(req.getParameter("birth"));
            String password = req.getParameter("pass");
            String phone = req.getParameter("phone");

            if (!EmailUtils.verifyEmail(email)) {
                throw new RuntimeException("Email não foi digitado corretamente! Siga a sintaxe 'nome.sobrenome@vidya.org.br'");
            }
            email = email.substring(0, email.indexOf("@"));

            if (!nome.equals(teacher.getName())) {
                teacher.setName(nome);
            }
            if (!email.equals(teacher.getLogin())) {
                teacher.setLogin(email);
            }
            if (!birth.equals(teacher.getBirthDate())) {
                teacher.setBirthDate(birth);
            }
            if (!phone.equals(teacher.getPhone())) {
                teacher.setPhone(phone);
            }
            if (!password.isBlank() && !password.equals(teacher.getPassword())) {
                try{
                    password = HashConfig.hashSenha(password);
                }catch(NoSuchAlgorithmException nsae){
                    nsae.printStackTrace();
                }
                teacher.setPassword(password);
            }

            if (teach.update(teacher)) {
                HttpSession session = req.getSession();
                session.setAttribute("success", "Dados do professor " + teacher.getName() + " alterados com sucesso!");
            } else {
                HttpSession session = req.getSession();
                session.setAttribute("error", "Dados do professor " + teacher.getName() + " não foram alterados!");
            }
            showTeachers(req, resp);
        } catch (NullPointerException | NumberFormatException exc) {
            HttpSession session = req.getSession();
            session.setAttribute("error", "Alguns dados não foram preenchidos!");
            editTeacher(req, resp);
        } catch (RuntimeException exc) {
            HttpSession session = req.getSession();
            session.setAttribute("error", exc.getMessage());
            editTeacher(req, resp);
        }
    }
    private void deleteTeacher(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Teacher teacher = teach.read(Integer.parseInt(req.getParameter("teacher")));

            if (teach.delete(teacher.getId()) == 1) {
                req.getRequestDispatcher("/adminTeachers?type=noot").forward(req, resp);

                HttpSession session = req.getSession();
                session.setAttribute("success", "Professor " + teacher.getName() + " deletado com sucesso!");
            }
            else
                throw new UnavailableException("Professor " + teacher.getName() + " não pôde ser deletado...");
        } catch (NullPointerException exc) {
            HttpSession session = req.getSession();
            session.setAttribute("error", "Alguns dados não foram preenchidos!");
            req.getRequestDispatcher("adminTeachers?type=edit").forward(req, resp);
        } catch (UnavailableException exc) {
            HttpSession session = req.getSession();
            session.setAttribute("error", exc.getMessage());
            req.getRequestDispatcher("adminTeachers?type=edit").forward(req, resp);
        }
    }

    // Métodos auxiliares
    private List<Teacher> filter(List<Teacher> teachers, String name) {
        List<Teacher> newTeachers = new LinkedList<>();
        Pattern mat = Pattern.compile(name, Pattern.CASE_INSENSITIVE);
        Matcher match;
        for (Teacher teacher : teachers) {
            match = mat.matcher(teacher.getName());
            if (match.find()) {
                newTeachers.add(teacher);
            }
        }
        return newTeachers;
    }
}