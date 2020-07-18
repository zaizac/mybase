$(document).ready(function() {
	jQuery('#tblLabResultReg').dataTable({ "dom": 't<"bottom"p><"clear">',"serverSide":false,"fnDrawCallback": function ( oSettings ) {hidePagination(this,"#tblLabResultReg");}});
	jQuery('#tblLabResultNotReg').dataTable({ "dom": 't<"bottom"p><"clear">',"serverSide":false,"fnDrawCallback": function ( oSettings ) {hidePagination(this,"#tblLabResultNotReg");}});
});


function submitLabResult() {
	 submitForm('/labResult/confirm','labResultForm');
}

function downloadSample() {
	location.href = contextPath +"/labResult/downloadTemplate";
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