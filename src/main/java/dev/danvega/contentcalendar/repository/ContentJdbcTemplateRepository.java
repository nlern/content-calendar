package dev.danvega.contentcalendar.repository;

import dev.danvega.contentcalendar.model.Content;
import dev.danvega.contentcalendar.model.Status;
import dev.danvega.contentcalendar.model.Type;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class ContentJdbcTemplateRepository {

    private final JdbcTemplate jdbcTemplate;

    public ContentJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static Content mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Content(rs.getInt("id"),
                rs.getString("title"),
                rs.getString("desc"),
                Status.valueOf(rs.getString("status")),
                Type.valueOf(rs.getString("content_type")),
                rs.getObject("date_created", LocalDateTime.class),
                rs.getObject("date_updated", LocalDateTime.class),
                rs.getString("url")
        );
    }

    public List<Content> findAll() {
        String sql = "SELECT * FROM Content";
        List<Content> contents = jdbcTemplate.query(sql, ContentJdbcTemplateRepository::mapRow);
        return contents;
    }

    public Content findById(int id) {
        String sql = "SELECT * FROM Content WHERE id=?";
        return jdbcTemplate.queryForObject(sql, ContentJdbcTemplateRepository::mapRow, id);
    }

    public void add(String title, String desc, String status, String contentType, String url) {
        String sql = "INSERT INTO Content (title, desc, status, content_type, date_created, url) VALUES (?, ?, ?, ?, NOW(), ?)";
        jdbcTemplate.update(sql, title, desc, status, contentType, url);
    }

    public void update(int id, String title, String desc, String status, String contentType, String url) {
        String sql = "UPDATE Content SET title=?, desc=?, status=?, content_type=?, date_updated=NOW(), url=? WHERE id=?";
        jdbcTemplate.update(sql, title, desc, status, contentType, url, id);
    }

    public void delete(int id) {
        String sql = "DELETE FROM Content WHERE id=?";
        jdbcTemplate.update(sql, id);
    }

    public boolean existsById(int id) {
        String sql = "SELECT COUNT(*) FROM Content WHERE id=?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count > 0;
    }
}
