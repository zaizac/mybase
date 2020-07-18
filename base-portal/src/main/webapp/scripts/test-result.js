$(document).ready(function() {

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
  	            		var isCanUpdate = $("#isAccess").val();
  	            		iconStr = '<i class="fa fa-eye fa-lg"></i>';
  	            		var title = "View Test Result";
  	            		if(isCanUpdate == "true"){
  	            			var iconStr = '<i class="fa fa-edit fa-lg"></i>';
  	            			var title = "Update Test Result";
  	            		}
  	            		var transferCase ='<center><a title="'+title+'" class="mr-2" href=' + contextPath + '/patient/result/update/'+ row.patientRefNo +' >'+iconStr+'</a></center>';
  	            		return transferCase;
  	            	}
  	            },
  	          ],
		"ajax": $.fn.dataTable.pipeline({
			"pages" : 1,
        	"type" : "GET",
            "url": contextPath + "/patient/result/paginated",
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
            	$('#select2-healthOutcomeMtdtCd-container').text("- Please Select -")
            	$("#healthOutcomeMtdtCd").val("");
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
            	oTablePatientLst.columns().search("").draw();
            	$(".em-toggle").click();
            });
           
        },
	"fnDrawCallback": function ( oSettings ) {processRowNum(oSettings); hidePagination(this,"#tblPatientLst");portalUtil.showMainLoading(false);}  
	});
	
	$('#tblPatientLst').dataTable().fnSetFilteringEnterPress();


	$('#dtPckr').daterangepicker({
	    singleDatePicker: true,
	    showDropdowns: true,
	    endDate : new Date(),
	    maxDate: new Date(),
	    minYear: 1901,
	    maxYear: parseInt(moment().format('YYYY'), 15),
	    locale: { format: "DD/MM/YYYY" },
	}, function(start, end, label) {
		$('input[name="testResultDt"]').val(start.format('DD/MM/YYYY'))
	});

	$('#dtPckrSampleRcvDate').daterangepicker({
	    singleDatePicker: true,
	    showDropdowns: true,
	    endDate : new Date(),
	    maxDate: new Date(),
	    minYear: 1901,
	    maxYear: parseInt(moment().format('YYYY'), 15),
	    locale: { format: "DD/MM/YYYY" },
	}, function(start, end, label) {
		$('input[name="sampleReceiveDt"]').val(start.format('DD/MM/YYYY'))
	});	
	
	$('#dtPckrSampleProcessDate').daterangepicker({
	    singleDatePicker: true,
	    showDropdowns: true,
	    endDate : new Date(),
	    maxDate: new Date(),
	    minYear: 1901,
	    maxYear: parseInt(moment().format('YYYY'), 15),
	    locale: { format: "DD/MM/YYYY" },
	}, function(start, end, label) {
		$('input[name="sampleProcessDt"]').val(start.format('DD/MM/YYYY'))
	});
	
	var oTablePatientLst2 = $('#tblSubTestLst').DataTable({
    	"processing": true,
	    "serverSide": true,
	    'responsive': true,
	    'destroy' : true,
		"ajax": $.fn.dataTable.pipeline({
			"pages" : 1,
        	"type" : "GET",
            "url": contextPath + "/patient/result/update/paginated",
            "action": 'xhttp',
            'beforeSend': dtRequestHeader,
            "dataSrc": dtDataSrc,
            "data": function ( data ) {
            	data.patientId = $("#patientId").val();
            	data.caseId = $("#caseId").val();
            },
            "error": function(){  // error handling
            	console.log("error");
            }
      	 }),
      	"initComplete": function(settings, json) {
      		$('input#sampleReceiveDt').unbind();
      		$('input#nationalId').unbind();
      		$('input#status').unbind();
      		$('#searchFilter').bind('click', function(e) {
            	portalUtil.showMainLoading(true);
            	oTablePatientLst2
            	.column(0).search($('input#patientId').val())
            	
                oTablePatientLst2.draw();
            	$(".em-toggle").click();
            });
            $('#searchClear').bind('click', function(e) {
            	$('input#patientId').val("")
            	oTablePatientLst2.columns().search("").draw();
            	$(".em-toggle").click();
            });
           
        },
        "columns": [
        		{ "data": null, "orderable" : false },
	            { "data": "sampleReceiveDt",
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
	            { "data": "samplingDesc", "searchable" : true, "orderable" : true, 
  	            	"render": function (data, type, row) { 
	            			return data || "-";
  	            	},
  	            },
	            { "data": "testResultDt",
	            	"render": function (data, type, row) {  
	            		/*if(typeof data == 'string') {
	            			return data;
	            		}*/
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
	            { "data": "result" , 
	            	"render": function ( data, type, row ) {
	            		var result = "-";
	            		if(data) {
	            			var color = "badge-primary";
	            			if(data == 'SARS-COV-2 DETECTED') {
	            				color = "badge-danger";
	            			} else if(data == 'SARS-COV-2 NOT DETECTED') {
	            				color = "badge-info";
	            			} else if(data == 'POS_COVID') {
	            				color = "badge-danger";
	            			} else if(data == 'NEG_COVID') {
	            				color = "badge-info";
	            			}
	            			result = "<span class='badge badge-secondary " + color + "'>" + data + "</span>";
	            		}
          			return result;
	            	}
	            },
	            { "data": null, "searchable" : false, "orderable" : false, 
	            	"render": function ( data, type, row ) {
	            		var isCanUpdate = $("#isAccess").val();
  	            		var iconStr = '<i class="fa fa-edit fa-lg"></i>';
  	            		var title = "Update Test Result";
  	            		if(isCanUpdate == "false"){
  	            			iconStr = '<i class="fa fa-eye fa-lg"></i>';
  	            			var title = "View Test Result";
  	            		}
	            		return '<center><a title="'+title+'" class="mr-2" href=' + contextPath + '/patient/result/update/byTestid/'+ row.testResultId +' >'+iconStr+'</a></center>';
	            	}
	            },
	            { "data": null, "searchable" : false, "orderable" : false, 
  	            	"render": function ( data, type, row ) {
  	            	var check = '';
                      selectedIds.forEach(function(item, index){
                      console.log(data, item, index)
                      if(data) {
                          if(data.testResultId == item) {
                              check = 'checked="checked"';
                          }
                      }
                     });     
                   var isCanUpdate = $("#isAccess").val();
  	                 if(isCanUpdate == "true"){
                      return '<label class="checkbox-inline pull-center"><input type="checkbox" ' + check + ' class="checkbox check-success checkbox-circle"><span class="label-text"></span></label>'; 
  	                 }else {
  	                  return '<label class="checkbox-inline pull-center"><input disabled type="checkbox" ' + check + ' class="checkbox check-success checkbox-circle"><span class="label-text"></span></label>'; 
  	                }
  	            }
	            }
	          ],
	          "fnDrawCallback": function ( oSettings ) {processRowNum(oSettings); hidePagination(this,"#tblSubTestLst");portalUtil.showMainLoading(false);}  
	});
	checkScreening(319);
	$('#tblSubTestLst').dataTable().fnSetFilteringEnterPress();
	 var selectedIds = new Array();
	$('#tblSubTestLst tbody').on( 'click', 'input.checkbox', function () {
        var data = oTablePatientLst2.row( $(this).parents('tr') ).data();
        data.isDeleted = $(this)[0].checked;
        if(data.isDeleted){
        	selectedIds.push(data.testResultId);
        }else{
        	for (var index = 0; index < selectedIds.length; index++) {
    			console.log(selectedIds[index]);
    			if (selectedIds[index] == data.testResultId) {
    				selectedIds.splice(index, 1);
    			}
    		}
        }
        document.getElementById("selectedIdDeleteId").value = selectedIds;
    } );
	
	$('#deleteLabResult').on('click', function(){
		if(selectedIds <=0){
			portalUtil.showInfo('Please select record to delete.');
		    return false;	
		}
		portalUtil.showConfirm('Are you sure you want to delete the record(s)?', function(result) {
		     if (result) {
		    	 var patientRefNo = $('#patientRefNo').val();
		    	 var form = $("form#deleteLabForm");
		    	 var testId =null;
		    	 $("<input />").attr("type", "hidden")
		    	 .attr("name", "patientRefNo").attr("value", patientRefNo)
		    	 .appendTo(form)
		    	 oTablePatientLst2.rows(function ( idx, data, node ) { 
		    		 testId =data.testResultId; 
		    		if(data.isDeleted) {
		    			$("<input />").attr("type", "hidden")
				        	.attr("name", "testResults["+ idx+ "].testResultId").attr("value", data.testResultId)
				        	.appendTo(form)
			        	$("<input />").attr("type", "hidden")
				        	.attr("name", "testResults["+ idx+ "].isDeleted").attr("value", data.isDeleted)
				        	.appendTo(form)
		    		}
		    	 });
		    	 if (form.length) {
		    		 portalUtil.showMainLoading(true);
		    		 var selectedIdDelete = $('#selectedIdDeleteId').val();
		    		 var url =  contextPath +"/patient/result/update/delete/"+testId;
		    		 var inputData = { selectedIdDelete: selectedIdDelete}
		    		 $.ajax({
		    				headers : {
		    					'X-CSRF-Token' : csrf_token
		    				},
		    				type: "GET", 
		    				url: url,
		    				data: inputData,
		    			}).done(function(data) {
		    				var $response=$(data);
				    		 portalUtil.showMainLoading(false);
		    			    var oneval = $response.filter('#message_modal').modal('show');
		    			});
		            }
		     }
		});
	});
		
		
	$("input[name='sampleTypes']:checked").each(function (index, obj) {
    	if (this.value == "ST_7") {
    		var isCanUpdate = $("#isAccess").val();
    		if(isCanUpdate == "false"){
        		$("#sampleName").prop("disabled", true);
    		}else {
        		$("#sampleName").prop("disabled", false);
    		}
        } else{
        	$("#sampleName").prop("disabled", true);
        }
    });	
	
	$("input[name='sampleTypes']").change(function(){
	    if (this.checked && this.value == "ST_7") {
	    	$("#sampleName").prop("disabled", false);
		} else if (this.checked && this.value == "ST_6" && this.value == "ST_7"){
			$("#sampleName").prop("disabled", false);
			$("#sampleName").val("");
		} else if (this.checked && this.value == "ST_5" && this.value == "ST_7"){
			$("#sampleName").prop("disabled", false);
			$("#sampleName").val("")
		}else if (this.checked && this.value == "ST_4" && this.value == "ST_7"){
			$("#sampleName").prop("disabled", false);
			$("#sampleName").val("")
		}else if (this.checked && this.value == "ST_3" && this.value == "ST_7"){
			$("#sampleName").prop("disabled", false);
			$("#sampleName").val("")
		}else if (this.checked && this.value == "ST_2" && this.value == "ST_7"){
			$("#sampleName").prop("disabled", false);
			$("#sampleName").val("")
		}else if (this.checked && this.value == "ST_1" && this.value == "ST_7"){
			$("#sampleName").prop("disabled", false);
			$("#sampleName").val("")
		}else if (!this.checked && this.value == "ST_7"){
			$("#sampleName").prop("disabled", true);
			$("#sampleName").val("")
		}
	}); 
});



function checkScreening(object){
	 var requesterMtdtId = object.value;
	 if(requesterMtdtId == '319'){
		 $("#screeningMoh").show()
		 $("#screeningNonMoh").hide()
		 $("#screeningNonGov").hide()
	 }
	 if(requesterMtdtId == '320'){
		 $("#screeningNonMoh").show()
		 $("#screeningMoh").hide()
		$("#screeningNonGov").hide()

	 }
	 if(requesterMtdtId == '321'){
		 $("#screeningNonGov").show()
		 $("#screeningNonMoh").hide()
		 $("#screeningMoh").hide()
	 }
	 portalUtil.showMainLoading(false);
	
}

	var requesterMtdtIdValue = $("input[name='requesterMtdtId']:checked").val();
	if(requesterMtdtIdValue == "319") {
		$("#screeningMoh").show()
		 $("#screeningNonMoh").hide()
		 $("#screeningNonGov").hide()
	} else if (requesterMtdtIdValue == "320"){
		$("#screeningNonMoh").show()
		 $("#screeningMoh").hide()
		$("#screeningNonGov").hide()
	}else if (requesterMtdtIdValue == "321"){
		 $("#screeningNonGov").show()
		 $("#screeningNonMoh").hide()
		 $("#screeningMoh").hide()
	}else{
		$("#screeningNonGov").hide()
		 $("#screeningNonMoh").hide()
		 $("#screeningMoh").hide()
	}


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
