
function clearClosedContactForm() {
	$('input#closedContactName').val("")
    $('input#closedContactId').val("")
 	$('input#closedContactPhoneNo').val("")
 	$("#closedContactRelation").val("").select2();
	$('#closedContactEditBtn').hide();
	$('#closedContactCancelBtn').hide();
	$('#closedContactAddBtn').show();
}

function clearConfContactForm() {
	$('input#confContactName').val("")
    $('input#confContactId').val("")
 	$('#confContactFirstDt').next('input').val("")
 	$("#confContactLastDt").next('input').val("");
	$('#confContactEditBtn').hide();
	$('#confContactCancelBtn').hide();
	$('#confContactAddBtn').show();
}

$(document).ready(function() {
	$(window).keydown(function(event){
	    if(event.keyCode == 13) {
	      event.preventDefault();
	      return false;
	    }
	  });
	var editBtn = '<a title="Update" class="edit mr-2 cursor-pointer" ><i class="fa fa-edit fa-lg"></i></a>';
	var delBtn = '<a title="Delete" class="remove cursor-pointer" ><i class="fa fa-trash-o fa-lg"></i></a>';
	var recDelBtn = '<a title="Record Marked for Deletion" class="recordDel mr-2" style="display:none;color: red"><i class="fa fa-ban fa-lg"></i></a>'
	var cancelBtn = '<a title="Cancel" class="cancel cursor-pointer" style="display:none;color: black"><i class="fa fa-times fa-lg"></i></a>'
	
	var tblConfContactLst = $('#tblConfContactLst').DataTable({
		"dom": '<"top">t<"bottom"ip><"clear">',
		"serverSide": false,
		responsive : true,
		"columnDefs": [
			{ "targets": [ 0 ], "orderable": false },
            { "targets": [ 5 ], "visible": false },
            { "targets": [ 6 ], "visible": false },
            { "targets": [ 7 ], "orderable": false },
        ],
        "initComplete": function(oSettings, json) {
			$('#confContactAddBtn').bind('click', function(e) {	   
				var name = $("input#confContactName").val();
		        var id = $("input#confContactId").val();
		        var firstDt = $("#confContactFirstDt").next('input').val();
		        var lastDt = $("#confContactLastDt").next('input').val();
		        if(!name && !id && !firstDt && !lastDt) {
		        	$("#confContactError").text("Please fill in information to add Confirmed Cases Contact.");
		        	return;		        	
		        }
		        $("#confContactError").text(""); // Reset the message
		        $('#tblConfContactLst').dataTable().fnAddData([null,
		        	id ? id.toUpperCase() : '', 
		        	name ? name.toUpperCase() : '', 
        			firstDt, lastDt, null, true, editBtn + delBtn + recDelBtn + cancelBtn] );
		        clearConfContactForm();
		        $(".em-toggle").click();
            });
            $('#confContactEditBtn').bind('click', function(e) {
            	var name = $("input#confContactName").val();
            	var somevalue = $("input#confContactIdx").val();
            	tblConfContactLst.rows(function ( idx, data, node ) {  
                    if(idx === parseInt(somevalue)){
                    	var name = $("input#confContactName").val();
        		        var id = $("input#confContactId").val();
        		        var firstDt = $("#confContactFirstDt").next('input').val();
        		        var lastDt = $("#confContactLastDt").next('input').val();
        		        if(!name && !id && !firstDt && !lastDt) {
        		        	$("#confContactError").text("Please fill in information to add Confirmed Cases Contact.");
        		        	return;		        	
        		        }
        		        $("#confContactError").text(""); // Reset the message
				        $('#tblConfContactLst').dataTable().fnUpdate([idx+1,
				        	id ? id.toUpperCase() : '',  
				        	name ? name.toUpperCase() : '', 
		        			firstDt, lastDt, data[5], data[6] || true, editBtn + delBtn + recDelBtn + cancelBtn], idx, undefined, false);
				        clearConfContactForm();
                     }
            		
                     return false;
                 })
            });
            $('#confContactCancelBtn').bind('click', function(e) {
            	clearConfContactForm();
            });
            
		},
		"fnDrawCallback": function ( oSettings ) { processRowNum(oSettings); hidePagination(this,"#tblConfContactLst")}
	});
	
	$('#tblConfContactLst tbody').on( 'click', 'a.remove', function () {
        var data = tblConfContactLst.row( $(this).parents('tr') ).data();
        data[6] = false;
        $(this).hide()
        $(this).closest('tr').find('a.edit').hide()
        $(this).closest('tr').find('a.recordDel').show()
        $(this).closest('tr').find('a.cancel').show()
    } );
	$('#tblConfContactLst tbody').on( 'click', 'a.cancel', function () {
        var data = tblConfContactLst.row( $(this).parents('tr') ).data();
        data[6] = true;
        $(this).hide()
        $(this).closest('tr').find('a.recordDel').hide()
        $(this).closest('tr').find('a.edit').show()
        $(this).closest('tr').find('a.remove').show()
    } );
	$('#tblConfContactLst tbody').on( 'click', 'a.edit', function () {
        var data = tblConfContactLst.row( $(this).parents('tr') ).data();
        if(data) {       	
        	$('input#confContactId').val(data[1])
        	$('input#confContactName').val(data[2])
        	$('#confContactFirstDt').next('input').val(data[3]);
        	$('#confContactLastDt').next('input').val(data[4]);
        	$('#confContactEditBtn').show();
        	$('#confContactCancelBtn').show();
        	$('#confContactAddBtn').hide();
        }        
        var idx = tblConfContactLst.row( $(this).parents('tr') ).index();
        $("input#confContactIdx").val(idx);
    });
	var tblClosedContactLst = $('#tblClosedContactLst').DataTable({
		"dom": '<"top">t<"bottom"ip><"clear">',
		"serverSide": false,
		responsive : true,
		"columnDefs": [
            { "targets": [ 4 ], "visible": false },
            { "targets": [ 5 ], "visible": false },
            { "targets": [ 6 ], "visible": false },
            { "targets": [ 7 ], "orderable": false,},
        ],
		"initComplete": function(oSettings, json) {
			$('#closedContactAddBtn').bind('click', function(e) {	   
				var name = $("input#closedContactName").val();
		        var id = $("input#closedContactId").val();
		        var phoneNo = $("input#closedContactPhoneNo").intlTelInput("getNumber");
		        var relation = $("#closedContactRelation").select2('data'); 
		        if(!name && !id && !phoneNo && !relation[0].id) {
		        	$("#closedContactError").text("Please fill in information to add Close Contact.");
		        	return;		        	
		        }
		        $("#closedContactError").text(""); // Reset the message
		        $('#tblClosedContactLst').dataTable().fnAddData([
		        	id ? id.toUpperCase() : '', 
		        	name ? name.toUpperCase() : '', 
		        	phoneNo, relation[0].text != "- Please Select -" ? relation[0].text : '', 
        			relation[0].id, true, null, editBtn + delBtn + recDelBtn + cancelBtn] );
		        clearClosedContactForm();
		        $(".em-toggle").click();
            });
            $('#closedContactEditBtn').bind('click', function(e) {
            	var name = $("input#closedContactName").val();
            	var somevalue = $("input#closedContactIdx").val();
            	tblClosedContactLst.rows(function ( idx, data, node ) {  
                    if(idx === parseInt(somevalue)){
                    	data[0] == name;
                        var name = $("input#closedContactName").val();
				        var id = $("input#closedContactId").val();
				        var phoneNo = $("input#closedContactPhoneNo").intlTelInput("getNumber");
				        var relation = $("#closedContactRelation").select2('data');
				        if(!name && !id && !phoneNo && !relation[0].id) {
				        	$("#closedContactError").text("Please fill in information to add Close Contact.");
				        	return;		        	
				        }
				        $("#closedContactError").text(""); // Reset the message
				        $('#tblClosedContactLst').dataTable().fnUpdate([
				        	id ? id.toUpperCase() : '', 
				        	name ? name.toUpperCase() : '',
				        	phoneNo, 
				        	relation[0].text, relation[0].id, data[5] || true, data[6],  editBtn + delBtn + recDelBtn + cancelBtn], idx, undefined, false);
				        clearClosedContactForm()
                     }
            		
                     return false;
                 })
            });
            $('#closedContactCancelBtn').bind('click', function(e) {
            	clearClosedContactForm();
            });
            
		},
		"fnDrawCallback": function ( oSettings ) { hidePagination(this,"#tblClosedContactLst")}
	});
	
	$('#tblClosedContactLst tbody').on( 'click', 'a.remove', function () {
        var data = tblClosedContactLst.row( $(this).parents('tr') ).data();
        data[5] = false;
        $(this).hide()
        $(this).closest('tr').find('a.edit').hide()
        $(this).closest('tr').find('a.recordDel').show()
        $(this).closest('tr').find('a.cancel').show()
    } );
	$('#tblClosedContactLst tbody').on( 'click', 'a.cancel', function () {
        var data = tblClosedContactLst.row( $(this).parents('tr') ).data();
        data[5] = true;
        $(this).hide()
        $(this).closest('tr').find('a.recordDel').hide()
        $(this).closest('tr').find('a.edit').show()
        $(this).closest('tr').find('a.remove').show()
    } );
	$('#tblClosedContactLst tbody').on( 'click', 'a.edit', function () {
        var data = tblClosedContactLst.row( $(this).parents('tr') ).data();
        if(data) {       	
        	$('input#closedContactId').val(data[0])
        	$('input#closedContactName').val(data[1])
        	$('input#closedContactPhoneNo').intlTelInput("setNumber", data[2]);
        	$("#closedContactRelation").val(data[4]).select2();
        	$('#closedContactEditBtn').show();
        	$('#closedContactCancelBtn').show();
        	$('#closedContactAddBtn').hide();
        }        
        var idx = tblClosedContactLst.row( $(this).parents('tr') ).index();
        $("input#closedContactIdx").val(idx);
    });
	
	$("form").submit(function() { 
        tblClosedContactLst.rows(function ( idx, data, node ) {  
        	$("<input />").attr("type", "hidden")
	        	.attr("name", "closeContacts["+ idx+ "].nationalId").attr("value", data[0])
	        	.appendTo("form")
        	$("<input />").attr("type", "hidden")
	        	.attr("name", "closeContacts["+ idx+ "].firstNm").attr("value", data[1])
	        	.appendTo("form")
	        $("<input />").attr("type", "hidden")
	        	.attr("name", "closeContacts["+ idx+ "].contactNo").attr("value", data[2])
	        	.appendTo("form")
	        $("<input />").attr("type", "hidden")
	        	.attr("name", "closeContacts["+ idx+ "].relationCd").attr("value", data[4])
	        	.appendTo("form")
	        $("<input />").attr("type", "hidden")
	        	.attr("name", "closeContacts["+ idx+ "].isActive").attr("value", data[5])
	        	.appendTo("form")
	        $("<input />").attr("type", "hidden")
	        	.attr("name", "closeContacts["+ idx+ "].patientContactId").attr("value", data[6])
	        	.appendTo("form")
        });
        tblConfContactLst.rows(function ( idx, data, node ) {  
        	$("<input />").attr("type", "hidden")
	        	.attr("name", "confirmedContacts["+ idx+ "].contactId").attr("value", data[1])
	        	.appendTo("form")
	        $("<input />").attr("type", "hidden")
	        	.attr("name", "confirmedContacts["+ idx+ "].contactName").attr("value", data[2])
	        	.appendTo("form")
	        $("<input />").attr("type", "hidden")
	        	.attr("name", "confirmedContacts["+ idx+ "].contactDtFirst").attr("value", data[3])
	        	.appendTo("form")
	        $("<input />").attr("type", "hidden")
	        	.attr("name", "confirmedContacts["+ idx+ "].contactDtLast").attr("value", data[4])
	        	.appendTo("form")
        	$("<input />").attr("type", "hidden")
	        	.attr("name", "confirmedContacts["+ idx+ "].contactCaseId").attr("value", data[5])
	        	.appendTo("form")
	        $("<input />").attr("type", "hidden")
	        	.attr("name", "confirmedContacts["+ idx+ "].isActive").attr("value", data[6])
	        	.appendTo("form")
        });
    });
	
	$(window).on('load', function(){
		var isView = $('#isView').val();
		if(isView == 'true') {
			console.log('Visible FALSE')
			tblConfContactLst.column(7).visible(false)
			tblClosedContactLst.column(7).visible(false)
		} else {
			console.log('Visible TRUE')
			tblConfContactLst.column(7).visible(true)
			tblClosedContactLst.column(7).visible(true)
		}
	})
	
	var dtPckrPayload = {
		    singleDatePicker: true,
		    showDropdowns: true,
		    endDate : new Date(),
		    maxDate: new Date(),
		    minYear: 1901,
		    maxYear: parseInt(moment().format('YYYY'),10)
		};
	
	$('#dobPckr').daterangepicker(dtPckrPayload, function(start) {
		$('input[name="dob"]').val(start.format('DD/MM/YYYY'));
		var years = moment().diff(start, 'years');
		$('input[name="age"]').val(years);
	}); 
	
	$('#exposeDtPckr').daterangepicker(dtPckrPayload, function(start) {
		$('input[name="exposeDt"]').val(start.format('DD/MM/YYYY'));
	});
	
	$('#firstDtLabPckr').daterangepicker(dtPckrPayload, function(start) {
		$('input[name="firstDtLab"]').val(start.format('DD/MM/YYYY'));
	});
	
	$('#hospAdmPckr').daterangepicker(dtPckrPayload, function(start) {
		$('input[name="hospAdm"]').val(start.format('DD/MM/YYYY'));
	});
	
	$('#isoDatePckr').daterangepicker(dtPckrPayload, function(start) {
		$('input[name="isoDate"]').val(start.format('DD/MM/YYYY'));
	});
	
	$('#dtResubRepPckr').daterangepicker(dtPckrPayload, function(start) {
		$('input[name="dtResubRep"]').val(start.format('DD/MM/YYYY'));
	});
	
	$('#stDtAmtHospPckr').daterangepicker(dtPckrPayload, function(start) {
		$('input[name="stDtAmtHosp"]').val(start.format('DD/MM/YYYY'));
	});
	
	$('#dtRelHspPckr').daterangepicker(dtPckrPayload, function(start) {
		$('input[name="dtRelHsp"]').val(start.format('DD/MM/YYYY'));
	});
	
	$('#dtLstLbritoryTstPckr').daterangepicker(dtPckrPayload, function(start) {
		$('input[name="dtLstLbritoryTst"]').val(start.format('DD/MM/YYYY'));
	});
	
	$('#dtDeathPckr').daterangepicker(dtPckrPayload, function(start) {
		$('input[name="dtDeath"]').val(start.format('DD/MM/YYYY'));
	});
	
	//section 3
	$('#dateTrav1Pckr').daterangepicker(dtPckrPayload, function(start) {
		$('input[name="dateTrav1"]').val(start.format('DD/MM/YYYY'));
	});
	
	$('#dateTrav2Pckr').daterangepicker(dtPckrPayload, function(start) {
		$('input[name="dateTrav2"]').val(start.format('DD/MM/YYYY'));
	});
	
	$('#dateTrav3Pckr').daterangepicker(dtPckrPayload, function(start) {
		$('input[name="dateTrav3"]').val(start.format('DD/MM/YYYY'));
	});
	
	$('#dateHealthCareWrkr').daterangepicker(dtPckrPayload, function(start) {
		$('input[name="dtCaseHealthCareWrkr"]').val(start.format('DD/MM/YYYY'));
	});
	
	$('#confContactFirstDt').daterangepicker(dtPckrPayload, function(start) {
		$('#confContactFirstDt').next('input').val(start.format('DD/MM/YYYY'));
	});
	
	$('#confContactLastDt').daterangepicker(dtPckrPayload, function(start) {
		$('#confContactLastDt').next('input').val(start.format('DD/MM/YYYY'));
	});
	
	$('#systomDate').daterangepicker(dtPckrPayload, function(start) {
		$('input[name="symptomsDt"]').val(start.format('DD/MM/YYYY'));
	});
	

	$('#dateReportingPckr').daterangepicker(dtPckrPayload, function(start) {
		$('input[name="dateReporting"]').val(start.format('DD/MM/YYYY'));
	});
	
	$('#dtCaseHealthfacility').daterangepicker(dtPckrPayload, function(start) {
        $('input[name="dtCaseHealthfacility"]').val(start.format('DD/MM/YYYY'));
    });
	
	$('#dateDischargePkr').daterangepicker(dtPckrPayload, function(start) {
        $('input[name="dateDischarge"]').val(start.format('DD/MM/YYYY'));
    });
	
	//section 2
	$("input[name='connComCb']").change(function(){
	    if($(this).val() == "Y") {
			$("#comorbidityOption").show()
	    	$("#optionOther").hide()
	    	$("#optionPregnancy").hide()
		} else {
			$("#comorbidityOption").hide()
			$("#optionPregnancy").hide()
			$("#optionOther").hide()
		}
	});
	
	
	//section 2 condition
	$("input[name='patientCondLst']").change(function(){
	    if ($(this).val() == "OTH") {
	    	$("#optionOther").show()
		} else if(($(this).val() == "PRG")){
			$("#optionPregnancy").show()
		}
	}); 
	
	$("input[name='symptom']").change(function(){
	    if($(this).val() == "Y") {
			$("#symptomOption").show()
		} else {
			$("#symptomOption").hide()
		}
	});
	
	$("input[name='admHosp']").change(function(){
	    if($(this).val() == "Y") {
			$("#admitOption").show()
		} else {
			$("#admitOption").hide()
		}
	});
	
	$("input[name='infCon']").change(function(){
	    if($(this).val() == "Y") {
			$("#isolation").show()
		} else {
			$("#isolation").hide()
		}
	}); 
	
	// section 3
	$("input[name='isTrvelBefore']").change(function(){
	    if($(this).val() == "Y") {
			$("#cntryVisitList").show()
		} else {
			$("#cntryVisitList").hide()
		}
	});
	
	$("input[name='isCaseHealthCareWrkr']").change(function(){
	    if($(this).val() == "Y") {
			$("#healthCareWrkr").show()
		} else {
			$("#healthCareWrkr").hide()
		}
	});
	
	$("input[name='isContactConfrmCase']").change(function(){
	    if($(this).val() == "Y") {
			$("#confirmCaseContact").show()
		} else {
			$("#confirmCaseContact").hide()
		}
	});
	
	if ($("input[name='idType']:checked").val() == "1"){
		$("#myKad").show()
		$("#othersId").hide()
    } else {
    	$("#myKad").hide()
		$("#othersId").show()
    }	
	
	$("input[name='idType']").change(function(){
		$('#icNo').val('');
		$('#othersKp').val('');
	    if($(this).val() == "1") {
	    	$("#myKad").show()
			$("#othersId").hide()
		} else {
			$("#myKad").hide()
			$("#othersId").show()
		}
	});
	
	$("input[name='adHosp']").change(function(){
	    if($(this).val() == "Y") {
			$("#dateShow").show()
			$("#icuShow").show()
			$("#ventilatinShow").show()
		    $("#oxygenationShow").show()
		} else {
			$("#dateShow").hide()
			$("#icuShow").hide()
			$("#ventilatinShow").hide()
		    $("#oxygenationShow").hide()
		}
	}); 
	
	if ($("input[name='healthOutCm']").val()== "RCV") {
		$("#resultTestId").show()
		$("#dateLabTest").show()
		$("#dateDischargeId").show()
		$("#finalDiagnosid").show()
		$("#dateDeathId").hide()
		$("#explainId").hide()
		$("#causeDeathId").hide()
	} else if ($("input[name='healthOutCm']:checked").val()== "DTH"){
		$("#dateDeathId").show()
		$("#causeDeathId").show()
		$("#explainId").hide()
		$("#dateLabTest").hide()
		$("#resultTestId").hide()
		$("#dateDischargeId").hide()
		$("#finalDiagnosid").hide()
	} else if($("input[name='healthOutCm']:checked").val()== "OTH") {
		$("#explainId").show()
		$("#resultTestId").hide()
		$("#dateLabTest").hide()
		$("#dateDeathId").hide()
		$("#causeDeathId").hide()
		$("#dateDischargeId").hide()
		$("#finalDiagnosid").hide()
	}
	
	$("input[name='healthOutCm']").change(function(){
	     if ($(this).val() == "RCV" ){
			$("#resultTestId").show()
			$("#dateLabTest").show()
			$("#dateDischargeId").show()
			$("#finalDiagnosid").show()
			$("#dateDeathId").hide()
			$("#explainId").hide()
			$("#causeDeathId").hide()
		} else if ($(this).val() == "DTH"){
			$("#dateDeathId").show()
			$("#causeDeathId").show()
			$("#explainId").hide()
			$("#dateLabTest").hide()
			$("#resultTestId").hide()
			$("#dateDischargeId").hide()
			$("#finalDiagnosid").hide()
		} else if ($(this).val() == "OTH"){
			$("#explainId").show()
			$("#resultTestId").hide()
			$("#dateLabTest").hide()
			$("#dateDeathId").hide()
			$("#causeDeathId").hide()
			$("#dateDischargeId").hide()
			$("#finalDiagnosid").hide()
		} else {
			$("#resultTestId").hide()
			$("#dateLabTest").hide()
			$("#dateDeathId").hide()
			$("#explainId").hide()
			$("#causeDeathId").hide()
			$("#dateDischargeId").hide()
			$("#finalDiagnosid").hide()
		}
	}); 
	
	if ($("input[name='healthOutCm']:checked").val()== "RCV") {
		$("#resultTestId").show()
		$("#dateLabTest").show()
		$("#dateDischargeId").show()
		$("#finalDiagnosid").show()
		$("#dateDeathId").hide()
		$("#explainId").hide()
		$("#causeDeathId").hide()
	} else if ($("input[name='healthOutCm']:checked").val()== "DTH"){
		$("#dateDeathId").show()
		$("#causeDeathId").show()
		$("#explainId").hide()
		$("#resultTestId").hide()
		$("#dateLabTest").hide()
		$("#dateDischargeId").hide()
		$("#finalDiagnosid").hide()
	} else if($("input[name='healthOutCm']:checked").val()== "OTH") {
		$("#explainId").show()
		$("#resultTestId").hide()
		$("#dateLabTest").hide()
		$("#dateDeathId").hide()
		$("#causeDeathId").hide()
		$("#dateDischargeId").hide()
		$("#finalDiagnosid").hide()
	}
	
	
	$("input[name='asymtoTime']").change(function(){
	    if($(this).val() == "Y") {
			$("#symptomsDtId").show()
		} else {
			$("#symptomsDtId").hide()
		}
	});
	
    $('#tblSearchList tbody tr').each(function(){
    	$('#tblSearchList .input-group-text').daterangepicker(dtPckrPayload, function(start) {
			var dateInput = $(this)[0].element[0].parentElement.parentElement.getElementsByClassName('form-control');
			dateInput[0].value = start.format('DD/MM/YYYY');
		});
    });
    
    var symptomValue = $("input[name='symptom']:checked").val();
    if(symptomValue == "Y") {
		$("#symptomOption").show()
	} else {
		$("#symptomOption").hide()
	}
    
    var connComCbValue = $("input[name='connComCb']:checked").val();
    if(connComCbValue == "Y") {
		$("#comorbidityOption").show()
	} else {
		$("#comorbidityOption").hide()
	}
    
    var admHospValue = $("input[name='admHosp']:checked").val();
    if(admHospValue == "Y") {
		$("#admitOption").show()
	} else {
		$("#admitOption").hide()
	}
    
    var admHospValue = $("input[name='infCon']:checked").val();
    if(admHospValue == "Y") {
		$("#isolation").show()
	} else {
		$("#isolation").hide()
	}    
    
    var isCaseHealthCareWrkrValue = $("input[name='isCaseHealthCareWrkr']:checked").val();
    if(isCaseHealthCareWrkrValue == "Y") {
		$("#healthCareWrkr").show()
	} else {
		$("#healthCareWrkr").hide()
	}
	
    var isTrvelBeforeValue = $("input[name='isTrvelBefore']:checked").val();
    if(isTrvelBeforeValue == "Y") {
		$("#cntryVisitList").show()
	} else {
		$("#cntryVisitList").hide()
	}
    
    var asymtoTimeValue = $("input[name='asymtoTime']:checked").val();
    if(asymtoTimeValue == "Y") {
		$("#symptomsDtId").show()
	} else {
		$("#symptomsDtId").hide()
	}
    
    var adHospValue = $("input[name='adHosp']:checked").val();
    if(adHospValue == "Y") {
		$("#dateShow").show()
		$("#icuShow").show()
		$("#ventilatinShow").show()
	    $("#oxygenationShow").show()
	} else {
		$("#dateShow").hide()
		$("#icuShow").hide()
		$("#ventilatinShow").hide()
	    $("#oxygenationShow").hide()
	}
    
    var healthOutCmLstValue = $("input[name='healthOutCm']:checked").val();
    if (healthOutCmLstValue == "RCV" ){
		$("#resultTestId").show()
		$("#dateLabTest").show()
		$("#dateDeathId").hide()
		$("#explainId").hide()
	} else if (healthOutCmLstValue == "DTH"){
		$("#dateDeathId").show()
		$("#explainId").hide()
		$("#resultTestId").hide()
		$("#dateLabTest").hide()
	} else if (healthOutCmLstValue == "OTH"){
		$("#explainId").show()
		$("#resultTestId").hide()
		$("#dateLabTest").hide()
		$("#dateDeathId").hide()
	} else {
		$("#dateDeathId").hide()
		$("#resultTestId").hide()
		$("#explainId").hide()
		$("#dateLabTest").hide()
	}
    
    $("input[name='patientCondLst']:checked").each(function (index, obj) {
    	if (this.value == "OTH") {
            $("#optionOther").show()
        } else if(this.value == "PRG"){
            $("#optionPregnancy").show()
        }
    });
    
    var isVisitedHealthValue = $("input[name='isVisitedHealth']:checked").val();
    if(isVisitedHealthValue == "Y") {
        $("#facility").show()
    } else {
        $("#facility").hide()
    }
    
    var isContactConfrmCaseValue = $("input[name='isContactConfrmCase']:checked").val();
	    if(isContactConfrmCaseValue == "Y") {
			$("#confirmCaseContact").show()
		} else {
			$("#confirmCaseContact").hide()
		}
	
    
    $("input[name='isVisitedHealth']").change(function(){
        if($(this).val() == "Y") {
            $("#facility").show()
        } else {
            $("#facility").hide()
        }
    });
    
    checkGender(true);
    
    $('#symptom').click(function() {
		$("#symptomOption").css("display", "none");
	});
	
	if($("#dob").prop("readonly")) {
		$("#dob").css("background-color","#fff");
	}
	
	if($("#dob").is(":disabled")) {
		$("#dobPckr").css("pointer-events","none"); 
	} else {
		$("#dobPckr").css("pointer-events","visible");
	}
	
	if($("#dateReporting").is(":disabled")) {
		$("#dateReportingPckr").css("pointer-events","none"); 
	} else {
		$("#dateReportingPckr").css("pointer-events","visible");
	}
	
	if($("#exposeDt").is(":disabled")) {
		$("#exposeDtPckr").css("pointer-events","none"); 
	} else {
		$("#exposeDtPckr").css("pointer-events","visible");
	}
	
	if($("#firstDtLab").is(":disabled")) {
		$("#firstDtLabPckr").css("pointer-events","none"); 
	} else {
		$("#firstDtLabPckr").css("pointer-events","visible");
	}
	
	if($("#dtResubRep").is(":disabled")) {
		$("#dtResubRepPckr").css("pointer-events","none"); 
	} else {
		$("#dtResubRepPckr").css("pointer-events","visible");
	}
	
	if($("#dateReporting").prop("readonly")) {
		$("#dateReporting").css("background-color","#fff");
	}
	
	if($("#exposeDt").prop("readonly")) {
		$("#exposeDt").css("background-color","#fff");
	}
	
	if($("#firstDtLab").prop("readonly")) {
		$("#firstDtLab").css("background-color","#fff");
	}
	
	if($("#hospAdm").prop("readonly")) {
		$("#hospAdm").css("background-color","#fff");
	}
	
	if($("#isoDate").prop("readonly")) {
		$("#isoDate").css("background-color","#fff");
	}
	
	if($("#dateTrav1").prop("readonly")) {
		$("#dateTrav1").css("background-color","#fff");
	}
	
	if($("#dateTrav2").prop("readonly")) {
		$("#dateTrav2").css("background-color","#fff");
	}
	
	if($("#dateTrav3").prop("readonly")) {
		$("#dateTrav3").css("background-color","#fff");
	}
	
	if($("#dtResubRep").prop("readonly")) {
		$("#dtResubRep").css("background-color","#fff");
	}
	
	if($("#symptomsDt").prop("readonly")) {
		$("#symptomsDt").css("background-color","#fff");
	}
	
	if($("#stDtAmtHosp").prop("readonly")) {
		$("#stDtAmtHosp").css("background-color","#fff");
	}
	
	if($("#dtLstLbritoryTst").prop("readonly")) {
		$("#dtLstLbritoryTst").css("background-color","#fff");
	}
	
	if($("#dtCaseHealthCareWrkr").prop("readonly")) {
		$("#dtCaseHealthCareWrkr").css("background-color","#fff");
	}

	if($("#stDayContact01").prop("readonly")) {
		$("#stDayContact01").css("background-color","#fff");
	}
	
	if($("#stDayContact02").prop("readonly")) {
		$("#stDayContact02").css("background-color","#fff");
	}
	
	
	if($("#stDayContact03").prop("readonly")) {
		$("#stDayContact03").css("background-color","#fff");
	}
	
	
	if($("#stDayContact04").prop("readonly")) {
		$("#stDayContact04").css("background-color","#fff");
	}
	
	if($("#stDayContact05").prop("readonly")) {
		$("#stDayContact05").css("background-color","#fff");
	}
	
	if($("#lsDayContact01").prop("readonly")) {
		$("#lsDayContact01").css("background-color","#fff");
	}
	
	if($("#lsDayContact02").prop("readonly")) {
		$("#lsDayContact02").css("background-color","#fff");
	}
	
	if($("#lsDayContact03").prop("readonly")) {
		$("#lsDayContact03").css("background-color","#fff");
	}
	
	if($("#lsDayContact04").prop("readonly")) {
		$("#lsDayContact04").css("background-color","#fff");
	}
	
	if($("#lsDayContact05").prop("readonly")) {
		$("#lsDayContact05").css("background-color","#fff");
	}
	
	if($("#dtRelHsp").prop("readonly")) {
		$("#dtRelHsp").css("background-color","#fff");
	}
	
	if($("#dtCaseHealthfacility").prop("readonly")) {
		$("#dtCaseHealthfacility").css("background-color","#fff");
	}
	
	if($("#dateDischarge").prop("readonly")) {
		$("#dateDischarge").css("background-color","#fff");
	}
	
	onChangeState("selCurrState","selCurrDistrict");
	onChangeDistrict("selCurrDistrict","selCurrMukimCd");
	
	onChangeState("selOtherState","selOtherDistrict");
	onChangeDistrict("selOtherDistrict","selOtherMukimCd");
	
	onChangeState("selOffState","selOffDistrict");
	onChangeDistrict("selOffDistrict","selOffMukimCd");
	
});


function onChangeState(selState,selDistrict) {
    var stateId =  $('#'+selState).val();  
    
    if(stateId == ""){
    	return;
    }
    
    var method = "GET";
    portalUtil.showMainLoading(true);
        var inputUrl2 = contextPath + '/patient/form/district/'+stateId;
        $.ajax({
            headers: { 'X-CSRF-Token': csrf_token },
            type: method, 
            url: inputUrl2, 
        }).done(function(data) {
        	var districts = $("#"+selDistrict);
            var prevDistrictValue = districts.val();
            var newDistrictList = $.map(data, function(obj) {
                obj.text = obj.districtDesc.toUpperCase();
                obj.id = obj.districtCd;
                return obj;
            })
            districts.empty();
            newDistrictList.unshift({
                id: " ",
                districtCd: "",
                districtDesc:"",
                state:{},
                text: "- Please Select -"
            })
            districts.select2({
                data: newDistrictList,
                theme : "bootstrap"
            })
            var isCurrDistValExist = newDistrictList.find(function(disctrict,index) {
                return disctrict.districtCd == prevDistrictValue && prevDistrictValue != "";
            })

            var newSelValue = isCurrDistValExist ? isCurrDistValExist.districtCd : newDistrictList[0].id;
            districts.val(newSelValue).trigger("change");
            portalUtil.showMainLoading(false);
    });
}


function onChangeDistrict(selDistrict,selMukim) {
	var disrictCd =  $('#'+selDistrict).val();  
	
	if(disrictCd == ""){
		return;
	}
	
    var method = "GET";
    portalUtil.showMainLoading(true);
    var inputUrl = contextPath + '/patient/form/mukim/'+disrictCd;
    $.ajax({
        headers: { 'X-CSRF-Token': csrf_token },
        dataType: "json",
        type: method, 
        url: inputUrl, 
    }).done(function(data) {
        var mukims = $("#"+selMukim);
        var prevmukimsValue = mukims.val();
        var newMukimsList = $.map(data, function(obj) {
            obj.text = obj.mukimDesc.toUpperCase();
            obj.id = obj.mukimCd;
            return obj;
        })
        mukims.empty();
        newMukimsList.unshift({
            id: " ",
            mukimCd: "",
            mukimDesc:"",
            state:{},
            text: "- Please Select -"
        })
        mukims.select2({
            data: newMukimsList,
            theme : "bootstrap"
        })
        var isCurrMukimValExist = newMukimsList.find(function(mukim,index) {
            return mukim.mukimCd == prevmukimsValue && prevmukimsValue != "";
        })

        var newSelectedValue = isCurrMukimValExist ? isCurrMukimValExist.mukimCd : newMukimsList[0].id;
        mukims.val(newSelectedValue).trigger("change");
        portalUtil.showMainLoading(false);

    });
}

function isAlphaNumeric(str) {
	  var code, i, len;
	  for (i = 0, len = str.length; i < len; i++) {
	    code = str.charCodeAt(i);
	    if (!(code > 47 && code < 58) && // numeric (0-9)
	        !(code > 64 && code < 91) && // upper alpha (A-Z)
	        !(code > 96 && code < 123)) { // lower alpha (a-z)
	      return false;
	    }
	  }
	  return true;
	};
	
function isAlphaNumericWith4SpecChar(str){
	  var code, i, len;
	  for (i = 0, len = str.length; i < len; i++) {
	    code = str.charCodeAt(i);
	    if (!(code > 47 && code < 58) && // numeric (0-9)
	        !(code > 64 && code < 91) && // upper alpha (A-Z)
	        !(code > 96 && code < 123) && // lower alpha (a-z)
	        (code != 40 && code != 41 && code != 47 && code != 45)) { //four special character
	      return false;
	    }
	  }
	  return true;
};


function checkIDExist(){
	var idType = $("input[name='idType']:checked").val();
	var specChar=  /^[A-Za-z]\w{7,14}$/;
	
	var idNo;
	if(idType == 1){
		idNo = $('#icNo').val();
	}else{
		idNo = $('#othersKp').val();
	}
	
	$('#errIcNoInvld').hide();
	$('#errOthIdInvld').hide();
	$('#errIcNo').hide();
	$('#errOthId').hide();
	
	if(idType == 1){
		if(!isAlphaNumeric(idNo)) {
			$('#errIcNoInvld').show();
		}
	}else if(idType == 5){
		if(!isAlphaNumericWith4SpecChar(idNo)){
			$('#errOthIdInvld').show();
		}
	}else{
		if(!isAlphaNumeric(idNo)) {
			$('#errOthIdInvld').show();
		}
	}
	
	var prevIdType = $('#prevIdType').val();
	var prevIdNo = $('#prevIdNo').val();
	
	if(idType != '' && idNo != '' && (idType != prevIdType || idNo != prevIdNo)){
		$('#prevIdType').val(idType);
		$('#prevIdNo').val(idNo);
		 
		portalUtil.showMainLoading(true);
	    var method = "GET";
	    var inputUrl = contextPath + '/patient/form/checkIdNo';
		$.ajax({
	        headers: { 'X-CSRF-Token': csrf_token },
	        type: method, 
	        url: inputUrl, 
	        data: {idNo:idNo},
	    	success: function(data) {
		    	if(data != ''){
		    		portalUtil.showMainLoading(false);
		    		portalUtil.showMainLoading(true);
		    		location.href = contextPath + '/patient/form/'+data;
		    	}else{
		    		portalUtil.showMainLoading(false);
		    	}
		    },
	        error: function (xhr, ajaxOptions, thrownError) {
	            alert(xhr.status);
	            alert(thrownError);
	        }
		});
		
	}
}

function checkGender(onload){
    var gender = $("input[name='gender']:checked").val();
    if(gender == 'M'){
        var method = "GET";
        var inputUrl = contextPath + "/patient/form/checkComorbidityHide";
       
        if(!onload){
            portalUtil.showMainLoading(true);
        }
 
        $.ajax({
            headers: { 'X-CSRF-Token': csrf_token },
            type: method,
            url: inputUrl,
            data: {gender:gender},
        	success: function(data) {
	            if(data != ''){
	                $.each(data, function( index, value ) {
	                    $("input[name='patientCondLst'][value='"+value+"']").parent().hide();
	                });
	               
	                portalUtil.showMainLoading(false);
	            }
	        },
	        error: function (xhr, ajaxOptions, thrownError) {
	            alert(xhr.status);
	            alert(thrownError);
	          }});
    }else{
        $("input[name='patientCondLst'][type='checkbox']").each(function(){
            $("input[name='patientCondLst']").parent().show();
        });
    }
}

function restrictSpace() {	
	if (event.keyCode == 32) {
		event.returnValue = false;
		return false;
	}
}

function removeNonAlphaNumeric(t, k) {
	var eleVal = document.getElementById(t.id);
	//console.log(eleVal.value+'|'+k);
	eleVal.value = eleVal.value.replace(/[^a-zA-Z\d\s]/g,'');
}

function changeToUpperCaseNoSpace(t) {
	   var eleVal = document.getElementById(t.id);
	   eleVal.value= eleVal.value.toUpperCase().replace(/ /g,'');
}

//control special character
$("#othersId").keypress(function(event) {
	var idType = $("input[name='idType']:checked").val();
	var character = String.fromCharCode(event.keyCode);	
	//if other id allow special character
	if(idType == 5){
		 return LimitSpecChar(character);    
	}else {
	    return isValid(character);     
	}
});

function isValid(str) {
    return !/[~`!@#$%\^&*()+=\-\[\]\\';,/{}|\\":<>\?]/g.test(str);
}

function LimitSpecChar(str){
    return /[a-zA-Z0-9\(\)\/-]/.test(str);
}

function allowAlphaNumericNoSpace(e) {
	  var code = ('charCode' in e) ? e.charCode : e.keyCode;
	  if (!(code > 47 && code < 58) && // numeric (0-9)
	    !(code > 64 && code < 91) && // upper alpha (A-Z)
	    !(code > 96 && code < 123)) { // lower alpha (a-z)
	    e.preventDefault();
	  }
	}

function popupSearching(initialLoad) {
	$('#sectionSearching').modal('show');	
	
		 $(window).keydown(function(event){
		    if(event.keyCode == 13) {
		      event.preventDefault();
		      return false;
		    }
		  });
		
		var oTableConfirmCases = $('#tblConfirmContactPatientList').DataTable({  
	    	"processing": true,
		    "serverSide": true,
		    'responsive': true,
		    'destroy' : true,
		    "columns": [
	    			{ "data": null, "searchable" : false, "orderable" : false },	    		
		    	    { "data": "nationalId" }, 
		    	    { "data": "firstNm" },
	  	          	{ "data": "caseId", "render": function (data, type, row) { 
	        			return data || "-";
		            	},
		            },
		            { "data": null, "searchable" : false, "orderable" : false, 
	  	            	"render": function ( data, type, row ) {
	  	            		return "<a class='select cursor-pointer' title='Select'><i class='fa fa-check fa-lg'></i></a>";
	  	            	}
	  	            },
	  	          ],
			"ajax": $.fn.dataTable.pipeline({
				"pages" : 1,
	        	"type" : "GET",
	            "url": contextPath + "/patient/form/contactconfirmCases/paginated",
	            "action": 'xhttp',
	            'beforeSend': dtRequestHeader,
	            "dataSrc": dtDataSrc,
	            "data": function ( data ) {
	            	data.contactNationalId = $("#contactNationalId").val();
	            	data.contactName = $("#contactName").val();
	            	data.contactCaseId = $('#contactCaseId').val();
	            	data.initialLoad = $('#initialLoad').val();
	            },
	            "error": function(){  // error handling
	            }
	      	 }),
	      	"initComplete": function(settings, json) {
	      		$('input#contactNationalId').unbind();
	      		$('input#contactName').unbind();
	      		$('input#contactCaseId').unbind();
	      		$('input#initialLoad').unbind();
	      		
	      		$('#searchFilter').bind('click', function(e){
	      			var id = $('#contactNationalId').val();
	      			var name = $('#contactName').val();
	      			var hasError = false;
	      			$('#errNationalId').hide();
	      			$('#errContactNo').hide();
	      			
	      			if(id != null && id.length < 5 && id.length > 0) {
	      		        $('#errNationalId').show();
	      		      hasError = true;
	      			}
	      			
	      			if(name != null && name.length < 2 && name.length > 0){
	      		        $('#errContactNo').show();
	      		      hasError = true;
	      			}
	      			console.log('hasError: ', hasError);

	      			if(hasError == false){
	      				portalUtil.showMainLoading(true);
	      				oTableConfirmCases
	      				.column(1).search($('#contactNationalId').val())
	      				.column(2).search($('#contactName').val())
	      				.column(3).search($('#contactCaseId').val())
	      				.column(5).search($('#initialLoad').val('false'))
	      				oTableConfirmCases.draw();
	      			} else {
	      			   return;
	      			}
	            });
	            $('#searchClear').bind('click', function(e) {
	            	$('#errNationalId').hide();
	      			$('#errContactNo').hide();
	            	$('input#contactNationalId').val("")
	            	$('input#contactName').val("")
	            	$('input#contactCaseId').val("")
	            	$('input#initialLoad').val("")
	               oTableConfirmCases.columns().search("").draw();
	            });
	           
	        },
	      	"fnDrawCallback": function ( oSettings ) {processRowNum(oSettings); hidePagination(this,"#tblConfirmContactPatientList"),portalUtil.showMainLoading(false);} 
		});	
		 $('#tblConfirmContactPatientList tbody').on( 'click', 'tr a.select', function () {
		        var data = oTableConfirmCases.row( $(this).parents('tr') ).data();
		        if(data) {	        	
		        	$('#confContactId').val(data.nationalId);
		        	$('#confContactName').val(data.firstNm);
		        	$('#sectionSearching').modal('hide');
		        	$('input#contactNationalId').val("")
	            	$('#contactName').val("")
	            	$('#contactCaseId').val("")
	            	$('#initialLoad').val('true')
		        }
		    } );
		    
	    $('#tblConfirmContactPatientList').dataTable().fnSetFilteringEnterPress();
}


