package com.nhnacademy.shoppingmall.user.repository.impl;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Repository
public class UserRepositoryImpl implements UserRepository {
    @Autowired
    private DataSource dataSource;

    @Override
    public Optional<User> findByUserIdAndUserPassword(String userId, String userPassword) {
        /*todo#3-1 회원의 아이디와 비밀번호를 이용해서 조회하는 코드 입니다.(로그인)
          해당 코드는 SQL Injection이 발생합니다. SQL Injection이 발생하지 않도록 수정하세요.
         */
        Connection connection = DataSourceUtils.getConnection(dataSource);

//        String sql =String.format("select user_id, user_name, user_password, user_birth, user_auth, user_point, created_at, latest_login_at from users where user_id='%s' and user_password ='%s'",
//                userId,
//                userPassword
//        );
        String sql = "select user_id, user_name, user_password, user_birth, user_auth, user_point, created_at, latest_login_at from users where user_id = ? and user_password = ?";

        log.debug("sql:{}",sql);

        try( PreparedStatement psmt = connection.prepareStatement(sql)
        ) {
            psmt.setString(1, userId);
            psmt.setString(2, userPassword);

            try(ResultSet rs = psmt.executeQuery()) {
                if(rs.next()){
                    User user = new User(
                            rs.getString("user_id"),
                            rs.getString("user_name"),
                            rs.getString("user_password"),
                            rs.getString("user_birth"),
                            User.Auth.valueOf(rs.getString("user_auth")),
                            rs.getInt("user_point"),
                            Objects.nonNull(rs.getTimestamp("created_at")) ? rs.getTimestamp("created_at").toLocalDateTime() : null,
                            Objects.nonNull(rs.getTimestamp("latest_login_at")) ? rs.getTimestamp("latest_login_at").toLocalDateTime() : null
                    );
                    return Optional.of(user);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }

        return Optional.empty();
    }

    @Override
    public Optional<User> findById(String userId) {
        //todo#3-2 회원조회
        Connection connection = DataSourceUtils.getConnection(dataSource);

        String sql = "select * from users where user_id = ?";

        try(PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setString(1, userId);

            try(ResultSet rs = psmt.executeQuery()) {
                if(rs.next()) {
                    User user = new User(rs.getString("user_id"),
                            rs.getString("user_name"),
                            rs.getString("user_password"),
                            rs.getString("user_birth"),
                            User.Auth.valueOf(rs.getString("user_auth")),
                            rs.getInt("user_point"),
                            rs.getTimestamp("created_at").toLocalDateTime(),
                            Objects.nonNull(rs.getTimestamp("latest_login_at")) ? rs.getTimestamp("latest_login_at").toLocalDateTime() : null);
                    return Optional.of(user);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
        return Optional.empty();
    }

    @Override
    public int save(User user) {
        //todo#3-3 회원등록, executeUpdate()을 반환합니다.
        Connection connection = DataSourceUtils.getConnection(dataSource);

        String sql = "insert into users(user_id, user_name, user_password, user_birth, user_auth, user_point, created_at, latest_login_at)" +
                " values(?, ?, ?, ?, ?, ?, ?, ?)";

       try(PreparedStatement psmt = connection.prepareStatement(sql)) {
           psmt.setString(1, user.getUserId());
           psmt.setString(2, user.getUserName());
           psmt.setString(3, user.getUserPassword());
           psmt.setString(4, user.getUserBirth());
           psmt.setString(5, user.getUserAuth().name());
           psmt.setInt(6, user.getUserPoint());
           psmt.setTimestamp(7, Timestamp.valueOf(user.getCreatedAt()));
           psmt.setTimestamp(8, (Objects.nonNull(user.getLatestLoginAt()) ? Timestamp.valueOf(user.getLatestLoginAt()) : null));

           int result = psmt.executeUpdate();
           log.debug("Save User : {}", result);

           return result;
       } catch (SQLException e) {
           throw new RuntimeException(e);
       } finally {
           DataSourceUtils.releaseConnection(connection, dataSource);
       }
    }

    @Override
    public int deleteByUserId(String userId) {
        //todo#3-4 회원삭제, executeUpdate()을 반환합니다.
        Connection connection = DataSourceUtils.getConnection(dataSource);

        String sql = "delete from users where user_id = ?";

        try(PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setString(1, userId);

            int result = psmt.executeUpdate();
            log.debug("Delete User : {}", result);
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
    }

    @Override
    public int update(User user) {
        //todo#3-5 회원수정, executeUpdate()을 반환합니다.
        Connection connection = DataSourceUtils.getConnection(dataSource);

        String sql = "update users set user_name = ?, user_password = ?, user_birth = ?, user_auth = ?, user_point = ? where user_id = ?";

        try(PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setString(1, user.getUserName());
            psmt.setString(2, user.getUserPassword());
            psmt.setString(3, user.getUserBirth());
            psmt.setString(4, user.getUserAuth().name());
            psmt.setInt(5, user.getUserPoint());
            psmt.setString(6, user.getUserId());

            int result = psmt.executeUpdate();
            log.debug("Update User : {}", result);
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
    }

    @Override
    public int updateLatestLoginAtByUserId(String userId, LocalDateTime latestLoginAt) {
        //todo#3-6, 마지막 로그인 시간 업데이트, executeUpdate()을 반환합니다.
        Connection connection = DataSourceUtils.getConnection(dataSource);

        String sql = "update users set latest_login_at = ? where user_id = ?";

        try(PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setTimestamp(1, Timestamp.valueOf(latestLoginAt));
            psmt.setString(2, userId);

            int result = psmt.executeUpdate();
            log.debug("Update LatestLoginAt : {}", result);
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
    }

    @Override
    public int countByUserId(String userId) {
        //todo#3-7 userId와 일치하는 회원의 count를 반환합니다.
        Connection connection = DataSourceUtils.getConnection(dataSource);

        String sql = "select count(*) as user_cnt from users where user_id = ?";

        try(PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setString(1, userId);

            try(ResultSet rs = psmt.executeQuery()) {
                if(rs.next()) {
                    return rs.getInt("user_cnt");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
        return 0;
    }

    @Override
    public Page<User> findAll(int page, int pageSize, String userId) {
        Connection connection = DataSourceUtils.getConnection(dataSource);

        List<User> content = new ArrayList<>();
        int offset = (page - 1) * pageSize;
        boolean isSearch = userId != null && !userId.isEmpty();
        String sql;

        if(isSearch) {
            sql = "select * from users where user_id=? order by user_auth asc, created_at desc limit ?, ?";
        } else {
            sql = "select * from users order by user_auth asc, created_at limit ?, ?";
        }

        try(PreparedStatement pstm = connection.prepareStatement(sql)) {
            if(isSearch) {
                pstm.setString(1, userId);
                pstm.setInt(2, offset);
                pstm.setInt(3, pageSize);
            } else {
                pstm.setInt(1, offset);
                pstm.setInt(2, pageSize);
            }

            ResultSet rs = pstm.executeQuery();
            while(rs.next()) {
                content.add(new User(
                        rs.getString("user_id"),
                        rs.getString("user_name"),
                        rs.getString("user_password"),
                        rs.getString("user_birth"),
                        User.Auth.valueOf(rs.getString("user_auth")),
                        rs.getInt("user_point"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("latest_login_at").toLocalDateTime()
                ));
            }
        } catch (SQLException e) {
            log.error("User findAll error", e);
            throw new RuntimeException(e);
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }

        long totalCount = totalCount();
        return new Page<>(content, totalCount);
    }

    @Override
    public long totalCount() {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        String sql = "select count(*) from users";

        try(PreparedStatement pstm = connection.prepareStatement(sql)) {
            ResultSet rs = pstm.executeQuery();
            if(rs.next()) {
                return rs.getLong(1);
            }
        } catch (SQLException e) {
            log.error("User totalCount error", e);
            throw new RuntimeException(e);
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }

        return 0L;
    }
}
