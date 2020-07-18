<div layout:fragment="content">
     <div id="content-home">
     
		<div id="search_criteria">	
		<form th:action="@{/task/task-create}" method="post" th:object="${dataObj}">     	
     		<legend th:text="#{ttl.tsk.cred.new}"></legend>
     		<fieldset>   			
     		<table id="fld_container">
                <tbody>					
							<tr>
								<td th:text="#{lbl.refno}"></td>
								<td>:</td>
								<td><input th:type="text"  th:field="*{refNo}"/> </td>
							</tr>							
							<tr>
								<td th:text="#{lbl.usr.appcant.id}"></td>
								<td>:</td>
								<td><input th:type="text"  th:field="*{applUserId}"/> </td>
							</tr>							
							<tr>
								<td th:text="#{lbl.usr.appcant.name}"></td>
								<td>:</td>
								<td><input th:type="text"  th:field="*{applUserName}"/> </td>
							</tr>							
							<tr>
								<td th:text="#{lbl.app.date}"></td>
								<td>:</td>
								<td><input th:type="text" th:field="*{applDate}"/> </td>
							</tr>							
							<tr>
								<td th:text="#{lbl.app.status.id}"></td>
								<td>:</td>
								<td><input th:type="text"  th:field="*{applStsId}"/> </td>
							</tr>							
							<tr>
								<td th:text="#{lbl.remark}"></td>
								<td>:</td>
								<td><textarea th:field="*{applRemark}" rows="3"></textarea></td>
							</tr>							
							<tr>
								<td th:text="#{lbl.mod.id}"></td>
								<td>:</td>
								<td><input th:type="text"  th:field="*{moduleId}"/> </td>
							</tr>							
							<tr>
								<td th:text="#{lbl.ofcr.id}"></td>
								<td>:</td>
								<td><input th:type="text"  th:field="*{officerId}"/> </td>
							</tr>							
							<tr>
								<td th:text="#{lbl.branch.id}"></td>
								<td>:</td>
								<td><input th:type="text"  th:field="*{branchId}"/> </td>
							</tr>							
							<tr>
								<td th:text="#{lbl.level.id}"></td>
								<td>:</td>
								<td><input th:type="text"  th:field="*{levelId}"/> </td>
							</tr>							
							<tr>
								<td th:text="#{lbl.trans.id}"></td>
								<td>:</td>
								<td><input th:type="text"  th:field="*{tranId}"/> </td>
							</tr>							
							<tr>
								<td th:text="#{lbl.status.id}"></td>
								<td>:</td>
								<td><input th:type="text"  th:field="*{statusId}"/> </td>
							</tr>							
							<tr>
								<td th:text="#{lbl.last.status.id}"></td>
								<td>:</td>
								<td><input th:type="text"  th:field="*{lastStatusId}"/> </td>
							</tr>							
							<tr>
								<td th:text="#{lbl.queue.ind}"></td>
								<td>:</td>
								<td><input th:type="text"  th:field="*{queueInd}"/> </td>
							</tr>
							
							<tr>
								<td colspan="3" style="text-align:center;">
									<input th:type="submit" name="cancel" th:value="#{btn.cmn.bck}"/> 	
									<input  th:type="submit" name="createtask" th:value="#{btn.cmn.cred.task}" /> 
									<!-- <input  th:type="submit" name="assintask" th:value="#{btn.cmn.cred.task.assgn}" />  -->
								</td>
							</tr>

				</tbody>
            </table>
     		
		</fieldset>	
    </form>  
	</div>
	</div>
</div>