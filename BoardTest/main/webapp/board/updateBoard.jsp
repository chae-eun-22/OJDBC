<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<%@ page import="java.util.Date" %> <!-- java 내장된 날짜용 객체를 삽입 -->	
<%-- <%
	String name = (String) request.getAttribute("name");
%> --%>
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
	
<title>게시글 수정</title>
</head>
<body>

	<div class="container py-4">

		<jsp:include page="/menu.jsp"></jsp:include>
		<!-- 메뉴바를 외부파일로 연결 -->
		
		<div class="p-5 mb-4 bg-body-tertiary rounded-3">
			<div class="container-fluid py-1">
				<h1 class="display-5 fw-bold">게시글 수정</h1>
				<p class="col-md-8 fs-4">Board Updating</p>
			</div>
		</div> <!-- 중간타이틀 : 상단 box -->
		
		<div class="row align-items-md-stretch   text-center">
		 	<form name="newWrite" action="./BoardWriteAction.do" method="post" onsubmit="return checkForm()">
		 		<%-- <input name="id" type="hidden" class="form-control" value="${sessionId}"> --%>
		 		<div class="mb-3 row">
		 			<label class="col-sm-2 control-label">성명</label>
		 			<div class="col-sm-3">
		 				<input name="name" id="name" type="text" class="form-control" placeholder="name">
		 			</div>
		 		</div>
		 		<div class="mb-3 row">
		 			<label class="col-sm-2 control-label">제목</label>
		 			<div class="col-sm-5">
		 				<input name="subject" type="text" class="form-control" placeholder="subject">
		 			</div>
		 		</div>
		 		<div class="mb-3 row">
		 			<label class="col-sm-2 control-label">내용</label>
		 			<div class="col-sm-8">
		 				<textarea name="content" id="content" cols="50" rows="5" class="form-control" placeholder="content"></textarea>
		 			</div>
		 		</div>
		 		<div class="mb-3 row">
		 			<div class="col-sm-offset-2 col-sm-10">
		 				<input type="submit" class="btn btn-primary" value="수정">
		 				<input type="reset" class="btn btn-primary" value="취소">
		 			</div>
		 		</div>
	   		</form>

	   	</div>
		<jsp:include page="/footer.jsp"></jsp:include>
	</div>
	
</body>
</html>