package com.school.miniinter.controller.Teacher;

import com.school.miniinter.dao.ClassDAO;
import com.school.miniinter.dao.GradeDAO;
import com.school.miniinter.dao.TeachersDAO;
import com.school.miniinter.models.Class.Class;
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

@WebServlet(name="UpdateGrade", urlPatterns={"/updateGrade",
        "/updateData","/addData"})
public class PageUpdateGrade extends HttpServlet {

    ClassDAO classDAO = new ClassDAO();
    TeachersDAO teachersDAO = new TeachersDAO();
    GradeDAO gradeDAO = new GradeDAO();
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
            IOException{

        HttpSession session =  request.getSession();
        Integer idTeacher = (Integer)session.getAttribute("idTeacher");
        System.out.println(idTeacher);
        int idSubject = (Integer) session.getAttribute("subject");
        System.out.println(idSubject);
        String url = request.getServletPath();
        String changeClass = request.getParameter("changeClass");

        if(idTeacher == null){
            response.sendRedirect(request.getContextPath()+"/Inicio/login.jsp");
        }else{
            if(url.equals("/updateGrade") && changeClass == null) {
                List<Class> list = classDAO.readClassByTeacherAndSubject(idTeacher,
                        idSubject);

                if (list.isEmpty()) {
                    request.getRequestDispatcher("WEB-INF/teacher/homeProfessor.jsp").forward(request
                            , response);
                }
                request.setAttribute("listClass", list);

                Integer idFirstClassShow = list.get(0).getId();
                request.setAttribute("id_class",idFirstClassShow);
                List<GradeForStudent> list1 =
                        teachersDAO.readGradeStudentBySubjectAndClass(idTeacher,
                                idSubject, idFirstClassShow);

                request.setAttribute("listGradeByStudent", list1);

                request.getRequestDispatcher("WEB-INF/teacher/updateGradeTeacher.jsp").forward(request, response);
            }else if(url.equals("/updateGrade") && changeClass.equals("1")) {

                List<Class> list = classDAO.readClassByTeacherAndSubject(idTeacher,
                        idSubject);

                if(list.isEmpty()){
                    request.getRequestDispatcher("WEB-INF/teacher/homeProfessor.jsp").forward(request
                            , response);
                }

                request.setAttribute("listClass", list);

                Integer id_class = Integer.parseInt(request.getParameter(
                        "class"));
                request.setAttribute("id_class",id_class);

                List<GradeForStudent> list1 =
                        teachersDAO.readGradeStudentBySubjectAndClass(idTeacher,
                                idSubject, id_class);

                request.setAttribute("listGradeByStudent", list1);

                request.getRequestDispatcher("WEB-INF/teacher/updateGradeTeacher.jsp").forward(request, response);

            }else if(url.equals("/updateData")){

                System.out.println("AQUI");
                Integer idData = Integer.parseInt(request.getParameter(
                        "idData"));
                Double data = Double.parseDouble(request.getParameter("data"));

                int retorno = gradeDAO.updateGradeById(idData,data);

                if(retorno == 0){
//                    não sei como retornar uma mensagem para o jsp 😭 mas o
//                    retorno deve indicar que não foi encontrada o id da nota
                }else if(retorno > 1){
//                     mesma coisa aqui, porém indicar a alteração foi feita
//                     com sucesso
                }else{
//                    Indicar que houve um erro ao executar a query
                }
                Integer id_class= Integer.parseInt(request.getParameter(
                        "class"));
                request.getRequestDispatcher("updateGrade?changeClass=1&class" +
                        "="+id_class).forward(request,response);
            }else if(url.equals("/addData")){
                Double value = Double.parseDouble(request.getParameter("data"));
                Integer id_student = Integer.parseInt(request.getParameter(
                        "idStudent"));
                Integer id_subject = (Integer) session.getAttribute(
                        "subject");
                String grade_type = request.getParameter("n");

                SimpleGrade simpleGrade = new SimpleGrade(id_student,
                        id_subject,value,grade_type);
                if(gradeDAO.insertGradeByStudent(simpleGrade)){
//                    Colocar uma mensagem de nota lançada com sucesso
                }else{
//                    Colocar que houve algum erro ao cadastrar essa nota no
//                    sistema
                }

                Integer id_class= Integer.parseInt(request.getParameter(
                        "class"));
                request.getRequestDispatcher("updateGrade?changeClass=1&class" +
                        "="+id_class).forward(request,response);

            }
        }
    }
}
