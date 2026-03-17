<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"
         trimDirectiveWhitespaces="true" session="false" %>

<div style="margin:auto; max-width:800px;">
    <!-- 상품 등록 -->
    <div class="p-2 mb-5">
        <h2 class="mb-3">[관리자] 상품 등록</h2>

        <form method="post" action="/admin/product/create.do" enctype="multipart/form-data">

            <div class="form-floating mb-2">
                <input type="text" name="product_name"
                       class="form-control"
                       id="product_name"
                       placeholder="상품 이름"
                       required>
                <label for="product_name">상품 이름</label>
            </div>

            <div class="form-floating mb-2">
                <input type="text" name="product_desc"
                       class="form-control"
                       id="product_desc"
                       placeholder="상품 설명"
                       required>
                <label for="product_desc">상품 설명</label>
            </div>

            <div class="form-floating mb-2">
                <input type="number" name="product_price"
                       class="form-control"
                       id="product_price"
                       placeholder="상품 가격"
                       required>
                <label for="product_price">상품 가격</label>
            </div>

            <div class="form-floating mb-2">
                <input type="number" name="product_stock"
                       class="form-control"
                       id="product_stock"
                       placeholder="상품 재고"
                       required>
                <label for="product_stock">상품 재고</label>
            </div>

            <div class="mb-3">
                <label for="formFile" class="form-label">상품 이미지</label>
                <input class="form-control" type="file" id="formFile" name="imageFile">
            </div>

            <div class="mb-3">
                <label class="form-label fw-bold">카테고리 선택 (1~3개)</label>
                <div class="border rounded p-3">
                    <div class="row row-cols-4 g-2">
                        <c:choose>
                            <c:when test="${not empty categories}">
                                <c:forEach var="category" items="${categories}">
                                    <div class="col">
                                        <div class="form-check">
                                            <input class="form-check-input"
                                                   type="checkbox"
                                                   name="categoryIds"
                                                   value="${category.categoryId}"
                                                   id="category_${category.categoryId}">

                                            <label class="form-check-label"
                                                   for="category_${category.categoryId}">
                                                    ${category.categoryName}
                                            </label>
                                        </div>
                                    </div>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <a class="text-danger" href="/admin/categories/create.do">
                                    카테고리를 먼저 등록하세요
                                </a>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>


            <button class="w-100 btn btn-lg btn-primary mt-3" type="submit">
                상품 등록
            </button>

        </form>

        <div class="text-danger mt-2">${error}</div>
    </div>
</div>

<script>
    document.querySelectorAll("input[name='categoryIds']").forEach(cb => {
        cb.addEventListener("change", function() {
            let checked = document.querySelectorAll("input[name='categoryIds']:checked");

            if(checked.length > 3) {
                alert("카테고리는 최대 3개까지 선택 가능합니다.");
                this.checked = false;
            }
        });
    });
</script>