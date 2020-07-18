
$('#searchClear').click(function() {
	$('#stateId').val("")
});


$('#printBtnId').click(function() {
	var statecode = $('#stateId').val();
	var url = "report/state/print/state?state="+statecode;
	title = "Total Cases By State"
	documentPopup(url, title, '', '');
});


function documentReport(url, title, type,	method) {
	
	var rptName = $('#rptId').val();
	
	if(rptName != '' && rptName == 'CCA'){
		url = contextPath + '/report/rptCovid/print/ConfirmedCasesAnalysis.pdf';
		title = "CONFIRMED CASES ANALYSIS";
	}

	if(rptName != '' && rptName == 'TRBC'){
		url = contextPath + '/report/rptCovid/print/TotalRegisterbyCenter.pdf';
		title = "TOTAL REGISTER BY CENTER";
	}
	
	if(rptName != '' && rptName == 'C19CBS'){
		url = contextPath + '/report/rptCovid/print/Covid-19CasesbyState.pdf';
		title = "COVID-19 CASES BY STATE";
	}
	
	if(rptName != '' && rptName == 'TRUBD'){
		url = contextPath + '/report/rptCovid/print/TotalREgisteredUserByDay.pdf';
		title = "TOTAL REGISTERED USER BY DAY";
	}
	
	jQuery("#rptTtl").html(title);
	var object = '<div id="pdfUrlId" width="100%" height="100%"></div>';

	jQuery("#pdfUrl").replaceWith(object);
	PDFObject.embed(url, "#pdfUrlId", {
		pdfOpenParams: {
			width: "100%",
			height: "100%"
		}
	});
	
  jQuery("#rptDialog" ).modal( "show" );

}


 function onChangeRptName(rptId) {
   
	var rptName = $('#rptId').val();
	if(rptName != ''){
		$("#printBtnCovidId").show()
		$("#searchClear").show()


	}else{
		$("#printBtnCovidId").hide()
		$("#searchClear").hide()


	}

}
 
 
 $(document).ready(function() {
		$("#printBtnCovidId").hide()
		$("#searchClear").hide()


 });
