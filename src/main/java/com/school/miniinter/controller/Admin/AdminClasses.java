package com.school.miniinter.controller.Admin;

import com.school.miniinter.dao.*;
import com.school.miniinter.models.Class.Class;
import com.school.miniinter.models.Subject.Subject;
import com.school.miniinter.models.TeachingAssignment.Teaching;
import com.school.miniinter.models.Teacher.Teacher;
import com.school.miniinter.models.TeachingAssignment.TeachingAssignment;
import com.school.miniinter.utils.ClassUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.UnavailableException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name="adminClasses", urlPatterns = "/adminClasses")
public class AdminClasses extends HttpServlet {

    private final ClassDAO classDAO = new ClassDAO();
    
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
                    editClass(req, resp);
                }
                case ("create") -> {
                    createClass(req,resp);
                }
                default -> {
                    showClasses(req, resp);
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
                    insertClass(req,resp);
                }
                case ("update") -> {
                    updateClass(req, resp);
                }
                case ("delete") -> {
                    deleteClass(req, resp);
                }
                default -> {
                    showClasses(req, resp);
                }
            }
        }
    }

    // Métodos de redirecionamento
    private void showClasses(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Class> classes = classDAO.read();
        HttpSession session = req.getSession();
        session.setAttribute("classes", classes);

        req.getRequestDispatcher("WEB-INF/admin/class/classes.jsp").forward(req, resp);
    }
    private void editClass(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Class classroom = classDAO.read(Integer.parseInt(req.getParameter("classroom")));
        HttpSession session = req.getSession();
        session.setAttribute("classroom", classroom);

        TeachersDAO teach = new TeachersDAO();
        List<Teacher> teachers = teach.read();
        session.setAttribute("teachers", teachers);

        SubjectsDAO sub = new SubjectsDAO();
        List<Subject> subjects = sub.read();
        session.setAttribute("subjects", subjects);

        TeachingDAO assign = new TeachingDAO();
        Teaching[] aulas = assign.readByIdClas(classroom.getId());
        session.setAttribute("aulas", aulas);

        req.getRequestDispatcher("WEB-INF/admin/class/classEdit.jsp").forward(req, resp);
    }
    private void createClass(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException{
        req.getRequestDispatcher("WEB-INF/admin/class/classInsert.jsp").forward(req,
                resp);
    }

    // Métodos de acesso ao banco
    private void insertClass(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        try {

            String series = req.getParameter("series");
            String classroom = req.getParameter("classroom");

            if (!ClassUtils.verifySeries(series) || !ClassUtils.verifyClassroom(classroom)) {
                throw new RuntimeException("Digite uma série compatível (1,2," +
                        "3,6,7,8,9) e uma classe compatível (A-Z).");
            }
            Character classChar = Character.toUpperCase(classroom.charAt(0));

            Class classInsert = new Class(series.charAt(0),
                    classChar);

            if (classDAO.insert(classInsert)) {
                HttpSession session = req.getSession();
                session.setAttribute("success", "Classe adicionada com " +
                        "sucesso!");
            } else {
                HttpSession session = req.getSession();
                session.setAttribute("error", "Ocorreu um erro ao alterar os " +
                        "dados da classe, tente novamente!");
            }

            req.getRequestDispatcher("/adminClasses?type=noot").forward(req,
                    resp);
        } catch (NullPointerException exc) {
            HttpSession session = req.getSession();
            session.setAttribute("error", "Alguns dados não foram preenchidos!");
            req.getRequestDispatcher("/adminClasses?type=create").forward(req,
                    resp);
        }
    }
    private void updateClass(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            TeachingDAO assign = new TeachingDAO();
            int idClass = Integer.parseInt(req.getParameter("classroom"));
            Class classroom = classDAO.read(idClass);
            TeachingAssignment[] aulas = new TeachingAssignment[6];

            char series = req.getParameter("series").charAt(0);
            char classLetter = req.getParameter("turma").charAt(0);

            if (!classroom.getSeries().equals(series))
                classroom.setSeries(series);
            if (!classroom.getClassroom().equals(classLetter))
                classroom.setClassroom(classLetter);

            classDAO.update(classroom);

            for (int n = 0; n < 6; n++) {
                String result =  req.getParameter("aula"+n+"Id");
                int idTeaching = isNull(result);

                String subject =  req.getParameter("aula"+n+"Subject");
                int idSubject = isNull(subject);

                String teacher =  req.getParameter("aula"+n+"Teacher");
                int idTeacher = isNull(teacher);

                aulas[n] = new TeachingAssignment(
                        idTeaching,
                        idClass,
                        idSubject,
                        idTeacher,
                        (n+1)
                );
            }

            boolean exito = false;

            for (TeachingAssignment aula : aulas) {
                if (aula.getIdSubject() != -1 && aula.getIdTeacher() != -1) {
                    if ((aula.getId() == -1) && !aula.isNull()) {
                        exito = assign.insert(aula);
                    } else {
                        exito = assign.update(aula);
                    }
                }
            }

            if (exito) {
                HttpSession session = req.getSession();
                session.setAttribute("success", "Turma " + classroom.getSeries() + "°" + classroom.getClassroom() + " alterada com sucesso!");
            } else {
                HttpSession session = req.getSession();
                session.setAttribute("error", "Turma " + classroom.getSeries() + "°" + classroom.getClassroom() + " nâo foi alterada!");
            }
            req.getRequestDispatcher("/adminClasses?type=noot").forward(req, resp);
        } catch (NullPointerException exc) {
            HttpSession session = req.getSession();
            session.setAttribute("error", "Alguns dados não foram preenchidos!");
            req.getRequestDispatcher("adminClasses?type=edit").forward(req, resp);
        }
    }
    private void deleteClass(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Class classroom = classDAO.read(Integer.parseInt(req.getParameter("classroom")));

            if (classDAO.delete(classroom.getId())) {
                req.getRequestDispatcher("/adminClasses?type=noot").forward(req, resp);

                HttpSession session = req.getSession();
                session.setAttribute("success", "Turma " + classroom.getSeries() + "°" + classroom.getClassroom() + " deletada com sucesso!");
            }
            else
                throw new UnavailableException("Turma " + classroom.getSeries() + "°" + classroom.getClassroom() + " não pôde ser deletada...");
        } catch (NullPointerException exc) {
            HttpSession session = req.getSession();
            session.setAttribute("error", "Alguns dados não foram preenchidos!");
            req.getRequestDispatcher("adminClasses?type=edit").forward(req, resp);
        } catch (UnavailableException exc) {
            HttpSession session = req.getSession();
            session.setAttribute("error", exc.getMessage());
            req.getRequestDispatcher("adminClasses?type=edit").forward(req, resp);
        }
    }

    // Métodos auxiliares
    private int isNull(String object) {
        if(object != null && !object.isBlank()){
            return Integer.parseInt(object);
        }else{
            return -1;
        }
    }
}
