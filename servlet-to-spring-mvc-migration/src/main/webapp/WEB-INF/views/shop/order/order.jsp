<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" session="false" %>

<div class="container py-5">
    <h2 class="fw-bold mb-4">주문서 작성</h2>

    <div class="card mb-4">
        <div class="card-header fw-bold">
            주문 상품
        </div>
        <c:set var="totalPrice" value="0" />
        <c:forEach var="item" items="${orderItems}">
            <c:set var="totalPrice" value="${totalPrice + (item.product.price * item.quantity)}" />
            <div class="card-body">
                <div class="row align-items-center">
                    <div class="col-md-2">
                        <img src="${item.product.image}"
                             class="img-fluid rounded"
                             alt="${item.product.productName}"
                             onerror="this.src='/resources/no-image.png'">
                    </div>

                    <div class="col-md-6">
                        <h5 class="mb-1">${item.product.productName}</h5>
                    </div>
                    <div class="col-md-2 text-end">
                        <fmt:formatNumber value="${item.product.price}" pattern="#,###"/>원
                    </div>

                    <div class="col-md-2 text-end">
                        ${item.quantity}개
                    </div>

                </div>
            </div>
        </c:forEach>
    </div>

    <div class="card">
        <div class="card-header fw-bold">
            주문 정보
        </div>

        <div class="card-body">
            <form method="post" action="/orders.do">
                <div class="row mb-3">
                    <label class="col-sm-2 col-form-label">주문자</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" id="orderUserName" value="${user.userName}" readonly>
                    </div>
                </div>

                <div class="row mb-4">
                    <label class="col-sm-2 col-form-label">배송지</label>

                    <div class="col-sm-10">
                        <c:choose>
                            <c:when test="${not empty addresses}">
                                <select class="form-select" name="address_id">
                                    <c:forEach var="address" items="${addresses}">
                                        <option value="${address.addressId}">
                                                ${address.address}
                                        </option>
                                    </c:forEach>
                                </select>
                            </c:when>

                            <c:otherwise>
                                <a class="text-danger" href="/mypage/address.do">
                                    주소를 먼저 등록하세요
                                </a>
                            </c:otherwise>
                        </c:choose>

                    </div>
                </div>

                <div class="text-end">
                    <h5 class="fw-bold mb-3 d-inline">
                        총 결제 예상 금액:
                        <span class="text-danger"><fmt:formatNumber value="${totalPrice}" pattern="#,###"/> 원</span>
                    </h5>
                    <button type="submit" class="btn btn-warning px-4 ml-4">
                        주문하기
                    </button>
                </div>
            </form>
        </div>
    </div>

</div>