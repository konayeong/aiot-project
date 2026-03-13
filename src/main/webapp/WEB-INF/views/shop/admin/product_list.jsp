<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"
         trimDirectiveWhitespaces="true" session="false" %>

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

<div style="margin:auto; max-width:800px;">
    <!-- 상품 검색 -->
    <div class="mb-4">

        <form method="get" action="/admin/products.do" class="d-flex">

            <input type="search"
                   name="searchKeyword"
                   value="${search_keyword}"
                   class="form-control me-2"
                   placeholder="상품명으로 검색하세요">

            <button class="btn btn-outline-dark" style="width: 10%">
                검색
            </button>

        </form>

    </div>

    <!-- 상품 목록 -->
    <div class="d-flex justify-content-between align-items-center mb-3">

        <h3 class="mb-0">상품 목록</h3>

        <a href="/admin/products/create.do" class="btn btn-warning">
            상품등록
        </a>

    </div>

    <table class="table table-borderless table-hover">

        <thead class="table-dark">
        <tr>
            <th>ID</th>
            <th>이미지</th>
            <th>상품명</th>
            <th>가격</th>
            <th>재고</th>
            <th>관리</th>
        </tr>
        </thead>

        <tbody>

        <c:forEach var="product" items="${products}">
            <tr>
                <td>${product.productId}</td>
                <td>
                    <a href="/products/detail.do?product_id=${product.productId}">
                        <img src="${product.image}"
                             class="card-img-top"
                             style="width:60px;height:60px;object-fit:cover"
                             onerror="this.src='/resources/no-image.png'">
                    </a>
                </td>

                <td>${product.productName}</td>
                <td>${product.price}원</td>
                <td>
                        ${product.stock}
                    <c:if test="${product.stock == 0}">
                        <span class="badge bg-danger">품절</span>
                    </c:if>
                </td>

                <td>
                    <a href="/admin/products/edit.do?product_id=${product.productId}"
                       class="btn btn-sm btn-secondary">
                        수정
                    </a>
                    <form method="post" action="/admin/products/delete.do" style="display:inline;">
                        <input type="hidden" name="product_id" value="${product.productId}">
                        <button type="submit" class="btn btn-sm btn-danger">
                            삭제
                        </button>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <nav>
        <ul class="pagination justify-content-center custom-pagination">
            <c:if test="${currentPage > 1}">
                <li class="page-item">
                    <a class="page-link" href="/admin/products.do?page=${currentPage - 1}&searchKeyword=${search_keyword}">
                        <span aria-hidden="true">&laquo;</span>
                    </a>
            </c:if>

            <c:forEach var="i" begin="1" end="${totalPages}">
                <li class="page-item ${i == currentPage ? 'active' : ''}">
                    <a class="page-link" href="/admin/products.do?page=${i}&searchKeyword=${search_keyword}">
                            ${i}
                    </a>
                </li>
            </c:forEach>

            <c:if test="${currentPage < totalPages}">
                <li class="page-item">
                    <a class="page-link" href="/admin/products.do?page=${currentPage + 1}&searchKeyword=${search_keyword}">
                        <span aria-hidden="true">&raquo;</span>
                    </a>
                </li>
            </c:if>
        </ul>
    </nav>

</div>