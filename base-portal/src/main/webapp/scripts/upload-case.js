$(document).ready(function() {
	//jQuery('#tblUpldCaseReg').dataTable({ "dom": 't<"bottom"p><"clear">',"serverSide":false,"fnDrawCallback": function ( oSettings ) {hidePagination(this,"#tblUpldCaseReg");}});
	//jQuery('#tblUpldCaseNotReg').dataTable({ "dom": 't<"bottom"p><"clear">',"serverSide":false,"fnDrawCallback": function ( oSettings ) {hidePagination(this,"#tblUpldCaseNotReg");}});
	//jQuery('#tblUpldCaseIdReg').dataTable({ "dom": 't<"bottom"p><"clear">',"serverSide":false,"fnDrawCallback": function ( oSettings ) {hidePagination(this,"#tblUpldCaseIdReg");}});
});


function submitUploadCase() {
	 submitForm('/bulk/case/confirm','uploadCaseForm');
}

function downloadSample() {
	location.href = contextPath +"/bulk/case/downloadTemplate";
	portalUtil.showMainLoading(false);	 
}

function submitForm(url, formId, wildcard) {
	var form = document.getElementById(formId);
	if(wildcard != null) {
		form.action = form.action + "?" + wildcard;
	}
	if(url != null) form.action = contextPath + url;
	//form.submit();
	submitFinalForm(form);
}

function submitFinalForm(form) {
    var submitFormFunction = Object.getPrototypeOf(form).submit;
    submitFormFunction.call(form);
}