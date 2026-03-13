package com.nhnacademy.shoppingmall.product.repository.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class ProductRepositoryImpl implements ProductRepository {

    @Override
    public long totalCount(String productName) {
        Connection connection = DbConnectionThreadLocal.getConnection();

        boolean isSearch = productName != null && !productName.isEmpty();
        String sql;

        if(isSearch) {
            sql = "select count(*) as totalCount from products where product_name like ?";
        }else {
            sql = "select count(*) as totalCount from products";
        }

        try(PreparedStatement psmt = connection.prepareStatement(sql)) {
            if(isSearch) {
                psmt.setString(1, '%' + productName + '%');
            }

            try(ResultSet rs = psmt.executeQuery()) {
                if(rs.next()) {
                    return rs.getInt("totalCount");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return 0L;
    }

    @Override
    public long totalCount(int categoryId) {
        Connection connection = DbConnectionThreadLocal.getConnection();

        String sql = "select count(*) as totalCount from products p join product_categories pc on p.product_id = pc.product_id where pc.category_id = ?";

        try(PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setInt(1, categoryId);

            try(ResultSet rs = psmt.executeQuery()) {
                if(rs.next()) {
                    return rs.getInt("totalCount");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0L;
    }

    @Override
    public Page<Product> findAll(int page, int size, String productName) {
        Connection connection = DbConnectionThreadLocal.getConnection();

        int offset = (page-1) * size;
        boolean isSearch = productName != null && !productName.isEmpty();
        String sql;

        if(isSearch) {
            sql = "select * from products where product_name like ? order by product_id limit ?, ?";
            log.debug(sql);
        }else {
            sql = "select * from products order by product_id limit ?, ?";
        }

        try(PreparedStatement psmt = connection.prepareStatement(sql)) {
            if(isSearch) {
                psmt.setString(1, "%" + productName + "%");
                psmt.setInt(2, offset);
                psmt.setInt(3, size);
            } else {
                psmt.setInt(1, offset);
                psmt.setInt(2, size);
            }

            try(ResultSet rs = psmt.executeQuery()) {
                List<Product> products = new ArrayList<>();

                while(rs.next()) {
                    products.add(new Product(
                            rs.getInt("product_id"),
                            rs.getString("product_name"),
                            rs.getString("description"),
                            rs.getInt("price"),
                            rs.getInt("stock"),
                            rs.getString("image")
                    ));
                }
                // TODO Q 기존에는 데이터가 있을 때만 totalCount를 가져옴. 근데 gpt가 그러면 안된다는데 이유 알아내기
                long totalCount = totalCount(productName);

                return new Page<>(products, totalCount);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Page<Product> findAllByCategoryId(int page, int size, int categoryId) {
        Connection connection = DbConnectionThreadLocal.getConnection();

        int offset = (page-1) * size;
        String sql = "select * from products p join product_categories pc on p.product_id = pc.product_id where pc.category_id = ? order by p.product_id limit ?, ?";

        try(PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setInt(1, categoryId);
            psmt.setInt(2, offset);
            psmt.setInt(3, size);

            List<Product> products = new ArrayList<>();

            try(ResultSet rs = psmt.executeQuery()) {
                while(rs.next()) {
                    products.add(new Product(
                            rs.getInt("p.product_id"),
                            rs.getString("p.product_name"),
                            rs.getString("p.description"),
                            rs.getInt("p.price"),
                            rs.getInt("p.stock"),
                            rs.getString("p.image")
                    ));
                }
                long totalCount = totalCount(categoryId);

                return new Page<>(products, totalCount);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // === CRUD ===
    @Override
    public Optional<Product> findById(int productId) {
        Connection connection = DbConnectionThreadLocal.getConnection();

        String sql = "select * from products where product_id = ?";

        try(PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setInt(1, productId);

            try(ResultSet rs = psmt.executeQuery()) {
                if(rs.next()) {
                    Product product = new Product(
                            rs.getInt("product_id"),
                            rs.getString("product_name"),
                            rs.getString("description"),
                            rs.getInt("price"),
                            rs.getInt("stock"),
                            rs.getString("image")
                    );
                    log.debug("[Product] findById : {}, {}", productId, rs.getString("product_name"));
                    return Optional.of(product);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public int save(Product product) {
        Connection connection = DbConnectionThreadLocal.getConnection();

        String sql = "insert into products(product_name, description, price, stock, image) values (?,?,?,?,?)";

        try(PreparedStatement psmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            psmt.setString(1, product.getProductName());
            psmt.setString(2, product.getDescription());
            psmt.setInt(3, product.getPrice());
            psmt.setInt(4, product.getStock());
            psmt.setString(5, product.getImage());

            int result = psmt.executeUpdate();
            log.debug("[Product] save : {}", result);

            try(ResultSet rs = psmt.getGeneratedKeys()) {
                if (rs.next()) {
                    product.setProductId(rs.getInt(1));
                }
            }

            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int update(Product product) {
        Connection connection = DbConnectionThreadLocal.getConnection();

        String sql = "update products set product_name = ?, description = ?, price = ?, stock = ?, image = ? where product_id = ?";

        try(PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setString(1, product.getProductName());
            psmt.setString(2, product.getDescription());
            psmt.setInt(3, product.getPrice());
            psmt.setInt(4, product.getStock());
            psmt.setString(5, product.getImage());
            psmt.setInt(6, product.getProductId());

            int result = psmt.executeUpdate();
            log.debug("[Product] update : {}", result);
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteByProductId(int productId) {
        Connection connection = DbConnectionThreadLocal.getConnection();

        String sql = "delete from products where product_id = ?";

        try(PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setInt(1, productId);

            int result = psmt.executeUpdate();
            log.debug("[Product] deleteById {} result :{}", productId, result);
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteAll() {
        Connection connection = DbConnectionThreadLocal.getConnection();

        String sql = "delete from products";

        try(PreparedStatement psmt = connection.prepareStatement(sql)) {
            int result = psmt.executeUpdate();

            log.debug("[Product] deleteAll : {}", result);
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int countByProductId(int productId) {
        Connection connection = DbConnectionThreadLocal.getConnection();

        String sql = "select count(*) as cnt from products where product_id = ?";

        try(PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setInt(1, productId);

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

    @Override
    public int countByProductName(String productName) {
        Connection connection = DbConnectionThreadLocal.getConnection();

        String sql = "select count(*) as cnt from products where product_name = ?";

        try(PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setString(1, productName);

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
