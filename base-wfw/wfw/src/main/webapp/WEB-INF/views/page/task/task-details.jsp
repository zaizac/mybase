<div layout:fragment="content">
     <div id="content-home">
     
		<div id="search_criteria">	
		<form th:action="@{/task/task-details}" method="post" th:object="${dataObj}">     	
     		<legend th:text="#{lbl.tsk.dtls}"></legend>
     		<fieldset>   	
     		<input th:type="hidden"  th:field="*{taskId}"/> 		
     		<table id="fld_container">
                <tbody>
							<tr>
								<td width="39%" align="right" th:text="#{lbl.refno}"></td>
								<td width="2%">:</td>
								<td th:if="${!readonly}"><input th:type="text"  th:field="*{refNo}"/> </td>
								<td th:text="*{refNo}" th:if="${readonly}"></td>
							</tr>					
							<tr>
								<td th:text="#{lbl.usr.appcant.id}"></td>
								<td>:</td>
								<td th:if="${!readonly}"><input th:type="text"  th:field="*{applUserId}"/> </td>
								<td th:text="*{applUserId}" th:if="${readonly}"></td>
							</tr>							
							<tr>
								<td th:text="#{lbl.usr.appcant.name}"></td>
								<td>:</td>
								<td th:if="${!readonly}"><input th:type="text"  th:field="*{applUserName}" /> </td>
								<td th:text="*{applUserName}" th:if="${readonly}"></td>
							</tr>							
							<tr>
								<td th:text="#{lbl.app.date}"></td>
								<td>:</td>
								<td th:if="${!readonly}"><input th:type="text"  th:field="*{applDate}" /> </td>
								<td th:text="*{#dates.format(applDate, 'dd/MM/yyyy')}" th:if="${readonly}"></td>
							</tr>							
							<tr>
								<td th:text="#{lbl.app.status.id}"></td>
								<td>:</td>
								<td th:if="${!readonly}"><input th:type="text"  th:field="*{applStsId}" /> </td>
								<td th:text="*{applStsId}" th:if="${readonly}"></td>
							</tr>							
							<tr>
								<td th:text="#{lbl.remark}"></td>
								<td>:</td>
								<td th:if="${!readonly}"><input th:type="text"  th:field="*{applRemark}" /> </td>
								<td th:text="*{applRemark}" th:if="${readonly}"></td>
							</tr>							
							<tr>
								<td th:text="#{lbl.mod.id}"></td>
								<td>:</td>
								<td th:if="${!readonly}"><input th:type="text"  th:field="*{moduleId}" /> </td>
								<td th:text="*{moduleId}" th:if="${readonly}"></td>
							</tr>							
							<tr>
								<td th:text="#{lbl.ofcr.id}"></td>
								<td>:</td>
								<td th:if="${!readonly}"><input th:type="text"  th:field="*{officerId}" /> </td>
								<td th:text="*{officerId}" th:if="${readonly}"></td>
							</tr>							
							<tr>
								<td th:text="#{lbl.branch.id}"></td>
								<td>:</td>
								<td th:if="${!readonly}"><input th:type="text"  th:field="*{branchId}" /> </td>
								<td th:text="*{branchId}" th:if="${readonly}"></td>
							</tr>	
							<tr>
								<td th:text="#{lbl.type.id}"></td>
								<td>:</td>								
								<td th:text="*{typeDesc}"></td>
							</tr>						
							<tr>
								<td th:text="#{lbl.level.id}"></td>
								<td>:</td>
								<td th:if="${!readonly}"><input th:type="text"  th:field="*{levelDesc}" /> </td>
								<td th:text="*{levelDesc}" th:if="${readonly}"></td>
							</tr>							
							<tr>
								<td th:text="#{lbl.trans.id}"></td>
								<td>:</td>
								<td th:if="${!readonly}"><input th:type="text"  th:field="*{tranId}" /> </td>
								<td th:text="*{tranId}" th:if="${readonly}"></td>
							</tr>							
							<tr>
								<td th:text="#{lbl.status.id}"></td>
								<td>:</td>
								<td th:if="${!readonly}"><input th:type="text"  th:field="*{statusDesc}" /> </td>
								<td th:text="*{statusDesc}" th:if="${readonly}"></td>
							</tr>							
							<tr>
								<td th:text="#{lbl.last.status.id}"></td>
								<td>:</td>
								<td th:if="${!readonly}"><input th:type="text"  th:field="*{lastStatusDesc}" /> </td>
								<td th:text="*{lastStatusDesc}" th:if="${readonly}"></td>
							</tr>							
							<tr>
								<td th:text="#{lbl.queue.ind}"></td>
								<td>:</td>
								<td th:if="${!readonly}"><input th:type="text"  th:field="*{queueInd}" /> </td>
								<td th:text="*{queueInd=='I'?'Inboxed':'Queue'}" th:if="${readonly}"></td>
							</tr>
							<tr>
								<td colspan="3" style="text-align:right;">									
									<a th:href="@{/task/task-history-list(taskId=*{taskId})}"><span>View History</span></a>
								</td>
							</tr>
							<tr>
							 <td>&nbsp;</td>
							  <td>&nbsp;</td>
								<td style="text-align:left;">
									<input th:type="submit" name="cancel" th:value="#{btn.cmn.bck}" th:if="${showBack}"/> 								
									<input th:type="submit" name="edit" th:value="#{btn.cmn.edit}" th:if="${showEdit}"/> 
									<!-- <input th:type="submit" name="copy" th:value="#{btn.cmn.copy}" th:if="${showCopy}"/> 
									<input th:type="submit" name="escalate" th:value="#{btn.cmn.escalate}" th:if="${showEscalate}"/> -->
									<input th:type="submit" name="update" th:value="#{btn.cmn.upd}" th:if="${showUpdate}"/>
								</td>
							</tr>

				</tbody>
            </table>
     		
		</fieldset>	
    </form>  
	</div>
	</div>
</div>