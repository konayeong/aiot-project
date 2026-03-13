<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="x" uri="jakarta.tags.xml" %>
<%@ taglib prefix="sql" uri="jakarta.tags.sql" %>

<!doctype html>
<html lang="ko">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
    <title>nhn아카데미 shopping mall</title>

</head>
<body class="d-flex flex-column min-vh-100">
    <div class="mainContainer">
        <nav class="p-3 bg-dark text-white">
            <div class="container">
                <div class="d-flex flex-wrap align-items-center justify-content-center justify-content-lg-start">
                    <ul class="nav col-12 col-lg-auto me-lg-auto mb-2 justify-content-center mb-md-0">
                        <li><a href="/index.do" class="nav-link px-2 text-secondary">Home</a></li>

                        <c:if test="${not empty sessionScope.loginUser}">
                            <c:choose>
                                <%-- 관리자인 경우 --%>
                                <c:when test="${sessionScope.loginUser.userAuth == 'ROLE_ADMIN'}">
                                    <li><a href="/admin/products.do" class="nav-link px-2 text-white">관리자페이지로</a></li>
                                </c:when>

                                <%-- 일반 유저인 경우 (마이페이지와 장바구니 묶음) --%>
                                <c:otherwise>
                                    <li><a href="/mypage/index.do" class="nav-link px-2 text-white">마이페이지</a></li>
                                    <li><a href="/cart.do" class="nav-link px-2 text-white">장바구니</a></li>
                                </c:otherwise>
                            </c:choose>
                        </c:if>
                    </ul>

                    <div class="text-end">
                        <c:choose>
                            <%-- 세션에 'loginUser' 객체가 존재할 경우 (로그인 성공 상태) --%>
                            <c:when test="${not empty sessionScope.loginUser}">
                                <span class="text-white me-3 fw-bold">${sessionScope.loginUser.userName}님</span>
                                <span class="text-white me-3">Point: ${sessionScope.loginUser.userPoint}P</span>
                                <a class="btn btn-outline-light me-2" href="/logout.do">로그아웃</a>
                                <form action="/mypage/withdrawalAction.do" method="post" style="display:inline;">
                                    <button type="submit" class="btn btn-outline-danger me-2" onclick="return confirm('정말로 탈퇴하시겠습니까?');">회원탈퇴</button>
                                </form>
                            </c:when>

                            <%-- 세션에 'loginUser' 객체가 없을 경우 (비로그인 상태) --%>
                            <c:otherwise>
                                <a class="btn btn-outline-light me-2" href="/login.do">로그인</a>
                                <a class="btn btn-warning" href="/signup.do">회원가입</a>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
        </nav>

        <main>
            <div class="album py-5 bg-light">
                <div class="container">
                    <jsp:include page="${layout_content_holder}" />
                </div>
            </div>

        </main>

    </div>
    <footer class="text-muted py-5 mt-auto">
        <div class="container">
            <p class="float-end mb-1">
                <a href="#">Back to top</a>
            </p>
            <p class="mb-1">shoppingmall example is © nhnacademy.com</p>
        </div>
    </footer>
</body>
</html>