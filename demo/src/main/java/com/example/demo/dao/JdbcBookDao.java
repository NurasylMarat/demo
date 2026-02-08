package com.example.demo.dao;

import com.example.demo.db.DatabaseConnection;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcBookDao implements BookDao {

    private Book map(ResultSet rs) throws SQLException {
        long id = rs.getLong("id");
        String title = rs.getString("title");
        String author = rs.getString("author");
        int year = rs.getInt("year");
        return new Book(id, title, author, year);
    }

    @Override
    public List<Book> getAll() {
        String sql = "SELECT id, title, author, year FROM books";
        List<Book> list = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement st = conn.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {

            while (rs.next()) list.add(map(rs));
            return list;

        } catch (SQLException e) {
            throw new RuntimeException("DB error in getAll()", e);
        }
    }

    @Override
    public Book getById(long id) {
        String sql = "SELECT id, title, author, year FROM books WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {

            st.setLong(1, id);

            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) return map(rs);
                throw new NotFoundException("Book not found: id=" + id);
            }

        } catch (SQLException e) {
            throw new RuntimeException("DB error in getById()", e);
        }
    }

    @Override
    public Book create(Book book) {
        String sql = "INSERT INTO books(title, author, year) VALUES (?, ?, ?) RETURNING id";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {

            st.setString(1, book.getTitle());
            st.setString(2, book.getAuthor());
            st.setInt(3, book.getYear());

            try (ResultSet rs = st.executeQuery()) {
                rs.next();
                long id = rs.getLong("id");
                book.setId(id);
                return book;
            }

        } catch (SQLException e) {
            throw new RuntimeException("DB error in create()", e);
        }
    }

    @Override
    public Book update(long id, Book book) {
        // проверим что существует
        getById(id);

        String sql = "UPDATE books SET title=?, author=?, year=? WHERE id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {

            st.setString(1, book.getTitle());
            st.setString(2, book.getAuthor());
            st.setInt(3, book.getYear());
            st.setLong(4, id);

            st.executeUpdate();
            return getById(id);

        } catch (SQLException e) {
            throw new RuntimeException("DB error in update()", e);
        }
    }

    @Override
    public void delete(long id) {
        String sql = "DELETE FROM books WHERE id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {

            st.setLong(1, id);
            int rows = st.executeUpdate();
            if (rows == 0) throw new NotFoundException("Book not found: id=" + id);

        } catch (SQLException e) {
            throw new RuntimeException("DB error in delete()", e);
        }
    }

    @Override
    public List<Book> findByTitle(String title) {
        String sql = "SELECT id, title, author, year FROM books WHERE LOWER(title) LIKE LOWER(?)";
        List<Book> list = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {

            st.setString(1, "%" + title + "%");

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) list.add(map(rs));
            }
            return list;

        } catch (SQLException e) {
            throw new RuntimeException("DB error in findByTitle()", e);
        }
    }

    @Override
    public List<Book> filterByYear(int fromYear) {
        String sql = "SELECT id, title, author, year FROM books WHERE year >= ? ORDER BY year";
        List<Book> list = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {

            st.setInt(1, fromYear);

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) list.add(map(rs));
            }
            return list;

        } catch (SQLException e) {
            throw new RuntimeException("DB error in filterByYear()", e);
        }
    }

    @Override
    public List<Book> sortByYear() {
        String sql = "SELECT id, title, author, year FROM books ORDER BY year";
        List<Book> list = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement st = conn.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {

            while (rs.next()) list.add(map(rs));
            return list;

        } catch (SQLException e) {
            throw new RuntimeException("DB error in sortByYear()", e);
        }
    }
}
