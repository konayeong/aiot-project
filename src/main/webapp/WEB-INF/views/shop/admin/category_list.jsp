<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"
         trimDirectiveWhitespaces="true" session="false" %>

<div style="margin:auto; max-width:800px;">
    <!-- 카테고리 리스트 -->
    <div class="d-flex justify-content-between align-items-center mb-3">

        <h3 class="mb-3">카테고리 목록</h3>

        <a href="/admin/categories/create.do" class="btn btn-primary">
            카테고리 등록
        </a>

    </div>
    <table class="table table-striped table-hover">

        <thead class="table-dark">
        <tr>
            <th>번호</th>
            <th>카테고리명</th>
            <th>정렬순서</th>
            <th>관리</th>
        </tr>
        </thead>

        <tbody>

        <c:forEach var="category" items="${categories}" varStatus="status">
            <tr>

                <td>${status.count}</td>

                <td>${category.categoryName}</td>

                <td>${category.sortOrder}</td>

                <td>

                    <!-- 수정 -->
                    <a href="/admin/categories/edit.do?category_id=${category.categoryId}"
                       class="btn btn-sm btn-warning">
                        수정
                    </a>

                    <!-- 삭제 -->
                    <form method="post" action="/admin/categories/delete.do" style="display:inline;">
                        <input type="hidden" name="category_id" value="${category.categoryId}">
                        <button type="submit" class="btn btn-sm btn-danger">
                            삭제
                        </button>
                    </form>

                </td>

            </tr>
        </c:forEach>

        </tbody>

    </table>

</div>
