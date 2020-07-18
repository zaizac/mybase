var trLink = true;
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
    
    var oTablePatientLst = $('#tblBulkTransfer').DataTable({  
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
  	            		var check = '';
  	            		selectedIds.forEach(function(item, index){
  	            			console.log(data, item, index)
  	            			if(data) {
  	            				if(data.patientId == item) {
  	            					check = 'checked="checked"';
  	            				}
  	            			}
  	            			
  	            		});
  	            		return '<label class="checkbox-inline pull-center"><input id="checkBoxId" type="checkbox" ' + check + ' class="checkbox check-success checkbox-circle"><span class="label-text"></span></label>';
  	            	}
  	            }
  	          ],
		"ajax": $.fn.dataTable.pipeline({
			"pages" : 1,
        	"type" : "GET",
            "url": contextPath + "/patient/transferByBulk/paginated",
            "action": 'xhttp',
            'beforeSend': dtRequestHeader,
            "dataSrc": dtDataSrc,
            "data": function ( data ) {
            	data.name = $("#name").val();
            	data.caseId = $("#caseId").val();
            	data.nationalId = $("#nationalId").val();
            	data.status = $('#status').find(":selected").val();
            	/*data.state = $('#state').find(":selected").val();
            	data.district = $('#district').find(":selected").val();*/
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
/*      		$('input#state').unbind();
      		$('input#district').unbind();*/
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
/*                .column(6).search($('#state').val())
                .column(7).search($('#district').val())*/
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
/*            	if($('#defaultState').val()) {
            		// DO NOTHING
            	} else {
            		$("#state").select2("val", "");
            		$('#select2-state-container').text("- Please Select -")
                	$("#state").val("");
            	}*/
            	if($('#defaultDistrict').val()) {
            		// DO NOTHING
            	} /*else {
            		$("#district").select2("val", "");
            		$('#select2-district-container').text("- Please Select -")
                	$("#district").val("");
            	}*/
            	$('#select2-healthOutcomeMtdtCd-container').text("- Please Select -")
            	$("#healthOutcomeMtdtCd").val("");
            	/*if($('#state') != undefined) {
            		
            	}*/
            	$('#select2-centerId-container').text("- Please Select -")
            	selectedIds.length = 0;
            	oTablePatientLst.columns().search("").draw();
            	$(".em-toggle").click();
            });
           
        },
      	"fnDrawCallback": function ( oSettings ) {processRowNum(oSettings); hidePagination(this,"#tblBulkTransfer");portalUtil.showMainLoading(false);} 
	});
  
	$('#tblBulkTransfer').dataTable().fnSetFilteringEnterPress();
	 var selectedIds = new Array();
	$('#tblBulkTransfer tbody').on( 'click', 'input.checkbox', function () {
      var data = oTablePatientLst.row( $(this).parents('tr') ).data();
      data.isTransferedBulk = $(this)[0].checked;
      if(data.isTransferedBulk){
      	selectedIds.push(data.patientId);
      }else{
      	for (var index = 0; index < selectedIds.length; index++) {
  			console.log(selectedIds[index]);
  			if (selectedIds[index] == data.patientId) {
  				selectedIds.splice(index, 1);
  			}
  		}
      }
      document.getElementById("selectedIdTransferBulkId").value = selectedIds;
  } );
	
	$('#updtTransderBulk').on('click', function(){
		//validation if not select checkbox
		if(selectedIds <=0){
			portalUtil.showInfo('Please select record to transfer case.');
		    return false;	
		}
		
		//validation if new center null
		var centerId = $('#centerId').val();
		if(centerId ==''){
			$('#errClinicId').show();
			return;
		}
		
		portalUtil.showConfirm('Are you sure you want to transfer this patients?', function(result) {
		     if (result) {
		    	 var patientRefNo = $('#patientRefNo').val();
		    	 var centerId = $('#centerId').val();
		    	 console.log('centerId' + centerId);
		    	 var form = $("form#transferBuilkForm");
		    	 var testId =null;
		    	 $("<input />").attr("type", "hidden")
		    	 .attr("name", "patientId").attr("value", patientId)
		    	 .appendTo(form)
		    	 oTablePatientLst.rows(function ( idx, data, node ) { 
		    		 testId = centerId; //get from selected dropdown center id
		    		if(data.isTransferedBulk) {
		    			$("<input />").attr("type", "hidden")
				        	.attr("name", "patintBulkTransfer["+ idx+ "].patientId").attr("value", data.patientId)
				        	.appendTo(form)
			        	$("<input />").attr("type", "hidden")
				        	.attr("name", "patintBulkTransfer["+ idx+ "].isTransferedBulk").attr("value", data.isTransferedBulk)
				        	.appendTo(form)
		    		}
		    	 });
		    	 if (form.length) {
		    		 portalUtil.showMainLoading(true);
		    		 var selectedIdTransferBulk = $('#selectedIdTransferBulkId').val();
		    		 var url =  contextPath +"/patient/transferByBulk/transfer/"+testId;
		    		 var inputData = { selectedIdTransferBulk: selectedIdTransferBulk}
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
});
