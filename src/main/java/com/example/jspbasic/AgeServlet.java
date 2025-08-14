package com.example.jspbasic;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;


public class AgeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String ageParam = req.getParameter("age");
        String result;

        if (ageParam == null || ageParam.isBlank()) {
            result = "나이를 입력하세요.";
        } else {
            try {
                int age = Integer.parseInt(ageParam.trim());
                result = (age >= 20) ? "성인입니다." : "미성년자입니다.";
                // 숫자도 필요하면 함께 전달
                req.setAttribute("ageValue", age);
            } catch (NumberFormatException e) {
                result = "나이는 숫자로 입력하세요.";
            }
        }

        // JSP에서 ${age}로 메시지 출력
        req.setAttribute("age", result);
        req.getRequestDispatcher("/age.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}


