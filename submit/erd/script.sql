-- users
CREATE TABLE `users` (
                         `user_id` varchar(50) NOT NULL COMMENT '아이디',
                         `user_name` varchar(50) NOT NULL COMMENT '이름',
                         `user_password` varchar(200) NOT NULL COMMENT 'mysql password 사용',
                         `user_birth` varchar(8) NOT NULL COMMENT '생년월일 : 19840503',
                         `user_auth` varchar(10) NOT NULL COMMENT '권한: ROLE_ADMIN,ROLE_USER',
                         `user_point` int NOT NULL COMMENT 'default : 1000000',
                         `created_at` datetime NOT NULL COMMENT '가입 일자',
                         `latest_login_at` datetime DEFAULT NULL COMMENT '마지막 로그인 일자',
                         PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='회원';


-- categories
CREATE TABLE categories (
                            category_id INT AUTO_INCREMENT PRIMARY KEY,
                            category_name VARCHAR(100) NOT NULL,
                            sort_order INT NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='카테고리';


-- products
CREATE TABLE products (
                          product_id INT AUTO_INCREMENT PRIMARY KEY,
                          product_name VARCHAR(200) NOT NULL,
                          price INT NOT NULL,
                          stock INT NOT NULL,
                          image VARCHAR(500)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='상품';

-- product_categories
CREATE TABLE product_categories (
                            product_id INT NOT NULL,
                            category_id INT NOT NULL,

                            PRIMARY KEY (product_id, category_id),

                            CONSTRAINT fk_pc_product
                                FOREIGN KEY (product_id)
                                REFERENCES products(product_id)
                                ON DELETE CASCADE,

                            CONSTRAINT fk_pc_category
                                FOREIGN KEY (category_id)
                                REFERENCES categories(category_id)
                                ON DELETE CASCADE

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='상품 카테고리';

-- addresses
CREATE TABLE addresses (
                           address_id INT AUTO_INCREMENT PRIMARY KEY,
                           user_id VARCHAR(50) NOT NULL,
                           address VARCHAR(200) NOT NULL COMMENT '주소',

                           CONSTRAINT fk_addresses_user
                               FOREIGN KEY (user_id)
                                   REFERENCES users(user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='회원주소';

-- orders
CREATE TABLE orders (
                        order_id INT AUTO_INCREMENT PRIMARY KEY,
                        user_id VARCHAR(50) NOT NULL,
                        address_id INT NOT NULL,
                        used_point INT DEFAULT 0,
                        created_at DATETIME NOT NULL,

                        CONSTRAINT fk_orders_user
                            FOREIGN KEY (user_id)
                                REFERENCES users(user_id),

                        CONSTRAINT fk_orders_address
                            FOREIGN KEY (address_id)
                                REFERENCES addresses(address_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='주문';


-- order_items
CREATE TABLE order_items (
                             order_item_id INT AUTO_INCREMENT PRIMARY KEY,
                             order_id INT NOT NULL,
                             product_id INT NOT NULL,
                             price INT NOT NULL,
                             quantity INT NOT NULL,

                             CONSTRAINT fk_order_items_order
                                 FOREIGN KEY (order_id)
                                 REFERENCES orders(order_id),

                             CONSTRAINT fk_order_items_product
                                 FOREIGN KEY (product_id)
                                     REFERENCES products(product_id)

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='주문 아이템';


-- point_history
CREATE TABLE point_history (
                               point_history_id INT AUTO_INCREMENT PRIMARY KEY,
                               user_id VARCHAR(50) NOT NULL,
                               order_id int,
                               point_type TINYINT NOT NULL COMMENT 'REGISTER(1), LOGIN(2), ORDER_USE(3), ORDER_REWARD(4) ',
                               amount INT NOT NULL,
                               created_at DATETIME NOT NULL,

                               CONSTRAINT fk_point_history_user
                                   FOREIGN KEY (user_id)
                                       REFERENCES users(user_id),

                               CONSTRAINT fk_point_history_order
                                   FOREIGN KEY (order_id)
                                       REFERENCES orders(order_id)

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='포인트 내역';