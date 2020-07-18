<div layout:fragment="content">
     <div id="content-home">
     
		<div id="search_criteria">
		
		<form th:action="@{/task/task-list}" method="post" th:object="${searchForm}">     		
     		<legend th:text="#{lbl.srch.crtra}"></legend>     		
     		<fieldset>
   			<div th:if="${error}" class="alert alert-danger" role="alert"><p th:text="${error}"></p></div>	
     		<table id="fld_container">
                <tbody>
                			<tr>
								<td th:text="#{lbl.refno}"></td>
								<td align="center">:</td>
								<td><input th:type="text" id="appid" th:field="*{applicationId}"/> <br /></td>
							</tr>
							<tr>
								<td width="39%" align="right" th:text="#{lbl.ofcr.id}"></td>
								<td width="2%" align="center">:</td>
								<td width="59%" align="left"><input th:type="text" id="officerid" th:field="*{officerId}"/> 
								</td>
							</tr>	
							<tr>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>
								<input th:type="submit" th:value="#{btn.cmn.sea}" /> 
								<input th:type="reset" th:value="#{btn.cmn.rst}" /></td>
							</tr>

				</tbody>
            </table>
     		
		</fieldset>	
    </form>
    
   
	</div>

		<div id="data_table">
			<table id="tbl_data">		
					<tr>
						<th colspan="8" th:text="#{lbl.tasks}"></th>
					</tr>		
				<tr>					
					<th th:text="#{lbl.refno}" width="10%"></th>
					<th th:text="#{lbl.usr.appcant.id}" width="10%"></th>
					<th th:text="#{lbl.usr.appcant.name}" width="22%"></th>
					<th th:text="#{lbl.app.date}" width="8%"></th>
					<th th:text="#{lbl.mod.id}" width="5%"></th>
					<th th:text="#{lbl.ofcr.id}" width="10%"></th>
					<th th:text="#{lbl.col.action}" width="5%"></th>
				</tr>
				<tr th:each="task : ${dataListObj}">					
					<td th:text="${task.refNo}"></td>
					<td th:text="${task.applUserId}"></td>
					<td th:text="${task.applUserName}"></td>
					<td th:text="${#dates.format(task.applDate, 'dd/MM/yyyy')}"></td>
					<td th:text="${task.moduleId}"></td>
					<td th:text="${task.officerId}"></td>
					<td style="text-align: center;"><a
						th:href="@{/task/task-details(taskId=${task.taskId})}"><span>Details</span></a></td>
				</tr>
				<tr>
						<th colspan="8"><div th:if="${msg}"><p th:text="*{msg}"></p></div></th>
				</tr>
			</table>
		</div>
	</div>
</div>