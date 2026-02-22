package com.school.miniinter.controller.Admin;

import com.school.miniinter.dao.*;
import com.school.miniinter.models.Class.Class;
import com.school.miniinter.models.Students.Students;
import com.school.miniinter.models.Subject.Subject;
import com.school.miniinter.models.TeachingAssignment.Teaching;
import com.school.miniinter.models.Teacher.Teacher;
import com.school.miniinter.models.TeachingAssignment.TeachingAssignment;
import com.school.miniinter.utils.ClassUtils;
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
import java.time.LocalDate;
import java.util.List;

@WebServlet(name="adminClasses", urlPatterns = "/adminClasses")
public class AdminClasses extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Object admin = session.getAttribute("admin");
        String type = "noot noot";
        if (req.getParameter("type") != null) {
            type = req.getParameter("type");
        }

        if (admin == null) {
            resp.sendRedirect(req.getContextPath()+"/authentication/loginaa.jsp");
        } else {
            switch (type) {
                case ("showClass") -> {
                    showClass(req, resp);
                }
                case ("editClass") -> {
                    editClass(req, resp);
                }
                case ("createClass") -> {
                    createClass(req,resp);
                }
                case ("insertClass") -> {
                    insertClass(req,resp);
                }
                case ("updateClass") -> {
                    updateClass(req, resp);
                }
                case ("deleteClass") -> {
                    deleteClass(req, resp);
                }
                default -> {
                    showClasses(req, resp);
                }
            }
        }
    }

    private void showClass(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ClassDAO clas = new ClassDAO();
        Class classroom = clas.read(Integer.parseInt(req.getParameter("classroom")));
        HttpSession session = req.getSession();
        session.setAttribute("class", classroom);
        TeachingDAO assign = new TeachingDAO();
        Teaching[] aulas = assign.readByIdClas(classroom.getId());

        session.setAttribute("aulas", aulas);

        req.getRequestDispatcher("WEB-INF/admin/classShow.jsp").forward(req, resp);
    }

    private void showClasses(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ClassDAO clas = new ClassDAO();
        List<Class> classes = clas.read();
        HttpSession session = req.getSession();
        session.setAttribute("classes", classes);

        req.getRequestDispatcher("WEB-INF/admin/classes.jsp").forward(req, resp);
    }

    private void editClass(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ClassDAO clas = new ClassDAO();
        Class classroom = clas.read(Integer.parseInt(req.getParameter("classroom")));
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

        req.getRequestDispatcher("WEB-INF/admin/classEdit.jsp").forward(req, resp);
    }

    private void createClass(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException{
        req.getRequestDispatcher("WEB-INF/admin/classInsert.jsp").forward(req,
                resp);
    }

    private void insertClass(HttpServletRequest req,
                             HttpServletResponse resp) throws ServletException, IOException{
        try {
            ClassDAO clas = new ClassDAO();

            String series = req.getParameter("series");
            String classroom = req.getParameter("classroom");

            if (!ClassUtils.verifySeries(series) || !ClassUtils.verifyClassroom(classroom)) {
                throw new RuntimeException("Digite uma série compatível (1,2," +
                        "3,6,7,8,9) e uma classe compatível (A-Z).");
            }
            Character classChar = Character.toUpperCase(classroom.charAt(0));

            Class classInsert = new Class(series.charAt(0),
                    classChar);

            if (clas.insert(classInsert)) {
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
            req.getRequestDispatcher("/adminClasses?type=createClass").forward(req,
                    resp);
        }
    }

    private void updateClass(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            ClassDAO clas = new ClassDAO();
            TeachingDAO assign = new TeachingDAO();
            int idClass = Integer.parseInt(req.getParameter("classroom"));
            Class classroom = clas.read(idClass);
            TeachingAssignment[] aulas = new TeachingAssignment[6];

            String series = req.getParameter("series");
            String classR = req.getParameter("turma");

            if (!classroom.getSeries().equals(series))
                classroom.setSeries(series.charAt(0));
            if (!classroom.getClassroom().equals(classR))
                classroom.setClassroom(classR.charAt(0));

            clas.update(classroom);

            for (int n = 0; n < 6; n++) {
                Object result =  req.getParameter("aula"+n+"Id");
                Integer idTeaching;
                if(result != null){
                    idTeaching = Integer.parseInt(String.valueOf(result));
                }else{
                    idTeaching = -1;
                }
                aulas[n] = new TeachingAssignment(
                        idTeaching,
                        idClass,
                        Integer.parseInt(req.getParameter("aula"+n+"Subject")),
                        Integer.parseInt(req.getParameter("aula"+n+"Teacher")),
                        n
                );
            }

            boolean exito = false;
            for (TeachingAssignment aula : aulas) {
                if ((aula.getId() == null) && !aula.isNull()) {
                    exito = assign.insert(aula);
                } else {
                    exito = assign.update(aula);
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
            req.getRequestDispatcher("adminClasses?type=editClass").forward(req, resp);
        }
    }

    private void deleteClass(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            ClassDAO clas = new ClassDAO();
            Class classroom = clas.read(Integer.parseInt(req.getParameter("classroom")));

            if (clas.delete(classroom.getId())) {
                req.getRequestDispatcher("/adminClasses?type=noot").forward(req, resp);

                HttpSession session = req.getSession();
                session.setAttribute("success", "Turma " + classroom.getSeries() + "°" + classroom.getClassroom() + " deletada com sucesso!");
            }
            else
                throw new UnavailableException("Turma " + classroom.getSeries() + "°" + classroom.getClassroom() + " não pôde ser deletada...");
        } catch (NullPointerException exc) {
            HttpSession session = req.getSession();
            session.setAttribute("error", "Alguns dados não foram preenchidos!");
            req.getRequestDispatcher("adminClasses?type=editClass").forward(req, resp);
        } catch (UnavailableException exc) {
            HttpSession session = req.getSession();
            session.setAttribute("error", exc.getMessage());
            req.getRequestDispatcher("adminClasses?type=editClass").forward(req, resp);
        }
    }
}
