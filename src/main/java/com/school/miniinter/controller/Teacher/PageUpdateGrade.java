package com.school.miniinter.controller.Teacher;

import com.school.miniinter.dao.ClassDAO;
import com.school.miniinter.dao.TeachersDAO;
import com.school.miniinter.models.Class.Class;
import com.school.miniinter.models.Students.GradeForStudent;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet(name="UpdateGrade", value={"/updateGrade"})
public class PageUpdateGrade extends HttpServlet {

    ClassDAO classDAO = new ClassDAO();
    TeachersDAO teachersDAO = new TeachersDAO();
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
            IOException{
        HttpSession session =  request.getSession();
        Integer idTeacher = (Integer)session.getAttribute("idTeacher");
        Integer idSubject = (Integer) session.getAttribute("subject");

        if(idTeacher == null){
            response.sendRedirect(request.getContextPath()+"/authentication/login.jsp");
        }else{
            List<Class> list = classDAO.readClassByTeacherAndSubject(idSubject,
                    idSubject);

            request.setAttribute("listClass",list);

            Class firstClassShow = list.get(0);
            List<GradeForStudent> list1 =
                    teachersDAO.readGradeStudentBySubjectAndClass(idTeacher,
                            idSubject,firstClassShow);

            request.setAttribute("listGradeByStudent",list1);

            request.getRequestDispatcher("WEB-INF/updateGradeTeacher.jsp").forward(request,response);

//            próximo passo: testas no jsp para ver se os atributos estão
//            vindo certinhos
        }
    }
}
