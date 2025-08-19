package com.example.jspbasic.web;

import com.example.jspbasic.model.Post;
import com.example.jspbasic.repo.MemoryPostRepository;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/posts/*")
public class PostServlet extends HttpServlet {
    private MemoryPostRepository repo;

    @Override public void init() {
        ServletContext ctx = getServletContext();
        synchronized (ctx) {
            repo = (MemoryPostRepository) ctx.getAttribute("repo");
            if (repo == null) { repo = new MemoryPostRepository(); ctx.setAttribute("repo", repo); }
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
                req.setAttribute("post", repo.findByid(id));
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
<<<<<<< HEAD
            String author = (session != null) ? String.valueOf(session.getAttribute("loginId")) : null;
=======
            String author = (session != null) ? String.valueOf(session.getAttribute("logined")) : null;
>>>>>>> ef693b3 (feat(posts): 세션 로그인 아이디로 작성자 저장, 리다이렉트 경로 보강)
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
