package com.school.miniinter.controller.authenticationSystem;

import com.school.miniinter.dao.StudentsDAO;
import com.school.miniinter.dao.TeachersDAO;
import com.school.miniinter.models.Teacher.Teacher;
import com.school.miniinter.utils.EmailUtils;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.mail.*;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.ServletException;
import jakarta.servlet.UnavailableException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Properties;

@WebServlet(name = "PasswordReset", urlPatterns = "/passwordReset")
public class PasswordReset extends HttpServlet {
    private String number;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String type = req.getParameter("type");
        try {
            switch (type) {
                case "sendCode" -> {
                    sendCode(req, resp);
                }
                case "verifyCode" -> {
                    verifyCode(req, resp);
                }
            }
        } catch (MessagingException exc) {
            String error = "Desculpe! Não é possível solicitar uma nova senha no momento, tente novamente mais tarde.";
            HttpSession session = req.getSession();
            session.setAttribute("error", error);
            resp.sendRedirect("/Inicio/password.jsp");
        } catch (IllegalArgumentException exc) {
            HttpSession session = req.getSession();
            session.setAttribute("error", exc.getMessage());
            resp.sendRedirect("/Inicio/password.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession siteSession = req.getSession();
        String login = (String) siteSession.getAttribute("login");
        String pw = req.getParameter("pw1");
        String pw2 = req.getParameter("pw2");
        if (pw2.equals(pw)) {
            StudentsDAO stud = new StudentsDAO();
            TeachersDAO teach = new TeachersDAO();
            int idStudent = stud.readIdByLogin(login);
            Teacher teacher = teach.readByLogin(login);

            if (idStudent == 0) {
                teacher.setPassword(pw);
                teach.update(teacher);
            } else {
                stud.updatePassword(pw, idStudent);
            }
            resp.sendRedirect(req.getContextPath()+"/Inicio/login.jsp");
        } else {
            String error = "As duas senhas são diferentes!";
            HttpSession session = req.getSession();
            session.setAttribute("error", error);
            req.getRequestDispatcher("WEB-INF/resetPassword.jsp").forward(req, resp);        }
    }

    private void sendCode(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, MessagingException {
        this.number = String.valueOf((int) (Math.random() * 1000000));
        HttpSession siteSession = req.getSession();
        String login = req.getParameter("login");
        Dotenv envVars = Dotenv.load();
        String email = envVars.get("EMAIL");
        String password = envVars.get("EMAIL_PASSWORD");

        if (login.equals("vinicius.vboas1@gmail.com") || EmailUtils.verifyEmail(login)) {
            siteSession.setAttribute("login", login.substring(login.indexOf("@")));

            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.imap.starttls.required", "true");
            props.put("mail.imap.ssl.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");

            Session session = Session.getInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(email, password);
                }
            });

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(email));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(login));
            message.setSubject("Solicitação de mudança de senha");
            message.setText("Olá! Alguém fez uma solicitação para mudar sua senha da Vidya." +
                    "\nCaso não seja você, apenas ignore este email com segurança.\nO código pedido é: '" + number + "'.");
            Transport.send(message);
            resp.sendRedirect(req.getContextPath()+"/Inicio/code.jsp");
        } else throw new IllegalArgumentException("Email não foi digitado corretamente! Siga a sintaxe 'nome.sobrenome@vidya.org.br'");
    }
    private void verifyCode(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, MessagingException {
        String code = req.getParameter("code");
        if (code.equals(number)) {
            req.getRequestDispatcher("WEB-INF/resetPassword.jsp").forward(req, resp);
        } else {
            String error = "Desculpe! Este não é o código solicitado. Tente novamente com outro código.";
            HttpSession session = req.getSession();
            session.setAttribute("error", error);
            resp.sendRedirect("/Inicio/code.jsp");
        }
    }
}
