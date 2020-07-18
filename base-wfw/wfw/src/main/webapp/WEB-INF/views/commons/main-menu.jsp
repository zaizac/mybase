
<div id='cssmenu'>
	<ul>
		<li><a th:href="@{/home}"><span>Home</span></a></li>
		<li sec:authorize="hasAnyRole('R0')"><a th:href="@{/maintenance/user-list}"><span>Users</span></a></li>
		<li sec:authorize="hasAnyRole('R1')"><a th:href="@{/task/application}"><span>Apply</span></a></li>
		<li><a th:href="@{/task/task-my-inbox}" sec:authorize="hasAnyRole('R2','R3','R4')"><span>Inbox(s)</span></a></li>
		<li><a th:href="@{/task/task-queue}" sec:authorize="hasAnyRole('R2','R3','R4')"><span> &nbsp;Task(s)&nbsp;&nbsp; </span></a></li>
		<li sec:authorize="hasAnyRole('R0')"><a  th:href="@{/task/task-list}"><span> Inquiry&nbsp;</span></a></li>
		<li sec:authorize="hasAnyRole('R0')" ><a th:href="@{/maintenance/wf-config}"><span> Maintenance</span></a></li>
		<!-- <li class='has-sub'><a href='#'><span>&nbsp;Configuration&#x25BC;</span></a>
			<ul>
				<li><a th:href="@{/maintenance/wf-config}"><span> Workflow Config</span></a></li>
				<li><a th:href="@{/maintenance/type-list}"><span> Workflow Type </span></a></li>
				<li><a th:href="@{/maintenance/level-list}"><span> Workflow Level </span></a></li>
				<li><a th:href="@{/maintenance/asgn-tran-list}"><span> Assaign Transaction </span></a></li>
				<li><a th:href="@{/maintenance/asgn-role-list}"><span> Assaign Role </span></a></li>
				<li class='last'><a th:href="@{/maintenance/status-list}"><span> Workflow Status </span></a></li>
			</ul>
		</li>	 -->
		<!-- <li class='has-sub'><a href='#'><span>Test &#x25BC;</span></a>
			<ul>
			    <li><a th:href="@{/task/application}"><span> Apply </span></a></li>
				<li><a th:href="@{/task/task-queue}"><span> Task Queue </span></a></li>
				<li><a th:href="@{/task/task-my-inbox}"><span> My Inbox </span></a></li>
				
			</ul>
		</li> -->
		
		<li><a th:href="@{/home}"><span> About US </span></a></li>
		<li><a th:href="@{/home}"><span> Contact US </span></a></li>
		<li class='last'><a th:href="@{/logout-process}"><span> Logout </span></a></li>
	</ul>
</div>
