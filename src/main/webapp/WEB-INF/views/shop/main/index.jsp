<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="container py-5">

    <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 row-cols-lg-4 g-4">

        <c:forEach var="product" items="${products}">
            <div class="col">

                <div class="card h-100 shadow-sm">

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

                        <p class="text-danger fw-bold mb-2">
                                ${product.price}원
                        </p>

                        <div class="d-flex justify-content-between align-items-center">

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

    <nav class="mt-5">
        <ul class="pagination justify-content-center">

            <c:if test="${currentPage > 1}">
                <li class="page-item">
                    <a class="page-link"
                       href="/index.do?page=${currentPage - 1}&searchKeyword=${searchKeyword}">
                        Previous
                    </a>
                </li>
            </c:if>

            <c:forEach var="i" begin="1" end="${totalPages}">
                <li class="page-item ${i == currentPage ? 'active' : ''}">
                    <a class="page-link"
                       href="/index.do?page=${i}&searchKeyword=${searchKeyword}">
                            ${i}
                    </a>
                </li>
            </c:forEach>

            <c:if test="${currentPage < totalPages}">
                <li class="page-item">
                    <a class="page-link"
                       href="/index.do?page=${currentPage + 1}&searchKeyword=${searchKeyword}">
                        Next
                    </a>
                </li>
            </c:if>

        </ul>
    </nav>

</div>