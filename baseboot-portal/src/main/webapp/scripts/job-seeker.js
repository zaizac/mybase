$(document).ready(function() {
	dataTableCardInit('#kids_profile_table');
	dataTableCardInit('#edu_profile_table');
	dataTableCardInit('#exp_profile_table');
	dataTableCardInit('#nok_profile_table');
	
	$('#expdatePckr').daterangepicker({
	    singleDatePicker: true,
	    showDropdowns: true,
	    startDate : new Date(),
	    minDate: new Date(),
	}, function(start, end, label) {
		$('input[name="expiryDate"]').val(start.format('DD/MM/YYYY'))
	});
	
	$('#issuedatePckr').daterangepicker({
	    singleDatePicker: true,
	    showDropdowns: true,
	}, function(start, end, label) {
		$('input[name="issueDate"]').val(start.format('DD/MM/YYYY'))
	});
	
	
    $('#dobJlsPassportPckr').daterangepicker({
	    singleDatePicker: true,
	    showDropdowns: true,
	    endDate : new Date(),
	    maxDate: new Date(),
	    minYear: 1901,
	    maxYear: parseInt(moment().format('YYYY'),10)
	}, function(start, end, label) {
		$('input[name="dob"]').val(start.format('DD/MM/YYYY'))
	});
	
    $('#dobJlsPckr').daterangepicker({
	    singleDatePicker: true,
	    showDropdowns: true,
	    endDate : new Date(),
	    maxDate: new Date(),
	    minYear: 1901,
	    maxYear: parseInt(moment().format('YYYY'),10)
	}, function(start, end, label) {
		$('input[name="dob"]').val(start.format('DD/MM/YYYY'))
		var years = moment().diff(start, 'years');
		document.getElementById("ageId").value = years;
	});
    
    $('input[name="dob"]').on("blur", function(){
    	var start = this.value;
    	if(start) {
    		var str = start.split("/")
    		start = str[2]+"-"+str[1]+"-"+str[0]
    		start = moment(start).format('DD/MM/YYYY')
    		var years = moment().diff(start, 'years');
    		document.getElementById("ageId").value = years;
    	}
    })
});

function submitJLSProfileDQ(passportNo){
	window.location.href  = contextPath + '/jobSeekerProfile/submitJLSProfileDQ?passportNo=' + passportNo;

}

function onChangeMaritalSts(status) {
	console.log("status ===="+status );
}

$('#tblViewCompLst tbody').on('click', 'tr', function () {
var orderId = $(this).find('td:eq(3)').text();
if(orderId != undefined || orderId != null || orderId != '') {
	location.href = contextPath + "/job-seeker/viewCompanyDetails/" +orderId;
}
});

$('#tblReqLst tbody').on('click', 'tr', function () {
	var raId = $(this).find('td:eq(6)').text();
	if(raId != undefined || raId != null || raId != '') {
		location.href = contextPath + "/request/details/" +raId;
	}
});

function termsCond(url) {
	console.log(contextPath + url+ "/")
	$.ajax({
		headers : {
			'X-CSRF-Token' : csrf_token
		},
		url : contextPath + url,
		type : 'GET',
		async : false,
		success : function(data) {
			$('#popupTtl').text(prop.termsaAndCon);
			$('#popup_content').html(data);
			$("#popup_modal").modal({backdrop: true});
			$('#popup_modal').modal('show');
		},
		error : function() {
		}
	});
}

function findOutMore(url) {
	console.log(contextPath + url+ "/")
	$.ajax({
		headers : {
			'X-CSRF-Token' : csrf_token
		},
		url : contextPath + url,
		type : 'GET',
		async : false,
		success : function(data) {
			$('#popupTtl').text('Find Out More');
			$('#popup_content').html(data);
			$("#popup_modal").modal({backdrop: true});
			$('#popup_modal').modal('show');
		},
		error : function() {
		}
	});
}

window.onload = function() {
	console.log("onload typeListId");
	var typeValue = $('#typeListId option:selected').val();
	
	console.log("value:--" + value);
	
	if (value == '1') {
		console.log("1:--" + value);
		$("#passportId").show();
		$("#nationalId").hide();
	} else if (value == '2') {
		console.log("2:--" + value);
		$("#passportId").hide();
		$("#nationalId").show();
	}
	
}

function statusSelectTypeId(id) {
	
	var value = $('#typeListId option:selected').val();
	console.log("value:" + value)
	if (value == '1') {
		$("#passportId").show();
		$("#nationalId").hide();
	} else if (value == '2') {
		$("#passportId").hide();
		$("#nationalId").show();
	}
	
}

window.onload = function() {
	console.log("onload");
	/*var typeValue = $('#noEduId option:selected').val();
	
	console.log("typeValue:--" + typeValue);
	if (typeValue == 'NO EDUCATION') {
		console.log("NO EDUCATION:--" + typeValue);
		$("#instNameId").hide();
		$("#gradYearId").hide();
		$("#locationId").hide();
		$("#fieldStudyId").hide();
		$("#eduUploadId").hide();
		$("#noEduId").val = typeValue;
	}*/
	
	var value = $('#typeListId option:selected').val();
	if (value != undefined) {
		if (value == '1') {
			$("#passportId").show();
			$("#nationalId").hide();
		} else if (value == '2') {
			$("#passportId").hide();
			$("#nationalId").show();
		}
	}
}

/*function statusSelectNoEduId(id) {
	
	var typeValue = $('#noEduId option:selected').val();
	console.log("typeValue:" + typeValue)
	if (typeValue == 'NO EDUCATION') {
		console.log("NO EDUCATION:--" + typeValue);
		$("#instNameId").hide();
		$("#gradYearId").hide();
		$("#locationId").hide();
		$("#fieldStudyId").hide();
		$("#eduUploadId").hide();
	} else if (typeValue == 'PRIMARY' || typeValue == 'HIGH SCHOOL' || typeValue == 'DIPLOMA') {
		console.log("VALUE:--" + typeValue);
		$("#instNameId").show();
		$("#gradYearId").show();
		$("#locationId").show();
		$("#fieldStudyId").show();
		$("#eduUploadId").show();
	} 
}*/

