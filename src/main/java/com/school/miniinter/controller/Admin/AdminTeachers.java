package com.school.miniinter.controller.Admin;

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
import java.sql.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(name="adminTeachers", urlPatterns = "/adminTeachers")
public class AdminTeachers extends HttpServlet {
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
                case ("showTeacher") -> {
                    showTeacher(req, resp);
                }
                case ("editTeacher") -> {
                    editTeacher(req, resp);
                }
                case ("updateTeacher") -> {
                    updateTeacher(req, resp);
                }
                case ("deleteTeacher") -> {
                    deleteTeacher(req, resp);
                }
                default -> {
                    showTeachers(req, resp);
                }
            }
        }
    }

    private void showTeacher(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TeachersDAO teach = new TeachersDAO();
        Teacher teacher = teach.read((Integer) req.getAttribute("teacher"));
        HttpSession session = req.getSession();
        session.setAttribute("teacher", teacher);

        req.getRequestDispatcher("WEB-INF/admin/teacherShow.jsp");
    }

    private void showTeachers(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TeachersDAO teach = new TeachersDAO();
        List<Teacher> teachers = teach.read();
        HttpSession session = req.getSession();
        session.setAttribute("teachers", teachers);

        req.getRequestDispatcher("WEB-INF/admin/teachers.jsp");
    }

    private void editTeacher(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TeachersDAO teach = new TeachersDAO();
        Teacher teacher = teach.read(Integer.parseInt(req.getParameter("teacher")));
        HttpSession session = req.getSession();
        session.setAttribute("teacher", teacher);

        req.getRequestDispatcher("WEB-INF/admin/teacherEDit.jsp").forward(req, resp);
    }

    private void updateTeacher(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            TeachersDAO teach = new TeachersDAO();
            Teacher teacher = teach.read(Integer.parseInt(req.getParameter("teacher")));

            String nome = req.getParameter("nome");
            String email = req.getParameter("email");
            Date birth =  Date.valueOf(req.getParameter("birth"));
            String password = req.getParameter("pass");

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
            if (!password.equals(teacher.getPassword())) {
                teacher.setPassword(password);
            }

            teach.update(teacher);

            req.getRequestDispatcher("/adminTeachers").forward(req, resp);

            HttpSession session = req.getSession();
            session.setAttribute("success", "Dados do professor" + teacher.getName() + "alterados com sucesso!");
        } catch (NullPointerException exc) {
            HttpSession session = req.getSession();
            session.setAttribute("error", "Alguns dados não foram preenchidos!");
            req.getRequestDispatcher("adminTeachers?type=editTeacher").forward(req, resp);
        }
    }

    private void deleteTeacher(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            TeachersDAO teach = new TeachersDAO();
            Teacher teacher = teach.read(Integer.parseInt(req.getParameter("teacher")));

            if (teach.delete(teacher.getId()) == 1) {
                req.getRequestDispatcher("/adminTeachers").forward(req, resp);

                HttpSession session = req.getSession();
                session.setAttribute("success", "Professor " + teacher.getName() + " deletado com sucesso!");
            }
            else
                throw new UnavailableException("Professor " + teacher.getName() + " não pôde ser deletado...");
        } catch (NullPointerException exc) {
            HttpSession session = req.getSession();
            session.setAttribute("error", "Alguns dados não foram preenchidos!");
            req.getRequestDispatcher("adminTeachers?type=editTeacher").forward(req, resp);
        } catch (UnavailableException exc) {
            HttpSession session = req.getSession();
            session.setAttribute("error", exc.getMessage());
            req.getRequestDispatcher("adminTeachers?type=editTeacher").forward(req, resp);
        }
    }
}
