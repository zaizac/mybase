function clearMovementForm() {
	$('input#movementActivities').val("")
    $('input#movementContactName').val("")
 	$('input#movementContactPhone').val("")
	$('#movementEditBtn').hide();
	$('#movementCancelBtn').hide();
	$('#movementAddBtn').show();
}

function processRowNumTwo(oSettings) {
	if (oSettings.bSorted || oSettings.bFiltered) {
		for (var i = 0, iLen = oSettings.aiDisplay.length; i < iLen; i++) {
			$('td:eq(0)', oSettings.aoData[oSettings.aiDisplay[i]].nTr).html(
					i + 1);
		}
	}
}

var editBtn = '<a title="Update" class="edit mr-2 cursor-pointer" ><i class="fa fa-edit fa-lg"></i></a>';
var delBtn = '<a title="Delete" class="remove cursor-pointer" ><i class="fa fa-trash-o fa-lg"></i></a>';
var recDelBtn = '<a title="Record Marked for Deletion" class="recordDel mr-2" style="display:none;color: red"><i class="fa fa-ban fa-lg"></i></a>'
var cancelBtn = '<a title="Cancel" class="cancel cursor-pointer" style="display:none;color: black"><i class="fa fa-times fa-lg"></i></a>'
	
$(document).ready(function() {
	
	$(window).keydown(function(event){
	    if(event.keyCode == 13) {
	      event.preventDefault();
	      return false;
	    }
	  });
	
	var tblMovementLst = $('#tblMovementLst').DataTable({
		"dom": '<"top">t<"bottom"ip><"clear">',
		"serverSide": false,
		responsive : true,
		"columnDefs": [
			{ "targets": [ 0 ], "orderable": false },
            { "targets": [ 4 ], "visible": false },
            { "targets": [ 5 ], "visible": false },
            { "targets": [ 6 ], "orderable": false },
        ],
        "initComplete": function(oSettings, json) {
			$('#movementAddBtn').bind('click', function(e) {	   
				var activity = $("input#movementActivities").val();
		        var name = $("input#movementContactName").val();
		        var phone = $("input#movementContactPhone").intlTelInput("getNumber");
		        if(!name && !activity && !phone) {
		        	$("#movementError").text("Please fill in information to add Movement History.");
		        	return;		        	
		        }
		        $("#movementError").text(""); // Reset the message
		        $('#tblMovementLst').dataTable().fnAddData([null,
		        	activity ? activity.toUpperCase() : '', 
		        	name ? name.toUpperCase() : '', 
        			phone, true, null, editBtn + delBtn + recDelBtn + cancelBtn] );
		        clearMovementForm();
		        $(".em-toggle").click();
            });
            $('#movementEditBtn').bind('click', function(e) {
            	var somevalue = $("input#movementIdx").val();
            	tblMovementLst.rows(function ( idx, data, node ) {  
                    if(idx === parseInt(somevalue)){
                    	var activity = $("input#movementActivities").val();
        		        var name = $("input#movementContactName").val();
        		        var phone = $("input#movementContactPhone").intlTelInput("getNumber");
        		        if(!name && !activity && !phone) {
        		        	$("#movementError").text("Please fill in information to add Movement History.");
        		        	return;		        	
        		        }
        		        $("#movementError").text(""); // Reset the message
				        $('#tblMovementLst').dataTable().fnUpdate([null,
				        	activity ? activity.toUpperCase() : '', 
				        	name ? name.toUpperCase() : '', 
		        			phone, data[4] || true, data[5], editBtn + delBtn + recDelBtn + cancelBtn], idx, undefined, false);
				        clearMovementForm();
                     }
            		
                     return false;
                 })
            });
            $('#movementCancelBtn').bind('click', function(e) {
            	clearMovementForm();
            });
            
		},
		"fnDrawCallback": function ( oSettings ) { processRowNumTwo(oSettings); hidePagination(this,"#tblMovementLst")}
	});
	
	$('#tblMovementLst tbody').on( 'click', 'a.remove', function () {
        var data = tblMovementLst.row( $(this).parents('tr') ).data();
        data[4] = false;
        $(this).hide()
        $(this).closest('tr').find('a.edit').hide()
        $(this).closest('tr').find('a.recordDel').show()
        $(this).closest('tr').find('a.cancel').show()
    } );
	$('#tblMovementLst tbody').on( 'click', 'a.cancel', function () {
        var data = tblMovementLst.row( $(this).parents('tr') ).data();
        data[4] = true;
        $(this).hide()
        $(this).closest('tr').find('a.recordDel').hide()
        $(this).closest('tr').find('a.edit').show()
        $(this).closest('tr').find('a.remove').show()
    } );
	$('#tblMovementLst tbody').on( 'click', 'a.edit', function () {
        var data = tblMovementLst.row( $(this).parents('tr') ).data();
        if(data) {       	
        	$('input#movementActivities').val(data[1])
        	$('input#movementContactName').val(data[2])
        	$('input#movementContactPhone').intlTelInput("setNumber", data[3]);
        	$('#movementEditBtn').show();
        	$('#movementCancelBtn').show();
        	$('#movementAddBtn').hide();
        }        
        var idx = tblMovementLst.row( $(this).parents('tr') ).index();
        $("input#movementIdx").val(idx);
    });
		
	$("#movementForm").submit(function() { 
        tblMovementLst.rows(function ( idx, data, node ) {  
        	$("<input />").attr("type", "hidden")
	        	.attr("name", "patientInvestigateList["+ idx+ "].activity").attr("value", data[1])
	        	.appendTo("form#movementForm")
        	$("<input />").attr("type", "hidden")
	        	.attr("name", "patientInvestigateList["+ idx+ "].contactName").attr("value", data[2])
	        	.appendTo("form#movementForm")
	        $("<input />").attr("type", "hidden")
	        	.attr("name", "patientInvestigateList["+ idx+ "].contactPrhoneNo").attr("value", data[3])
	        	.appendTo("form#movementForm")
	        $("<input />").attr("type", "hidden")
	        	.attr("name", "patientInvestigateList["+ idx+ "].isActive").attr("value", data[4])
	        	.appendTo("form#movementForm")
	        $("<input />").attr("type", "hidden")
	        	.attr("name", "patientInvestigateList["+ idx+ "].patientInvesId").attr("value", data[5])
	        	.appendTo("form#movementForm")
        });
	});
	var start = moment("2020-04-01");
    var end = moment();

    function cb(start, end) {
    	if(start) $('input[name="createDtFrom"]').val(start.format('DD/MM/YYYY'));
    	if(end) $('input[name="createDtTo"]').val(end.format('DD/MM/YYYY'));
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
  	            		var transferCase ='<center><a title="Update Movement History" class="mr-2" href=' + contextPath + '/patient/movement/update/'+ row.patientRefNo +' ><i class="fa fa-edit fa-lg"></i></a></center>';
  	            		return transferCase;
  	            	}
  	            }
  	          ],
		"ajax": $.fn.dataTable.pipeline({
			"pages" : 1,
        	"type" : "GET",
            "url": contextPath + "/patient/movement/paginated",
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
      	"fnDrawCallback": function ( oSettings ) { processRowNum(oSettings); hidePagination(this,"#tblPatientLst");portalUtil.showMainLoading(false);}  
	});
	
	$('#tblPatientLst').dataTable().fnSetFilteringEnterPress();
	
	$('#moventDatePckr').daterangepicker({
	    singleDatePicker: true,
	    showDropdowns: true,
	    endDate : new Date(),
	    maxDate: new Date(),
	    minYear: 1901,
	    maxYear: parseInt(moment().format('YYYY'), 15),
	    locale: { format: "DD/MM/YYYY" },
	}, function(start, end, label) {
		$('input[name="date"]').val(start.format('DD/MM/YYYY'))
	});
	
	if($("#date").prop("readonly")) {
		$("#date").css("background-color","#fff");
	}
	
});

function loadOnChangeDays(object) {
	var dayId = object.value;
	var patientId = $('#patientId').val();
	$('#savemomentHistory').text("Save");
	var inputURL = contextPath + '/patient/movement/find';
	var inputData = { dayId: dayId, patientId : patientId};
	$('#tblMovementLst').dataTable().fnClearTable();
	portalUtil.showMainLoading(true);
	$.ajax({
			headers : {
				'X-CSRF-Token' : csrf_token
			},
			type: "GET", 
			url: inputURL,
			data: inputData,
		}).done(function(data) {
			var histList = data.patientInvestigateList;
			if(histList != null){
				for (var i = 0; i < histList.length; ++i){
					var obj = histList[i];
					var endId = i+1;
					var activity = obj.activity;
					var name = obj.contactName;
					var phone = obj.contactPrhoneNo;
					var active = obj.isActive;
					var id = obj.patientInvesId;
					$('#tblMovementLst').dataTable().fnAddData([i+1,
						activity ? activity.toUpperCase() : '', 
								name ? name.toUpperCase() : '', 
								phone, obj.isActive, obj.patientInvesId, editBtn + delBtn + recDelBtn + cancelBtn]);
					$('input[name="date"]').val(obj.activityDt);
					$('input[name="date"]').attr('readonly','true');
				}
				$('#savemomentHistory').text("Update");
				
			}
			
			if(data.date != null){
				$('input[name="date"]').val(data.date);
				$('input[name="date"]').attr('readonly','true');
			}
			
		});
	
	 portalUtil.showMainLoading(false);
	
}

function onChangeDays(object) {
	
	//Clear existing data
	for (var i = 0; i < 10; ++i){
		var endId = i+1;
		$('#activities'+endId).val('');
		$('#contactName'+endId).val('');
		$('#contactPhone'+endId).val('');
		
	}
	
	var dayId = object.value;
	var patientId = $('#patientId').val();
	$('#savemomentHistory').text("Save");
	var inputURL = contextPath + '/patient/movement/find';
	 var inputData = { dayId: dayId, patientId : patientId}
	 portalUtil.showMainLoading(true);
	 $.ajax({
			headers : {
				'X-CSRF-Token' : csrf_token
			},
			type: "GET", 
			url: inputURL,
			data: inputData,
		}).done(function(data) {
			
			// Reset initial value
			for (var i = 0; i < 10; ++i){
				var endId = i+1;
				$('#investId'+endId).val(null);
				$('#activities'+endId).val(null);
				$('#activities'+endId).val(null);
				$('#contactName'+endId).val(null);
				$('#contactPhone'+endId).val(null);
				$('input[name="date"]').val(null);
			}
			
			var histList = data.patientInvestigateList;
			if(histList != null){
				for (var i = 0; i < histList.length; ++i){
					var obj = histList[i];
					var endId = i+1;
					$('#investId'+endId).val(obj.patientInvesId);
					$('#activities'+endId).val(obj.activity);
					$('#activities'+endId).val(obj.activity);
					$('#contactName'+endId).val(obj.contactName);
					if(obj.contactPrhoneNo != null){
						var str = obj.contactPrhoneNo;
						$('#contactPhone'+endId).intlTelInput(
							"setNumber", str
							);
						//$('#contactPhone'+endId).val(str.substring(3));
					}
					$('input[name="date"]').val(obj.activityDt);
					$('input[name="date"]').attr('readonly','true');
					$('#moventDatePckr').remove();
				}
				$('#savemomentHistory').text("Update");
			}
			
			if(data.date != null){
				$('input[name="date"]').val(data.date);
				$('input[name="date"]').attr('readonly','true');
				$('#moventDatePckr').remove();
			}
			
		});
	
	 portalUtil.showMainLoading(false);
	
}


function documentMoveHistory(url, title, type,	method) {
	
	var nationalId = $('#nationalId').val();
	var dayMtdtId = $('#status').val();
	url = url + '?nationalId=' + nationalId + '&dayMtdtId=' + dayMtdtId;
	if (!title) {
		title = "Movement History"
	}
	documentPopup(url, title, type, method);
}

function documentMoveHistory2(url, title, type,	method) {
	
	var nationalId = $('#nationalId').val();
	var dayMtdtId = $('#status').val();
	url = 'print/MovementHistory.pdf?nationalId=' + nationalId + '&dayMtdtId=' + dayMtdtId;
	if (!title) {
		title = "Movement History"
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
