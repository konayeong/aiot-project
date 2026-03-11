<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<div class="container mt-4">
  <div class="row mb-4">
    <div class="col">
      <h2 class="fw-bold">마이페이지</h2>
      <p class="text-muted">내 정보와 포인트 사용 내역을 한눈에 확인하세요.</p>
    </div>
  </div>

  <div class="row mb-4">
    <div class="col-12">
      <div class="card shadow-sm">
        <div class="card-body d-flex justify-content-around">
          <a href="/mypage/index.do" class="btn btn-primary fw-bold px-4">포인트 내역</a>
<%--          <a href="#" class="btn btn-outline-secondary px-4">회원 정보 수정</a>--%>
          <a href="/mypage/address.do" class="btn btn-outline-secondary px-4">주소 관리</a>
        </div>
      </div>
    </div>
  </div>

  <div class="row">
    <div class="col-12">
      <div class="card shadow-sm">
        <div class="card-header bg-white py-3">
          <h5 class="mb-0 fw-bold">포인트 사용 내역</h5>
        </div>
        <div class="card-body p-0">
          <div class="table-responsive">
            <table class="table table-hover text-center align-middle mb-0">
              <thead class="table-light">
              <tr>
                <th>날짜</th>
                <th>유형</th>
                <th>변동 금액</th>
                <th>관련 주문번호</th>
              </tr>
              </thead>
              <tbody>
              <c:choose>
                <c:when test="${empty pointHistoryPage.content}">
                  <tr>
                    <td colspan="4" class="py-5 text-muted">포인트 사용 내역이 없습니다.</td>
                  </tr>
                </c:when>
                <c:otherwise>
                  <c:forEach var="point" items="${pointHistoryPage.content}">
                    <tr>
                      <td>${point.createdAt}</td>

                      <td>
                        <c:choose>
                          <c:when test="${point.pointType == 'REGISTER'}"><span class="badge bg-success">회원가입</span></c:when>
                          <c:when test="${point.pointType == 'LOGIN'}"><span class="badge bg-info text-dark">로그인 적립</span></c:when>
                          <c:when test="${point.pointType == 'ORDER_USE'}"><span class="badge bg-danger">주문 사용</span></c:when>
                          <c:when test="${point.pointType == 'ORDER_REWARD'}"><span class="badge bg-primary">주문 적립</span></c:when>
                          <c:otherwise><span class="badge bg-secondary">${point.pointType}</span></c:otherwise>
                        </c:choose>
                      </td>

                      <td class="${point.pointType == 'ORDER_USE' ? 'text-danger' : 'text-success'} fw-bold">
                          ${point.pointType == 'ORDER_USE' ? '-' : '+'}
                        <fmt:formatNumber value="${point.amount}" pattern="#,###"/> P
                      </td>

                      <td>
                        <c:choose>
                          <c:when test="${not empty point.orderId}">${point.orderId}</c:when>
                          <c:otherwise>-</c:otherwise>
                        </c:choose>
                      </td>
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

  <c:if test="${pointHistoryPage.totalCount > 0}">
    <c:set var="totalPages" value="${pointHistoryPage.totalCount / 10}" />
    <c:if test="${pointHistoryPage.totalCount % 10 != 0}">
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