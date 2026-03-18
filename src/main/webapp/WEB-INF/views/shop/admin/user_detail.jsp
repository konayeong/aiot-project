<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
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


<div style="margin:auto;">

  <!-- 회원 목록 -->
  <div class="d-flex justify-content-between align-items-center mb-3">
    <h3 class="mb-0">
      <c:choose>
        <c:when test="${user.userAuth == 'ROLE_ADMIN'}"><span class="badge bg-danger me-2">관리자</span></c:when>
        <c:otherwise><span class="badge bg-primary me-2">회원</span></c:otherwise>
      </c:choose>
      ${user.userName} 님의 정보
    </h3>  </div>

  <table class="table table-borderless table-hover">

    <thead class="table-dark">
    <tr>
      <th>권한</th>
      <th>ID</th>
      <th>이름</th>
      <th>비밀번호</th>
      <th>생일</th>
      <th>보유포인트</th>
      <th>가입 날짜</th>
      <th>최근 로그인</th>
    </tr>
    </thead>

    <tbody>

      <tr>
        <td>
          <c:choose>
            <c:when test="${user.userAuth == 'ROLE_ADMIN'}"><span class="badge bg-danger">관리자</span></c:when>
            <c:when test="${user.userAuth == 'ROLE_USER'}"><span class="badge bg-primary">회원</span></c:when>
            <c:otherwise><span class="badge bg-secondary">${user.userAuth}</span></c:otherwise>
          </c:choose>
        </td>

        <td>${user.userId}</td>

        <td>${user.userName}</td>

        <td>${user.userPassword}</td>

        <td>${user.userBirth}</td>

        <td><fmt:formatNumber value='${user.userPoint}' pattern='#,###'/> P</td>

        <td>${user.createdAt}</td>

        <td>${user.latestLoginAt}</td>

      </tr>
    </tbody>
  </table>

  <h4 class="fw-bold mt-5 mb-3">등록된 배송지</h4>
  <div class="card shadow-sm border-0 mb-4">
    <div class="card-body p-0">
      <table class="table table-hover text-center align-middle mb-0">
        <thead class="table-light">
        <tr>
          <th>주소</th>
        </tr>
        </thead>
        <tbody>
        <c:choose>
          <c:when test="${empty addressList}">
            <tr>
              <td colspan="3" class="py-5 text-muted">등록된 배송지가 없습니다.</td>
            </tr>
          </c:when>
          <c:otherwise>
            <c:forEach var="address" items="${addressList}">
              <tr>
                <td class="fw-bold">${address.address}
              </tr>
            </c:forEach>
          </c:otherwise>
        </c:choose>
        </tbody>
      </table>
    </div>
  </div>

  <h4 class="fw-bold mt-5 mb-3">주문 내역</h4>
  <div class="card shadow-sm border-0 mb-4">
    <div class="card-body p-0">
      <table class="table table-hover text-center align-middle mb-0">
        <thead class="table-light">
        <tr>
          <th>주문번호</th>
          <th>주문일자</th>
          <th>결제액</th>
          <th>상세내역</th>
        </tr>
        </thead>
        <tbody>
        <c:choose>
          <c:when test="${empty orderPage.content}">
            <tr>
              <td colspan="4" class="py-5 text-muted">이 회원의 주문 내역이 없습니다.</td>
            </tr>
          </c:when>
          <c:otherwise>
            <c:forEach var="order" items="${orderPage.content}">
              <tr>
                <td class="fw-bold text-primary">${order.orderId}</td>
                <td>
                  <fmt:parseDate value="${order.createdAt}" pattern="yyyy-MM-dd'T'HH:mm:ss" var="parsedOrderDate" type="both" />
                  <fmt:formatDate value="${parsedOrderDate}" pattern="yyyy-MM-dd HH:mm"/>
                </td>
                <td><fmt:formatNumber value="${order.usedPoint}" pattern="#,###"/> P</td>
                <td><a href="/admin/orderDetail.do?user_id=${user.userId}&order_id=${order.orderId}" class="btn btn-outline-secondary">확인</a></td>
              </tr>
            </c:forEach>
          </c:otherwise>
        </c:choose>
        </tbody>
      </table>
    </div>
  </div>

  <c:if test="${totalPages > 0}">
    <nav class="mb-4">
      <ul class="pagination justify-content-center custom-pagination">
        <c:if test="${currentPage > 1}">
          <li class="page-item">
            <a class="page-link" href="/admin/memberDetail.do?userId=${user.userId}&page=${currentPage - 1}">
              <span aria-hidden="true">&laquo;</span>
            </a>
          </li>
        </c:if>

        <c:forEach var="i" begin="1" end="${totalPages}">
          <li class="page-item ${i == currentPage ? 'active' : ''}">
            <a class="page-link" href="/admin/memberDetail.do?userId=${user.userId}&page=${i}">
                ${i}
            </a>
          </li>
        </c:forEach>

        <c:if test="${currentPage < totalPages}">
          <li class="page-item">
            <a class="page-link" href="/admin/memberDetail.do?userId=${user.userId}&page=${currentPage + 1}">
              <span aria-hidden="true">&raquo;</span>
            </a>
          </li>
        </c:if>
      </ul>
    </nav>
  </c:if>
  <div class="text-end mt-4">
    <a href="/admin/memberList.do" class="btn btn-secondary">목록으로 돌아가기</a>
  </div>

</div>