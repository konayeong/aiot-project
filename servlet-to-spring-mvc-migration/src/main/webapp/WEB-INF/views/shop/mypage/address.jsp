<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

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
          <a href="/mypage/order.do" class="btn btn-outline-secondary px-4">주문 내역</a>
          <a href="/mypage/info.do" class="btn btn-outline-secondary px-4">회원 정보 수정</a>
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

                    <div class="d-flex gap-2">
                      <button type="button" class="btn btn-sm btn-outline-secondary" data-bs-toggle="modal" data-bs-target="#editModal${addr.addressId}">
                        수정
                      </button>

                      <form action="/mypage/addressDeleteAction.do" method="post" class="m-0">
                        <input type="hidden" name="address_id" value="${addr.addressId}">
                        <button type="submit" class="btn btn-sm btn-outline-danger" onclick="return confirm('이 주소를 삭제하시겠습니까?');">삭제</button>
                      </form>
                    </div>
                  </li>

                  <div class="modal fade" id="editModal${addr.addressId}" tabindex="-1" aria-labelledby="editModalLabel${addr.addressId}" aria-hidden="true">
                    <div class="modal-dialog">
                      <div class="modal-content">
                        <form action="/mypage/addressUpdateAction.do" method="post">
                          <div class="modal-header">
                            <h5 class="modal-title fw-bold" id="editModalLabel${addr.addressId}">배송지 수정</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                          </div>
                          <div class="modal-body">
                            <input type="hidden" name="address_id" value="${addr.addressId}">

                            <div class="alert alert-light border mb-3 text-muted" style="font-size: 0.9rem;">
                              <strong>기존 주소:</strong><br>
                                ${addr.address}
                            </div>

                            <label class="form-label text-muted fw-bold">새로운 주소 입력</label>

                            <div class="mb-2">
                              <input type="text" class="form-control" name="zipcode" placeholder="우편번호 (예: 12345)" required>
                            </div>
                            <div class="mb-2">
                              <input type="text" class="form-control" name="base_address" placeholder="기본 주소 (예: 광주광역시 동구 조선대길 123)" required>
                            </div>
                            <div class="mb-2">
                              <input type="text" class="form-control" name="detail_address" placeholder="상세 주소 (예: IT융합대학 1층)" required>
                            </div>

                          </div>
                          <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
                            <button type="submit" class="btn btn-primary">수정 완료</button>
                          </div>
                        </form>
                      </div>
                    </div>
                  </div>
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
              <div class="mb-3 row">
                <label for="zipcode" class="col-sm-3 col-form-label fw-bold text-end">우편번호</label>
                <div class="col-sm-9">
                  <input type="text" class="form-control" id="zipcode" name="zipcode" placeholder="12345" required>
                </div>
              </div>

              <div class="mb-3 row">
                <label for="base_address" class="col-sm-3 col-form-label fw-bold text-end">기본 주소</label>
                <div class="col-sm-9">
                  <input type="text" class="form-control" id="base_address" name="base_address" placeholder="광주광역시 동구 조선대길 123" required>
                </div>
              </div>

              <div class="mb-3 row">
                <label for="detail_address" class="col-sm-3 col-form-label fw-bold text-end">상세 주소</label>
                <div class="col-sm-9">
                  <input type="text" class="form-control" id="detail_address" name="detail_address" placeholder="IT융합대학 1층" required>
                </div>
              </div>
            </div>
            <button type="submit" class="btn btn-primary w-100 fw-bold">주소 저장하기</button>
          </form>
        </div>
      </div>
    </div>
  </div>
</div>