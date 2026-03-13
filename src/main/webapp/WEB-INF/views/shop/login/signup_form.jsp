<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div style="margin: auto; width: 400px;">
  <div class="p-2">
    <form method="post" action="/signupAction.do">

      <h1 class="h3 mb-3 fw-normal">Signup Page</h1>

      <div class="form-floating">
        <input type="text" name="user_id" class="form-control" id="user_id" placeholder="회원 아이디" required>
        <label for="user_id">회원 아이디</label>
      </div>

      <div class="form-floating">
        <input type="text" name="user_name" class="form-control" id="user_name" placeholder="회원 이름" required>
        <label for="user_name">회원 이름</label>
      </div>

      <div class="form-floating">
        <input type="password" name="user_password" class="form-control" id="user_password" placeholder="비밀번호" required>
        <label for="user_password">비밀번호</label>
      </div>

      <div class="form-floating">
        <input type="text" name="user_birth" class="form-control" id="user_birth" placeholder="회원 생년월일" required>
        <label for="user_birth">회원 생년월일(YYYYmmdd)</label>
      </div>

      <button class="w-100 btn btn-lg btn-warning mt-3" type="submit">Sign up</button>

      <p class="mt-5 mb-3 text-muted">© 2022-2024</p>

    </form>
  </div>
</div>

<c:if test="${not empty errorMessage}">
  <script>
    alert("${errorMessage}");
  </script>
</c:if>