var trLink = true;
$(document).ready(function() {

	$(window).keydown(function(event){
	    if(event.keyCode == 13) {
	      event.preventDefault();
	      return false;
	    }
	  });
	
	var start = moment("2020-04-01");
    var end = moment();

    function cb(start, end) {
        $('input[name="createDtFrom"]').val(start.format('DD/MM/YYYY'));
        $('input[name="createDtTo"]').val(end.format('DD/MM/YYYY'));
    }

    $('#createDtRange').daterangepicker({
    	minDate: moment("2020-04-01"),
        startDate: start,
        endDate: end,
        ranges: {
           'Today': [moment(), moment()],
           'Yesterday': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
           'Last 7 Days': [moment().subtract(6, 'days'), moment()],
           'Last 30 Days': [moment().subtract(29, 'days'), moment()],
           'This Month': [moment().startOf('month'), moment().endOf('month')],
           'Last Month': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
        }
    }, cb);

    cb(start, end);
    
    var oTablePatientLst = $('#tblPatientLst').DataTable({  
    	"processing": true,
	    "serverSide": true,
	    'responsive': true,
	    'destroy' : true,
	    "columns": [
	    		{ "data": null, "searchable" : false, "orderable" : false },	    		
  	          	{ "data": "caseId", "render": function (data, type, row) { 
        			return data || "-";
	            	}, },
  	          	{ "data": "createDt",
  	            	"render": function (data, type, row) {  
  	        			if (data) {
  	          		 	 	 var date = new Date(data);
  	              		 	 var dd = date.getDate();
  	              		 	 var mm = date.getMonth()+1;
  	              		 	 var yyyy = date.getFullYear();
  	              		 	 
  	              		 	 if (dd<10) {
  	              		 		 dd = "0"+dd;
  	              		 	 }
  	              		 	 if (mm<10) {
  	              		 		 mm = "0"+mm;
  	              		 	 }
  	           			 
  	              		 	 return dd+"/"+mm+"/"+yyyy;
  	          		 	 }
  	       			 return "-";
  	            	},
  	            },
  	            { "data": "firstNm" },
  	            { "data": "nationalId" },
  	            { "data": "medicalStatus",
  	            	"render": function (data, type, row) { 
  	            		var status = "-";
  	            		if(data) {
  	            			var color = "badge-primary";
  	            			if(data.statusCd == 'POS_COVID') {
  	            				color = "badge-danger";
  	            			} else if(data.statusCd == 'NEG_COVID') {
  	            				color = "badge-info";
  	            			} else if(data.statusCd == 'PEND_RESUL') {
  	            				color = "badge-warning";
  	            			} else if(data.statusCd == 'NO_SYMPTOM') {
  	            				color = "badge-success";
  	            			}
  	            			status = "<span class='badge badge-secondary " + color + "'>" + data.statusDesc + "</span>";
  	            		}
            			return status;
  	            	},
            	},
            	{ "data": "healthOutcomeMtdtCd",
  	            	"render": function (data, type, row) { 
	            			return data || "N/A";
  	            	},
	            },
	            { "data": "patientRefNo", "visible" : false},
  	            { "data": null, "searchable" : false, "orderable" : false, 
  	            	"render": function ( data, type, row ) {
  	            		var transferCase ='<center><a title="Transfer Case" class="mr-2" href=' + contextPath + '/patient/transfer/'+ row.patientRefNo +' ><i class="fa fa-exchange fa-lg"></i></a>';
  	            		transferCase = transferCase+ '<a title="Update Patient Info" class="mr-2" href=' + contextPath + '/patient/transfer/update/'+ row.patientRefNo + '><i class="fa fa-edit fa-lg"></i></a></center>';
  	            		return transferCase;
  	            	}
  	            }
  	          ],
		"ajax": $.fn.dataTable.pipeline({
			"pages" : 1,
        	"type" : "GET",
            "url": contextPath + "/patient/transfer/paginated",
            "action": 'xhttp',
            'beforeSend': dtRequestHeader,
            "dataSrc": dtDataSrc,
            "data": function ( data ) {
            	data.name = $("#name").val();
            	data.caseId = $("#caseId").val();
            	data.nationalId = $("#nationalId").val();
            	data.status = $('#status').find(":selected").val();
            	data.state = $('#state').find(":selected").val();
            	data.district = $('#district').find(":selected").val();
            	data.createDtFrom = $('input[name="createDtFrom"]').val();
            	data.createDtTo = $('input[name="createDtTo"]').val();
            	data.healthOutcomeMtdtCd = $('#healthOutcomeMtdtCd').find(":selected").val();
            },
            "error": function(){  // error handling
            	console.log("error");
            }
      	 }),
      	"initComplete": function(settings, json) {
      		$('input#name').unbind();
      		$('input#nationalId').unbind();
      		$('input#status').unbind();
      		$('input#state').unbind();
      		$('input#district').unbind();
      		$('input#healthOutcomeMtdtCd').unbind();
      		$('#searchFilter').bind('click', function(e) {
            	portalUtil.showMainLoading(true);
            	oTablePatientLst
            	.column(0).search($('input#caseId').val())
            	.column(1).search($('input[name="createDtFrom"]').val())
                .column(1).search($('input[name="createDtTo"]').val())
            	.column(2).search($('input#name').val())
            	.column(3).search($('input#nationalId').val())
                .column(4).search($('#status').val())
                .column(5).search($('#healthOutcomeMtdtCd').val())
                .column(6).search($('#state').val())
                .column(7).search($('#district').val())
                oTablePatientLst.draw();
            	$(".em-toggle").click();
            });
            $('#searchClear').bind('click', function(e) {
            	$('input#name').val("")
            	$('input#caseId').val("")
            	$('input#nationalId').val("")
            	$("#status").select2("val", "");
            	$("#healthOutcomeMtdtCd").select2("val", "");
            	$('input[name="createDtFrom"]').val("")
            	$('input[name="createDtTo"]').val("")
            	$('#select2-status-container').text("- Please Select -")
            	$("#status").val("");
            	if($('#defaultState').val()) {
            		// DO NOTHING
            	} else {
            		$("#state").select2("val", "");
            		$('#select2-state-container').text("- Please Select -")
                	$("#state").val("");
            	}
            	if($('#defaultDistrict').val()) {
            		// DO NOTHING
            	} else {
            		$("#district").select2("val", "");
            		$('#select2-district-container').text("- Please Select -")
                	$("#district").val("");
            	}
            	$('#select2-healthOutcomeMtdtCd-container').text("- Please Select -")
            	$("#healthOutcomeMtdtCd").val("");
            	if($('#state') != undefined) {
            		
            	}
            	oTablePatientLst.columns().search("").draw();
            	$(".em-toggle").click();
            });
           
        },
      	"fnDrawCallback": function ( oSettings ) {processRowNum(oSettings); hidePagination(this,"#tblPatientLst");portalUtil.showMainLoading(false);} 
	});
    
	$('#tblPatientLst').dataTable().fnSetFilteringEnterPress();
	
	
	$("input[name='idType']").change(function(){
		$('#nationalMyKad').val('');
		$('#othNationalId').val('');
	    if($(this).val() == "1") {
	    	$("#myKad").show()
			$("#othersId").hide()
		} else {
			$("#myKad").hide()
			$("#othersId").show()
		}
	});
	
	if ($("input[name='idType']:checked").val() == "1"){
		$("#myKad").show()
		$("#othersId").hide()
    } else {
    	$("#myKad").hide()
		$("#othersId").show()
    }	

});

function onChangeState(selState, selDistrict) {
    var stateId =  $('#'+selState).val();    
    var method = "GET";
    if(stateId) {
	    var inputUrl = contextPath + '/patient/transfer/center/'+stateId;
	    portalUtil.showMainLoading(true);
	    $.ajax({
	        headers: { 'X-CSRF-Token': csrf_token },
	        type: method, 
	        url: inputUrl, 
	    }).done(function(data) {
	    	var districts = $("#"+selDistrict);
	        var prevDistrictValue = districts.val();
	        var newDistrictList = $.map(data, function(obj) {
	            obj.text = obj.branchName.toUpperCase();
	            obj.id = obj.branchCode;
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