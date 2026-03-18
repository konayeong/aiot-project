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
    <!-- 회원 검색 -->
    <div class="mb-4">

        <form method="get" action="/admin/memberList.do" class="d-flex">

            <input type="search"
                   name="searchUser"
                   value="${searchUser}"
                   class="form-control me-2"
                   placeholder="회원 아이디로 검색하세요">

            <button class="btn btn-outline-dark" style="width: 10%">
                검색
            </button>

        </form>

    </div>

    <!-- 회원 목록 -->
    <div class="d-flex justify-content-between align-items-center mb-3">
        <h3 class="mb-0">회원 목록</h3>
    </div>

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
            <th>상세보기</th>
        </tr>
        </thead>

        <tbody>

        <c:forEach var="user" items="${users.content}">
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

                <td><a href="/admin/memberDetail.do?userId=${user.userId}" class="btn btn-outline-primary">확인</a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <nav>
        <ul class="pagination justify-content-center custom-pagination">
            <c:if test="${currentPage > 1}">
                <li class="page-item">
                    <a class="page-link" href="/admin/memberList.do?page=${currentPage - 1}&searchUser=${search_keyword}">
                        <span aria-hidden="true">&laquo;</span>
                    </a>
            </c:if>

            <c:forEach var="i" begin="1" end="${totalPages}">
                <li class="page-item ${i == currentPage ? 'active' : ''}">
                    <a class="page-link" href="/admin/memberList.do?page=${i}&searchUser=${search_keyword}">
                            ${i}
                    </a>
                </li>
            </c:forEach>

            <c:if test="${currentPage < totalPages}">
                <li class="page-item">
                    <a class="page-link" href="/admin/memberList.do?page=${currentPage + 1}&searchUser=${search_keyword}">
                        <span aria-hidden="true">&raquo;</span>
                    </a>
                </li>
            </c:if>
        </ul>
    </nav>

</div>