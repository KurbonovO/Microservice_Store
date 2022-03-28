package com.example.order.demo.repo;

import com.example.order.demo.entity.Order;
import com.example.order.demo.entity.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class OrderRepository {
    @Autowired
    private DataSource dataSource;

    public void createOrder(Order order){
        JdbcTemplate jt = new JdbcTemplate(dataSource);
        String sql = "INSERT INTO ORDERS (TOTAL) VALUES (?)";
        //jt.update("INSERT INTO ORDER (TOTAL) VALUES (?)", order.getTotal());
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jt.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(sql);
            ps.setDouble(1, order.getTotal());
            return ps;
        }, keyHolder);

        long id =  (long) keyHolder.getKey();
        order.setId(id);
        createOrderItem(order);
    }

    public void createOrderItem(final Order order){
        JdbcTemplate jt = new JdbcTemplate(dataSource);
        String sql = "INSERT INTO ORDER_ITEM (ORDER_ID, ITEM_ID, QTY) VALUES (?,?,?)";
        order.getItems().stream()
                        .forEach(i-> jt.update(sql, order.getId(), i.getItemId(), i.getQty()));

    }

    public List<Order> getAll(){
        JdbcTemplate jt = new JdbcTemplate(dataSource);
        List<Order> result =jt.query("SELECT * FROM ORDERS", new RowMapper<Order>() {
            @Override
            public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
                Order order = new Order();
                order.setId(rs.getLong("id"));
                order.setTotal(rs.getDouble("total"));
                return order;
            }
        });
        result.stream().forEach(o-> {
            List<OrderItem> items = jt.query("SELECT * FROM ORDER_ITEM WHERE ORDER_ID = ?", new RowMapper<OrderItem>() {
                @Override
                public OrderItem mapRow(ResultSet rs, int rowNum) throws SQLException {
                    OrderItem oi = new OrderItem();
                    oi.setItemId(rs.getInt("ITEM_ID"));
                    oi.setQty(rs.getInt("QTY"));
                    return oi;
                }
            }, o.getId());
            o.setItems(items);
        });
        return result;
    }
}
