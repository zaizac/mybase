$(document).ready(function() {
	$(window).keydown(function(event){
	    if(event.keyCode == 13) {
	      event.preventDefault();
	      return false;
	    }
	  });
	
	var oTableUserLst = $('#tblUserLst').DataTable({
		"processing": true,
	    "serverSide": true,
	    'responsive': true,
	    'destroy' : true,
	    "columns": [
	    		{ "data": null, "orderable" : false },
	            { "data": "userId" },
	            { "data": "firstName" },
	            { "data": "lastName" },
	            { "data": "userRoleGroup.userGroupDesc" },
	            { "data": "status", "orderable" : true, 
	            	"render": function ( data, type, row ) {
	            		var status = "-", statusDesc;
  	            		if(data) {
  	            			var color = "badge-primary";
  	            			if(data == 'A') {
  	            				color = "badge-success"; 
  	            				statusDesc = "ACTIVE";
  	            			} else if(data == 'F') {
  	            				color = "badge-warning";
  	            				statusDesc = "PENDING ACTIVATION";  
  	            			} else  {
  	            				color = "badge-danger";
  	            				statusDesc = "DEACTIVATED";
  	            			}
  	            			status = "<span class='badge badge-secondary " + color + "'>" + statusDesc + "</span>";
  	            		}
            			return status;
	            	}
	            },
	          ],
		"ajax": $.fn.dataTable.pipeline({
			"pages" : 1,
        	"type" : "GET",
            "url": contextPath + "/user-list/paginated",
            "action": 'xhttp',
            'beforeSend': dtRequestHeader,
            "dataSrc": dtDataSrc,
            "data": function ( data ) {
            	data.firstName = $("#firstName").val();
            	data.lastName = $("#lastName").val();
            	data.status = $('#status').find(":selected").val();
            	data.userRoleGroupCode = $('#userRoleGroupCode').find(":selected").val();
            },
            "error": function(){  // error handling
            	console.log("error");
            }
      	 }),
      	"initComplete": function(settings, json) {
      		$('input#firstName').unbind();
      		$('input#lastName').unbind();
      		$('input#status').unbind();
      		$('#searchFilter').bind('click', function(e) {
            	portalUtil.showMainLoading(true);
            	oTableUserLst
            	.column(1).search($('input#firstName').val())
            	.column(2).search($('input#lastName').val())
                .column(4).search($('#status').find(":selected").val())
                if($('#userRoleGroupCode') != undefined) {
                	oTableUserLst.column(3).search($('#userRoleGroupCode').find(":selected").val())
                }
                oTableUserLst.draw();
            	$(".em-toggle").click();
            });
            $('#searchClear').bind('click', function(e) {
            	$('input#firstName').val("")
            	$('input#lastName').val("")
            	$("#status").val("").select2();
            	$("#userRoleGroupCode").val("").select2();
            	oTableUserLst.columns().search("").draw();
            	$(".em-toggle").click();
            });
           
        },
      	
      	"fnDrawCallback": function ( oSettings ) { processRowNum(oSettings); hidePagination(this,"#tblUserLst");portalUtil.showMainLoading(false);} 
	});
	
	$('#tblUserLst').dataTable().fnSetFilteringEnterPress();
	$('#tblUserLst tbody').on( 'click', 'tr', function () {
        var d = oTableUserLst.row( this ).data();
        location.href = contextPath + "/user-list/user?id=" + d.userId;
    });
	$('#tblUserLst tbody').on( 'mouseover', 'tr', function () {
		var d = oTableUserLst.row( this ).data();
		if(d != undefined) { $(this).addClass('cursor-pointer'); }
    });

	$('#dobPckr').daterangepicker({
	    singleDatePicker: true,
	    showDropdowns: true,
	    endDate : new Date(),
	    maxDate: new Date(),
	    minYear: 1901,
	    maxYear: parseInt(moment().format('YYYY'), 15),
	    locale: { format: "DD/MM/YYYY" },
	}, function(start, end, label) {
		$('input[name="dob"]').val(start.format('DD/MM/YYYY'))
	});
	
	$("#userRoleGroupCode").change(function(){
		var urgc = $('#userRoleGroupCode').attr('class');
		if(!urgc.includes('urgSrch')) {
			var code = $('#userRoleGroupCode').find(":selected").val();
			if(code) {			
				var inputURL = contextPath + '/user-list/userGroup/child/' + code
				var inputData = {}
				portalUtil.showMainLoading(true);
				$.ajax({
					headers : {
						'X-CSRF-Token' : csrf_token
					},
					type: "POST", 
					url: inputURL, 
					data: inputData,
				}).done(function(data) {
					if(data.selCountry) {
						$("#stateDrpdwn").attr("style", null);
					} else {
						$("#stateCd").val(null);
						$("#stateDrpdwn").attr("style", "display:none");
					}
					
					if(data.selBranch) {
						$("#branchDrpdwn").attr("style", "");
					} else {
						$("#userGroupRoleBranchCd").val(null);
						$("#branchDrpdwn").attr("style", "display:none");
					}
					portalUtil.showMainLoading(false);
				});
			}
		}
	});
	
});

function checkUserIdExists(userId) {
    var uid = $(userId).val();
    var inputURL = contextPath + '/user-list/checkUserIdExists'
    var inputData = { userId: uid}
    
    $.ajax({
		headers : {
			'X-CSRF-Token' : csrf_token
		},
		type: "GET", 
		url: inputURL, 
		 data: inputData,
	}).done(function(data) {
		document.getElementById("userId").disabled = true;
	});
		
}

function displayPassword() {
	  var x = document.getElementById("password");
	  if (x.type === "password") {
	    x.type = "text";
	  } else {
	    x.type = "password";
	  }
	}