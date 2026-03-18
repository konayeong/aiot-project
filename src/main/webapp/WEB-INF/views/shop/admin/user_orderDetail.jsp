<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<div class="container mt-4 mb-5" style="max-width: 800px;">
    <h3 class="fw-bold mb-4">주문 상세 내역 (주문번호: ${orderId})</h3>

    <div class="card shadow-sm mb-4">
        <div class="card-body p-0">
            <table class="table mb-0 text-center align-middle table-hover">
                <thead class="table-light">
                <tr>
                    <th scope="col" style="width: 15%;">이미지</th>
                    <th scope="col" style="width: 35%;">상품명</th>
                    <th scope="col" style="width: 15%;">단가</th>
                    <th scope="col" style="width: 15%;">구매 수량</th>
                    <th scope="col" style="width: 20%;">합계</th>
                </tr>
                </thead>
                <tbody>
                <c:choose>
                    <c:when test="${empty orderDetails}">
                        <tr>
                            <td colspan="4" class="py-5 text-muted">상세 내역을 찾을 수 없습니다.</td>
                        </tr>
                    </c:when>

                    <c:otherwise>
                        <c:forEach var="detail" items="${orderDetails}">
                            <c:set var="itemTotal" value="${detail.price * detail.quantity}" />
                            <c:set var="totalPrice" value="${totalPrice + itemTotal}" />
                            <tr>
                                <td>
                                    <img src="${detail.image}" class="img-thumbnail" alt="${detail.productName}"
                                         style="max-width: 80px; max-height: 80px; object-fit: cover;"
                                         onerror="this.src='/resources/no-image.png'">
                                </td>

                                <td class="text-start fw-bold">
                                    <a href="/products/detail.do?product_id=${detail.productId}" class="text-decoration-none text-dark">
                                            ${detail.productName}
                                    </a>
                                </td>

                                <td><fmt:formatNumber value="${detail.price}" pattern="#,###"/> 원</td>

                                <td>
                                        ${detail.quantity} 개
                                </td>

                                <td>
                                    <fmt:formatNumber value="${itemTotal}" pattern="#,###"/> 원
                                </td>
                            </tr>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
                </tbody>
            </table>
        </div>
    </div>

    <c:if test="${not empty orderDetails}">
        <div class="card-footer bg-light p-4 text-end">
            <h4 class="fw-bold mb-3">
                총 결제 금액: <span class="text-danger"><fmt:formatNumber value="${totalPrice}" pattern="#,###"/> 원</span>
            </h4>
            <div class="text-end">
                <a href="/admin/memberDetail.do?userId=${userId}" class="btn btn-secondary">목록으로 돌아가기</a>
            </div>
        </div>
    </c:if>

</div>