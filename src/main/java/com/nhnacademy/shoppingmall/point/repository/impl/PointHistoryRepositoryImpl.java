package com.nhnacademy.shoppingmall.point.repository.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.point.domain.PointHistory;
import com.nhnacademy.shoppingmall.point.repository.PointHistoryRepository;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
public class PointHistoryRepositoryImpl implements PointHistoryRepository {
    @Override
    public int save(PointHistory pointHistory) {
        String sql = "insert into point_history (user_id, order_id, point_type, amount, created_at) values (?,?,?,?,?)";
        Connection connection = DbConnectionThreadLocal.getConnection();

        try(PreparedStatement psmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            psmt.setString(1, pointHistory.getUserId());

            if(Objects.isNull(pointHistory.getOrderId())) {
                psmt.setNull(2, Types.INTEGER);
            } else {
                psmt.setInt(2, pointHistory.getOrderId());
            }

            psmt.setInt(3, pointHistory.getPointType().getValue());
            psmt.setInt(4, pointHistory.getAmount());
            psmt.setTimestamp(5, Timestamp.valueOf(pointHistory.getCreatedAt()));
            psmt.executeUpdate();

            ResultSet rs = psmt.getGeneratedKeys();
            if(rs.next()) {
                pointHistory.setPointHistoryId(rs.getInt(1));
                return pointHistory.getPointHistoryId();
            }
        } catch (SQLException e) {
            log.error("PointHistory save error", e);
            throw new RuntimeException(e);
        }

        return 0;
    }

    @Override
    public int deleteByUserId(String userId) {
        Connection connection = DbConnectionThreadLocal.getConnection();

        String sql = "delete from point_history where user_id=?";

        try(PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setString(1, userId);

            int result = psmt.executeUpdate();
            log.debug("Delete PointHistory: {}", result);
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Page<PointHistory> findAllByUserId(String userId, int page, int pageSize) {
        Connection connection = DbConnectionThreadLocal.getConnection();

        List<PointHistory> content = new ArrayList<>();
        int offset = (page - 1) * pageSize;
        String sql = "select * from point_history where user_id=? order by created_at desc limit ?, ?";

        try(PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setString(1, userId);
            pstm.setInt(2, offset);
            pstm.setInt(3, pageSize);

            ResultSet rs = pstm.executeQuery();
            while(rs.next()) {
                content.add(new PointHistory(
                        rs.getInt("point_history_id"),
                        rs.getString("user_id"),
                        rs.getInt("order_id"),
                        PointHistory.PointType.valueOf(rs.getInt("point_type")),
                        rs.getInt("amount"),
                        rs.getTimestamp("created_at").toLocalDateTime()
                ));
            }
        } catch (SQLException e) {
            log.error("PointHistory findAllByUserId error", e);
            throw new RuntimeException(e);
        }

        long totalCount = totalCountByUserId(userId);

        return new Page<>(content, totalCount);
    }

    @Override
    public long totalCountByUserId(String userId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "select count(*) from point_history where user_id=?";

        try(PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setString(1, userId);
            ResultSet rs = pstm.executeQuery();
            if(rs.next()) {
                return rs.getLong(1);
            }
        } catch (SQLException e) {
            log.error("PointHistory totalCountByUserId error", e);
            throw new RuntimeException(e);
        }

        return 0L;
    }
}
