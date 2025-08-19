<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h2>새 글</h2>
<form action="<c:url value='/posts/create'/>" method="post">
    <p><input name="title" placeholder="제목" required style="width:400px"></p>
    <p><textarea name="content" placeholder="내용" required rows="8" cols="60"></textarea></p>
    <button type="submit">등록</button>
</form>
<p><a href="<c:url value='/posts'/>">취소</a></p>
