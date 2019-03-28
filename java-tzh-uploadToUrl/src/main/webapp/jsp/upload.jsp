<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<!--  
	SpringMVC 处理静态资源:
	1. 为什么会有这样的问题:
	优雅的 REST 风格的资源URL 不希望带 .html 或 .do 等后缀
	若将 DispatcherServlet 请求映射配置为 /, 
	则 Spring MVC 将捕获 WEB 容器的所有请求, 包括静态资源的请求, SpringMVC 会将他们当成一个普通请求处理, 
	因找不到对应处理器将导致错误。
	2. 解决: 在 SpringMVC 的配置文件中配置 <mvc:default-servlet-handler/>
-->
</head>
<script src="<%=request.getContextPath()%>/js/jquery-3.3.1.js"></script>
<script type="text/javascript">
	function aa(t) {
		var href = $(t).attr("href");//取值超连接当中的href属性
		document.getElementById("filePath").value = href;
		$("#user").submit();
	}

	function bb(t) {
		var href = $(t).attr("href");
		document.getElementById("loadFilePath").value = href;
		$("#download").submit();
	}
</script>
<body>
	<form action="user" method="POST" id="user">
		<input type="hidden" name="filePath" id="filePath" />
	</form>
	<form action="download" method="POST" id="download">
		<input type="hidden" name="loadFilePath" id="loadFilePath" />
	</form>

	<c:forEach items="${requestScope.fileList1 }" var="list">
		<c:if test="${list.mark == 1}">
			<h4>${list.name }</h4>
		</c:if>
		<c:if test="${list.mark == 0}">
			<a href="${list.name}" onclick="aa(this)">${list.name }</a>
		</c:if>
	</c:forEach>
	<br/>
	<br/>
	<c:forEach items="${requestScope.fileList }" var="emp">
		<!-- 判断是文件还是文件夹 -->
		<c:if test="${emp.mark == 0}">
			<a href="${emp.name}" onclick="aa(this)">${emp.name}</a>
			<br>
		</c:if>
		<c:if test="${emp.mark  == 1}">
			<a href="${emp.name}" onclick="bb(this)">${emp.name}</a>
			<br>
		</c:if>
	</c:forEach>
</body>
</html>
