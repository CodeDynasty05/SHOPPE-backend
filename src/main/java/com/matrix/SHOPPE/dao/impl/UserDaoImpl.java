package com.matrix.SHOPPE.dao.impl;

import com.matrix.SHOPPE.dao.SQLQueries;
import com.matrix.SHOPPE.dao.UserDao;
import com.matrix.SHOPPE.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserDaoImpl implements UserDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public List<User> getUsers() {
        return jdbcTemplate.query(SQLQueries.SELECT_ALL_USERS, new BeanPropertyRowMapper<>(User.class));
    }

    @Override
    public User getUserById(Integer id) {
        return jdbcTemplate.
                queryForObject(SQLQueries.SELECT_USER_BY_ID,
                        new MapSqlParameterSource("id",id),
                        new BeanPropertyRowMapper<>(User.class));
    }

    @Override
    public void addUser(User user) {
        jdbcTemplate.update(SQLQueries.INSERT_USER,new BeanPropertySqlParameterSource(user));
    }

    @Override
    public void updateUser(User user) {
        jdbcTemplate.update(SQLQueries.UPDATE_USER,new BeanPropertySqlParameterSource(user));
    }

    @Override
    public void deleteUser(Integer id) {
        jdbcTemplate.update(SQLQueries.DELETE_USER, new MapSqlParameterSource("id",id));
    }
}
