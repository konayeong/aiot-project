package com.nhnacademy.shoppingmall.point.repository.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.point.domain.PointHistory;
import com.nhnacademy.shoppingmall.point.repository.PointHistoryRepository;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.Objects;

@Slf4j
public class PointHistoryRepositoryImpl implements PointHistoryRepository {
    @Override
    public int save(PointHistory pointHistory) {
        String sql = "insert into point_history (user_id, order_id, pointy_type, amount, created_at) values (?,?,?,?,?)";
        Connection connection = DbConnectionThreadLocal.getConnection();

        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, pointHistory.getUserId());

            if(Objects.isNull(pointHistory.getOrderId())) {
                statement.setNull(2, Types.INTEGER);
            } else {
                statement.setInt(2, pointHistory.getOrderId());
            }

            statement.setInt(3, pointHistory.getPointType().getValue());
            statement.setInt(4, pointHistory.getAmount());
            statement.setTimestamp(5, Timestamp.valueOf(pointHistory.getCreatedAt()));

            return statement.executeUpdate();
        } catch (SQLException e) {
            log.error("PointHistory save error", e);
            throw new RuntimeException(e);
        }
    }
}
