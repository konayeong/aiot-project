<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" trimDirectiveWhitespaces="true" %>
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
    <title>nhn아카데미 shopping mall Admin</title>

</head>
<body class="d-flex flex-column min-vh-100">
        <header class="p-3 bg-dark text-white">
            <div class="container">
                <div class="d-flex flex-wrap align-items-center">
                    <ul class="nav">
                        <li><a href="/index.do" class="nav-link px-2 text-white">메인페이지로</a></li>
                    </ul>

                    <div class="mx-auto">
                        <a class="btn btn-outline-light me-2" href="/admin/products.do">상품</a>
                        <a class="btn btn-warning" href="/admin/categories.do">카테고리</a>
                        <a class="btn btn-primary me-2" href="/admin/memberList.do">회원</a>
                    </div>

                    <div>
                        <a class="btn btn-outline-light" href="/logout.do">로그아웃</a>
                    </div>
                </div>
            </div>
        </header>

        <main>
            <div class="album py-5 bg-light">
                <div class="container">
                    <jsp:include page="${layout_content_holder}" />
                </div>
            </div>

        </main>

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