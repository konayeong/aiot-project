<%@ page contentType="text/html;charset=UTF-8" language="java"
         trimDirectiveWhitespaces="true" session="false" %>

<div style="margin:auto; max-width:800px;">

    <div class="p-2 mb-5">
        <h2 class="mb-3">[관리자] 카테고리 등록</h2>

        <form method="post" action="/admin/categories/create.do">

            <div class="form-floating mb-2">
                <input type="text" name="category_name"
                       class="form-control"
                       id="category_name"
                       placeholder="카테고리 이름"
                       required>
                <label for="category_name">카테고리 이름</label>
            </div>

            <div class="form-floating mb-2">
                <input type="number" name="sort_order"
                       class="form-control"
                       id="sort_order"
                       placeholder="정렬 순서"
                       required>
                <label for="sort_order">정렬 순서</label>
            </div>

            <button class="w-100 btn btn-lg btn-primary mt-3" type="submit">
                카테고리 등록
            </button>
        </form>

        <div class="text-danger mt-2">${error}</div>
    </div>
</div>
