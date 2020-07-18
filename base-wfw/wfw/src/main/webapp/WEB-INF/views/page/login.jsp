
	<div id="login">
	
		<form th:action="@{/login}" method="post">
     	
     		<legend th:text="Login"></legend>
     		<fieldset>
     		
   			<div th:if="${error}" class="alert alert-danger" role="alert"><p th:text="*{error}"></p></div>
   			<div th:if="${msg}" class="alert alert-info" role="alert"><p th:text="*{msg}"></p></div>
     		<div>
     		<table>
                <tbody>
                	<tr>
                        <td>
           		<input class="col-xs-9" th:type="text" name="username" th:placeholder="#{lbl.cmn.uname}" autofocus="autofocus"/> <br/>
           		</td>
                    </tr>
                    <tr>
                        <td colspan="2" style="line-height:10px;">&nbsp;</td>
                    </tr>
                    <tr>
                        <td>
           		<input class="col-xs-9" th:type="password" name="password" th:placeholder="#{lbl.cmn.pword}" /> <br/>
           		</td>
                    </tr>
                    <tr>
                        <td colspan="2" style="line-height:10px;">&nbsp;</td>
                    </tr>
                   
           		<tr>
                        <td>
                        <input class="btn btn-info" th:type="submit" th:value="#{lbl.cmn.login}" />
                        </td>
                    </tr>
                     
           	</tbody>
            </table>
            </div>
     		
		</fieldset>	
    </form>
    
   
	</div>