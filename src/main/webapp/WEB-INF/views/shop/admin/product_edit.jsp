<%--
  Created by IntelliJ IDEA.
  User: chosun-nhn55
  Date: 26. 3. 12.
  Time: 오후 12:56
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"  trimDirectiveWhitespaces="true" session="false" %>

<div style="margin:auto">
    <div class="p-2 mb-5">
        <h2 class="mb-3">[관리자] 상품 수정</h2>

        <form method="post" action="/admin/products/edit.do" enctype="multipart/form-data">
            <input type="hidden" name="product_id" value="${product.productId}">
            <div class="form-floating mb-2">
                <input type="text" name="product_name"
                       class="form-control"
                       id="product_name"
                       placeholder="상품 이름"
                       value="${product.productName}"
                       required>
                <label for="product_name">상품 이름</label>
            </div>

            <div class="form-floating mb-2">
                <input type="text" name="product_desc"
                       class="form-control"
                       id="product_desc"
                       placeholder="상품 설명"
                       value="${product.description}"
                       required>
                <label for="product_desc">상품 설명</label>
            </div>

            <div class="form-floating mb-2">
                <input type="number" name="product_price"
                       class="form-control"
                       id="product_price"
                       placeholder="상품 가격"
                       value="${product.price}"
                       required>
                <label for="product_price">상품 가격</label>
            </div>

            <div class="form-floating mb-2">
                <input type="number" name="product_stock"
                       class="form-control"
                       id="product_stock"
                       placeholder="상품 재고"
                       value="${product.stock}"
                       required>
                <label for="product_stock">상품 재고</label>
            </div>

            <div class="form-floating mb-2">
                <img src="${product.image}"
                     style="width: 200px; height: 200px; object-fit: cover"
                     onerror="this.src='/resources/no-image.png'">
                <input type="hidden" name="existingImage" value="${product.image}">
                <input type="file" name="imageFile">
            </div>

            <div class="mb-3">
                <label class="form-label fw-bold">카테고리 선택 (1~3개)</label>
                <div class="border rounded p-3">
                    <div class="row row-cols-4 g-2">
                        <c:forEach var="category" items="${categories}">
                            <div class="col">
                                <div class="form-check">

                                    <input class="form-check-input"
                                           type="checkbox"
                                           name="categoryIds"
                                           value="${category.categoryId}"
                                           id="category_${category.categoryId}"
                                            <c:if test="${checkCategoryIds.contains(category.categoryId)}">
                                                   checked
                                            </c:if>>

                                    <label class="form-check-label"
                                           for="category_${category.categoryId}">
                                            ${category.categoryName}
                                    </label>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>

            <button class="w-100 btn btn-lg btn-primary mt-3" type="submit">
                상품 수정
            </button>
        </form>
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