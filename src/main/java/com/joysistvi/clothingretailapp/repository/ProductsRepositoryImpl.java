package com.joysistvi.clothingretailapp.repository;

import com.joysistvi.clothingretailapp.config.DbConnection;
import com.joysistvi.clothingretailapp.model.Products;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductsRepositoryImpl implements ProductsRepository {
    private final DbConnection dbConnection;

    public ProductsRepositoryImpl(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public List<Products> findAllActive() {
        List<Products> products = new ArrayList<>();
        String query = "SELECT p.id, p.name, p.description, p.category_id, c.name AS category_name " +
                "FROM products p " +
                "JOIN categories c ON p.category_id = c.id " +
                "WHERE p.is_archived = 0 " +
                "ORDER BY p.id ASC";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query);
             ResultSet rs = prep.executeQuery()) {

            while (rs.next()) {
                products.add(new Products(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getInt("category_id"),
                        rs.getString("category_name")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Repository Error (findAllActive): " + e.getMessage());
        }
        return products;
    }

    @Override
    public List<Products> findAllArchived() {
        List<Products> products = new ArrayList<>();
        String query = "SELECT p.id, p.name, p.description, p.category_id, c.name AS category_name " +
                "FROM products p " +
                "JOIN categories c ON p.category_id = c.id " +
                "WHERE p.is_archived = 1 " +
                "ORDER BY p.id ASC";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query);
             ResultSet rs = prep.executeQuery()) {

            while (rs.next()) {
                products.add(new Products(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getInt("category_id"),
                        rs.getString("category_name")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Repository Error (findAllArchived): " + e.getMessage());
        }
        return products;
    }

    @Override
    public List<Products> findActiveByCategory(int categoryId) {
        List<Products> products = new ArrayList<>();
        String query = "SELECT p.id, p.name, p.description, p.category_id, c.name AS category_name " +
                "FROM products p " +
                "JOIN categories c ON p.category_id = c.id " +
                "WHERE p.is_archived = 0 AND p.category_id = ? " +
                "ORDER BY p.id ASC";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setInt(1, categoryId);

            try (ResultSet rs = prep.executeQuery()) {
                while (rs.next()) {
                    products.add(new Products(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("description"),
                            rs.getInt("category_id"),
                            rs.getString("category_name")
                    ));
                }
            }
        } catch (SQLException e) {
            System.out.println("Repository Error (findActiveByCategory): " + e.getMessage());
        }
        return products;
    }

    @Override
    public int countActiveByCategory(int categoryId) {
        String query = "SELECT COUNT(*) AS total " +
                "FROM products p " +
                "WHERE p.is_archived = 0 AND p.category_id = ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setInt(1, categoryId);

            try (ResultSet rs = prep.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total");
                }
            }
        } catch (SQLException e) {
            System.out.println("Repository Error (countActiveByCategory): " + e.getMessage());
        }
        return 0;
    }

    @Override
    public boolean save(Products product) {
        String query = "INSERT INTO products (name, description, category_id) VALUES (?, ?, ?)";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setString(1, product.getName());
            prep.setString(2, product.getDescription());
            prep.setInt(3, product.getCategoryId());
            return prep.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Repository Error (save): " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(Products product) {
        String query = "UPDATE products SET name = ?, description = ?, category_id = ? WHERE id = ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setString(1, product.getName());
            prep.setString(2, product.getDescription());
            prep.setInt(3, product.getCategoryId());
            prep.setInt(4, product.getId());
            return prep.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Repository Error (update): " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteById(int id) {
        String query = "DELETE FROM products WHERE id = ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setInt(1, id);
            return prep.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Repository Error (deleteById): " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean archiveById(int id) {
        String query = "UPDATE products SET is_archived = 1 WHERE id = ?";

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
        String query = "UPDATE products SET is_archived = 0 WHERE id = ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setInt(1, id);
            return prep.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Repository Error (restoreById): " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Products> searchByName(String keyword) {
        List<Products> products = new ArrayList<>();
        String query = "SELECT p.id, p.name, p.description, p.category_id, c.name AS category_name " +
                "FROM products p " +
                "JOIN categories c ON p.category_id = c.id " +
                "WHERE p.is_archived = 0 AND p.name LIKE ? " +
                "ORDER BY p.id ASC";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setString(1, "%" + keyword + "%");

            try (ResultSet rs = prep.executeQuery()) {
                while (rs.next()) {
                    products.add(new Products(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("description"),
                            rs.getInt("category_id"),
                            rs.getString("category_name")
                    ));
                }
            }
        } catch (SQLException e) {
            System.out.println("Repository Error (searchByName): " + e.getMessage());
        }
        return products;
    }

    @Override
    public Products findById(int id) {
        String query = "SELECT p.id, p.name, p.description, p.category_id, c.name AS category_name " +
                "FROM products p " +
                "JOIN categories c ON p.category_id = c.id " +
                "WHERE p.id = ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setInt(1, id);

            try (ResultSet rs = prep.executeQuery()) {
                if (rs.next()) {
                    return new Products(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("description"),
                            rs.getInt("category_id"),
                            rs.getString("category_name")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Repository Error (findById): " + e.getMessage());
        }
        return null;
    }
}