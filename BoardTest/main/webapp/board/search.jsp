<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="./resources/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/js/bootstrap.bundle.min.js"
    integrity="sha384-j1CDi7MgGQ12Z7Qab0qlWQ/Qqz24Gc6BM0thvEMVjHnfYGF0rmFCozFSxQBxwHKO" crossorigin="anonymous"></script>
<title>검색 결과 - 책판매 쇼핑몰</title>
</head>
<body>
<div class="container mt-4">
    <h2>검색 결과 목록</h2>

    <c:if test="${empty boardList}">
        <div class="alert alert-warning">검색 결과가 없습니다.</div>
    </c:if>

    <c:if test="${not empty boardList}">
        <ul class="list-group">
            <c:forEach var="item" items="${boardList}">
                <li class="list-group-item">
                    <strong>${item.subject}</strong><br/>
                    ${item.content}<br/>
                    <small class="text-muted">작성자: ${item.name}</small>
                </li>
            </c:forEach>
        </ul>
    </c:if>
</div>
</body>
</html>
