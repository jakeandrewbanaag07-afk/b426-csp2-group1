
package com.joysistvi.clothingretailapp.repository;

import com.joysistvi.clothingretailapp.config.DbConnection;
import com.joysistvi.clothingretailapp.model.Categories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoriesRepositoryImpl implements CategoriesRepository {

    private final DbConnection dbConnection;

    // Constructor
    public CategoriesRepositoryImpl(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    // ============================================================
    // RETRIEVE CATEGORIES
    // ============================================================

    @Override
    public List<Categories> findAllActive(int id) {
        return List.of();
    }

    @Override
    public List<Categories> findAllActive() {
        List<Categories> categories = new ArrayList<>();

        String query = "SELECT id, name, description " +
                "FROM categories " +
                "WHERE is_archived = 0 " +
                "ORDER BY id ASC";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query);
             ResultSet rs = prep.executeQuery()) {

            while (rs.next()) {
                categories.add(new Categories(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description")
                ));
            }

        } catch (SQLException e) {
            System.out.println("Repository Error (findAllActive): " + e.getMessage());
        }

        return categories;
    }

    @Override
    public List<Categories> findAllArchived() {
        List<Categories> categories = new ArrayList<>();

        String query = "SELECT id, name, description " +
                "FROM categories " +
                "WHERE is_archived = 1 " +
                "ORDER BY id ASC";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query);
             ResultSet rs = prep.executeQuery()) {

            while (rs.next()) {
                categories.add(new Categories(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description")
                ));
            }

        } catch (SQLException e) {
            System.out.println("Repository Error (findAllArchived): " + e.getMessage());
        }

        return categories;
    }

    // ============================================================
    // CATEGORY OPERATIONS
    // ============================================================

    @Override
    public boolean save(Categories category) {
        String query = "INSERT INTO categories (name, description) VALUES (?, ?)";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setString(1, category.getName());
            prep.setString(2, category.getDescription());

            return prep.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Repository Error (save): " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(Categories category) {
        String query = "UPDATE categories " +
                "SET name = ?, description = ? " +
                "WHERE id = ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setString(1, category.getName());
            prep.setString(2, category.getDescription());
            prep.setInt(3, category.getId());

            return prep.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Repository Error (update): " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteById(int id) {
        String query = "DELETE FROM categories WHERE id = ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setInt(1, id);

            return prep.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Repository Error (deleteById): " + e.getMessage());
            return false;
        }
    }

    // ============================================================
    // ARCHIVE OPERATIONS
    // ============================================================

    @Override
    public boolean archiveById(int id) {
        String query = "UPDATE categories " +
                "SET is_archived = 1 " +
                "WHERE id = ? AND is_archived = 0";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setInt(1, id);

            return prep.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Repository Error (archiveById): " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean restoreById(int id) {
        String query = "UPDATE categories " +
                "SET is_archived = 0 " +
                "WHERE id = ? AND is_archived = 1";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setInt(1, id);

            return prep.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Repository Error (restoreById): " + e.getMessage());
            return false;
        }
    }

    // ============================================================
    // SEARCH CATEGORIES
    // ============================================================

    @Override
    public List<Categories> searchByName(String keyword) {
        List<Categories> categories = new ArrayList<>();

        String query = "SELECT id, name, description " +
                "FROM categories " +
                "WHERE is_archived = 0 AND name LIKE ? " +
                "ORDER BY id ASC";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setString(1, "%" + keyword + "%");

            try (ResultSet rs = prep.executeQuery()) {
                while (rs.next()) {
                    categories.add(new Categories(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("description")
                    ));
                }
            }

        } catch (SQLException e) {
            System.out.println("Repository Error (searchByName): " + e.getMessage());
        }

        return categories;
    }

    // ============================================================
    // FIND CATEGORY BY ID
    // ============================================================

    @Override
    public Categories findById(int id) {
        String query = "SELECT id, name, description " +
                "FROM categories " +
                "WHERE id = ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setInt(1, id);

            try (ResultSet rs = prep.executeQuery()) {
                if (rs.next()) {
                    return new Categories(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("description")
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println("Repository Error (findById): " + e.getMessage());
        }

        return null;
    }
}
