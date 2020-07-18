<div layout:fragment="content">
<br/>
     <table width="100%" align="center" >
        <tr>
									<td th:text="#{lbl.type.id}" align="right" width="30%"></td>
									<td>:</td>
									<td>
									<select style="width: 350px;" id="cmbWfwType" onchange="onChangeType()">											
											<option th:each="nlevel : ${typeList}" th:value="${nlevel.typeId}"
												th:text="${nlevel.description}+' ('+${nlevel.typeId}+')'">
											</option>
									</select>									
								</td>
		</tr>
     </table>
     <br/>
     <div id="content-home">
		<div id="config_table">
		    <div style="width:90%; margin-left:2%; text-align: left;">
						  <p><a style="text-decoration: underline;" th:href="@{/maintenance/wf-config-edit(id=,type=T, action=add)}"><span>Add New Type</span></a></p></div>
			<table id="tbl_data_config" border="1">	
					<tr class="table_title_row" id="table_title_row">
						<th colspan="8">Configuration</th>
					</tr>		
				<tr>
					<th th:text="Type" width="20%"></th>
					<th th:text="Level" width="20%"></th>
					<th th:text="Status" width="60%"></th>
				</tr>
				<tr th:each="type : ${dataListObj}" >
				     <td>	
						  <p  th:text="'Type ID : '+${type.typeId}"></p>
						  <p  th:text="'Type : '+${type.description}"></p>
						  <p  th:text="'Assign Type : '+${type.assignType=='M'?'Manual':'Auto'}"></p>
						  <p  th:text="'Is Active : '+${type.isActive=='N'?'No':'Yes'}"></p>
						  <br/>
						  <p th:text="#{lbl.trans.id}+': '+${type.tranId}"></p>
						  <p th:text="#{lbl.mod.id}+': '+${type.moduleId}"></p>
						  <p th:text="#{lbl.view.path}+': '+${type.viewPath}"></p>
						  <div style="width:90%; text-align: right;">
						  <p><a style="text-decoration: underline;" th:href="@{/maintenance/wf-config-edit(id=${type.typeId},type=T, action=edit)}"><span>Edit</span></a>&nbsp;&nbsp;&nbsp;
						  <a style="text-decoration: underline;" th:href="@{/maintenance/wf-config-edit(id=${type.typeId},type=T, action=add)}"><span>Add New Type</span></a></p></div>
						  
					</td>	
					<td colspan="2" style="vertical-align: top;"> 
						<table style="width:100%; padding: 0;">
						     							
							<div style="width:90%; text-align: left;" th:if="${#lists.isEmpty(type.wfwLevelList)==true}">						  				
						  	  <p><a style="text-decoration: underline;" th:href="@{/maintenance/wf-config-edit(id=${type.typeId},type=L, action=add)}"><span>Add Level</span></a></p>
						  	</div>
						      <tr th:each="level : ${type.wfwLevelList}">
								<td width="25%">
									<p th:text="'Level ID : '+ ${level.levelId}"></p>
									<p th:text="'Level : '+${level.description}"></p>
									<p th:text="'Flow No : '+${level.flowNo}"></p>	
									<br/>
						  			<p th:text="'User Role : '+${level.roles}"></p>								
									<div style="width: 90%; text-align: right;">
										<p>
											<a style="text-decoration: underline;"
												th:href="@{/maintenance/wf-config-edit(id=${level.levelId},type=L, action=edit)}"><span>Edit</span></a>
											&nbsp;&nbsp;&nbsp; <a style="text-decoration: underline;"
												th:href="@{/maintenance/wf-config-edit(id=${type.typeId},type=L, action=add)}"><span>Add
													Level</span></a>
										</p>
									</div>
								</td>
								<td>
							      <div style="width:90%; text-align: left;">
						  				<p>&nbsp;&nbsp;&nbsp;
						  				<a style="text-decoration: underline;" th:href="@{/maintenance/wf-config-edit(id=${level.levelId},type=S, action=add)}"><span>Add Status</span></a></p></div>
							         <table style="width:100%">
										<tr th:if="${#lists.isEmpty(level.wfwStatusList)==false}">
											<td style="text-align: center; 
												padding:3px 3px 3px 3px;	
												border-top:1px solid #fafafa; 
												border-bottom:1px solid #e0e0e0;
												background: #fff;
												font-weight:bold;	
												background: -webkit-gradient(linear, left top, left bottom, from(#ededed), to(#ebebeb));
												background: -moz-linear-gradient(top,  #ededed,  #ebebeb);">ID</td>
											<th>Status</th>
											<th>App Status</th>
											<th>Flow No</th>
											<th>Next Level</th>
											<th>Is Display</th>
											<th>Is Initial</th>
											<th>Email</th>
											<th>Is Active</th>
											<th>Action</th>
										</tr>
										<tr th:each="status : ${level.wfwStatusList}">
											<td th:text="${status.statusId}"></td>
											<td align="left" th:text="${status.description}"></td>
											<td align="left" th:text="${status.applStsId}"></td>
											<td th:text="${status.flowNo}"></td>
											<td th:text="${status.nextProcId}"></td>
											<td th:text="${status.isDisplay=='N'?'No':'Yes'}"></td>
											<td th:text="${status.isInitialSts=='N'?'No':'Yes'}"></td>
											<td th:text="${status.isEmailRequired=='N'?'No':'Yes'}"></td>
											<td th:text="${status.isActive=='N'?'No':'Yes'}"></td>											
											<td><a style="text-decoration: underline;"
												th:href="@{/maintenance/wf-config-edit(id=${status.statusId},type=S, action=edit)}"><span>Edit</span></a></td>
										</tr>
									</table>
									<br/>
							      </td>
						      </tr>
						  </table>
					  </td>
				</tr>
				
			</table>
		</div>
	</div>
</div>