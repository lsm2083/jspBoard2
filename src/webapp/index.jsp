c
<form action="hello" method="get">
    <input name="name" placeholder="Your name"/>
    <button type="submit">Go</button>
</form>

<form action = "age" method="get">
    <input name = "age" placeholder="Your age"/>
    <button type="submit">Go</button>
</form>

<form action="${pageContext.request.contextPath}/age" method="post">
    <input name = "age" type="number" placeholder="your age"/>
    <button type="submit">Go</button>
</form>
