$(document).ready(function() {
});

function viewCompanyInfo(obj) {

	var emContent = "";
	var rowId = $(obj).attr('id');
	var name = $("#cmpnyName"+rowId).html();
	if (!name) {
		var y = document.getElementById("cmpnyInfo");
		y.style.display = "none";
		var x = document.getElementById("emailInfo");
		x.style.display = "block";
		
		emContent = $("#emailTemplate" + rowId).val();
		$('#simpleSpan').html(emContent);
		
	} else {
		var x = document.getElementById("emailInfo");
		x.style.display = "none";
		var y = document.getElementById("cmpnyInfo");
		y.style.display = "block";

		var cmpnyLogo = document.getElementById("cmpnyLogo" + rowId);
		var cmpnyName = $("#cmpnyName"+rowId).html();
		var cmpnyOwner = $("#cmpnyOwner" + rowId).val();
		var cmpnyFullAddress = $("#cmpnyFullAddress" + rowId).val();
		var cmpnyRegNo = $("#cmpnyRegNo" + rowId).val();
		var cmpnyEmail = $("#cmpnyEmail" + rowId).val();
		var cmpnyUrl = $("#cmpnyUrl" + rowId).val();
		
		var logo = document.getElementById("cmpnyLogoInfo");
		logo.src = cmpnyLogo.src;
		$('#cmpnyLogoInfo').html(logo);
		$('#cmpnyNameInfo').html(cmpnyName);
		$('#cmpnyOwnerInfo').html(cmpnyOwner);
		$('#cmpnyFullAddressInfo').html(cmpnyFullAddress);
		$('#cmpnyRegNoInfo').html(cmpnyRegNo);
		$('#cmpnyEmailInfo').html(cmpnyEmail);
		$('#cmpnyUrlInfo').html(cmpnyUrl);
	}
 
}