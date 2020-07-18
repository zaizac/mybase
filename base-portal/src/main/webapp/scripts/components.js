$(document).ready(function() {	
	$(".otp-new").otp();
	
	$("#time").timepicker({		
		showSeconds : true
	});
	
	$('#dtRangePckr').daterangepicker({
	    "showDropdowns": true,
	    "linkedCalendars": false,
	    "showCustomRangeLabel": false,
	    autoUpdateInput: true,
	    locale: {
	    	format: "DD/MM/YYYY",
	    },
	}, function(start, end, label) {
		$('input[name="dtRangeStart"]').val(start.format('DD/MM/YYYY'))
		$('input[name="dtRangeEnd"]').val(end.format('DD/MM/YYYY'))
	});
	
	$('#dtPckr').daterangepicker({
	    singleDatePicker: true,
	    showDropdowns: true,
	    // startDate: new Date(),
	    endDate : new Date(),
	    // minDate: new Date(),
	    maxDate: new Date(),
	    minYear: 1901,
	    maxYear: parseInt(moment().format('YYYY'),10),
	    // showWeekNumbers: false,
	    // showISOWeekNumbers: false,
	    // timePicker: false,
	    // timePickerIncrement: 5,
	    // timePicker24Hour: false,
	    // timePickerSeconds: false,
	    // ranges: ,
	    // showCustomRangeLabel: false,
	    // alwaysShowCalendars: false,
	    // autoApply: false,
	    // linkedCalendars: false,
	    // isInvalidDate: ,
	    autoUpdateInput: true,
	    locale: {
	    	format: "DD/MM/YYYY",
	    },
	    // locale: { cancelLabel: 'Clear' }
	}, function(start, end, label) {
		$('input[name="dob"]').val(start.format('DD/MM/YYYY'))
	});
	
	$(function() {
		var start = moment().subtract(29, 'days');
	    var end = moment();
	    function cb(start, end) {
	        $('input[name="preDefDtRangeStart"]').val(start.format('DD/MM/YYYY'));
	        $('input[name="preDefDtRangeEnd"]').val(end.format('DD/MM/YYYY'));
	    }
	    
		$('#preDefDtRangePckr').daterangepicker({
		    "showDropdowns": true,
		    "linkedCalendars": false,
		    "showCustomRangeLabel": false,
		    autoUpdateInput: true,
		    locale: {
		    	format: "DD/MM/YYYY",
		    },
		    ranges: {
		           'Today': [moment(), moment()],
		           'Yesterday': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
		           'Last 7 Days': [moment().subtract(6, 'days'), moment()],
		           'Last 30 Days': [moment().subtract(29, 'days'), moment()],
		           'This Month': [moment().startOf('month'), moment().endOf('month')],
		           'Last Month': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')],
		           'Custom Range': 'custom'
		        }
		}, cb);
		cb(start, end);
	});
	
	$('#dtTablePlain').dataTable({
		dom : '<"top"i>tr<"bottom"p><"clear">',
		responsive: true
	});
	
	$('#dtTableLight').dataTable({
		dom : '<"top"i>tr<"bottom"p><"clear">',
		responsive: true
	});

	$('#dtTableDark').dataTable({
		dom : '<"top"i>tr<"bottom"p><"clear">',
		responsive: true
	});
	
	dataTableCardInit('#dtTableCard');
	
	$("#btnPrimaryPopup").click(function(){
		$("#test_modal").modal("show");
	})
	
	$("#btnReport").click(function(){
		$("#rptDialog").modal("show");
	})
	
	$("#btnMessage").click(function(){
		$("#message_modal").attr("class", $("#message_modal").attr("class") + " success");
		$("#message_modal").modal("show");
	})
	
	$("#btnOtp").click(function(){
		$("#otp_modal").modal("show");
	})
	
	$("#btnMsg").click(function(){
		portalUtil.showMessage("Success");
	})
	
	$("#btnInfo").click(function(){
		portalUtil.showInfo("This is an example of info message.");
	})
	
	$("#btnError").click(function(){
		portalUtil.showError("Error");
	})
	
	$("#btnWarn").click(function(){
		portalUtil.showWarn("Warning");
	})
	
	$("#btnSuccess").click(function(){
		portalUtil.showSuccess("Success");
	})
	
	$("#btnPopup").click(function(){
		var data = "<div class=\"row\">" +
	"<div class=\"col-lg-8 col-md-8 col-sm-12 col-xs-12 p-widget \"><section data-grid=\"8\" class=\"widget\">" +
		"<div class=\"widget-header\">" +
			"<h6>col-lg-8</h6></div></section></div>" +
	"<div class=\"col-lg-4 col-md-4 col-sm-12 col-xs-12 p-widget\"><section data-grid=\"4\" class=\"widget\">" +
		"<div class=\"widget-header\"><h6>col-lg-4</h6></div></section></div></div>"
		portalUtil.showPopup("Company Profile", data);
	})
	
	$("#btnConfirm").click(function(){
		portalUtil.showConfirm("Reload Page?", function(result) {
			if (result) {
				ajaxCall("Get", contextPath + '/components');
			}
		});
	})
	

	$(".star").select2({	  
		minimumResultsForSearch: -1,
	    placeholder: {
	        id: '-1', // the value of the option
	        text: 'Select an option'
	      },
	    multiple: false,
	    data: [{id: 0, text: 'No Rating'},{id: 3, text: '3 Rating'}, {id: 3.5, text: '3.5 Rating'}, {id: 4, text: '4 Rating'}, {id: 4.5, text: '4.5 Rating'}, {id: 5, text: '5 Rating'}],
	    templateResult: starRating,
	    templateSelection: starRating
	});
	
});