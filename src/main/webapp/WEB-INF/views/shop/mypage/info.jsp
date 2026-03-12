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
                    <a href="/mypage/order.do" class="btn btn-outline-secondary px-4">주문 내역</a>
                    <a href="/mypage/info.do" class="btn btn-primary px-4">회원 정보 수정</a>
                    <a href="/mypage/address.do" class="btn btn-outline-secondary px-4">주소 관리</a>
                </div>
            </div>
        </div>
    </div>

    <div class="row justify-content-center">
        <div class="col-md-8">
            <div class="card shadow-sm">
                <div class="card-header bg-white py-3">
                    <h5 class="mb-0 fw-bold">회원 정보 수정</h5>
                </div>
                <div class="card-body p-4">
                    <form action="/mypage/infoAction.do" method="post">

                        <div class="mb-3 row">
                            <label for="user_id" class="col-sm-3 col-form-label fw-bold text-end">아이디</label>
                            <div class="col-sm-9">
                                <input type="text" readonly class="form-control-plaintext text-muted" id="user_id" value="${loginUser.userId}">
                            </div>
                        </div>

                        <div class="mb-3 row">
                            <label class="col-sm-3 col-form-label fw-bold text-end">보유 포인트</label>
                            <div class="col-sm-9">
                                <input type="text" readonly class="form-control-plaintext text-warning fw-bold" value="<fmt:formatNumber value='${loginUser.userPoint}' pattern='#,###'/> P">
                            </div>
                        </div>

                        <div class="mb-3 row">
                            <label for="user_name" class="col-sm-3 col-form-label fw-bold text-end">이름</label>
                            <div class="col-sm-9">
                                <input type="text" class="form-control" id="user_name" name="user_name" value="${loginUser.userName}" required>
                            </div>
                        </div>

                        <div class="mb-3 row">
                            <label for="user_password" class="col-sm-3 col-form-label fw-bold text-end">비밀번호</label>
                            <div class="col-sm-9">
                                <input type="password" class="form-control" id="user_password" name="user_password" value="${loginUser.userPassword}" required>
                            </div>
                        </div>

                        <div class="mb-3 row">
                            <label for="user_birth" class="col-sm-3 col-form-label fw-bold text-end">생년월일</label>
                            <div class="col-sm-9">
                                <input type="text" readonly class="form-control-plaintext text-muted" id="user_birth" value="${loginUser.userBirth}">
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-sm-9 offset-sm-3">
                                <button type="submit" class="btn btn-primary w-100 fw-bold py-2">정보 수정하기</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<c:if test="${not empty errorMessage}">
    <script>
        alert("${errorMessage}");
    </script>
</c:if>