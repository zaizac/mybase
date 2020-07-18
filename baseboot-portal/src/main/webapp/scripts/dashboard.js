$(document).ready(function() {
	$('#tblJobOpLst').dataTable({
		dom : '<"top"i>tr<"bottom"p><"clear">',
		scrollY: "400px",
        scrollCollapse: true,
        fnDrawCallback: function ( oSettings ) {
            $(oSettings.nTHead).hide();
        }
	});

});