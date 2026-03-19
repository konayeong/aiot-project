package com.nhnacademy.shoppingmall.order.repository.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.order.domain.Order;
import com.nhnacademy.shoppingmall.order.domain.OrderItem;
import com.nhnacademy.shoppingmall.order.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class OrderRepositoryImpl implements OrderRepository {
    @Override
    public int save(Order order) {
        String sql = "insert into orders (user_id, address_id, used_point, created_at) values(?,?,?,?)";
        Connection connection = DbConnectionThreadLocal.getConnection();

        try(PreparedStatement pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstm.setString(1, order.getUserId());
            pstm.setInt(2, order.getAddressId());
            pstm.setInt(3, order.getUsedPoint());
            pstm.setTimestamp(4, Timestamp.valueOf(order.getCreatedAt()));
            pstm.executeUpdate();

            ResultSet rs = pstm.getGeneratedKeys();
            if(rs.next()) {
                order.setOrderId(rs.getInt(1));
                return order.getOrderId();
            }
        } catch (SQLException e) {
            log.error("Order save error", e);
            throw new RuntimeException(e);
        }

        return 0;
    }

    @Override
    public Page<Order> findAllByUserId(String userId, int page, int pageSize) {
        Connection connection = DbConnectionThreadLocal.getConnection();

        List<Order> content = new ArrayList<>();
        int offset = (page - 1) * pageSize;
        String sql = "select * from orders where user_id=? order by created_at desc limit ?, ?";

        try(PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setString(1, userId);
            pstm.setInt(2, offset);
            pstm.setInt(3, pageSize);

            ResultSet rs = pstm.executeQuery();
            while(rs.next()) {
                content.add(new Order(
                        rs.getInt("order_id"),
                        rs.getString("user_id"),
                        rs.getInt("address_id"),
                        rs.getInt("used_point"),
                        rs.getTimestamp("created_at").toLocalDateTime()
                ));
            }
        } catch (SQLException e) {
            log.error("Order findAllByUserId error", e);
            throw new RuntimeException(e);
        }

        long totalCount = totalCountByUserId(userId);

        return new Page<>(content, totalCount);
    }

    @Override
    public long totalCountByUserId(String userId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "select count(*) from orders where user_id=?";

        try(PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setString(1, userId);
            ResultSet rs = pstm.executeQuery();
            if(rs.next()) {
                return rs.getLong(1);
            }
        } catch (SQLException e) {
            log.error("Order totalCountByUserId error", e);
            throw new RuntimeException(e);
        }

        return 0L;
    }

    @Override
    public List<OrderItem> findOrderDetailsByOrderId(int orderId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        List<OrderItem> details = new ArrayList<>();
        String sql = "select p.product_id, p.product_name, oi.price, oi.quantity , p.image "+
                    "from order_items oi " +
                    "join products p on oi.product_id = p.product_id " +
                    "where oi.order_id = ?";

        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setInt(1, orderId);
            ResultSet rs = pstm.executeQuery();

            while(rs.next()) {
                details.add(new OrderItem(
                        rs.getInt("product_id"),
                        rs.getString("product_name"),
                        rs.getInt("price"),
                        rs.getInt("quantity"),
                        rs.getString("image")
                ));
            }
        } catch (SQLException e) {
            log.error("Order findOrderDetailsByOrderId error", e);
            throw new RuntimeException(e);
        }
        return details;
    }
}
