<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                        ${product.price}원
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
                            <span class="badge bg-secondary me-1">
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

                                <c:if test="${product.stock == 0}">
                                    <span class="badge bg-danger ms-2">품절</span>
                                </c:if>
                            </span>
                        </li>

                    </ul>
                    <!-- 버튼 -->
                </div>
            </div>

        </div>
    </div>

</div>