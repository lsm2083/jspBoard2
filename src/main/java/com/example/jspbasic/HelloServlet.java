package com.example.jspbasic;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;

public class HelloServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String name = req.getParameter("name");
        req.setAttribute("name", (name == null || name.isBlank()) ? "World" : name);
        req.getRequestDispatcher("/hello.jsp").forward(req, resp);



    }
}
