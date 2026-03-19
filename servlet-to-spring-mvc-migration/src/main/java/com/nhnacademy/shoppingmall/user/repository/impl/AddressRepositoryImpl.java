package com.nhnacademy.shoppingmall.user.repository.impl;

import com.nhnacademy.shoppingmall.user.domain.Address;
import com.nhnacademy.shoppingmall.user.repository.AddressRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
public class AddressRepositoryImpl implements AddressRepository {
    @Autowired
    private DataSource dataSource;

    @Override
    public int save(Address address) {
        String sql = "insert into addresses (user_id, address) values(?, ?)";
        Connection conn = DataSourceUtils.getConnection(dataSource);

        try(PreparedStatement pstm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstm.setString(1, address.getUserId());
            pstm.setString(2, address.getAddress());
            pstm.executeUpdate();

            ResultSet rs = pstm.getGeneratedKeys();
            if(rs.next()) {
                address.setAddressId(rs.getInt(1));
                return address.getAddressId();
            }
        } catch (SQLException e) {
            log.error("Address save error", e);
            throw new RuntimeException(e);
        } finally {
            DataSourceUtils.releaseConnection(conn, dataSource);
        }

        return 0;
    }

    @Override
    public int update(Address address) {
        String sql = "update addresses set address=? where address_id=? and user_id=?";
        Connection connection = DataSourceUtils.getConnection(dataSource);

        try(PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setString(1, address.getAddress());
            pstm.setInt(2, address.getAddressId());
            pstm.setString(3, address.getUserId());

            return pstm.executeUpdate();
        } catch (SQLException e) {
            log.error("Address update error", e);
            throw new RuntimeException(e);
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
    }

    @Override
    public int deleteByAddressIdAndUserId(int addressId, String userId) {
        String sql = "delete from addresses where address_id = ? and user_id = ?";
        Connection conn = DataSourceUtils.getConnection(dataSource);

        try(PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setInt(1, addressId);
            pstm.setString(2, userId);

            return pstm.executeUpdate();
        } catch (SQLException e) {
            log.error("Address deleteByAddressAndUserId error", e);
            throw new RuntimeException(e);
        } finally {
            DataSourceUtils.releaseConnection(conn, dataSource);
        }
    }

    @Override
    public List<Address> findAllByUserId(String userId) {
        List<Address> addressList = new ArrayList<>();
        String sql = "select * from addresses where user_id=?";
        Connection conn = DataSourceUtils.getConnection(dataSource);

        try(PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setString(1, userId);

            ResultSet rs = pstm.executeQuery();
            while(rs.next()) {
                addressList.add(new Address(
                        rs.getInt("address_id"),
                        rs.getString("user_id"),
                        rs.getString("address")
                ));
            }

        } catch (SQLException e) {
            log.error("Address findAllByUserId error", e);
            throw new RuntimeException(e);
        } finally {
            DataSourceUtils.releaseConnection(conn, dataSource);
        }

        return addressList;
    }
}
