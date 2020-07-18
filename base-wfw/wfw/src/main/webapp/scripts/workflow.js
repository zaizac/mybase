/**
 * Copyright 2014 Bestinet Sdn Bhd
 * 
 * @author Md. Kamruzzaman
 */

function onChangeType() {
	var typeId = $('#cmbWfwType').val();
	var url = window.location.href;
	
	var urlParts = url.split('?typeId=');	
	url = urlParts[0] + '?typeId='+typeId;	
	window.location.href = url ;
	
}

function reset(){
	alert("test");
//	var url = window.location.href;
//	alert(url);
//	window.location.href = url + '/inbox';
}

function onChangeLevel() {
	var taskMasterId = $('#taskMasterId').val();
	var levelId = $('#levelid').val();	
	var url = window.location.href;
	var urlParts = url.split('?taskMasterId=');	
	url = urlParts[0] + '?taskMasterId='+taskMasterId + '&levelId='+levelId;	
	window.location.href = url ;
	
}

//function redirectURL(value) {
//	alert('test');
//	alert(value);
//}

function redirect(url) {
	window.location.href = contextPath + url;
}

function showHistoryDetails(id) {
	id = $(id).attr('id');
	var content = 'div'+id;		
	document.getElementById(content).style.display = '';
}

function hideHistoryDetails(id) {
	id = $(id).attr('id');
	id = id.replace('btn','div');	
	id = id.replace('img','div');	
	document.getElementById(id).style.display = 'none';
}

function showRemarks() {	
	document.getElementById('divRemarks').style.display = '';
}

function hideRemarks() {	
	document.getElementById('divRemarks').style.display = 'none';
}


jQuery(document).ready(function() {
	jQuery(".kp_nbr").keypress(function(e){if(e.which!=8 && e.which!=0 && (e.which<48 || e.which>57)){return false;}});			
	jQuery( "#applDate" ).datepicker({changeMonth: true, changeYear: true, dateFormat: 'dd/mm/yy', minDate: new Date(2000, 1 - 1, 1), maxDate: new Date()});
	    
});

jQuery(document).ready(function() {
	$('#reportData').DataTable({
        "dom": '<"top"lf>rt<"bottom"p><"clear">',
        "fnDrawCallback": function ( oSettings ) {processRowNum(oSettings)},
    	"aoColumnDefs": [{ "bSortable": false, "aTargets": [ 0 ] }],
    	"aaSorting": [[ 1, 'asc' ]]
    });
});

function initReportDetail() {
	$('#reportDetailData').DataTable({
	    "dom": '<"top"lf>rt<"bottom"p><"clear">',
	    "fnDrawCallback": function ( oSettings ) {processRowNum(oSettings)},
		"aoColumnDefs": [{ "bSortable": false, "aTargets": [ 0 ] }],
		"aaSorting": [[ 1, 'asc' ]]
	});
}

function processRowNum(oSettings) {
	if ( oSettings.bSorted || oSettings.bFiltered ) {
		for ( var i=0, iLen=oSettings.aiDisplay.length ; i<iLen ; i++ ) {
			$('td:eq(0)', oSettings.aoData[ oSettings.aiDisplay[i] ].nTr ).html( i+1 );
		}
	}
}

var appUtil = (function() {
    return {
        showMainLoading: function(isShow) {
            if ((typeof isShow) === "undefined") {
                isShow = true;
            }
            if (isShow) {
                $('#main_loading').attr('style', "display: block !important");
            } else {
                $('#main_loading').attr('style', "display: none !important");
            }
        }
    }
})();