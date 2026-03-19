<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>

<style>
    .custom-pagination .page-link {
        border: none;
        color: #2c3e50;
        font-weight: 700;
        border-radius: 20px;
        margin-top: 40px;
        margin-left : 10px;
        margin-right : 10px;
    }

    .custom-pagination .page-link:hover {
        background-color: #2c3e50;
        color: white;
    }

    .custom-pagination .page-item.active .page-link {
        background-color: #2c3e50;
        color: white;
    }
</style>

<div class="container py-5">

    <h3 class="mt-5">최근 본 상품</h3>
    <div class="row row-cols-1 row-cols-sm-2 row-cols-md-5 g-3 mb-5">
        <c:choose>
            <c:when test="${not empty recentProducts}">
                <c:forEach var="recentProduct" items="${recentProducts}">
                    <div class="col">
                        <div class="card shadow-sm">
                            <a href="/products/detail.do?product_id=${recentProduct.productId}">
                                <img src="${recentProduct.image}"
                                     class="card-img-top"
                                     style="height:150px; object-fit:cover;"
                                     alt="${recentProduct.productName}">
                            </a>
                            <div class="card-body text-center">
                                <p class="card-text fw-bold">${recentProduct.productName}</p>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <p class="text-muted">최근 본 상품이 없습니다.</p>
            </c:otherwise>
        </c:choose>
    </div>

    <!-- 상품 검색 -->
    <form method="get" action="/index.do" >
        <div class="mb-4">
            <div class="mb-4 text-center">
                <h4>카테고리를 선택하세요.</h4>
                <div class="border rounded p-3">
                    <c:forEach var="category" items="${categories}">
                        <a href="/index.do?category_id=${category.categoryId}"
                           class="btn me-2 mb-2 ${category.categoryId == click ? 'btn-dark text-white' : 'btn-outline-dark'}">
                                ${category.categoryName}
                        </a>
                    </c:forEach>
                    <div class="text-end">
                        <a href="/index.do" class="text-end text-danger">취소</a>
                    </div>
                </div>
            </div>
            <div class="d-flex">
                <input type="search"
                       name="searchKeyword"
                       value="${search_keyword}"
                       class="form-control me-2"
                       placeholder="상품명으로 검색하세요">

                <button class="btn btn-outline-dark" style="width: 10%">
                    검색
                </button>
            </div>

        </div>
    </form>


    <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 row-cols-lg-4 g-4">
        <c:choose>
            <c:when test="${not empty products}">
                <c:forEach var="product" items="${products}">
                    <div class="col">
                        <div class="card h-100 shadow-sm">
                            <a href="/products/detail.do?product_id=${product.productId}">
                                <img src="${product.image}"
                                     class="card-img-top"
                                     style="height:300px; object-fit:cover;"
                                     alt="${product.productName}">
                            </a>

                            <div class="card-body">
                                <h6 class="fw-semibold">${product.productName}</h6>
                                <p class="fw-bold"><fmt:formatNumber value="${product.price}" pattern="#,###"/>원</p>
                                <c:choose>
                                    <c:when test="${product.stock > 0}">
                                        <p class="text-muted">재고 ${product.stock}</p>

                                        <c:if test="${not empty sessionScope.loginUser}">
                                            <div class="d-flex gap-2">
                                                <form action="/cart/addAction.do" method="post" class="flex-fill">
                                                    <input type="hidden" name="product_id" value="${product.productId}">
                                                    <input type="hidden" name="quantity" value="1">

                                                    <button class="btn btn-outline-dark w-100">
                                                        장바구니
                                                    </button>
                                                </form>

                                                <form action="/orders/direct.do" method="get" class="flex-fill">
                                                    <input type="hidden" name="product_id" value="${product.productId}">
                                                    <button class="btn btn-warning w-100">
                                                        바로주문
                                                    </button>
                                                </form>
                                            </div>
                                        </c:if>
                                    </c:when>

                                    <c:otherwise>
                                        <button class="btn btn-danger w-100" disabled>
                                            품절
                                        </button>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <p class="text-muted">검색하신 상품 ${searchKeyword} 이 없습니다.</p>
            </c:otherwise>
        </c:choose>
    </div>


    <!-- pagination -->
    <nav>
        <ul class="pagination justify-content-center custom-pagination">
            <c:if test="${currentPage > 1}">
                <li class="page-item">
                    <a class="page-link"
                       href="/index.do?page=${currentPage - 1}">
                        &laquo;
                    </a>
                </li>
            </c:if>

            <c:forEach var="i" begin="1" end="${totalPages}">
                <li class="page-item ${i == currentPage ? 'active' : ''}">
                    <a class="page-link"
                       href="/index.do?page=${i}">
                            ${i}
                    </a>
                </li>
            </c:forEach>

            <c:if test="${currentPage < totalPages}">
                <li class="page-item">
                    <a class="page-link"
                       href="/index.do?page=${currentPage + 1}">
                        &raquo;
                    </a>
                </li>
            </c:if>
        </ul>
    </nav>
</div>