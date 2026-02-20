package com.school.miniinter.utils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "changeSubject", urlPatterns = "/changeSubject")
public class SubjectUtils extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        int idSubject = Integer.parseInt(req.getParameter("subject"));
        session.setAttribute("subject", idSubject);
        req.getRequestDispatcher("/homeTeacher").forward(req, resp);
    }
}
