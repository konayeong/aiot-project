<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<div class="container mt-4">
  <div class="row mb-4">
    <div class="col">
      <h2 class="fw-bold">마이페이지</h2>
    </div>
  </div>

  <div class="row mb-4">
    <div class="col-12">
      <div class="card shadow-sm">
        <div class="card-body d-flex justify-content-around">
          <a href="/mypage/index.do" class="btn btn-outline-secondary px-4">포인트 내역</a>
          <a href="/mypage/order.do" class="btn btn-primary px-4">주문 내역</a>
          <a href="/mypage/info.do" class="btn btn-outline-secondary px-4">회원 정보 수정</a>
          <a href="/mypage/address.do" class="btn btn-outline-secondary px-4">주소 관리</a>
        </div>
      </div>
    </div>
  </div>

  <div class="row">
    <div class="col-12">
      <div class="card shadow-sm">
        <div class="card-header bg-white py-3">
          <h5 class="mb-0 fw-bold">주문 내역</h5>
        </div>
        <div class="card-body p-0">
          <div class="table-responsive">
            <table class="table table-hover text-center align-middle mb-0">
              <thead class="table-light">
              <tr>
                <th>날짜</th>
                <th>주문 번호</th>
                <th>결제 금액</th>
              </tr>
              </thead>
              <tbody>
              <c:choose>
                <c:when test="${empty orderHistoryPage.content}">
                  <tr>
                    <td colspan="4" class="py-5 text-muted">주문 내역이 없습니다.</td>
                  </tr>
                </c:when>
                <c:otherwise>
                  <c:forEach var="order" items="${orderHistoryPage.content}">
                    <tr>
                      <td>${order.createdAt}</td>

                      <td>${order.orderId}</td>

                      <td>${order.usedPoint}</td>
                    </tr>
                  </c:forEach>
                </c:otherwise>
              </c:choose>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  </div>

  <c:if test="${orderHistoryPage.totalCount > 0}">
    <c:set var="totalPages" value="${orderHistoryPage.totalCount / 10}" />
    <c:if test="${orderHistoryPage.totalCount % 10 != 0}">
      <c:set var="totalPages" value="${totalPages + 1}" />
    </c:if>
    <fmt:parseNumber var="finalTotalPages" value="${totalPages}" integerOnly="true" />

    <nav class="mt-4">
      <ul class="pagination justify-content-center">
        <c:forEach begin="1" end="${finalTotalPages}" var="i">
          <li class="page-item ${currentPage == i ? 'active' : ''}">
            <a class="page-link" href="/mypage/index.do?page=${i}">${i}</a>
          </li>
        </c:forEach>
      </ul>
    </nav>
  </c:if>
</div>