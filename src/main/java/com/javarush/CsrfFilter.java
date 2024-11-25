package com.javarush;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.UUID;

@WebFilter({"/", "/secret/userview.msp"})

public class CsrfFilter extends HttpFilter {

    public static final String CSRF = "csrf";

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request.getMethod().equals("GET")) {
            addCsrf(request, response);
        } else if (request.getMethod().equals("POST")) {
            checkCsrf(request);
        }
        super.doFilter(request, response, chain);
    }

    private static void addCsrf(HttpServletRequest req, HttpServletResponse resp) {
        String csrf = UUID.randomUUID().toString();
        req.getSession().setAttribute(CSRF, csrf);
        Cookie cookie = new Cookie(CSRF, csrf);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(15 * 60);
        resp.addCookie(cookie);
    }

    private static void checkCsrf(HttpServletRequest req) {
        for (Cookie cookie : req.getCookies()) {
            if (cookie.getName().equals("csrf") && cookie.getValue().equals(req.getSession().getAttribute(CSRF))) {
                return;
            }
        }
        throw new RuntimeException("Csrf cookie not found");
    }
}
