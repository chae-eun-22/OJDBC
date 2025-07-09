<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="mvc.model.BoardDTO"%>
<%
	/* String sessionId = (String) session.getAttribute("sessionId"); */
	String name = (String) request.getAttribute("name");
	List boardList = (List) request.getAttribute("boardList");
	if (boardList == null) {
		boardList = new ArrayList(); // null 방지용 빈 리스트 할당
	}
	int total_record = ((Integer) request.getAttribute("total_record")).intValue();
	int pageNum = ((Integer) request.getAttribute("pageNum")).intValue();
	int total_page = ((Integer) request.getAttribute("total_page")).intValue();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="./resources/css/bootstrap.min.css" rel="stylesheet">
<!-- cdn이 아닌 로컬에 저장된 css -->
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-j1CDi7MgGQ12Z7Qab0qlWQ/Qqz24Gc6BM0thvEMVjHnfYGF0rmFCozFSxQBxwHKO"
	crossorigin="anonymous"></script>
<%-- <script type="text/javascript">
	function checkForm() {
		if (${sessionId == null}) {
			alert("로그인 해주세요.")
			return false;
		}
		
		location.href="./BoardWriteForm.do?id=<%=sessionId%>"
				
	}
</script> --%>
<title>Board</title>
</head>
<body>

	<div class="container py-4">

		<jsp:include page="../menu.jsp"></jsp:include>
		<!-- 메뉴바를 외부파일로 연결 -->

		<div class="p-5 mb-4 bg-body-tertiary rounded-3">
			<div class="container-fluid py-1">
				<h1 class="display-5 fw-bold">게시판</h1>
				<p class="col-md-8 fs-4">Board</p>
			</div>
		</div>
		<!-- 중간타이틀 : 상단 box -->

		<div class="row align-items-md-stretch   text-center">
			<form action="<c:url value="./BoardListAction.do"/>" method="post">
				<div class="text-end">
					<c:choose>
						<c:when test="${isSearch}">
							<c:if test="${not empty param.items and not empty param.text}">
								<div>'${text}'에 대한 검색 결과입니다.</div>
							</c:if>
							<span class="badge text-bg-success">검색 결과 ${total_record}건</span>
						</c:when>
						<c:otherwise>
							<span class="badge text-bg-success">전체 ${total_record}건</span>
						</c:otherwise>
					</c:choose>
				</div>

				<div style="padding-top: 20px">
					<table class="table table-hover text-center">
						<tr>
							<td>번호</td>
							<td>제목</td>
							<td>작성일</td>
							<td>조회</td>
							<td>글쓴이</td>
						</tr>
						
						<%
							if (boardList == null || boardList.size() == 0) {
						%>
							<tr>
								<td colspan="5">검색 결과가 없습니다.</td>
							</tr>
						<%
							}
						%>
						<%
						for (int j = 0; j < boardList.size(); j++) {
							BoardDTO notice = (BoardDTO) boardList.get(j);
						%>

						
						<tr>
							<td><%=notice.getNum()%></td>
							<td><a
								href="./BoardViewAction.do?num=<%=notice.getNum()%>&pageNum=<%=pageNum%>"><%=notice.getSubject()%></a></td>
							<td><%=notice.getRegist_day()%></td>
							<td><%=notice.getHit()%></td>
							<td><%=notice.getName()%></td>
						</tr>
						<%
						}
						%>
					</table>
				</div>

				<div align="center">
					<c:set var="pageNum" value="<%=pageNum%>" />
					<c:forEach var="i" begin="1" end="<%=total_page%>">
						<a href="<c:url value='./BoardListAction.do?'>
								<c:param name='pageNum' value='${i}' />
								<c:param name='items' value='${items}' />
								<c:param name='text' value='${text}' />
								</c:url>">
							<c:choose>
								<c:when test="${pageNum == i}">
									<font color='4C5317'><b> [${i}]</b></font>
								</c:when>
								<c:otherwise>
									<font color='4C5317'>[${i}]</font>
								</c:otherwise>
							</c:choose>
						</a>
					</c:forEach>
				</div>
				<div class="py-3" align="right">
					<a href="./BoardWriteForm.do" class="btn btn-primary">&laquo;글쓰기</a>
				</div>
				<div align="left">
					<form action="<c:url value='/BoardListAction.do' />" method="post">
						<select name="items" class="txt">
							<option value="subject" ${items == 'subject' ? 'selected' : ''}>제목</option>
							<option value="content" ${items == 'content' ? 'selected' : ''}>본문</option>
							<option value="name" ${items == 'name' ? 'selected' : ''}>글쓴이</option>
						</select>
						<input name="text" type="text" placeholder="검색어 입력" value="${text != null ? text: ''}"/>
						<input type="submit" id="btnAdd" class="btn btn-primary" value="검색" />
					</form>
				</div>
			</form>

		</div>
		<jsp:include page="../footer.jsp" />
	</div>
</body>
</html>