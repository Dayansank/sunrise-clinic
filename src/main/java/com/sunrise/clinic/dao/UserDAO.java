package com.sunrise.clinic.dao;

import com.sunrise.clinic.model.AdminUser;
import com.sunrise.clinic.model.ReceptionUser;
import com.sunrise.clinic.model.StaffUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    public StaffUser findByUsername(String username) {
        String sql = "SELECT user_id, username, password_hash, full_name, role, active FROM staff_users WHERE username = ?";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    StaffUser user = mapUser(rs);
                    user.setActive(rs.getBoolean("active"));
                    return user;
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Unable to load staff user.", e);
        }
        return null;
    }

    public StaffUser findById(int userId) {
        String sql = "SELECT user_id, username, password_hash, full_name, role, active FROM staff_users WHERE user_id = ?";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    StaffUser user = mapUser(rs);
                    user.setActive(rs.getBoolean("active"));
                    return user;
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Unable to load staff user.", e);
        }
        return null;
    }

    public List<StaffUser> findAll() {
        String sql = "SELECT user_id, username, password_hash, full_name, role, active FROM staff_users ORDER BY role, full_name";
        List<StaffUser> users = new ArrayList<>();
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                StaffUser user = mapUser(rs);
                user.setActive(rs.getBoolean("active"));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Unable to list staff users.", e);
        }
        return users;
    }

    public void insert(String username, String passwordHash, String fullName, String role) {
        String sql = "INSERT INTO staff_users (username, password_hash, full_name, role, active) VALUES (?, ?, ?, ?, 1)";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            statement.setString(2, passwordHash);
            statement.setString(3, fullName);
            statement.setString(4, role);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Unable to create staff user.", e);
        }
    }

    public boolean deleteById(int userId) {
        String sql = "DELETE FROM staff_users WHERE user_id = ?";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new IllegalStateException("Unable to delete staff user.", e);
        }
    }

    public void deactivate(int userId) {
        String sql = "UPDATE staff_users SET active = 0 WHERE user_id = ?";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Unable to deactivate staff user.", e);
        }
    }

    public int countActiveAdmins() {
        String sql = "SELECT COUNT(*) FROM staff_users WHERE role = 'ADMIN' AND active = 1";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            return rs.next() ? rs.getInt(1) : 0;
        } catch (SQLException e) {
            throw new IllegalStateException("Unable to count admins.", e);
        }
    }

    public int countAppointmentsCreatedBy(int userId) {
        String sql = "SELECT COUNT(*) FROM appointments WHERE created_by = ?";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            try (ResultSet rs = statement.executeQuery()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Unable to check staff appointments.", e);
        }
    }

    public String findPasswordHash(String username) {
        String sql = "SELECT password_hash FROM staff_users WHERE username = ? AND active = 1";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("password_hash");
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Unable to verify login.", e);
        }
        return null;
    }

    private StaffUser mapUser(ResultSet rs) throws SQLException {
        String role = rs.getString("role");
        StaffUser user = "ADMIN".equalsIgnoreCase(role) ? new AdminUser() : new ReceptionUser();
        user.setUserId(rs.getInt("user_id"));
        user.setUsername(rs.getString("username"));
        user.setFullName(rs.getString("full_name"));
        user.setRole(role);
        return user;
    }
}
