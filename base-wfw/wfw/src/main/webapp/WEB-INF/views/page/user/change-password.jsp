<html xmlns="http://www.w3.org/1999/xhtml" 
		xmlns:th="http://www.thymeleaf.org"
		layout:decorator="/layout/base-layout">
	<div layout:fragment="content">
		<div class="login-container">
		    <form th:action="@{/user/change-password}" th:object="${changePwordForm}" method="post">
		     	<fieldset>
		     		<legend th:text="#{cap.pword.chng}"></legend>
		   			<div th:if="${error}" class="alert alert-danger" role="alert"><p th:text="*{error}"></p></div>
		   			<div th:if="${msg}" class="alert alert-info" role="alert"><p th:text="*{msg}"></p></div>
		     		<div>
		           		<input class="col-xs-9" th:type="password" name="currPword" th:placeholder="#{lbl.cmn.pword.curr}" autofocus="autofocus"/> <br/>
		           		<input class="col-xs-9" th:type="password" name="newPword" th:placeholder="#{lbl.cmn.pword.new}" /> <br/>
		           		<input class="col-xs-9" th:type="password" name="repNewPword" th:placeholder="#{lbl.cmn.pword.new.1}" /> <br/>
		           		<input class="btn btn-info" th:type="submit" th:value="#{btn.cmn.sub}" />
		     		</div>
				</fieldset>
		    </form>
		</div>
	</div>
</html>