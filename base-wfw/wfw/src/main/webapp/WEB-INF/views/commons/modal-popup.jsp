<div class="modal fade" th:classappend="${mainPopupType}" id="popup_modal" data-backdrop="static">
   <div class="modal-dialog">
        <div class="modal-content">
        	<th:block th:with="hdrbg=${mainPopupType == 'warn'} ? 'modal-header-warn'">
        	</th:block>
            <div class="modal-header" th:classappend="div_legend">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true" title="Close">&times;</button>
                <h4 class="modal-title">                	
                	<th:block th:if="${mainPopupType == 'info'}" ><i class="glyphicon glyphicon-info-sign"></i> <th:block th:text="Information"></th:block></th:block>
                	<th:block th:if="${mainPopupType == 'sucess'}"><i class="glyphicon glyphicon-ok-sign"></i> <th:block th:text="Success"></th:block></th:block>
                	<th:block th:if="${mainPopupType == 'warn'}"><i class="glyphicon glyphicon-exclamation-sign"></i> <th:block th:text="Warning"></th:block></th:block>
                	<th:block th:if="${mainPopupType == 'error'}"><i class="glyphicon glyphicon-remove-sign"></i> <th:block th:text="Danger"></th:block></th:block>
                	<th:block th:if="${mainPopupType == 'custom'}"><th:block th:text="${mainPopupTitle}"></th:block></th:block>
					<th:block th:text="${mainPopupTitle}"></th:block>
                </h4>
            </div>
            <div class="modal-body">
				<th:block th:text="${mainPopupContent}"></th:block>
				<div id="popup_content"></div>
            </div>
           	<div id="reportDetailBody"></div>
            <div class="modal-footer">            	
            	<button class="btnClose" type="button" data-dismiss="modal"><th:block th:text="#{btn.cmn.ok}"/></button>
            </div>
        </div>
    </div>
</div>