package com.internetx.repository.impl;

import com.internetx.domain.User;
import com.internetx.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

@Repository
public class UserRepositoryDefaultImpl implements IUserRepository {

    @Autowired
    protected JdbcTemplate jdbcTemplate;


    @Override
    public int insert(User user) {

        String INSERT_SQL = "insert into user (id, login, password, fname, lname, email) values(?,?,?,?,?,?)";

        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS);
                ps.setLong(1, user.getId());
                ps.setString(2, user.getLogin());
                ps.setString(3, user.getPassword());
                ps.setString(4, user.getFname());
                ps.setString(5, user.getLname());
                ps.setString(6, user.getEmail());
                return ps;
            }
        }, holder);




        int newUserId = holder.getKey().intValue();

        user.setId(newUserId);
        return newUserId;
    }

    @Override
    public void update(User user) {

        String UPDATE_SQL = "update user set login = ?, password = ?, fname = ?, lname = ?, email = ? where id = ?";

        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(UPDATE_SQL);

                ps.setString(1, user.getLogin());
                ps.setString(2, user.getPassword());
                ps.setString(3, user.getFname());
                ps.setString(4, user.getLname());
                ps.setString(5, user.getEmail());
                ps.setLong(6, user.getId());
                return ps;
            }
        });


    }

    @Override
    public Optional<User> findByID(long id) {

        String sql = "select * from user where id = ?";

        User userCreated = null;

        try {


            userCreated = jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) ->
                    new User(
                            rs.getLong("id"),
                            rs.getString("login"),
                            rs.getString("password"),
                            rs.getString("fname"),
                            rs.getString("lname"),
                            rs.getString("email"),
                            null
                    ));

        } catch (EmptyResultDataAccessException ex) {

            return Optional.ofNullable(null);

        }

        return Optional.ofNullable(userCreated);
    }


    @Override
    public Optional<User> findByEmail(String email) {
        String sql = "select * from user where email = ?";

        User userCreated = null;

        try {
            userCreated = jdbcTemplate.queryForObject(sql, new Object[]{email}, (rs, rowNum) ->
                    new User(
                            rs.getLong("id"),
                            rs.getString("login"),
                            rs.getString("password"),
                            rs.getString("fname"),
                            rs.getString("lname"),
                            rs.getString("email"),
                            null
                    ));

        } catch(EmptyResultDataAccessException ex) {


        }


        return Optional.ofNullable(userCreated);
    }

    @Override
    public void deleteUser(int id) {

        String sql = "delete from user where id = ?";
        Object[] args = new Object[] {id};

        jdbcTemplate.update(sql, args);

    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query(
                "select * from user",
                (rs, rowNum) ->
                        new User(
                                rs.getLong("id"),
                                rs.getString("login"),
                                rs.getString("password"),
                                rs.getString("fname"),
                                rs.getString("lname"),
                                rs.getString("email"),
                                null
                        )
        );
    }

    @Override
    public int count() {
        return jdbcTemplate.queryForObject(
                "select count(*) from user", Integer.class);
    }


    @Override
    public void cleanUpDataBaseAfterID(int id) {

        String sql = "delete from user where id > ?";
        Object[] args = new Object[] {id};

        jdbcTemplate.update(sql, args);

    }
}
