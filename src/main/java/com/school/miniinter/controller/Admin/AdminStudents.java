package com.school.miniinter.controller.Admin;

import com.school.miniinter.config.HashConfig;
import com.school.miniinter.dao.AdministratorsDAO;
import com.school.miniinter.dao.ClassDAO;
import com.school.miniinter.dao.PreRegistrationDAO;
import com.school.miniinter.models.Class.Class;
import com.school.miniinter.dao.StudentsDAO;
import com.school.miniinter.models.PreRegistration.PreRegistration;
import com.school.miniinter.models.Students.Students;
import com.school.miniinter.models.Students.Summary;
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
import java.util.List;

@WebServlet(name="adminStudents", urlPatterns = "/adminStudents")
public class AdminStudents extends HttpServlet {
    StudentsDAO studDAO = new StudentsDAO();
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
                    editStudent(req, resp);
                }
                case ("create") -> {
                    createStudent(req, resp);
                }
                default -> {
                    showStudents(req, resp);
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
                case ("update") -> {
                    updateStudent(req, resp);
                }
                case ("delete") -> {
                    deleteStudent(req, resp);
                }
                case ("insert") -> {
                    insertStudent(req, resp);
                }
                default -> {
                    showStudents(req, resp);
                }
            }
        }
    }

    // Métodos de redirecionamento
    private void showStudents(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Summary> students = studDAO.readSummary();
        HttpSession session = req.getSession();
        session.setAttribute("students", students);

        req.getRequestDispatcher("WEB-INF/admin/student/students.jsp").forward(req, resp);
    }
    private void editStudent(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        ClassDAO cla = new ClassDAO();
        List<Class> classes = cla.read();
        session.setAttribute("classes", classes);


        int idStudent = Integer.parseInt(req.getParameter("student"));
        Students students = studDAO.readById(idStudent);
        session.setAttribute("student",students);

        req.getRequestDispatcher("WEB-INF/admin/student/studentEdit.jsp").forward(req, resp);
    }
    private void createStudent(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();

        ClassDAO cla = new ClassDAO();
        List<Class> classes = cla.read();
        session.setAttribute("classes", classes);

        PreRegistrationDAO pre = new PreRegistrationDAO();
        List<PreRegistration> preRegistrations = pre.readAllAvailableCpf();
        session.setAttribute("cpfs",preRegistrations);

        req.getRequestDispatcher("WEB-INF/admin/student/studentInsert.jsp").forward(req,
                resp);
    }

    // Métodos de acesso ao banco
    private void insertStudent(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            PreRegistrationDAO preDAO = new PreRegistrationDAO();
            
            Students student = new Students();

            int idCpf = Integer.parseInt(req.getParameter("cpf"));
            int idClass = Integer.parseInt(req.getParameter("classroom"));
            String name = req.getParameter("name");
            String email = req.getParameter("email");
            Date birth = Date.valueOf(LocalDate.parse(req.getParameter("birth")));
            String password = req.getParameter("pass");
            String phone = req.getParameter("phone");

            if (!EmailUtils.verifyEmail(email)) {
                throw new RuntimeException("Email não foi digitado corretamente! Siga a sintaxe 'nome.sobrenome@vidya.org.br'");
            }
            email = email.substring(0, email.indexOf("@"));

            student.setFk_class(idClass);
            student.setFull_name(name);
            student.setLogin(email);
            student.setBirth_date(birth);
            student.setPhone(phone);
            try{
                password = HashConfig.hashSenha(password);
            }catch(NoSuchAlgorithmException nsae){
                nsae.printStackTrace();
            }
            student.setPassword(password);
            Integer id_student = studDAO.readIdByName(name);
            PreRegistration pre = new PreRegistration(id_student,idCpf);

            if (studDAO.insert(student) && preDAO.updateFkStudentById(pre)) {
                HttpSession session = req.getSession();
                session.setAttribute("success", "Dados do estudante " + student.getFull_name() + " alterados com sucesso!");
            } else {
                HttpSession session = req.getSession();
                session.setAttribute("error", "Ocorreu um erro ao alterar os dados do estudante " + student.getFull_name() + ", tente novamente!");
            }

            req.getRequestDispatcher("/adminStudents?type=noot").forward(req, resp);
        } catch (NullPointerException exc) {
            HttpSession session = req.getSession();
            session.setAttribute("error", "Alguns dados não foram preenchidos!");
            req.getRequestDispatcher("adminStudents?type=create").forward(req, resp);
        }
    }
    private void updateStudent(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            
            Students student = studDAO.readById(Integer.parseInt(req.getParameter("student")));

            int idClass = Integer.parseInt(req.getParameter("classroom"));
            String nome = req.getParameter("name");
            String email = req.getParameter("email");
            Date birth =  Date.valueOf(LocalDate.parse(req.getParameter(
                    "birth")));
            String password = req.getParameter("pass");
            String phone = req.getParameter("phone");

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
            if (!phone.equals(student.getPhone())) {
                student.setPhone(phone);
            }
            if (!password.isBlank() && !password.equals(student.getPassword())) {
                try {
                    password = HashConfig.hashSenha(password);
                }catch(NoSuchAlgorithmException nsae){
                    nsae.printStackTrace();
                }
                student.setPassword(password);
            }

            switch (studDAO.update(student)) {
                case (1) -> {
                    HttpSession session = req.getSession();
                    session.setAttribute("success", "Dados do estudante " + student.getFull_name() + " alterados com sucesso!");
                }
                case (0) -> {
                    HttpSession session = req.getSession();
                    session.setAttribute("error", "Dados do estudante " + student.getFull_name() + " não puderam ser alterados!");
                }
                case (-1) -> {
                    HttpSession session = req.getSession();
                    session.setAttribute("error", "Ocorreu um erro ao alterar os dados do estudante " + student.getFull_name() + ", tente novamente!");
                }
            }

            req.getRequestDispatcher("/adminStudents?type=noot").forward(req, resp);
        } catch (NullPointerException exc) {
            HttpSession session = req.getSession();
            session.setAttribute("error", "Alguns dados não foram preenchidos!");
            req.getRequestDispatcher("adminStudents?type=edit").forward(req, resp);
        }
    }
    private void deleteStudent(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            
            Students student = studDAO.readById(Integer.parseInt(req.getParameter("student")));

            if (studDAO.delete(student.getId_student()) == 1) {
                req.getRequestDispatcher("/adminStudents?type=noot").forward(req, resp);

                HttpSession session = req.getSession();
                session.setAttribute("success", "Estudante " + student.getFull_name() + " deletado com sucesso!");
            }
            else
                throw new UnavailableException("Estudante " + student.getFull_name() + " não pôde ser deletado...");
        } catch (NullPointerException exc) {
            HttpSession session = req.getSession();
            session.setAttribute("error", "Alguns dados não foram preenchidos!");
            req.getRequestDispatcher("adminStudents?type=edit").forward(req, resp);
        } catch (UnavailableException exc) {
            HttpSession session = req.getSession();
            session.setAttribute("error", exc.getMessage());
            req.getRequestDispatcher("adminStudents?type=show").forward(req, resp);
        }
    }
}
