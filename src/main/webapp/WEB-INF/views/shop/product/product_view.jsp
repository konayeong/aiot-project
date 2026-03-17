<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"
         trimDirectiveWhitespaces="true" session="false" %>

<div class="container py-5">

    <div class="card shadow-sm border-0">
        <div class="row g-0">

            <!-- 상품 이미지 -->
            <div class="col-md-5 p-4 d-flex align-items-center justify-content-center bg-light">
                <img src="${product.image}"
                     class="img-fluid rounded"
                     style="max-height:420px; object-fit:cover;"
                     alt="${product.productName}"
                     onerror="this.src='/resources/no-image.png'">
            </div>


            <!-- 상품 정보 -->
            <div class="col-md-7">
                <div class="card-body p-4">

                    <!-- 상품명 -->
                    <h3 class="card-title fw-bold mb-3">
                        ${product.productName}
                    </h3>

                    <!-- 가격 -->
                    <h4 class="text-primary fw-bold mb-4">
                        <fmt:formatNumber value="${product.price}" pattern="#,###"/>원
                    </h4>

                    <!-- 설명 -->
                    <div class="mb-4">
                        <h6 class="text-muted fw-bold">상품 설명</h6>
                        <p class="mb-0">
                            ${product.description}
                        </p>
                    </div>


                    <!-- 카테고리 -->
                    <div class="mb-4">
                        <h6 class="text-muted fw-bold mb-2">카테고리</h6>

                        <c:forEach var="category" items="${categories}">
                            <span class="badge bg-secondary me-1 fw-bold">
                                    ${category.categoryName}
                            </span>
                        </c:forEach>

                    </div>

                    <!-- 상품 정보 -->
                    <ul class="list-group list-group-flush mb-4">

                        <li class="list-group-item d-flex justify-content-between">
                            <span class="text-muted">재고</span>

                            <span class="fw-bold">
                                ${product.stock}
                            </span>
                        </li>

                    </ul>
                        <div class="d-grid gap-2">
                            <c:choose>
                                <%-- 재고가 1개 이상일 때만 장바구니 버튼 활성화 --%>
                                <c:when test="${product.stock > 0}">
                                    <form id="orderForm">
                                        <input type="hidden" name="product_id" value="${product.productId}">
                                        <div class="mb-4">
                                            <label for="quantity" class="me-3 text-muted">구매 수량</label>
                                            <input type="number" id="quantity" name="quantity" class="form-control text-center"
                                                   value="1" min="1" max="${product.stock}" style="width: 100px;"
                                                ${product.stock == 0 ? 'disabled' : ''}>
                                        </div>

                                        <button class="btn btn-primary" type="submit" formaction="/cart/addAction.do" formmethod="post">
                                            장바구니 담기
                                        </button>

                                        <button class="btn btn-warning" type="submit" formaction="/orders/direct.do" formmethod="get">
                                            바로 주문
                                        </button>
                                    </form>
                                </c:when>
                                <%-- 품절일 경우 클릭 못 하게 막음 --%>
                                <c:otherwise>
                                    <button type="button" class="btn btn-danger" disabled>
                                        품절
                                    </button>
                                </c:otherwise>
                            </c:choose>
                        </div>
                </div>
            </div>

        </div>
    </div>

</div>