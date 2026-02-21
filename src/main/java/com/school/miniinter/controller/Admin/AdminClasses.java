package com.school.miniinter.controller.Admin;

import com.school.miniinter.dao.ClassDAO;
import com.school.miniinter.dao.SubjectsDAO;
import com.school.miniinter.dao.TeachersDAO;
import com.school.miniinter.dao.TeachingDAO;
import com.school.miniinter.models.Class.Class;
import com.school.miniinter.models.Subject.Subject;
import com.school.miniinter.models.TeachingAssignment.Teaching;
import com.school.miniinter.models.Teacher.Teacher;
import com.school.miniinter.models.TeachingAssignment.TeachingAssignment;
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
                case ("showClass") -> {
                    showClass(req, resp);
                }
                case ("editClass") -> {
                    editClass(req, resp);
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
        Class classroom = clas.read((Integer) req.getAttribute("classroom"));
        HttpSession session = req.getSession();
        session.setAttribute("classroom", classroom);

        req.getRequestDispatcher("WEB-INF/admin/classroomShow.jsp");
    }

    private void showClasses(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ClassDAO clas = new ClassDAO();
        List<Class> classes = clas.read();
        HttpSession session = req.getSession();
        session.setAttribute("classes", classes);

        req.getRequestDispatcher("WEB-INF/admin/classes.jsp");
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
        Teaching[] aulas = assign.read();
        session.setAttribute("aulas", aulas);

        req.getRequestDispatcher("WEB-INF/admin/classroomEdit.jsp").forward(req, resp);
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
                aulas[n] = new TeachingAssignment(
                        Integer.parseInt(req.getParameter("aula"+n+"Id")),
                        idClass,
                        Integer.parseInt(req.getParameter("aula"+n+"Subject")),
                        Integer.parseInt(req.getParameter("aula"+n+"Teacher")),
                        n
                );
            }

            for (TeachingAssignment aula : aulas) {
                if ((aula.getId() == null) && !aula.isNull()) {
                    assign.insert(aula);
                } else {
                    assign.update(aula);
                }
            }

            req.getRequestDispatcher("/adminClasses").forward(req, resp);

            HttpSession session = req.getSession();
            session.setAttribute("success", "Turma" + classroom.getSeries() + "°" + classroom.getClassroom() + " alterada com sucesso!");
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
                req.getRequestDispatcher("/adminClasses").forward(req, resp);

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
