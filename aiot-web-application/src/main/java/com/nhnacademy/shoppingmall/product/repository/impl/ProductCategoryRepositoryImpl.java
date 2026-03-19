package com.nhnacademy.shoppingmall.product.repository.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.product.domain.Category;
import com.nhnacademy.shoppingmall.product.repository.ProductCategoryRepository;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ProductCategoryRepositoryImpl implements ProductCategoryRepository {
    @Override
    public List<Category> getByProductId(int productId) {
        Connection connection = DbConnectionThreadLocal.getConnection();

        String sql = "select c.category_id as categoryId, c.category_name as categoryName, c.sort_order as sortOrder" +
                " from product_categories pc join categories c on pc.category_id = c.category_id" +
                " where pc.product_id = ?" +
                " order by categoryId";

        try(PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setInt(1, productId);

            try(ResultSet rs = psmt.executeQuery()) {
                List<Category> categories = new ArrayList<>();
                while(rs.next()) {
                    categories.add(new Category(
                            rs.getInt("categoryId"),
                            rs.getString("categoryName"),
                            rs.getInt("sortOrder")
                    ));
                }
                return categories;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Integer> getCategoryIdsByProductId(int productId) {
        Connection connection = DbConnectionThreadLocal.getConnection();

        String sql = "select category_id from product_categories where product_id = ? order by category_id";

        try(PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setInt(1, productId);

            try(ResultSet rs = psmt.executeQuery()) {
                List<Integer> categoryIds = new ArrayList<>();
                while(rs.next()) {
                    categoryIds.add(rs.getInt("category_id"));
                }
                return categoryIds;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int save(int productId, int categoryId) {
        Connection connection = DbConnectionThreadLocal.getConnection();

        String sql = "insert into product_categories(product_id, category_id) values(?,?)";

        try(PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setInt(1, productId);
            psmt.setInt(2, categoryId);

            int result = psmt.executeUpdate();
            log.debug("[Product_Categories] save : {}", result);
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteByProductId(int productId) {
        Connection connection = DbConnectionThreadLocal.getConnection();

        String sql = "delete from product_categories where product_id = ?";

        try(PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setInt(1, productId);

            int result = psmt.executeUpdate();
            log.debug("[Product_Category] delete : {}", result);
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
