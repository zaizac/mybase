$(document).ready(function() {
	var oTableUserLst = $('#tblUserLst').DataTable({  
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
            "url": contextPath + "/user-list/paginated",
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
            	data.fullName = $("#fullName").val();
            	data.status = $('#status').find(":selected").val();
            	data.userRoleGroupCode = $('#userRoleGroupCode').find(":selected").val();
            },
            "error": function(){  // error handling
            	console.log("error");
            }
      	 }),
      	"initComplete": function(settings, json) {
      		$('input#fullName').unbind();
      		$('input#status').unbind();
      		$('#searchFilter').bind('click', function(e) {
            	portalUtil.showMainLoading(true);
            	oTableUserLst
            	.column(1).search($('input#fullName').val())
                .column(2).search($('#status').val())
                if($('#userRoleGroupCode') != undefined) oTableUserLst.column(3).search($('#userRoleGroupCode').val())
                oTableUserLst.draw();
            	$(".em-toggle").click();
            });
            $('#searchClear').bind('click', function(e) {
            	$('input#fullName').val("")
            	$("#status").select2("val", "");
            	$("#userRoleGroupCode").select2("val", "");
            	$(".em-toggle").click();
            	oTableUserLst.columns().search("").draw();
            });
           
        },
      	"columns": [
      	            { "data": "userId" },
      	            { "data": "fullName" },
      	            { "data": "userRoleGroupDesc" },
      	            { "data": "status", "searchable" : true, "orderable" : true, 
      	            	"render": function ( data, type, row ) {
      	            		if(data == "A") {
      	            			return "ACTIVE";
      	            		} else if(data == "F") {
								return "PENDING ACTIVATION";      	            			
      	            		}
      	            		return "INACTIVE";
      	            	}
      	            },
      	          ],
      	"fnDrawCallback": function ( oSettings ) { portalUtil.showMainLoading(false);} 
	});
	
	$('#tblUserLst').dataTable().fnSetFilteringEnterPress();
	$('#tblUserLst tbody').on( 'click', 'tr', function () {
        var d = oTableUserLst.row( this ).data();
        location.href = contextPath + "/user-list/" + d.userId;
    });
	$('#tblUserLst tbody').on( 'mouseover', 'tr', function () {
		var d = oTableUserLst.row( this ).data();
		if(d != undefined) { $(this).addClass('cursor-pointer'); }
    });
});