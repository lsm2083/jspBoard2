package com.example.jspbasic.web;

import com.example.jspbasic.model.Post;
import com.example.jspbasic.repo.MemoryPostRepository;
import com.example.jspbasic.repo.PostRepository;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/posts/*")
public class PostServlet extends HttpServlet {
    private PostRepository repo;

    @Override
    public void init() {
        Object o = getServletContext().getAttribute("repo");
        if (o instanceof com.example.jspbasic.repo.PostRepository pr) {
            this.repo = pr;
        } else {
            // 아주 드문 경우 대비 폴백
            this.repo = new com.example.jspbasic.repo.MemoryPostRepository();
        }
    }


    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String path = req.getPathInfo(); // null, "/", "/new", "/detail"
        if (path == null || "/".equals(path)) {
            List<Post> posts = repo.findAllDesc();
            req.setAttribute("posts", posts);
            req.getRequestDispatcher("/WEB-INF/jsp/posts/list.jsp").forward(req, resp);
            return;
        }
        switch (path) {
            case "/new":
                req.getRequestDispatcher("/WEB-INF/jsp/posts/form.jsp").forward(req, resp);
                break;
            case "/detail":
                long id = Long.parseLong(req.getParameter("id"));
                req.setAttribute("post", repo.findById(id));
                req.getRequestDispatcher("/WEB-INF/jsp/posts/detail.jsp").forward(req, resp);
                break;
            default:
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String path = req.getPathInfo(); // "/create"
        if ("/create".equals(path)) {
            //제목&내용 안전처리
            String title = req.getParameter("title");
            String content = req.getParameter("content");

            //로그인 아이디 읽기 (세션이 없거나 값이 비어있으면 guest
            HttpSession session = req.getSession(false);

            String author = (session != null) ? String.valueOf(session.getAttribute("loginId")) : null;



            if (author == null || author.isBlank() || "null".equals(author)){
                author = "guest";
            }
            Post saved = repo.save(title, content, author);
            resp.sendRedirect(req.getContextPath() + "/posts/detail?id=" + saved.getId());
            return;
        }
        resp.sendError(HttpServletResponse.SC_NOT_FOUND);
    }
}
