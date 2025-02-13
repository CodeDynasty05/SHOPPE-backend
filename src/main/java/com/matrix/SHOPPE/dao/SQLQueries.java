package com.matrix.SHOPPE.dao;

public interface SQLQueries {
    String SELECT_ALL_USERS = "SELECT * FROM users";
    String SELECT_USER_BY_ID = "SELECT * FROM users WHERE id = :id";
    String INSERT_USER = "INSERT INTO users (name, email, password_hash) VALUES (:name, :email, :password)";
    String UPDATE_USER = "UPDATE users SET name = :name, email = :email, password_hash=:password WHERE id = :id";
    String DELETE_USER = "DELETE FROM users WHERE id = :id";
}
