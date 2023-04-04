package com.internetx.repository.impl;

import com.internetx.domain.Role;
import com.internetx.repository.IRoleRepository;
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
import java.util.List;
import java.util.Optional;

@Repository
public class RoleRepositoryDefaultImpl implements IRoleRepository {


    @Autowired
    protected JdbcTemplate jdbcTemplate;

    @Override
    public int insert(Role role) {
        String INSERT_SQL = "insert into role (id, user_id, role_admin, role_develop, role_cctld, role_gtld, " +
                "role_billing, role_registry, role_purchase_read,role_purchase_write, role_sale_write, role_sql) " +
                "values(?,?,?,?,?,?,?,?,?,?,?,?)";

        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS);
                ps.setLong(1, role.getId());
                ps.setLong(2, role.getUserId());
                ps.setBoolean(3, role.isRoleAdmin());
                ps.setBoolean(4, role.isRoleDevelop());
                ps.setBoolean(5, role.isRoleCctld());
                ps.setBoolean(6, role.isRoleGtld());
                ps.setBoolean(7, role.isRoleBilling());
                ps.setBoolean(8, role.isRoleRegistry());
                ps.setBoolean(9, role.isRolePurchase_read());
                ps.setBoolean(10, role.isRolePurchase_write());
                ps.setBoolean(11, role.isRoleSale_write());
                ps.setBoolean(12, role.isRoleSql());
                return ps;
            }
        }, holder);

        int newUserId = holder.getKey().intValue();
        return newUserId;
    }

    @Override
    public void update(Role role) {

        String UPDATE_SQL = "update role set user_id = ?, role_admin =  ?, role_develop = ?, role_cctld = ?, role_gtld= ?, " +
                " role_billing = ?, role_registry = ?, role_purchase_read = ?, role_purchase_write = ?, " +
                "role_sale_write = ?, role_sql = ? where id = ?";

        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(UPDATE_SQL);


                ps.setLong(1, role.getUserId());
                ps.setBoolean(2, role.isRoleAdmin());
                ps.setBoolean(3, role.isRoleDevelop());
                ps.setBoolean(4, role.isRoleCctld());
                ps.setBoolean(5, role.isRoleGtld());
                ps.setBoolean(6, role.isRoleBilling());
                ps.setBoolean(7, role.isRoleRegistry());
                ps.setBoolean(8, role.isRolePurchase_read());
                ps.setBoolean(9, role.isRolePurchase_write());
                ps.setBoolean(10, role.isRoleSale_write());
                ps.setBoolean(11, role.isRoleSql());
                ps.setLong(12, role.getId());
                return ps;
            }
        });


    }

    @Override
    public Optional<Role> findByID(long id) {
        String sql = "select * from role where id = ?";

        Role roleCreated = null;

        try {

            roleCreated = jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) ->
                    new Role(
                            rs.getLong("id"),
                            rs.getInt("user_id"),
                            rs.getBoolean("role_admin"),
                            rs.getBoolean("role_develop"),
                            rs.getBoolean("role_cctld"),
                            rs.getBoolean("role_gtld"),
                            rs.getBoolean("role_billing"),
                            rs.getBoolean("role_registry"),
                            rs.getBoolean("role_purchase_read"),
                            rs.getBoolean("role_purchase_write"),
                            rs.getBoolean("role_sale_write"),
                            rs.getBoolean("role_sql")
                    ));


        } catch (EmptyResultDataAccessException ex) {

            return Optional.ofNullable(null);

        }

        return Optional.ofNullable(roleCreated);
    }


    @Override
    public Optional<Role> findRoleByUserId(long user_id) {
        String sql = "select * from role where user_id = ?";

        Role roleCreated = null;

        try {

            roleCreated = jdbcTemplate.queryForObject(sql, new Object[]{user_id}, (rs, rowNum) ->
                    new Role(
                            rs.getLong("id"),
                            rs.getInt("user_id"),
                            rs.getBoolean("role_admin"),
                            rs.getBoolean("role_develop"),
                            rs.getBoolean("role_cctld"),
                            rs.getBoolean("role_gtld"),
                            rs.getBoolean("role_billing"),
                            rs.getBoolean("role_registry"),
                            rs.getBoolean("role_purchase_read"),
                            rs.getBoolean("role_purchase_write"),
                            rs.getBoolean("role_sale_write"),
                            rs.getBoolean("role_sql")
                    ));
        } catch (EmptyResultDataAccessException ex){

            return Optional.ofNullable(null);

        }

        return Optional.ofNullable(roleCreated);

    }

    @Override
    public void deleteRole(int id) {
        String sql = "delete from role where id = ?";
        Object[] args = new Object[] {id};

        jdbcTemplate.update(sql, args);
    }

    @Override
    public List<Role> findAll() {
        return jdbcTemplate.query(
                "select * from role",
                (rs, rowNum) ->
                        new Role(
                                rs.getLong("id"),
                                rs.getInt("user_id"),
                                rs.getBoolean("role_admin"),
                                rs.getBoolean("role_develop"),
                                rs.getBoolean("role_cctld"),
                                rs.getBoolean("role_gtld"),
                                rs.getBoolean("role_billing"),
                                rs.getBoolean("role_registry"),
                                rs.getBoolean("role_purchase_read"),
                                rs.getBoolean("role_purchase_write"),
                                rs.getBoolean("role_sale_write"),
                                rs.getBoolean("role_sql")
                        )
        );
    }

    @Override
    public int count() {
        return jdbcTemplate.queryForObject(
                "select count(*) from role", Integer.class);
    }


    public void cleanUpDataBaseAfterID(int id) {

        String sql = "delete from role where id > ?";
        Object[] args = new Object[] {id};

        jdbcTemplate.update(sql, args);

    }




}
