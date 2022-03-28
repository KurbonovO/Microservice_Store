package com.example.inventory.demo.repo;

import com.example.inventory.demo.entity.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class InventoryRepository {

    @Autowired
    private DataSource dataSource;

    public void addItem(Item item){
        JdbcTemplate jt = new JdbcTemplate(dataSource);
        jt.update("INSERT INTO INVENTORY (NAME, PRICE, QTY) VALUES (?,?,?)", item.getName(), item.getPrice(), item.getQty());
    }

    public List<Item> getAll(){
        JdbcTemplate jt = new JdbcTemplate(dataSource);
        List<Item> result =jt.query("SELECT * FROM INVENTORY", new RowMapper<Item>() {
            @Override
            public Item mapRow(ResultSet rs, int rowNum) throws SQLException {
                Item item = new Item();
                item.setId(rs.getInt("id"));
                item.setName(rs.getString("name"));
                item.setPrice(rs.getDouble("price"));
                item.setQty(rs.getInt("qty"));
                return item;
            }
        });
        return result;
    }

    public Item getItem(Integer id){
        JdbcTemplate jt = new JdbcTemplate(dataSource);
        Item result =jt.queryForObject("SELECT * FROM INVENTORY WHERE ID = ?", new RowMapper<Item>() {
            @Override
            public Item mapRow(ResultSet rs, int rowNum) throws SQLException {
                Item item = new Item();
                item.setId(rs.getInt("id"));
                item.setName(rs.getString("name"));
                item.setPrice(rs.getDouble("price"));
                item.setQty(rs.getInt("qty"));
                return item;
            }
        }, id);
        return result;
    }
}
