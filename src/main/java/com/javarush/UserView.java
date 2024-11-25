package com.javarush;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/secret/userview.msp")
public class UserView extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {


        String html = getHtml(req);
        resp.getWriter().write(html);

    }


    private String getHtml(HttpServletRequest req) {
        Object oUser = req.getAttribute("user");
        User user = oUser != null
                ? (User) oUser
                : new User("", "");
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        return """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <title>Title</title>
                </head>
                <body>
                <form method='post'>
                    <input type="text" value="%s" name="login">
                    <input type="password" value="%s" name="login">
                    <input type="submit" name="send">
                </form>
                
                </body>
                </html>
                """.formatted(user.getLogin(), user.getPassword());
    }
}