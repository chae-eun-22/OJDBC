/*
 *  addBook.jsp에서 입력 폼에 대한 검증용 코드 작성
 */

 
 function CheckAddBook() {
	 
	 var bookId = document.getElementById("bookId");
	 var name = document.getElementById("name");
	 var unitPrice = document.getElementById("unitPrice");
	 var unitsInStock = document.getElementById("unitsInStock");
	 var description = document.getElementById("description");
	 
	 /* 도서 아이디 체크 */
	 if (!check(/^ISBN[0-9]{4,11}$/, bookId,
	 	"[도서 코드]\nISBN과 숫자를 조합하여 5~12자까지 입력하세요\n첫 글자는 반드시 ISBN로 시작하세요"))
	 return false;
	 
	 /* 도서명 체크 */
	 if (name.value.length < 4 || name.value.length > 50) {
		 alert("[도서명]\n최소 4자에서 최대 50자까지 입력하세요");
		 name.focus();
		 return false;
	 }
	 
	 /* 도서 가격 체크 */
	 if (unitPrice.value.length == 0 || isNaN(unitPrice.value)) {
		 alert("[가격]\n숫자만 입력하세요");
		 unitPrice.focus;
		 return false;
	 }
	 
	 if (unitPrice.value <  0 || unitPrice.value > 7 || isNaN(unitPrice.value)) {
		 alert("[가격]\n1원 이상 100만원 미만 값만 입력하세요");
		 unitPrice.focus();
		 return false;
	 }
	 
	 /* 재고 수 체크 */
	 if (isNaN(unitsInStock.value) || unitsInStock.value.length < 4) {
		 alert("[재고 수]\n숫자만 입력하세요 또는 999개 이하만 입력 가능합니다.");
		 unitsInStock.focus();
		 return false;
	 }
	 
	 /* 상세 설명 10자 이상 */
	 if (description.value.length < 10) {
		 alert("[상세설명]\n최소 10자 이상 입력하세요");
		 description.focus();
		 return false;
	 }
	 
	 function check(regExp, e, msg) {
		 if (regExp.test(e.value)) {
			 return true;
		 }
		 alert(msg);
		 e.focus();
		 return false;
	 }
	 
	 
	 document.netBook.submit()
	 
 }