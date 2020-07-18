<div layout:fragment="content">
     <div id="content-home">
     
		<div id="search_criteria">	
		<form th:action="@{/task/application}" method="post" th:object="${dataObj}">     	
     		<legend th:text="#{Application}"></legend>
     		<fieldset>   			
     		<table id="fld_container">
                <tbody>								
							<tr>
								<td>Quantity</td>
								<td>:</td>
								<td><input th:type="text"  th:field="*{expQantity}"/> </td>
							</tr>	
							<tr>
								<td>Price</td>
								<td>:</td>
								<td><input th:type="text"  th:field="*{expPrice}"/> </td>
							</tr>						
							<tr>
								<td>Contact Name</td>
								<td>:</td>
								<td><input th:type="text"  th:field="*{conName}"/> </td>
							</tr>							
							<tr>
								<td>Address 1</td>
								<td>:</td>
								<td><input th:type="text" th:field="*{conAdd1}"/> </td>
							</tr>	
							<tr>
								<td>Address 2</td>
								<td>:</td>
								<td><input th:type="text" th:field="*{conAdd2}"/> </td>
							</tr>	
							<tr>
								<td>Address 3</td>
								<td>:</td>
								<td><input th:type="text" th:field="*{conAdd3}"/> </td>
							</tr>							
							<tr>
								<td>Postcode</td>
								<td>:</td>
								<td><input th:type="text"  th:field="*{conPcode}"/> </td>
							</tr>							
							<tr>
								<td>Tele Phone</td>
								<td>:</td>
								<td><input th:type="text"  th:field="*{conTelNo}"/></td>
							</tr>							
							<tr>
								<td>Fax</td>
								<td>:</td>
								<td><input th:type="text"  th:field="*{conFaxNo}"/> </td>
							</tr>							
							<tr>
								<td>Remarks</td>
								<td>:</td>
								<td><textarea th:field="*{applRemarks}" rows="3"></textarea></td>
							</tr>						
							
							<tr>
							   <td>&nbsp;</td>
								<td>&nbsp;</td>
								<td style="text-align:left;">
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<input  th:type="submit" name="createtask" th:value="#{btn.cmn.sub}" /> 
								</td>
							</tr>

				</tbody>
            </table>
     		
		</fieldset>	
    </form>  
	</div>
	</div>
</div>