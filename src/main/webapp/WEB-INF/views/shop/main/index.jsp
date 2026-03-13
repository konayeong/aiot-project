<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

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
    <div>
        <c:forEach var="cateogry" items="${categories}">
            <butoon type="button" class="btn btn-"
        </c:forEach>
    </div>

    <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 row-cols-lg-4 g-4 ">
        <c:forEach var="product" items="${products}">
            <div class="card-group">
                <div class="card">
                    <a href="/products/detail.do?product_id=${product.productId}">
                        <img src="${product.image}"
                             class="card-img-top"
                             style="height:250px; object-fit:cover;"
                             alt="${product.productName}"
                             onerror="this.src='/resources/no-image.png'">
                    </a>

                    <div class="card-body">
                        <h6 class="card-title fw-semibold">
                                ${product.productName}
                        </h6>
                        <p class="cart-text text-black fw-bold mb-2">
                                ${product.price}원
                        </p>
                        <div class="cart-text d-flex justify-content-between align-items-center">
                            <small class="text-muted">
                                재고 ${product.stock}
                            </small>
                            <c:if test="${product.stock == 0}">
                                <span class="badge bg-danger">품절</span>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
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