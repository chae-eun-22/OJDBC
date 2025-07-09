<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.Date" %> <!-- java 내장된 날짜용 객체를 삽입 -->	
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

<title>환영합니다. 책판매 쇼핑몰입니다.....</title>
</head>
<body>

	<div class="container py-4">

		<%@ include file="menu.jsp" %> 
		<!-- 메뉴바를 외부파일로 연결 -->
		
		<%!	String greeting = "Welcome to Book Shopping Mall";
			String tagline = "환영합니다. 웹 쇼핑몰 입니다. ";%>

		<div class="p-5 mb-4 bg-body-tertiary rounded-3">
			<div class="container-fluid py-1">
				<h1 class="display-5 fw-bold"><%=greeting%></h1>
				<p class="col-md-8 fs-4">BookMarket</p>
			</div>
			
		</div> <!-- 중간타이틀 : 상단 box -->
		
		
		 <div class="row align-items-md-stretch   text-center">
	     	<div class="col-md-12">
				<div class="h-100 p-5">
			  	   <h3><%=tagline%></h3>
			  	   <%
			  	   		Date day = new Date();
			  	   		String am_pm;	// 오전,오후 출력용
			  	   		int hour = day.getHours(); // 시간을 가져옴 
			  	   		int minute = day.getMinutes(); // 분을 가져옴
			  	   		int second = day.getSeconds(); // 초를 가져옴
			  	   		if(hour / 12 == 0 ){
			  	   			am_pm = "오전";
			  	   		}else {
			  	   			am_pm = "오후";
			  	   			hour =  hour - 12; // 오후 20 -> 오후 8
			  	   		}
			  	   		String CT = hour + ":" + minute + ":" + second + " " + am_pm;
			  	   		out.println("현재 접속 시간 : " + CT + "\n");
			  	   				
			  	   %>         
		        </div>
	    	</div>   
	   	</div> <!-- 본문영역 : 중간 box --> 
	   	
		<%@ include file="footer.jsp" %>

	</div>

</body>
</html>