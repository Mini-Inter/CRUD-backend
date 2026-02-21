package com.school.miniinter.controller.Admin;

import com.school.miniinter.dao.StudentsDAO;
import com.school.miniinter.models.Students.Students;
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

@WebServlet(name="adminStudents", urlPatterns = "/adminStudents")
public class AdminStudents extends HttpServlet {
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
                case ("showStudent") -> {
                    showStudent(req, resp);
                }
                case ("editStudent") -> {
                    editStudent(req, resp);
                }
                case ("updateStudent") -> {
                    updateStudent(req, resp);
                }
                case ("deleteStudent") -> {
                    deleteStudent(req, resp);
                }
                default -> {
                    showStudents(req, resp);
                }
            }
        }
    }

    private void showStudent(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StudentsDAO stud = new StudentsDAO();
        Students student = stud.readById((Integer) req.getAttribute("student"));
        HttpSession session = req.getSession();
        session.setAttribute("student", student);

        req.getRequestDispatcher("WEB-INF/admin/studentShow.jsp");
    }

    private void showStudents(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StudentsDAO stud = new StudentsDAO();
        List<Students> students = stud.readAll();
        HttpSession session = req.getSession();
        session.setAttribute("students", students);

        req.getRequestDispatcher("WEB-INF/admin/student.jsp");
    }

    private void editStudent(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StudentsDAO stud = new StudentsDAO();
        Students student = stud.readById(Integer.parseInt(req.getParameter("student")));
        HttpSession session = req.getSession();
        session.setAttribute("student", student);

        req.getRequestDispatcher("WEB-INF/admin/studentEdit.jsp").forward(req, resp);
    }

    private void updateStudent(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {

            StudentsDAO stud = new StudentsDAO();
            Students student = stud.readById(Integer.parseInt(req.getParameter("student")));

            int idClass = Integer.parseInt(req.getParameter("classroom"));
            String nome = req.getParameter("nome");
            String email = req.getParameter("email");
            Date birth =  Date.valueOf(req.getParameter("birth"));
            String password = req.getParameter("pass");

            if (!EmailUtils.verifyEmail(email)) {
                throw new RuntimeException("Email não foi digitado corretamente! Siga a sintaxe 'nome.sobrenome@vidya.org.br'");
            }
            email = email.substring(0, email.indexOf("@"));

            if (idClass != student.getFk_class()) {
                student.setFk_class(idClass);
            }
            if (!nome.equals(student.getFull_name())) {
                student.setFull_name(nome);
            }
            if (!email.equals(student.getLogin())) {
                student.setLogin(email);
            }
            if (!birth.equals(student.getBirth_date())) {
                student.setBirth_date(birth);
            }
            if (!password.equals(student.getPassword())) {
                student.setPassword(password);
            }

            stud.update(student);

            req.getRequestDispatcher("/adminStudents").forward(req, resp);

            HttpSession session = req.getSession();
            session.setAttribute("success", "Dados do professor" + student.getFull_name() + "alterados com sucesso!");
        } catch (NullPointerException exc) {
            HttpSession session = req.getSession();
            session.setAttribute("error", "Alguns dados não foram preenchidos!");
            req.getRequestDispatcher("adminStudents?type=editStudent").forward(req, resp);
        }
    }

    private void deleteStudent(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            StudentsDAO stud = new StudentsDAO();
            Students student = stud.readById(Integer.parseInt(req.getParameter("student")));

            if (stud.delete(student.getId_student()) == 1) {
                req.getRequestDispatcher("/adminStudents").forward(req, resp);

                HttpSession session = req.getSession();
                session.setAttribute("success", "Professor " + student.getFull_name() + " deletado com sucesso!");
            }
            else
                throw new UnavailableException("Professor " + student.getFull_name() + " não pôde ser deletado...");
        } catch (NullPointerException exc) {
            HttpSession session = req.getSession();
            session.setAttribute("error", "Alguns dados não foram preenchidos!");
            req.getRequestDispatcher("adminStudents?type=editStudent").forward(req, resp);
        } catch (UnavailableException exc) {
            HttpSession session = req.getSession();
            session.setAttribute("error", exc.getMessage());
            req.getRequestDispatcher("adminStudents?type=show").forward(req, resp);
        }
    }
}
