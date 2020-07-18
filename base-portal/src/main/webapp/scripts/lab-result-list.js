var trLink = true;
$(document).ready(function() {

	var start = moment("2020-04-01");
    var end = moment();

    function cb(start, end) {
        if(start) $('input[name="createDtFrom"]').val(start.format('DD/MM/YYYY'));
        if(end) $('input[name="createDtTo"]').val(end.format('DD/MM/YYYY'));
    }

    $('#createDtRange').daterangepicker({
    	minDate: moment("2019-01-01"),
        startDate: start,
        endDate: end,
        minYear: 2019,
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
    
	
	// DataTable for listing page
    var oTablePatientLst = $('#tblBulkUpdateList').DataTable({  
    	"processing": true,
	    "serverSide": true,
	    'responsive': true,
	    'destroy' : true,
	    "columns": [
	    		{ "data": null, "searchable" : false, "orderable" : false },
	    		{ "data": "bulkRefNo" },
	    		{ "data": "fileName" },
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
  	            { "data": "totalRecord"},
            	/*{ "data": "totalSuccess"},
            	{ "data": "totalFail"},*/
  	            { "data": "status"},
	  	        { "data": null, "searchable" : false, "orderable" : false, 
	  	            	"render": function ( data, type, row ) {
	  	            		
	  	            		if(row.status == 'COMPLETED (UNSUCCESSFUL)') {
	  	            			test=  test ='<center><a title="View" href=' + contextPath + '/labResultLst/details/'+ row.bulkFileId +' ><i class="fa fa-eye fa-lg"></i></a></center>';
	  	            			
	  	            		}else{
	  	            			test = '<center>-</center>';
	  	            		}
	  	            		return test;
	  	            	}
	  	         }
  	          ],
		"ajax": $.fn.dataTable.pipeline({
			"pages" : 1,
        	"type" : "GET",
            "url": contextPath + "/labResultLst/paginated",
            "action": 'xhttp',
            'beforeSend': dtRequestHeader,
            "dataSrc": dtDataSrc,
            "data": function ( data ) {
            	/*data.fileName = $("#fileName").val();*/
            	data.bulkRefNo = $("#bulkRefNo").val();
            	data.status = $('#status').find(":selected").val();
            	data.createDtFrom = $('input[name="createDtFrom"]').val();
            	data.createDtTo = $('input[name="createDtTo"]').val();
            },
            "error": function(){  // error handling
            }
      	 }),
      	"initComplete": function(settings, json) {
      		$('input#fileName').unbind();
      		$('input#bulkRefNo').unbind();
      		$('input#status').unbind();
      		$('#searchFilter').bind('click', function(e) {
            	portalUtil.showMainLoading(true);
            	oTablePatientLst
            	/*.column(1).search($('input#fileName').val())*/
            	.column(1).search($('input[name="createDtFrom"]').val())
                .column(1).search($('input[name="createDtTo"]').val())
            	.column(2).search($('input#bulkRefNo').val())
            	.column(3).search($('#status').val())
                oTablePatientLst.draw();
            	$(".em-toggle").click();
            });
            $('#searchClear').bind('click', function(e) {
            	/*$('input#fileName').val("")*/
            	$('input#bulkRefNo').val("")
            	$("#status").select2("val", "");
            	$('input[name="createDtFrom"]').val("")
            	$('input[name="createDtTo"]').val("")
            	$('#select2-status-container').text("- All -")
            	$("#status").val("");
  

            	oTablePatientLst.columns().search("").draw();
            	$(".em-toggle").click();
            });
           
        },
      	"fnDrawCallback": function ( oSettings ) {processRowNum(oSettings); hidePagination(this,"#tblBulkUpdateList");portalUtil.showMainLoading(false);} 
	});
    jQuery('#tblBulkUpdateList tbody').on('click', 'tr a', function() {
		trLink = false;
	});
    
	$('#tblBulkUpdateList').dataTable().fnSetFilteringEnterPress();
	
	
	// subDataTable for details page
    var oTablePatientLst2 = $('#tblBulkUpdateListDtl').DataTable({  
    	"processing": true,
	    "serverSide": true,
	    'responsive': true,
	    'destroy' : true,
	    "columns": [
	    		{ "data": null, "searchable" : false, "orderable" : false },
	    		{ "data": "idType"},
	    		{ "data": "nationalId",
	    			"render": function(data, type, row){
	    				return data || " ";
	    			}
	    		
	    		},
  	          	{ "data": "firstName",
  	          		"render": function(data, type, row){
    				return data || " ";
  	          		}
  	          	},
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
  	            { "data": "samplingDesc",
  	            	"render": function (data, type, row) { 
            			return data || "N/A";
	            	}
  	            
  	            
  	            },
  	           
  	          { "data": "result",
  	            	"render": function (data, type, row) { 
            			return data || " ";
	            	}
  	          },
  	          { "data": "requesterDesc",
  	        	"render": function (data, type, row) { 
        			return data || " ";
            	}
  	          },
  	          { "data": "sampleName",
  	        	"render": function (data, type, row) { 
        			return data || " ";
            	}
  	          },
  	          { "data": "testResultDt",
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
  	            { "data": "labName",
  	            	"render": function (data, type, row) { 
            			return data || " ";
	            	}
  	            },
  	            { "data": "specimen",
  	            	"render": function (data, type, row) { 
            			return data || " ";
	            	}
  	            },
  	            { "data": "typeTestDesc",
  	            	"render": function (data, type, row) { 
            			return data || " ";
	            	}
  	            },
  	            { "data": "remarks",
  	            	"render": function (data, type, row) { 
            			return data || " ";
	            	}
  	            }
  	          ],
		"ajax": $.fn.dataTable.pipeline({
			"pages" : 1,
        	"type" : "GET",
            "url": contextPath + "/labResultLst/patientDetails/paginated",
            "action": 'xhttp',
            'beforeSend': dtRequestHeader,
            "dataSrc": dtDataSrc,
            "data": function ( data ) {
            	data.bulkFileId = $("#bulkFileId").val();
            },
            "error": function(){  // error handling
            }
      	 }),
       	"initComplete": function(settings, json) {
      		$('#searchFilter').bind('click', function(e) {
            	portalUtil.showMainLoading(true);
            	oTablePatientLst2
            	.column(0).search($('input#bulkFileId').val())
            	
                oTablePatientLst2.draw();
            	$(".em-toggle").click();
            });
            $('#searchClear').bind('click', function(e) {
            	$('input#bulkFileId').val("")
            	oTablePatientLst2.columns().search("").draw();
            	$(".em-toggle").click();
            });
           
        },
      	 

      	"fnDrawCallback": function ( oSettings ) {processRowNum(oSettings); hidePagination(this,"#tblBulkUpdateListDtl");portalUtil.showMainLoading(false);} 
	});
    jQuery('#tblBulkUpdateListDtl tbody').on('click', 'tr a', function() {
		trLink = false;
	});
    
	$('#tblBulkUpdateListDtl').dataTable().fnSetFilteringEnterPress();
	
});
