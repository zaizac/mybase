<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml"
	xmlns:th="http://www.thymeleaf.org"
	xmlns:tiles="http://www.thymeleaf.org">

<head th:substituteby="commons/main-head"></head>
<body>
	<div id="page">
		<div id="header" th:include="commons/main-header"></div>
		<div id="menu" th:include="commons/main-menu"></div>
		<div id="content" tiles:include="content"></div>
		<div th:include="commons/modal-popup"></div>
		<div id="footer">
		  <footer class="main-footer" th:include="commons/main-footer"></footer>
		</div>

	</div>
	</body>
</html>
