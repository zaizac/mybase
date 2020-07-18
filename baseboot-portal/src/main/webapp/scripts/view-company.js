$(document).ready(function() {
	var oTableUserLst = $('#tblViewCompLst').DataTable({  
		"dom": '<"top"i>tr<"bottom"p><"clear">', 
		"processing": true,
        "serverSide": true,
        'responsive': true,
        'destroy' : true,
        'lengthChange': false,
        "language": {
        	"processing": "<img style='width:20px; height:20px;' src='" + contextPath + "/webjars/joblottery-webjar/images/loading.gif' />",
			"paginate": {"next": prop.dtbNext, "previous": prop.dtbPrevious },
		    "info": prop.dtbShowingPage + " " + "_PAGE_" +" "+ "" + prop.dtbOf + " " + " _PAGES_",
		    "infoEmpty": ""
		},
		"ajax": $.fn.dataTable.pipeline({
			"pages" : 1,
        	"type" : "GET",
            "url": contextPath + "/job-seeker/viewCompanyList/paginated",
            "action": 'xhttp',
            'beforeSend': function (request) {
                request.setRequestHeader("X-CSRF-Token", csrf_token);
                request.setRequestHeader("Content-Type", "application/json;charset=utf-8");
            },
            "dataSrc": function ( json ) {
            	var json = jQuery.parseJSON( json );
            	return json.data;
            },
            "data": function ( data ) {
            	data.companyName = $("#companyName").val();
            	data.cmpnyRegNo = $("#cmpnyRegNo").val();
            	data.status = $('#country').find(":selected").val();
            	data.userRoleGroupCode = $('#userRoleGroupCode').find(":selected").val();
            },
            "error": function(){  // error handling
            	console.log("error");
            }
      	 }),
      	"initComplete": function(settings, json) {
      		$('input#companyName').unbind();
      		$('input#cmpnyRegNo').unbind();
      		$('input#country').unbind();
      		$('#searchFilter').bind('click', function(e) {
            	portalUtil.showMainLoading(true);
            	oTableUserLst
            	.column(1).search($('input#companyName').val())
            	.column(1).search($('input#cmpnyRegNo').val())
                .column(2).search($('#country').val())
                if($('#userRoleGroupCode') != undefined) oTableUserLst.column(3).search($('#userRoleGroupCode').val())
                oTableUserLst.draw();
            	$(".em-toggle").click();
            });
            $('#searchClear').bind('click', function(e) {
            	$('input#companyName').val("")
            	$('input#cmpnyRegNo').val("")
            	$("#country").select2("val", "");
            	$("#userRoleGroupCode").select2("val", "");
            	$(".em-toggle").click();
            	oTableUserLst.columns().search("").draw();
            });
           
        },
      	"columns": [
      	            { "data": "userId" },
      	            { "data": "companyName" },
      	            { "data": "cmpnyRegNo" },
      	            { "data": "userRoleGroupDesc" },
      	            { "data": "country", "searchable" : true, "orderable" : true, },
      	            
      	            
      	            
      	            
      	    /*        { "data": "status", "searchable" : true, "orderable" : true, 
      	            	"render": function ( data, type, row ) {
      	            		if(data == "A") {
      	            			return "ACTIVE";
      	            		} else if(data == "F") {
								return "PENDING ACTIVATION";      	            			
      	            		}
      	            		return "INACTIVE";
      	            	}
      	            },*/
      	          ],
      	"fnDrawCallback": function ( oSettings ) { portalUtil.showMainLoading(false);} 
	});
	
	$('#tblViewCompLst tbody').on( 'mouseover', 'tr', function () {
		var d = oTableUserLst.row( this ).data();
		if(d != undefined) { $(this).addClass('cursor-pointer'); }
    });
	
	dataTableCardInit('#view_comp_table');
});


$('#tblViewCompLst tbody').on('click', 'tr', function () {
	//console.log(rowId);
	var orderId = $(this).find('td:eq(3)').text();
	if(orderId != undefined || orderId != null || orderId != '') {
		location.href = contextPath + "/job-seeker/viewCompanyDetails/" +orderId;
	}
	});