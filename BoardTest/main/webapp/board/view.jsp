<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="mvc.model.BoardDTO" %>
<%
	BoardDTO notice = (BoardDTO) request.getAttribute("board");
	int num = ((Integer) request.getAttribute("num")).intValue();
	int nowpage = ((Integer) request.getAttribute("page")).intValue();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="./resources/css/bootstrap.min.css" rel="stylesheet">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-j1CDi7MgGQ12Z7Qab0qlWQ/Qqz24Gc6BM0thvEMVjHnfYGF0rmFCozFSxQBxwHKO"
	crossorigin="anonymous"></script>
	
<title>Board</title>
</head>
<body>
	
	<div class="container py-4">

		<jsp:include page="../menu.jsp" /> 
		<!-- 메뉴바를 외부파일로 연결 -->
		
		<div class="p-5 mb-4 bg-body-tertiary rounded-3">
			<div class="container-fluid py-1">
				<h1 class="display-5 fw-bold">게시판</h1>
				<p class="col-md-8 fs-4">Board</p>
			</div>
		</div> <!-- 중간타이틀 : 상단 box -->
		
		 <div class="row align-items-md-stretch   text-center">
	     	<form name="newUpdate" action="BoardUpdateAction.do?num=<%=notice.getNum() %>&pageNum=<%=nowpage %>" method="post">
		 		<div class="mb-3 row">
		 			<label class="col-sm-2 control-label">성명</label>
		 			<div class="col-sm-3">
		 				<input name="name" class="form-control" value="<%=notice.getName() %>">
		 			</div>
		 		</div>
		 		<div class="mb-3 row">
		 			<label class="col-sm-2 control-label">제목</label>
		 			<div class="col-sm-5">
		 				<input name="subject" class="form-control" value="<%=notice.getSubject() %>">
		 			</div>
		 		</div>
		 		<div class="mb-3 row">
		 			<label class="col-sm-2 control-label">내용</label>
		 			<div class="col-sm-8" style="word-break: break-all;">
		 				<textarea name="content" cols="50" rows="5" class="form-control"> <%=notice.getContent() %></textarea>
		 			</div>
		 		</div>
		 		<div class="mb-3 row">
		 			<div class="col-sm-offset-2 col-sm-10">
		 				<%-- <c:set var="userId" value="<%=notice.getId() %>" /> --%>
		 				<%-- <c:if test="${sessionId == userId}"> --%>
		 				<p><a href="./BoardDeleteAction.do?num=<%=notice.getNum()%>&pageNum=<%=nowpage%>" class="btn btn-danger">삭제</a>
		 					<input type="submit" class="btn btn-success" value="수정">
		 				<%-- </c:if> --%>
		 				<a href="./BoardListAction.do?pageNum=<%=nowpage %>" class="btn btn-primary">목록</a>
		 			</div>
		 		</div>
	   		</form>
	   		
	   	</div>
		<jsp:include page="../footer.jsp" />
	</div>

</body>
</html>