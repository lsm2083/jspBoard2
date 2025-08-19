<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<h2>Posts</h2>

<c:url var="newUrl"    value="/posts/new"/>
<c:url var="loginUrl"  value="/login"/>
<c:url var="logoutUrl" value="/logout"/>

<p>
    <c:choose>
        <c:when test="${not empty sessionScope.loginId}">
            안녕하세요, ${sessionScope.loginId} |
            <a href="${logoutUrl}">로그아웃</a> |
            <a href="${newUrl}">새 글</a>
        </c:when>
        <c:otherwise>
            <a href="${loginUrl}">로그인</a>
        </c:otherwise>
    </c:choose>
</p>

<ul>
    <c:forEach var="p" items="${posts}">
        <c:url var="detailUrl" value="/posts/detail">
            <c:param name="id" value="${p.id}"/>
        </c:url>
        <li>
            <a href="${detailUrl}">[${p.id}] ${fn:escapeXml(p.title)} / ${p.author}</a>
        </li>
    </c:forEach>
</ul>
