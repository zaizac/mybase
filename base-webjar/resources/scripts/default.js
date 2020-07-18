$(document).on('keyup', '.input-amount', function() {
    var x = $(this).val();
    $(this).val(x.toString().replace(/,/g, "").replace(/\B(?=(\d{3})+(?!\d))/g, ","));
});

$(document).on('keypress', '.input-amount', function(e) {
	if(e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)){return false;}
});

$(document).on('keyup', '.format-number', function() {
    var x = $(this).val();
    $(this).val(x.toString().replace(/,/g, "").replace(/\B(?=(\d{3})+(?!\d))/g, ","));
});

$(document).on('keypress', '.limit-number', function(e) {
	if(e.which != 8 && e.which != 0 && ((e.which < 48 && e.which != 46) || e.which > 57 )){return false;}
});

function documentEmbed(inputUrl, objectId) {
	setTimeout(function(){
		$.ajax({
			async : false,
			global: false,
			headers: { 'X-CSRF-Token': csrf_token },
			type: "GET", 
			action: 'xhttp',
			url: inputUrl, 
			success : function(data) {
				var doc = data;
				var srcObj = contextPath + "/webjars/monter-ui/images/no_image.jpg";
				var mimeType = "image/png";
				if(doc.content != undefined) {
					srcObj = 'data:' + doc.contentType + ';base64,' + doc.content;
					mimeType = doc.contentType;
				} else if(doc.reportBytes != undefined) {
					srcObj = 'data:' + doc.mimeType + ';base64,' + doc.reportBytes;
					mimeType = doc.mimeType;
				} else if(doc.imageContent != undefined) {
					srcObj = 'data:' + doc.contentType + ';base64,' + doc.imageContent;
					mimeType = doc.contentType;
				}
				var clsObj = jQuery("."+objectId);
				var clsAtr = "id";
				if(clsObj.length > 0) {
					clsAtr = 'class';
				} else {
					clsObj = jQuery("#"+objectId);
				}
				var object = '<object ' + clsAtr + '="' + objectId + '" style="width: 200px; height: 250px; line-height: 330px;" ' +
				' data="' +  srcObj + '" ' +
				' type="' + mimeType + '" >' +
				'<embed ' +
				'  src="' + srcObj + '" ' +
				'  type="' + mimeType + '" ' + 
				'  width="100%" ' +
				'  height="500px" />' +
				'</object>';
				clsObj.replaceWith(object);
			}
		});
	}, 1000);
}

function documentPopup(url, title, type, method) {
	portalUtil.showMainLoading(true);
    setTimeout(function(){
    	var inputUrl = contextPath;
    	if(type=='aform' || type=='aslip') {
    		inputUrl = inputUrl + "/" + url
    		if(type=='aform') {
    			jQuery("#titleText").html(prop.lblRegFrm);
    		}
    		else if(type=='aslip') {
    			jQuery("#titleText").html(prop.lblAsgSlip);
    		}
    	} else{
    		inputUrl = inputUrl + "/" + url
    	}
    	
    	$.ajax({
    		async : false,
    		global: false,
    		headers: { 'X-CSRF-Token': csrf_token },
    		type: "GET", 
    		action: 'xhttp',
    		url: inputUrl, 
    		success : function(data) {
    			var doc = data;
    			jQuery("#rptTtl").html(title);
    			var srcObj = contextPath + "/webjars/monter-ui/images/no_image.jpg";
    			var mimeType = "image/png";
    			if(doc.content != undefined) {
    				srcObj = 'data:' + doc.contentType + ';base64,' + doc.content;
    				mimeType = doc.contentType;
    			} else if(doc.reportBytes != undefined) {
    				srcObj = 'data:' + doc.mimeType + ';base64,' + doc.reportBytes;
    				mimeType = doc.mimeType;
    			} else if(doc.imageContent != undefined) {
					srcObj = 'data:' + doc.contentType + ';base64,' + doc.imageContent;
					mimeType = doc.contentType;
    			}
    			var object = '<object id="pdfUrl" width="100%" height="500px"' +
    			' data="' +  srcObj + '" ' +
    			' type="' + mimeType + '" >' +
    			'<embed ' +
    			'  src="' + srcObj + '" ' +
    			'  type="' + mimeType + '" ' + 
    			'  width="100%" ' +
    			'  height="500px" />' +
    			'</object>';
    			jQuery("#pdfUrl").replaceWith(object);
    			jQuery("#rptDialog" ).modal( "show" );
    		}
    	}).done(function() {
    		portalUtil.showMainLoading(false);
    	});
    }, 1000);
}

/** For smart search; need to add attr data-link to combobox **/
var link = $(".advance-select").data("link");
$(".advance-select").select2({
	  ajax: {
	    url: link,
	    dataType: 'json',
	    delay: 250,
	    processResults:function(data){
	    	return { results:data }
	    }
	  },
	  escapeMarkup: function (markup) { return markup; }, // let our custom formatter work
	  templateResult: formatRepo,
	  templateSelection: formatRepoSelection
});


function formatRepo (repo) {
    if (repo.loading) return repo.text;
    if($("#selected_usr").length > 0 ){
    	if(repo.value === $("#selected_usr").text())
    		return;
    }
    return "<option value='"+repo.id+"'>"+repo.value+"</option>";
}

function formatRepoSelection (repo) {
    return repo.text || repo.value;
}

function generateCsv(url){
	window.open(url, "_blank").blur();
	window.focus();
}
	  
function getCookie(cname) {
    var name = cname + "=";
    var decodedCookie = decodeURIComponent(document.cookie);
    var ca = decodedCookie.split(';');
    for(var i = 0; i <ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) == ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) == 0) {
            return c.substring(name.length, c.length);
        }
    }
    return "";
}	

$.extend( $.fn.dataTable.defaults, {
	"dom": '<"top"l>rt<"bottom"ip><"clear">',
	"lengthChange" : true,
    "lengthMenu": [[10, 15, 25, 50, 100], [10, 15, 25, 50, 100]],
    "aaSorting": [],
    "language": {
    	"processing": "<img style='width:40px; height:40px;' src='" + contextPath + "/webjars/monster-ui/images/loading.gif' />",
		"paginate": {"next": prop.dtbNext, "previous": prop.dtbPrevious },
	    "info": prop.dtbShowingPage + " " + "_PAGE_" +" "+ "" + prop.dtbOf + " " + " _PAGES_",
	    "infoEmpty": "",
	    "lengthMenu": prop.dtbDisplay + " _MENU_ " + prop.dtbRecord,
	    "zeroRecords": "No Record Found"
	},
});