package com.javarush.controller;


import com.javarush.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;


@WebServlet("")
@Slf4j
public class IndexController extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("doGet start URI: {}", req.getRequestURI());
        HttpSession session = req.getSession();
        Object user = session.getAttribute("user");
        req.setAttribute("user", user);
        req.getRequestDispatcher("/secret/userview.msp").forward(req, resp);
        log.info("doGet finish URI: {}", req.getRequestURI());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        User user = new User(login, password);
        HttpSession session = req.getSession();
        session.setAttribute("user", user);
        resp.sendRedirect("/");

    }


}
