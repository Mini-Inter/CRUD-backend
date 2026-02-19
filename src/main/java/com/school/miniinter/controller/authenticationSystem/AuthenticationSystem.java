package com.school.miniinter.controller.authenticationSystem;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name="auth", value = {"/auth"})
public class AuthenticationSystem extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String type = req.getParameter("type");
        switch (type) {
            case "login" -> {
                String email = req.getParameter("email");
                String password = req.getParameter("pw");
                try {
                    if (email.equals("loren@vini.com")) {
                        if (password.equals("123")) {
                            req.getRequestDispatcher(req.getContextPath() + "/WEB-INF/home.jsp").forward(req, resp);
                        } else {
                            throw new IllegalArgumentException("Senha incorreta!");
                        }
                    } else {
                        throw new IllegalArgumentException("Email não encontrado!");
                    }
                } catch (NullPointerException exc) {
                    String error = "Alguns dados não foram preenchidos!";
                    HttpSession session = req.getSession();
                    session.setAttribute("error", error);
                    resp.sendRedirect(req.getContextPath() + "/authentication/login.jsp");
                } catch (IllegalArgumentException exc) {
                    String error = exc.getMessage();
                    HttpSession session = req.getSession();
                    session.setAttribute("error", error);
                    resp.sendRedirect(req.getContextPath() + "/authentication/login.jsp");
                }
            }
            case "signup" -> {

            }
        }
    }
}
