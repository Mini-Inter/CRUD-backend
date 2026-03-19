package com.school.miniinter.controller.Teacher;

import com.school.miniinter.dao.ClassDAO;
import com.school.miniinter.dao.GradeDAO;
import com.school.miniinter.dao.TeachersDAO;
import com.school.miniinter.models.Class.Class;
import com.school.miniinter.models.Grades.GradeForSubject;
import com.school.miniinter.models.Grades.SimpleGrade;
import com.school.miniinter.models.Students.GradeForStudent;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet(name="UpdateGrade", urlPatterns={"/updateGrade"})
public class PageUpdateGrade extends HttpServlet {

    ClassDAO classDAO = new ClassDAO();
    TeachersDAO teachersDAO = new TeachersDAO();
    GradeDAO gradeDAO = new GradeDAO();
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
        showGrades(request, response);
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        HttpSession session =  request.getSession();
        Integer idTeacher = (Integer)session.getAttribute("idTeacher");

        String type = request.getParameter("type");
        double oldValue = Double.parseDouble(request.getParameter("value"));
        double newValue;
        try {
            newValue = Double.parseDouble(request.getParameter("data"));
        } catch (NumberFormatException ignore) {
            newValue = -1.0;
        }

        if (idTeacher == null) {
            response.sendRedirect(request.getContextPath()+"/authentication/login.jsp");
        } else {
            if (newValue == -1.0) {
                if (oldValue != -1.0) {
                    deleteGrade(request, response);
                }
                else showGrades(request, response);
            }
            else if (oldValue!=-1.0) {
                updateGrade(request, response);
            } else {
                addGrade(request, response);
            }
        }
    }
    private void showGrades(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session =  request.getSession();
        Integer idTeacher = (Integer) session.getAttribute("idTeacher");
        int idSubject = (Integer) session.getAttribute("subject");

        if (idTeacher == null) {
            response.sendRedirect(request.getContextPath()+"/Inicio/login.jsp");
        } else {
            List<Class> list = classDAO.readClassByTeacherAndSubject(idTeacher, idSubject);

            if (list.isEmpty()) {
                request.getRequestDispatcher("WEB-INF/teacher/homeProfessor.jsp").forward(request, response);
            }
            request.setAttribute("listClass", list);

            int idFirstClassShow = list.get(0).getId();
            request.setAttribute("id_class", idFirstClassShow);
            List<GradeForStudent> list1 = teachersDAO.readGradeStudentBySubjectAndClass(idTeacher,idSubject, idFirstClassShow);

            request.setAttribute("listGradeByStudent", list1);

            request.getRequestDispatcher("WEB-INF/teacher/throwGrade.jsp").forward(request
                    , response);
        }
    }
    private void updateGrade(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session =  request.getSession();
        int idData = Integer.parseInt(request.getParameter("idData"));
        double newValue = Double.parseDouble(request.getParameter("data"));

        if(!gradeDAO.updateGradeById(idData, newValue)){
            session.setAttribute("error","Não foi possível fazer a alteração. Tente novamente.");
        }

        showGrades(request, response);
    }
    private void addGrade(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session =  request.getSession();
        String rawValue = request.getParameter("data");
        double value = -1.0;

        try {
            value = Double.parseDouble(rawValue);
        } catch (NumberFormatException ignored) {}

        Integer id_student = Integer.parseInt(request.getParameter("student"));
        Integer id_subject = (Integer) session.getAttribute("subject");
        String grade_type = request.getParameter("type");

        SimpleGrade simpleGrade = new SimpleGrade(id_student,id_subject,value,grade_type);
        gradeDAO.insertGradeByStudent(simpleGrade);

        showGrades(request, response);
    }
    private void deleteGrade(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session =  request.getSession();
        int idData = Integer.parseInt(request.getParameter("idData"));

        if(!gradeDAO.delete(idData)){
            session.setAttribute("error","Não foi possível fazer a alteração. Tente novamente.");
        }

        showGrades(request, response);
    }
}
