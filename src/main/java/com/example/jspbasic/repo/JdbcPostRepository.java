package com.example.jspbasic.repo;

import com.example.jspbasic.model.Post;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class JdbcPostRepository implements PostRepository {
    private final DataSource ds;

    public JdbcPostRepository(DataSource ds) { this.ds = ds; }

    @Override
    public Post save(String title, String content, String author) {
        String sql = "insert into posts(title, content, author) values(?,?,?)";
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, title);
            ps.setString(2, content);
            ps.setString(3, author);
            ps.executeUpdate();

            long id;
            try (ResultSet rs = ps.getGeneratedKeys()) {
                rs.next();
                id = rs.getLong(1);
            }
            return findById(id); // created_at 포함해서 다시 읽어오기
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Post findById(long id) {
        String sql = "select id,title,content,author,created_at from posts where id=?";
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return map(rs);
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Post> findAllDesc() {
        String sql = "select id,title,content,author,created_at from posts order by id desc";
        List<Post> list = new ArrayList<>();
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(map(rs));
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(long id, String title, String content, String author) {
        String sql = "update posts set title=?, content=? where id=? and author=?";
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, title);
            ps.setString(2, content);
            ps.setLong(3, id);
            ps.setString(4, author);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(long id, String author) {
        String sql = "delete from posts where id=? and author=?";
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.setString(2, author);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static Post map(ResultSet rs) throws SQLException {
        return new Post(
                rs.getLong("id"),
                rs.getString("title"),
                rs.getString("content"),
                rs.getString("author"),
                rs.getTimestamp("created_at").toLocalDateTime()
        );
    }
}
