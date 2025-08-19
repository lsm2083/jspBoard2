<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<h2>로그인</h2>
<c:if test="${not empty error}">
    <p style="color:red">${error}</p>
</c:if>
<form action="<c:url value='/login'/>" method="post">
    <input name="loginId" placeholder="아이디">
    <button type="submit">로그인</button>
</form>
<p><a href="<c:url value='/posts'/>">목록으로</a></p>
