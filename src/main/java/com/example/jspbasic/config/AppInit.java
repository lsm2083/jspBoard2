package com.example.jspbasic.config;

import com.example.jspbasic.repo.JdbcPostRepository;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import javax.sql.DataSource;
import java.sql.*;

@WebListener
public class AppInit implements ServletContextListener {

    private HikariDataSource ds; // 필드로 보관

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("[AppInit] START");

        try {
            HikariConfig cfg = new HikariConfig();
            cfg.setJdbcUrl("jdbc:h2:~/jspboard2;MODE=MySQL;DATABASE_TO_LOWER=TRUE;AUTO_SERVER=TRUE");
            cfg.setUsername("sa");
            cfg.setPassword("");
            cfg.setMaximumPoolSize(5);
            cfg.setDriverClassName("org.h2.Driver"); // 명시

            // (선택) 풀 이름 주면 로그 구분 쉬움
            cfg.setPoolName("jspBoard2Pool");

            // 드라이버 존재 확인 (이미 넣어둔 코드)
            Class.forName("org.h2.Driver");
            System.out.println("[AppInit] H2 driver present ✔");

            ds = new HikariDataSource(cfg);

            try (Connection c = ds.getConnection()) {
                System.out.println("[AppInit] H2 connected ✔");
            }

            try (Connection c = ds.getConnection(); Statement st = c.createStatement()) {
                st.execute("""
          create table if not exists posts(
            id bigint auto_increment primary key,
            title varchar(200) not null,
            content clob not null,
            author varchar(50) not null,
            created_at timestamp not null default current_timestamp
          )
        """);
            }
            System.out.println("[AppInit] schema ok ✔");

            // 애플리케이션에서 레포지토리 사용
            sce.getServletContext().setAttribute("repo",
                    new com.example.jspbasic.repo.JdbcPostRepository(ds));
            System.out.println("[AppInit] repo=JDBC ✔");

        } catch (Exception e) {
            e.printStackTrace();
            sce.getServletContext().setAttribute("repo",
                    new com.example.jspbasic.repo.MemoryPostRepository());
            System.out.println("[AppInit] repo=Memory (fallback) ⚠");
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("[AppInit] shutdown");
        // 1) Hikari 풀 정리 → housekeeper 쓰레드 종료
        if (ds != null) {
            try { ds.close(); } catch (Exception ignore) {}
        }
        // 2) 이 웹앱이 로드한 JDBC 드라이버 해제 (메모리 누수 경고 방지)
        try {
            var it = java.sql.DriverManager.getDrivers();
            while (it.hasMoreElements()) {
                var d = it.nextElement();
                if (d.getClass().getClassLoader() == getClass().getClassLoader()) {
                    java.sql.DriverManager.deregisterDriver(d);
                }
            }
        } catch (Exception ignore) {}
    }
}

