<div layout:fragment="content">
     <div id="content-home">
     <form th:action="@{/task/task-my-inbox}" method="post" th:object="${searchForm}"> 	
    	 <div id="search_criteria">			
     		<legend th:text="#{lbl.srch.crtra}"></legend>     		
     		<fieldset>   			
     		<table id="fld_container">
                <tbody>												
							<tr>
								<td th:text="#{lbl.refno}"></td>
								<td align="center">:</td>
								<td><input th:type="text" id="appid" th:field="*{applicationId}"/> <br /></td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>
								<input th:type="submit" th:value="#{btn.cmn.sea}" /> 
								<input name="reset" th:type="submit" th:value="#{btn.cmn.rst}" /></td>
							</tr>

				</tbody>
            </table>
     		
		</fieldset>	   
	</div>
	
		<div id="data_table">
			<table id="tbl_data">		
					<tr>
						<th colspan="8" th:text="#{My Inbox}"></th>
					</tr>		
				<tr>					
					<th th:text="#{lbl.refno}" width="10%"></th>
					<th th:text="#{lbl.usr.appcant.id}" width="10%"></th>
					<th th:text="#{lbl.usr.appcant.name}" width="27%"></th>
					<th th:text="#{lbl.app.date}" width="10%"></th>
					<th th:text="#{lbl.mod.id}" width="8%"></th>					
					<th th:text="#{lbl.col.action}" width="5%"></th>
					<th th:text="#{lbl.release}" width="5%"></th>
				</tr>
				<tr th:each="task : ${dataListObj}">						
					<td th:text="${task.refNo}"></td>
					<td th:text="${task.applUserId}"></td>
					<td th:text="${task.applUserName}"></td>
					<td th:text="${#dates.format(task.applDate, 'dd/MM/yyyy')}"></td>
					<td th:text="${task.moduleId}"></td>					
					<td style="text-align: center;"><a
						th:href="@{/task/application-details(taskId=${task.taskId})}"><span>Details</span></a></td>
					<td style="text-align: center;"><a
						th:href="@{/task/task-release(taskId=${task.taskId})}"><span>Release</span></a></td>
				</tr>
				<tr>
						<th colspan="8"><div><p th:text="*{msg}"></p></div></th>
				</tr>
			</table>
		</div>
		
    	</form>
	</div>
</div>