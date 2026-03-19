package com.nhnacademy.shoppingmall.product.repository.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.product.domain.Category;
import com.nhnacademy.shoppingmall.product.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class CategoryRepositoryImpl implements CategoryRepository {
    @Override
    public List<Category> findAll() {
        Connection connection = DbConnectionThreadLocal.getConnection();

        String sql = "select * from categories order by category_id";

        try(Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql)) {
            List<Category> categories = new ArrayList<>();

            while(rs.next()) {
                categories.add(new Category(
                        rs.getInt("category_id"),
                        rs.getString("category_name"),
                        rs.getInt("sort_order")
                ));
            }
            log.debug("[Category] findAll cnt : {}", categories.size());
            return categories;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Category> findById(int categoryId) {
        Connection connection = DbConnectionThreadLocal.getConnection();

        String sql = "select * from categories where category_id = ?";

        try(PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setInt(1, categoryId);

            try(ResultSet rs = psmt.executeQuery()) {
                Category category = null;
                if(rs.next()) {
                    category = new Category(
                            rs.getInt("category_id"),
                            rs.getString("category_name"),
                            rs.getInt("sort_order")
                    );
                    log.debug("[Category] findById : {}", category.getCategoryName());
                    return Optional.of(category);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public int save(Category category) {
        Connection connection = DbConnectionThreadLocal.getConnection();

        String sql = "insert into categories(category_name, sort_order) values (?,?)";

        try(PreparedStatement psmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            psmt.setString(1, category.getCategoryName());
            psmt.setInt(2, category.getSortOrder());

            int result = psmt.executeUpdate();
            log.debug("[Category] save : {}", result);

            // PK set
            try(ResultSet rs = psmt.getGeneratedKeys()) {
                if(rs.next()) {
                    category.setCategoryId(rs.getInt(1));
                }
            }

            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int update(Category category) {
        Connection connection = DbConnectionThreadLocal.getConnection();

        String sql = "update categories set category_name = ?, sort_order = ? where category_id = ?";

        try(PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setString(1, category.getCategoryName());
            psmt.setInt(2, category.getSortOrder());
            psmt.setInt(3, category.getCategoryId());

            int result = psmt.executeUpdate();
            log.debug("[Category] update : {}", result);
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteByCategoryId(int categoryId) {
        Connection connection = DbConnectionThreadLocal.getConnection();

        String sql = "delete from categories where category_id = ?";

        try(PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setInt(1, categoryId);

            int result = psmt.executeUpdate();
            log.debug("[Category] deleteById : {}", result);
            return result;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteAll() {
        Connection connection = DbConnectionThreadLocal.getConnection();

        String sql = "delete from categories";

        try(PreparedStatement psmt = connection.prepareStatement(sql)) {
            int result = psmt.executeUpdate();

            log.debug("[Category] deleteAll : {}", result);
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int countByCategoryId(int categoryId) {
        Connection connection = DbConnectionThreadLocal.getConnection();

        String sql = "select count(*) as cnt from categories where category_id = ?";

        try(PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setInt(1, categoryId);

            try(ResultSet rs = psmt.executeQuery()) {
                if(rs.next()) {
                    int result = rs.getInt("cnt");
                    log.debug("[Category] countById : {}", result);
                    return result;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    @Override
    public int countByCategoryNameOrSortOrder(String categoryName, int sortOrder) {
        Connection connection = DbConnectionThreadLocal.getConnection();

        String sql = "select count(*) as cnt from categories where category_name = ? or sort_order = ?";

        try(PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setString(1, categoryName);
            psmt.setInt(2, sortOrder);

            try(ResultSet rs = psmt.executeQuery()) {
                if(rs.next()) {
                    return rs.getInt("cnt");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }
}
