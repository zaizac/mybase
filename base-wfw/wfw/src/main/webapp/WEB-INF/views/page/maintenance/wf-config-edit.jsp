<div layout:fragment="content">
     <div id="content-home">
     
		<div id="search_criteria">	
		<form th:action="@{/maintenance/wf-config-edit}" method="post" th:object="${dataObj}">
			<input type="hidden" th:field="*{dbTableName}"/>     	
     		<legend th:text="*{maintenanceType}"></legend>
     		<fieldset>   			
     		<table id="fld_container">
                <tbody>		
                			<th:block th:if="*{dbTableName}=='twfWfType'">
                				<input type="hidden" th:field="*{typeId}" th:if="*{action}=='edit'"/>
								<tr>
									<td th:text="#{lbl.type.id}"></td>
									<td>:</td>
									<td th:text="*{typeId}" th:if="*{action}=='edit'"></td>
									<td th:if="*{action}=='add'"><input th:type="text"  th:field="*{typeId}"/> </td>
								</tr>	
								
								<tr>
									<td th:text="#{lbl.desc}"></td>
									<td>:</td>
									<td><input th:type="text"  th:field="*{description}"/> </td>
								</tr>	
								
								<tr>
									<td th:text="#{lbl.assgn.type}"></td>
									<td>:</td>
									<td>
									<input type="radio" th:field="*{assignType}" value="M"/>Manual
									<input type="radio" th:field="*{assignType}" value="A" />Auto
									</td>
								</tr>	
								
								<tr>
									<td th:text="#{lbl.status}"></td>
									<td>:</td>
									<td><input type="radio" th:field="*{isActive}" value="Y"/>Active
									<input type="radio" th:field="*{isActive}" value="N" />Inactive</td>
								</tr>
																
								<tr>
									<td th:text="#{lbl.trans.id}"></td>
									<td>:</td>
									<td><input th:type="text"  th:field="*{transId}"/> </td>
								</tr>	
																
								<tr>
									<td th:text="#{lbl.mod.id}"></td>
									<td>:</td>
									<td><input th:type="text"  th:field="*{moduleId}"/> </td>
								</tr>	
								
								<tr>
									<td th:text="#{lbl.view.path}"></td>
									<td>:</td>
									<td><input th:type="text"  th:field="*{viewPath}"/> </td>
								</tr>	
												
							</th:block>
														
							<th:block th:if="*{dbTableName}=='twfWfLevel'">
								<input type="hidden" th:field="*{typeId}"/>
								<input th:type="hidden"  th:field="*{levelId}" th:if="*{action}=='edit'"/> 
								<tr>
									<td th:text="#{lbl.type.id}"></td>
									<td>:</td>
									<td th:text="*{typeId}"></td>									
								</tr>
								<tr>
									<td th:text="#{lbl.level.id}"></td>
									<td>:</td>
									 <td th:text="*{levelId}" th:if="*{action}=='edit'"></td>
									 <td th:if="*{action}=='add'"><input th:type="text"  th:field="*{levelId}"/> </td>
								</tr>	
								
								<tr>
									<td th:text="#{lbl.desc}"></td>
									<td>:</td>
									<td><input th:type="text"  th:field="*{description}"/> </td>
								</tr>
								<tr>
									<td th:text="#{lbl.flow.no}"></td>
									<td>:</td>
									<td><input th:type="text"  th:field="*{flowNo}"/> </td>
								</tr>	
								<tr>
									<td th:text="#{lbl.roles}"></td>
									<td>:</td>
									<td><input th:type="text"  th:field="*{roles}"/> </td>
								</tr>						
							</th:block>
							
							<th:block th:if="*{dbTableName}=='twfWfStatus'">
								<input type="hidden" th:field="*{typeId}"/>
								<input th:type="hidden"  th:field="*{levelId}"/> 
								<input th:type="hidden"  th:field="*{statusId}" th:if="*{action}=='edit'"/> 
								<tr>
									<td th:text="#{lbl.type.id}"></td>
									<td>:</td>
									<td th:text="*{typeDesc}"></td>									
								</tr>
								<tr>
									<td th:text="#{lbl.level.id}"></td>
									<td>:</td>	
									<td th:text="*{levelDesc}"></td>										 
								</tr>	
								<tr th:if="*{action}=='add'">
									<td th:text="#{lbl.status.id}"></td>
									<td>:</td>									
									 <td><input th:type="text"  th:field="*{statusId}"/> </td>
								</tr>
								<tr>
									<td th:text="#{lbl.desc}"></td>
									<td>:</td>
									<td><input th:type="text"  th:field="*{description}"/> </td>
								</tr>
								<tr>
									<td th:text="#{lbl.app.status}"></td>
									<td>:</td>
									<td><input th:type="text"  th:field="*{applStsId}"/> </td>
								</tr>									
								<tr>
									<td th:text="#{lbl.flow.no}"></td>
									<td>:</td>
									<td><input th:type="text"  th:field="*{flowNo}"/> </td>
								</tr>
								<tr>
									<td th:text="#{lbl.next.level}"></td>
									<td>:</td>
									<td>
									<select th:field="*{nextProcId}" style="width: 200px;">
											<option value="">---- Select ----</option>
											<option th:each="nlevel : ${nextLevel}" th:value="${nlevel.levelId}"
												th:text="${nlevel.description}">
											</option>
									</select>
									<!-- <input th:type="text"  th:field="*{nextProcId}"/>  -->
								</td>
								</tr>
								<tr>
									<td th:text="#{lbl.display}"></td>
									<td>:</td>
									<td><input type="radio" th:field="*{isDisplay}" value="Y"/>Yes
									<input type="radio" th:field="*{isDisplay}" value="N" />No</td>
								</tr>								
								<tr>
									<td th:text="#{lbl.ini.state}"></td>
									<td>:</td>
									<td><input type="radio" th:field="*{isInitialSts}" value="Y"/>Yes
									<input type="radio" th:field="*{isInitialSts}" value="N" />No</td>
								</tr>
								<tr>
									<td th:text="#{lbl.email.req}"></td>
									<td>:</td>
									<td><input type="radio" th:field="*{isEmailRequired}" value="Y"/>Yes
									<input type="radio" th:field="*{isEmailRequired}" value="N" />No</td>
								</tr>
								<tr>
									<td th:text="#{lbl.status}"></td>
									<td>:</td>
									<td><input type="radio" th:field="*{isActive}" value="Y"/>Active
									<input type="radio" th:field="*{isActive}" value="N" />Inactive</td>
								</tr>					
							</th:block>
							<tr>
								<td></td>
								<td></td>
								<td>
									<input th:type="submit" name="cancel" th:value="#{btn.cmn.bck}"/> 	
									<input th:if="*{action}=='add'" th:type="submit" name="create" th:value="#{btn.cmn.cre}" /> 
									<input th:if="*{action}=='edit'"  th:type="submit" name="update" th:value="#{btn.cmn.upd}" /> 
								</td>
							</tr>
				</tbody>
            </table>
     		
		</fieldset>	
    </form>  
	</div>
	</div>
</div>