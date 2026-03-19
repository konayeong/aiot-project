INSERT INTO products (product_name, description, price, stock, image) VALUES
('노트북', '상품 설명이닷', 1200000, 10, 'https://cdn.011st.com/11dims/resize/600x600/quality/75/11src/product/6944460542/B.webp?823520521'),
('스마트폰', '상품 설명이닷',900000, 15, 'https://cdn.011st.com/11dims/resize/600x600/quality/75/11src/product/9104934723/B.webp?902998188'),
('태블릿', '상품 설명이닷', 600000, 20, 'https://cdn.011st.com/11dims/resize/600x600/quality/75/11src/product/8102507001/B.webp?349024438'),
('무선 이어폰', '상품 설명이닷',150000, 50, 'https://cdn.011st.com/11dims/resize/1000x1000/quality/75/11src/product/8653407353/B.webp?638799328'),
('스마트워치', '상품 설명이닷', 250000, 30, 'https://cdn.011st.com/11dims/resize/600x600/quality/75/11src/product/4178643824/B.webp?858150849'),
('청바지', '상품 설명이닷', 45000, 50, null),
('티셔츠','상품 설명이닷', 15000, 100, null),
('후드티', '상품 설명이닷', 40000, 40, null),
('코트', '상품 설명이닷',120000, 20, null),
('운동화', '상품 설명이닷',80000, 60, null),
('커피머신', '상품 설명이닷',150000, 8, 'https://cdn.011st.com/11dims/resize/600x600/quality/75/11src/product/8932135267/B.webp?251010969'),
('전기주전자','상품 설명이닷', 50000, 25, null),
('청소기', '상품 설명이닷',200000, 10, null),
('조명','상품 설명이닷', 35000, 40, 'https://cdn.011st.com/11dims/resize/600x600/quality/75/11src/product/8987572062/B.webp?58015150'),
('책상', '상품 설명이닷',120000, 15, 'https://cdn.011st.com/11dims/resize/600x600/quality/75/11src/product/9139887306/B.webp?423978569'),
('소설책', '상품 설명이닷',18000, 30, null),
('자기계발서','상품 설명이닷', 22000, 25, null),
('요리책','상품 설명이닷', 30000, 20, null),
('만화책', '상품 설명이닷',12000, 50, 'https://cdn.011st.com/11dims/resize/600x600/quality/75/11src/product/8903948955/B.webp?292604421'),
('사전', '상품 설명이닷',45000, 10, null),
('쿠키', '상품 설명이닷',5000, 100, 'https://cdn.011st.com/11dims/resize/600x600/quality/75/11src/product/8146129531/B.jpg?465028817'),
('초콜릿','상품 설명이닷', 3000, 200, 'https://cdn.011st.com/11dims/resize/600x600/quality/75/11src/product/8397152888/B.webp?545466636'),
('사과', '상품 설명이닷',2000, 150, 'https://cdn.011st.com/11dims/resize/600x600/quality/75/11src/product/8581007393/B.webp?634681584'),
('바나나', '상품 설명이닷',2500, 120, 'https://cdn.011st.com/11dims/resize/600x600/quality/75/11src/product/35591432/B.jpg?79000000'),
('커피', '상품 설명이닷',4500, 80, 'https://cdn.011st.com/11dims/resize/600x600/quality/75/11src/product/2427999503/B.webp?285213958');

-- product-categories 연결
INSERT INTO product_categories (product_id, category_id) VALUES
(152, 1),  -- 노트북 → 전자제품
(153, 1),  -- 스마트폰 → 전자제품
(154, 1),  -- 태블릿 → 전자제품
(155, 1),  -- 무선 이어폰 → 전자제품
(156, 1),  -- 스마트워치 → 전자제품

(157, 2),  -- 청바지 → 의류
(158, 2),  -- 티셔츠 → 의류
(159, 2),  -- 후드티 → 의류
(160, 2),  -- 코트 → 의류
(161, 2), -- 운동화 → 의류

(162, 3), -- 커피머신 → 생활용품
(163, 3), -- 전기주전자 → 생활용품
(164, 3), -- 청소기 → 생활용품
(165, 3), -- 조명 → 생활용품
(166, 3), -- 책상 → 생활용품

(167, 4), -- 소설책 → 도서
(168, 4), -- 자기계발서 → 도서
(169, 4), -- 요리책 → 도서
(170, 4), -- 만화책 → 도서
(171, 4), -- 사전 → 도서

(172, 5), -- 쿠키 → 음식
(173, 5), -- 초콜릿 → 음식
(174, 5), -- 사과 → 음식
(175, 5), -- 바나나 → 음식
(176, 5); -- 커피 → 음식