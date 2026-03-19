<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="container mt-4 mb-5">
    <div class="row mb-4">
        <div class="col">
            <h2 class="fw-bold">카트</h2>
        </div>
    </div>

    <div class="row">
        <div class="card shadow-sm h-100">
            <div class="card-header bg-white py-3">
                <h5 class="mb-0 fw-bold">장바구니</h5>
            </div>

            <div class="card-body p-0">
                <div class="table-responsive">
                    <table class="table table-hover text-center align-middle mb-0">
                        <thead class="table-light">
                        <tr>
                            <th scope="col" style="width: 15%;">이미지</th>
                            <th scope="col" style="width: 35%;">상품명</th>
                            <th scope="col" style="width: 15%;">판매가</th>
                            <th scope="col" style="width: 15%;">수량</th>
                            <th scope="col" style="width: 10%;">합계</th>
                            <th scope="col" style="width: 10%;">선택</th>
                        </tr>
                        </thead>
                        <tbody>

                        <c:set var="totalPrice" value="0" />

                        <c:choose>
                            <c:when test="${empty sessionScope.cart}">
                                <tr>
                                    <td colspan="6" class="py-5 text-muted">
                                        장바구니에 담긴 상품이 없습니다.
                                    </td>
                                </tr>
                            </c:when>

                            <c:otherwise>
                                <c:forEach var="item" items="${sessionScope.cart}">
                                    <c:set var="cartItem" value="${item.value}" />
                                    <c:set var="product" value="${cartItem.product}" />
                                    <%-- 각 상품의 총액 계산 (가격 * 수량) --%>
                                    <c:set var="itemTotal" value="${product.price * cartItem.quantity}" />
                                    <%-- 전체 총액 누적 --%>
                                    <c:set var="totalPrice" value="${totalPrice + itemTotal}" />

                                    <tr>
                                        <td>
                                            <img src="${product.image}" class="img-thumbnail" alt="${product.productName}"
                                                 style="max-width: 80px; max-height: 80px; object-fit: cover;"
                                                 onerror="this.src='/resources/no-image.png'">
                                        </td>

                                        <td class="text-start fw-bold">
                                            <a href="/products/detail.do?product_id=${product.productId}" class="text-decoration-none text-dark">
                                                    ${product.productName}
                                            </a>
                                        </td>

                                        <td>
                                            <fmt:formatNumber value="${product.price}" pattern="#,###"/> 원
                                        </td>

                                        <td>
                                            <form action="/cart/updateAction.do" method="post" class="d-flex justify-content-center align-items-center m-0">
                                                <input type="hidden" name="product_id" value="${product.productId}">
                                                <input type="number" name="quantity" class="form-control form-control-sm text-center me-1"
                                                       value="${cartItem.quantity}" min="1" max="${product.stock}" style="width: 60px;">
                                                <button type="submit" class="btn btn-sm btn-outline-secondary">변경</button>
                                            </form>
                                        </td>

                                        <td class="fw-bold text-primary">
                                            <fmt:formatNumber value="${itemTotal}" pattern="#,###"/> 원
                                        </td>

                                        <td>
                                            <form action="/cart/deleteAction.do" method="post" class="m-0">
                                                <input type="hidden" name="product_id" value="${product.productId}">
                                                <button type="submit" class="btn btn-sm btn-outline-danger">삭제</button>
                                            </form>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>

                        </tbody>
                    </table>
                </div>
            </div>
            <c:if test="${not empty sessionScope.cart}">
                <div class="card-footer bg-light p-4 text-end">
                    <h4 class="fw-bold mb-3">
                        총 결제 예상 금액: <span class="text-danger"><fmt:formatNumber value="${totalPrice}" pattern="#,###"/> 원</span>
                    </h4>

                    <a href="/index.do" class="btn btn-outline-secondary me-2">쇼핑 계속하기</a>
                        <input type="hidden" name="products" value="${products}">
                        <a href="/orders.do" class="btn btn-warning w-45">
                            주문하기
                        </a>
                </div>
            </c:if>
        </div>
    </div>
</div>