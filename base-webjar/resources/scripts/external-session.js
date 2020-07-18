var notifyBefore = 60; 
var cntr=0; 
var t = 0;
var c=0;

function continueSession(sessionAlive) {
	clearTimeout(t);
	bootbox.hideAll();
	loadSession(sessionAlive);
}

function closeSession() {
	bootbox.hideAll();
	window.location.href = getContextPath() + "/logout-process";
}

function loadSession(sessionAlive) {
	setTimeout(function() {
		c = 0; 
		bootbox.dialog({
			closeButton : false,
			message : prop.sessionContent,
			title : "<i class=\"fa fa-clock-o fa-7x fa-fw\"></i>"+prop.sessionTitle,
			className : "session-popup",
			callback: function() {
				$('.modal-header .session-popup').css('padding-bottom', '40px');
				$('.modal-header').css('padding-bottom','40px');
			},
			buttons : {
				confirm : {
					label : prop.btnContinue,
					className : "btn-success",
					callback : function() {
						continueSession(sessionAlive);
					}
				},
				cancel : {
					label : prop.btnLogout,
					className : "btn-default",
					callback : function() {
						closeSession();
					}
				}
			}
		});
		$('.modal-header').css('padding-bottom', '40px !important');
		$('.modal-header').css('padding-bottom', '40px');
		$('#timer').html(notifyBefore); 
		startCount();
	}, (sessionAlive - notifyBefore) * 300);
}			

function startCount() {
	countdown();
}

function countdown() {
    c++;
   	var remaining_time = notifyBefore - c;
   	$('#timer').html(remaining_time);
   	if( remaining_time == 0 ){
   		closeSession()
	} else {
    	t = setTimeout(function(){countdown()}, 1000);
	}
}

