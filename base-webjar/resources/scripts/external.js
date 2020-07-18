var csrf_token = jQuery("input[name=_csrf]").val();

var trLink = true;

$(window).bind('beforeunload', function() {
	portalUtil.showMainLoading(true);
});

// Disabled right click
$(document).bind('contextmenu', function (e) { e.preventDefault(); });

//Drawback for daterangepicker
/*
 * $(function(){ if (!Modernizr.inputtypes.date) { // If not native HTML5
 * support, fallback to jQuery datePicker $('input[type=date]').daterangepicker({ //
 * Consistent format with the HTML5 picker dateFormat : 'yy-mm-dd' }, //
 * Localization $.daterangepicker.regional['it'] ); } });
 */

/**
 * Datatable - Row number generation
 * 
 * @param oSettings
 * @returns
 */
function processRowNum(oSettings, col) {
	var no=oSettings._iDisplayStart;
	if( oSettings.aiDisplay.length > 0 ) {
		for ( var i=0, iLen=oSettings.aiDisplay.length ; i<iLen ; i++ ) {
			no=no+1;
			if(col) {
				$('td:eq(' + col +')', oSettings.aoData[ oSettings.aiDisplay[i] ].nTr ).html( no );
			} else {
				$('td:eq(0)', oSettings.aoData[ oSettings.aiDisplay[i] ].nTr ).html( no );
			}
		}
	}
}

/**
 * Datatable - Hides pagination
 * 
 * @param e
 * @param id
 * @returns
 */
function hidePagination(e, id) {
	if (e.api().data().length == 0) {
		if($(id + '_paginate')) $(id + '_paginate').css("display", "none");
		if($(id + '_info')) $(id + '_info').css("display", "none");
		if($(id + '_length')) $(id + '_length').css("display", "none");
	} else {
		if($(id + '_paginate')) $(id + '_paginate').removeAttr("style");
		if($(id + '_info')) $(id + '_info').removeAttr("style");
		if($(id + '_length')) $(id + '_length').removeAttr("style");
	}
}

function dtRequestHeader (request) {
    request.setRequestHeader("X-CSRF-Token", csrf_token);
    request.setRequestHeader("Content-Type", "application/json;charset=utf-8");
}

function dtDataSrc ( json ) {
	var result = jQuery.parseJSON( json );
	return result.data;
}

/**
 * Datatable - Initialize Table Card
 * 
 * @param id
 * @returns
 */
function dataTableCardInit(id) {
	$(id).dataTable({
		dom : '<"top"i>tr<"bottom"p><"clear">',
		scrollY: "400px",
        scrollCollapse: true,
		oLanguage : {
			"sEmptyTable" : "No data available",
			"sLoadingRecords" : "Please wait - loading...",
			"sProcessing" : "DataTables is currently busy",
			"sZeroRecords" : "No records to display"
		},
		fnDrawCallback : function(oSettings) {
			hidePagination(this, id);
			$(oSettings.nTHead).hide();
		}
	});
}

/**
 * Utility
 */
var portalUtil = (function() {
	return {
		showMainLoading : function(isShow) {
			if ((typeof isShow) === "undefined") {
				isShow = true;
			}
			if (isShow) {
				$('#overlay_message')
						.attr('style', "display: block !important");
			} else {
				$('#overlay_message').attr('style', "display: none !important");
			}
		},
		showMessage : function(message, type, callback, title) {
			bootbox.dialog({
				closeButton : false,
				message : message,
				title : title,
				className : "message-modal " + type,
				buttons : {
					confirm : {
						label : "Ok",
						className : "btn-secondary",
						callback : function() {
							if (callback)
								callback();
						}
					}
				}
			});
		},
		showPopup : function(title, content, fullscreen) {
			if(fullscreen) {
				title = title + '<i class="fa fa-arrows-alt cursor-pointer pull-right" onclick="$(\'.customPopup\').toggleClass(\'fullscreen\');" />';
			}
			bootbox.dialog({
				message : content,
				title : title,
				className : "customPopup",
				backdrop: true
			});
		},
		showSuccess : function(message, callback) {
			message = message || 'This operation succeeds';
			bootbox.dialog({
				closeButton : false,
				message : message,
				title : "<i class=\"fa\"></i> SUCCESS",
				className : "message-modal success",
				buttons : {
					confirm : {
						label : "Ok",
						className : "btn-success",
						callback : function() {
							if (callback) callback();
						}
					}
				}
			});
		},
		showError : function(message, callback) {
			message = message || 'This operation is failed';
			bootbox.dialog({
				closeButton : false,
				message : message,
				/*title : "Error <i class=\"fa\"></i> ",*/
				title : "Error",
				className : "message-modal error",
				buttons : {
					confirm : {
						label : "Ok",
						className : "btn-danger",
						callback : function() {
							if (callback) callback();
						}
					}
				}
			});
		},
		showWarn : function(message, callback) {
			message = message || 'This operation is a warning.';
			bootbox.dialog({
				closeButton : false,
				message : message,
				title : "<i class=\"fa\"></i> Warning",
				className : "message-modal warning",
				buttons : {
					confirm : {
						label : "Ok",
						className : "btn-warning",
						callback : function() {
							if (callback) callback();
						}
					}
				}
			});
		},
		showInfo : function(message, callback) {
			message = message || 'For your information.';
			bootbox.dialog({
				closeButton : false,
				message : message,
				title : "<i class=\"fa\"></i> Information",
				className : "message-modal info",
				buttons : {
					confirm : {
						label : "Ok",
						className : "btn-info",
						callback : function() {
							if (callback) callback();
						}
					}
				}
			});
		},
		showConfirm : function(message, callback) {
			message = message || 'Are you sure?';
			bootbox.dialog({
				closeButton : false,
				message : message,
				title : "<i class=\"glyphicon\"></i>"+prop.confModal,
				className : "message-modal confirm",
				buttons : {
					cancel : {
						label : prop.btnNo,
						className : "btn-default",
						callback : function() {
							if (callback) callback(false);
						}
					},
					confirm : {
						label : prop.btnYes,
						className : "btn-info",
						callback : function() {
							if (callback) callback(true);
						}
					}
				}
			});
		}
	}
})();

function getContextPath() {
	return window.contextPath;
}

function langSelect(lang) {
	var url = window.location.href;
	if (url.indexOf("lang=") > 0) {
		url = url.substr(0, url.indexOf('lang=') - 1);
	}
	var separator = (url.indexOf("?") === -1) ? "?" : "&";
	var newParam = separator + "lang=" + lang;
	if ((location.hash == null || location.hash == '') && url.indexOf("#") > 0) {
		url = url.replace('#', '') + newParam;
	}
	window.location.href = url + newParam;
}

function redirectURL(value) {
	location.href = getContextPath() + value;
}

function refresh() {
	location.reload();
}

function numberWithCommas(x) {
	return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

function validateEmailAddress(email) {
	var emailReg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;
	return emailReg.test(email);
}

function starRating(opt) {
	if (Number(opt.id) || opt.id == '0') {
		return $('<span>' + getStar(opt.id) + " - " + $(opt.element).text() + '</span>');
	}               
	return  $('<span>' + $(opt.element).text() + '</span>');	
}

function getStar(id) {
	var star = {full: 'fa-star', half: 'fa-star-half-full', none: 'fa-star-o'}
	var rating = "";
	if(id == 0) {
		for(let i=0;5 > i;i++) {
			rating = rating +'<span class="fa ' + star.none + ' fa-lg"></span>';
		}
	} else {
		for(let i=0;5 > i;i++) {
			var rate = '';
			if(id > i) {
				if(Number.isInteger(Number(id))) {
					rate = star.full;
				} else {
					var number = id > 0 ? Math.floor(id) : Math.ceil(id);
					if(number > i ) {
						rate = star.full;
					} else {
						rate = star.half;
					}
				}
			} else {
				rate = star.none;
			}
			rating = rating +'<span class="fa ' + rate +' fa-lg"></span>';
		}
	}
	
	return rating;
}

function documentPopup(url, title, type, method) {
	portalUtil.showMainLoading(true);
	setTimeout(
		function() {
			var inputUrl = getContextPath();
			inputUrl = inputUrl + "/" + url
			inputUrl = encodeURI(inputUrl);
			$.ajax({
				async : false,
				global : false,
				headers : {
					'X-CSRF-Token' : csrf_token
				},
				type : "GET",
				action : 'xhttp',
				url : inputUrl,
				success : function(data) {
					var doc = data;
					var srcObj = getContextPath() + "/webjars/monster-ui/images/no_image.jpg";
					var mimeType = "image/png";
					if (doc.content != undefined) {
						srcObj = 'data:' + doc.contentType + ';base64,' + doc.content;
						mimeType = doc.contentType;
					} else if (doc.reportBytes != undefined) {
						srcObj = 'data:' + doc.mimeType + ';base64,' + doc.reportBytes;
						mimeType = doc.mimeType;
					}
					var object = '<div style="background: transparent url(' + getContextPath() + '/webjars/monster-ui/images/loading.gif) no-repeat center;height: 100% !important;">'
						+ '<object width="100%" height="500px"'
						+ ' data="'	+ srcObj + '" '
						+ ' type="' + mimeType+ '" >'
						+ '<param value="' + srcObj + '" name="src"/>'
						+ '<param value="transparent" name="wmode"/>'
						+ '</object></div>';
					
					if(mimeType == 'application/pdf') {
						title = '<i class="fa fa-file-pdf-o fa-fw fa2x" /> '+ title;
					} else {
						title = '<i class="fa fa-file-image-o fa-fw fa2x" /> '+ title;
					}
					portalUtil.showPopup(title, object, true)
				}
			}).done(function() {
				portalUtil.showMainLoading(false);
			});
		}, 1000);

}

var userAgent = navigator.userAgent.toLowerCase(),
initialDate = new Date(),

$document = $(document),
$window = $(window),
$html = $("html"),

isDesktop = $html.hasClass("desktop"),
isIE = userAgent.indexOf("msie") != -1 ? parseInt(userAgent.split("msie")[1]) : userAgent.indexOf("trident") != -1 ? 11 : userAgent.indexOf("edge") != -1 ? 12 : false,
isMobile = /Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent),
isTouch = "ontouchstart" in window,
onloadCaptchaCallback, 
plugins = {
	    pointerEvents: isIE < 11 ? "js/pointer-events.min.js" : false,
	    	    bootstrapTooltip: $("[data-toggle='tooltip']"),
	    	    bootstrapModalDialog: $('.modal'),
	    	    bootstrapTabs: $(".tabs-custom-init"),
	    	    rdNavbar: $(".rd-navbar"),
	    	    rdParallax: $(".rd-parallax"),
	    	    rdGoogleMaps: $(".rd-google-map"),
	    	    rdMailForm: $(".rd-mailform"),
	    	    rdInputLabel: $(".form-label"),
	    	    regula: $("[data-constraints]"),
	    	    owl: $(".owl-carousel"),
	    	    swiper: $(".swiper-slider"),
	    	    search: $(".rd-search"),
	    	    searchResults: $('.rd-search-results'),
	    	    statefulButton: $('.btn-stateful'),
	    	    isotope: $(".isotope"),
	    	    popover: $('[data-toggle="popover"]'),
	    	    viewAnimate: $('.view-animate'),
	    	    photoSwipeGallery: $("[data-photo-swipe-item]"),
	    	    radio: $("input[type='radio']"),
	    	    checkbox: $("input[type='checkbox']"),
	    	    customToggle: $("[data-custom-toggle]"),
	    	    facebookWidget: $('#fb-root'),
	    	    counter: $(".counter"),
	    	    progressLinear: $(".progress-linear"),
	    	    circleProgress: $(".progress-bar-circle"),
	    	    dateCountdown: $('.DateCountdown'),
	    	    pageLoader: $(".page-loader"),
	    	    captcha: $('.recaptcha'),
	    	    scroller: $(".scroll-wrap"),
	    	    flickrfeed: $(".flickr"),
	    	    stepper: $("input[type='number']"),
	    	    selectFilter: $("select"),
	    	    slick: $('.slick-slider'),
	    	    twitterfeed: $(".twitter"),
	    	    eventPost: $(".event-post-variant-1")
	    	  };

/**
 * Initialize All Scripts
 */
$document.ready(function () {
	$(document).on(
			'keyup',
			'.input-amount',
			function() {
				var x = $(this).val();
				$(this).val(
						x.toString().replace(/,/g, "").replace(
								/\B(?=(\d{3})+(?!\d))/g, ","));
			});

	$(document).on('keypress', '.input-amount', function(e) {
		if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)) {
			return false;
		}
	});

	$(document).on(
			'keyup',
			'.format-number',
			function() {
				var x = $(this).val();
				$(this).val(
						x.toString().replace(/,/g, "").replace(
								/\B(?=(\d{3})+(?!\d))/g, ","));
			});

	$(document).on(
			'keypress',
			'.limit-number',
			function(e) {
				if (e.which != 8 && e.which != 0
						&& ((e.which < 48 && e.which != 46) || e.which > 57)) {
					return false;
				}
			});
	
	/**
	* Select2
	* @description Enables select2 plugin
	*/
	if (plugins.selectFilter.length) {
	    var i;
	    for (i = 0; i < plugins.selectFilter.length; i++) {
	      var select = $(plugins.selectFilter[i]);

	      select.select2({
	        theme: "bootstrap"
	      }).next().addClass(select.attr("class").match(/(input-sm)|(input-lg)|($)/i).toString().replace(new RegExp(",", 'g'), " "));
	    }
	  }
	
	/* Widget Toggle*/
	$('.widget .tools').each (function (i) {
	      var accordion = $(this)
	          , toggle = accordion.find ('.widget-toggle')
	          , activePanel = accordion.find ('.widget-body').parent ()

	      activePanel.addClass ('show')

	      toggle.on ('click', function (e) {
	        var panel = $(this).parents ('.widget .shell').children(".widget-body");
	    	var panelfooter = $(this).parents ('.widget .shell').children(".widget-footer");
	        if(panel.is(":hidden")) {
	        	panel.show()
	        	if(panelfooter.length != 0) panelfooter.show()
	        	toggle.html('<i class="fa fa-chevron-up"></i>')
	        } else {
	        	panel.hide();
	        	if(panelfooter.length != 0) panelfooter.hide()
	        	toggle.html('<i class="fa fa-chevron-down"></i>')
	        }
	      });
	});
	
	/* Mobile International Input Initialization */
	$(".input-contact").each (function (i) {
		var contact = $(this);
		var hiddenInput = contact.attr("name");
		contact.removeAttr("name");
		contact.intlTelInput({
			// allowDropdown: false,
			// autoHideDialCode: false,
			// autoPlaceholder: "off",
			// dropdownContainer: "body",
			// excludeCountries: ["us"],
			formatOnDisplay: true,
			geoIpLookup: function(callback) {
			   $.get("http://ipinfo.io", function() {}, "jsonp").always(function(resp) {
			     var countryCode = (resp && resp.country) ? resp.country : "";
			     callback(countryCode);
			   });
			},
			hiddenInput: hiddenInput,
			// initialCountry: "auto",
			// nationalMode: false,
			// onlyCountries: ['us', 'my'],
			//placeholderNumberType: "MOBILE",
			preferredCountries: ['my', 'id', 'bd'],
			separateDialCode: true,
			utilsScript: getContextPath() + "/webjars/monster-ui/scripts/jquery.intl-tel/js/utils.js"
		});
	});
	
	/**
	 * Initialize component to load lazily
	 */
	$(".fileupload-img-thumbnail-view").lazy({
		placeholder: "data:image/gif;base64,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",
		effect: "fadeIn",
		effectTime: 2000,
	});
	
	$(".otp-new").otp();
	$(".captcha").captcha();
	$(".bst-star-o").html(getStar(0));
	$(".bst-star-half").html(getStar(0.5));
	$(".bst-star-one").html(getStar(1));
	$(".bst-star-half-one").html(getStar(1.5));
	$(".bst-star-two").html(getStar(2));
	$(".bst-star-half-two").html(getStar(2.5));
	$(".bst-star-three").html(getStar(3));
	$(".bst-star-half-three").html(getStar(3.5));
	$(".bst-star-four").html(getStar(4));
	$(".bst-star-half-four").html(getStar(4.5));
	$(".bst-star-five").html(getStar(5));
	
	var $content     = $('#content'),
	$sidebar         = $('#sidebar'),
	$sidebarBtn      = $('#sidebar-btn'),
	$toggleCol       = $('body').add($content).add($sidebarBtn),
	sidebarIsVisible = false;

	$sidebarBtn.on('click', function() {
		if (!sidebarIsVisible) {
			bindContent();
		} else {
			unbindContent();
		}
	
		toggleMenu();
	});
	
	$sidebar.on('click', function() {
		if (!sidebarIsVisible) {
			bindContent();
		} else {
			unbindContent();
		}
	
		toggleMenu();
	});
	
	function bindContent() {
		$content.on('click', function() {
			toggleMenu();
			unbindContent();
		});
	}
	
	function unbindContent() {
		$content.off();
	}
	
	function toggleMenu() {
		$toggleCol.toggleClass('sidebar-show');
		$sidebar.toggleClass('show');
	
		if (!sidebarIsVisible) {
			sidebarIsVisible = true;
		} else {
			sidebarIsVisible = false;
		}
	}
	
});
