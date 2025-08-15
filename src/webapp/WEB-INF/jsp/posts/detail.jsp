<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<h2>${post.title}</h2>
<p>작성자: ${post.author}</p>
<pre>${post.content}</pre>
<p><a href="<c:url value='/posts'/>">목록</a></p>

