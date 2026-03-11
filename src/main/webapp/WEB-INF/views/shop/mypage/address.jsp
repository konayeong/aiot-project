<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

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
          <a href="/mypage/index.do" class="btn btn-outline-secondary fw-bold px-4">포인트 내역</a>
<%--          <a href="#" class="btn btn-outline-secondary px-4">회원 정보 수정</a>--%>
          <a href="/mypage/address.do" class="btn btn-primary px-4">주소 관리</a>
        </div>
      </div>
    </div>
  </div>

  <div class="row">
    <div class="col-md-8 mb-4">
      <div class="card shadow-sm h-100">
        <div class="card-header bg-white py-3">
          <h5 class="mb-0 fw-bold">등록된 배송지 목록</h5>
        </div>
        <div class="card-body p-0">
          <ul class="list-group list-group-flush">
            <c:choose>
              <c:when test="${empty addressList}">
                <li class="list-group-item py-4 text-center text-muted">등록된 배송지가 없습니다.</li>
              </c:when>
              <c:otherwise>
                <c:forEach var="addr" items="${addressList}">
                  <li class="list-group-item d-flex justify-content-between align-items-center py-3">
                    <div>
                      <i class="text-primary me-2 fw-bold">📍</i>
                        ${addr.address}
                    </div>

                    <form action="/mypage/addressDeleteAction.do" method="post" class="m-0">
                      <input type="hidden" name="address_id" value="${addr.addressId}">
                      <button type="submit" class="btn btn-sm btn-outline-danger" onclick="return confirm('이 주소를 삭제하시겠습니까?');">삭제</button>
                    </form>
                  </li>
                </c:forEach>
              </c:otherwise>
            </c:choose>
          </ul>
        </div>
      </div>
    </div>

    <div class="col-md-4 mb-4">
      <div class="card shadow-sm h-100">
        <div class="card-header bg-white py-3">
          <h5 class="mb-0 fw-bold">새 배송지 추가</h5>
        </div>
        <div class="card-body">
          <form action="/mypage/addressAddAction.do" method="post">
            <div class="mb-3">
              <label for="address" class="form-label text-muted">주소 입력</label>
              <textarea class="form-control" id="address" name="address" rows="3" placeholder="예) 광주광역시 동구 조선대길 123" required></textarea>
            </div>
            <button type="submit" class="btn btn-primary w-100 fw-bold">주소 저장하기</button>
          </form>
        </div>
      </div>
    </div>
  </div>
</div>