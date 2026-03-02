package com.school.miniinter.utils;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.school.miniinter.dao.GradeDAO;
import com.school.miniinter.dao.StudentsDAO;
import com.school.miniinter.dao.TeachersDAO;
import com.school.miniinter.models.Grades.GradeForSubject;
import com.school.miniinter.models.Students.BasicInfo;
import com.school.miniinter.models.Students.Students;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.List;
import java.util.Map;

@WebServlet(name="alterarImagem", value={"/uploadImage"})
@MultipartConfig
public class ImageUtils extends HttpServlet {

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
            IOException{
        HttpSession session = request.getSession();
        String type = request.getParameter("type");

        switch (type){
            case ("student") -> {
                Object idStudentRaw = session.getAttribute("idStudent");
                if (idStudentRaw == null) {
                    response.sendRedirect(request.getContextPath()+"/Inicio" +
                            "/login.jsp");
                } else {
                    int idStudent = (Integer) idStudentRaw;
                    Part partFile = request.getPart("image");
                    InputStream fileContent = partFile.getInputStream();
                    byte[] bytes = fileContent.readAllBytes();
                    String contentType = partFile.getContentType();

                    if (!contentType.startsWith("image/")) {
                        throw new ServletException("Arquivo inválido");
                    }

                    Dotenv dotenv = Dotenv.load();
                    Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                            "cloud_name",dotenv.get("CLOUD_NAME") ,
                            "api_key", dotenv.get("API_KEY_CLOUDNARY"),
                            "api_secret", dotenv.get("SECRET_KEY_CLOUDNARY")
                    ));

                    Map uploadResult = cloudinary.uploader().upload(bytes,
                            ObjectUtils.emptyMap());

                    String imageUrl = (String) uploadResult.get("secure_url");

                    imageUrl = imageUrl.replace("/upload/","/upload/w_140," +
                            "h_190,c_fill,g_face,q_auto,f_auto,r_14/");

                    StudentsDAO studentsDAO = new StudentsDAO();

                    studentsDAO.updateImage(imageUrl,idStudent);

                    request.getRequestDispatcher("profileStudent").forward(request,response);
                }
            }
            default -> {
                Object idTeacherRaw = session.getAttribute("idTeacher");
                if (idTeacherRaw == null) {
                    response.sendRedirect(request.getContextPath()+"/Inicio" +
                            "/login.jsp");
                } else {
                    int idTeacher = (Integer) idTeacherRaw;
                    Part partFile = request.getPart("image");
                    InputStream fileContent = partFile.getInputStream();
                    byte[] bytes = fileContent.readAllBytes();
                    String contentType = partFile.getContentType();

                    if (!contentType.startsWith("image/")) {
                        throw new ServletException("Arquivo inválido");
                    }

                    Dotenv dotenv = Dotenv.load();
                    Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                            "cloud_name",dotenv.get("CLOUD_NAME") ,
                            "api_key", dotenv.get("API_KEY_CLOUDNARY"),
                            "api_secret", dotenv.get("SECRET_KEY_CLOUDNARY")
                    ));

                    Map uploadResult = cloudinary.uploader().upload(bytes,
                            ObjectUtils.emptyMap());

                    String imageUrl = (String) uploadResult.get("secure_url");

                    imageUrl = imageUrl.replace("/upload/","/upload/w_140," +
                            "h_190,c_fill,g_face,q_auto,f_auto,r_14/");

                    TeachersDAO teachersDAO = new TeachersDAO();

                    teachersDAO.updateImage(imageUrl,idTeacher);

                    request.getRequestDispatcher("teacherProfile").forward(request,response);
            }
        }
    }
}}
