package com.school.miniinter.controller.Teacher;

import com.school.miniinter.dao.SubjectsDAO;
import com.school.miniinter.dao.TeachersDAO;
import com.school.miniinter.models.Teacher.AmountStudentByTeacher;
import com.school.miniinter.models.Teacher.CompleteInfo;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet(name="TeacherProfile", value={"/teacherProfile"})
public class TeacherProfile extends HttpServlet {

    TeachersDAO teachersDAO = new TeachersDAO();
    SubjectsDAO subjectsDAO = new SubjectsDAO();
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
            IOException{
        HttpSession session = request.getSession();
        Integer id_teacher = (Integer) session.getAttribute("idTeacher");
        Integer subject = (Integer)  session.getAttribute("subject");

        String name_subject = subjectsDAO.read(subject).getName();

        CompleteInfo completeInfo =
                teachersDAO.readCompleteInfoTeacher(id_teacher);

        completeInfo.setSubject(name_subject);

        request.setAttribute("completeInfoTeacher",completeInfo);

        List<AmountStudentByTeacher> amountStudentByTeacher =
                teachersDAO.amountStudentByTeacherAndClass(id_teacher,subject);

        request.setAttribute("amountStudentByTeacher",amountStudentByTeacher);

        request.getRequestDispatcher("WEB-INF/teacher/profileTeacher.jsp").forward(request,response);

    }
}
